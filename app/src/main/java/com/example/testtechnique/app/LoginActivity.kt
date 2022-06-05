package com.example.testtechnique.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.testtechnique.R
import com.example.testtechnique.data.UsersLocalDataSource
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.data.db.AppDatabase
import com.example.testtechnique.data.db.UserDao
import com.example.testtechnique.util.DataState
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.bt_login)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()


            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "db_name"
            ).build()
            val dataSource = UsersLocalDataSource(db.userDao())
            val mapper = UserMapper()
            val usersRepository = UsersRepository(dataSource, mapper)

            thread {
                Looper.prepare()
                val dataState = usersRepository.getUser(email, password)
                when (dataState) {
                    is DataState.Success<UserModel> -> {
                        Toast.makeText(this, dataState.data.pseudo, Toast.LENGTH_LONG).show()
                    }
                    is DataState.Error -> {
                        Toast.makeText(this, "Unknown user", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


}