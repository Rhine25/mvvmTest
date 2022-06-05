package com.example.testtechnique.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.testtechnique.R
import com.example.testtechnique.data.UsersLocalDataSource
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.data.db.AppDatabase
import com.example.testtechnique.domain.UserStateEvent
import com.example.testtechnique.domain.UserViewModel
import com.example.testtechnique.util.DataState

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.bt_login)
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "db_name"
        ).build()
        val dataSource = UsersLocalDataSource(db.userDao())
        val mapper = UserMapper()
        val usersRepository = UsersRepository(dataSource, mapper)
        userViewModel = UserViewModel(usersRepository)

        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()

            userViewModel.setStateEvent(UserStateEvent.GetUserEvent(email, password))
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        userViewModel.dataState.observe(this, Observer { dataState ->
            when(dataState) {
                is DataState.Success<UserModel> -> {
                    Toast.makeText(this, dataState.data.pseudo, Toast.LENGTH_LONG).show()
                }
                is DataState.Error -> {
                    Toast.makeText(this, "Unknown user", Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    //
                }
            }
        })
    }
}