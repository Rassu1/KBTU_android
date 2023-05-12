package com.example.a4assigment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddBook : AppCompatActivity(){

    private lateinit var binding: AddBookBinding

    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddBookBinding.inflate(layoutInflater)

        setContentView(binding.root)

        id = intent.getLongExtra("id", -1)

    }

}

