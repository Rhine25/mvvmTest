package com.example.testtechnique.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.testtechnique.R
import com.example.testtechnique.data.UsersLocalDataSource
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.data.db.AppDatabase
import com.example.testtechnique.domain.LoginStateEvent
import com.example.testtechnique.domain.LoginViewModel
import com.example.testtechnique.util.DataState

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var loginViewModel: LoginViewModel

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
        loginViewModel = LoginViewModel(usersRepository)

        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()

            loginViewModel.setStateEvent(LoginStateEvent.GetLoginEvent(email, password))
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        loginViewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<UserModel> -> {
                    Toast.makeText(this, dataState.data.pseudo, Toast.LENGTH_LONG).show()
                    intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("OWNER", dataState.data.id)
                    startActivity(intent)
                }
                is DataState.Error -> {
                    Toast.makeText(this, "Unknown user", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}