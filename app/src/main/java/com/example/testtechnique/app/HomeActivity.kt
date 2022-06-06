package com.example.testtechnique.app

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.testtechnique.R
import com.example.testtechnique.data.EstablishmentsLocalDataSource
import com.example.testtechnique.data.EstablishmentsRepository
import com.example.testtechnique.data.UsersLocalDataSource
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.data.db.AppDatabase
import com.example.testtechnique.domain.HomeStateEvent
import com.example.testtechnique.domain.HomeViewModel
import com.example.testtechnique.util.DataState

class HomeActivity : AppCompatActivity(), EstablishmentAdapter.EstablishmentItemListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: EstablishmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val owner = intent.getIntExtra("OWNER", 0)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "db_name"
        ).build()
        val establishmentDataSource = EstablishmentsLocalDataSource(db.establishmentDao())
        val establishmentMapper = EstablishmentMapper()
        val establishmentsRepository = EstablishmentsRepository(establishmentDataSource, establishmentMapper)
        val userDataSource = UsersLocalDataSource(db.userDao())
        val userMapper = UserMapper()
        val usersRepository = UsersRepository(userDataSource, userMapper)
        homeViewModel = HomeViewModel(establishmentsRepository, usersRepository)

        setupRecyclerView()
        subscribeObservers()

        homeViewModel.setStateEvent(HomeStateEvent.GetUserEvent(owner))
        homeViewModel.setStateEvent(HomeStateEvent.GetEstablishmentsEvent(owner))
    }

    private fun subscribeObservers() {
        homeViewModel.establishmentDataState.observe(this) { dataState ->
            when(dataState) {
                is DataState.Success<List<EstablishmentModel>> -> {
                    populateRecyclerView(dataState.data)
                }
                is DataState.Error -> {
                    Toast.makeText(this, "Error ${dataState.exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        homeViewModel.userDataState.observe(this) { dataState ->
            when(dataState) {
                is DataState.Success<UserModel> -> {
                    findViewById<TextView>(R.id.tv_hi).text = "Hi ${dataState.data.pseudo}! Here are your establishments:"
                }
                is DataState.Error -> {
                    findViewById<TextView>(R.id.tv_hi).text = "Hi! Here are your establishments:"
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = EstablishmentAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_establishments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun populateRecyclerView(establishments: List<EstablishmentModel>) {
        if (establishments.isNotEmpty()) adapter.setItems(ArrayList(establishments))
    }

    override fun onClickedEstablishment(establishmentName: String) {
        Toast.makeText(this, establishmentName, Toast.LENGTH_SHORT).show()
    }
}