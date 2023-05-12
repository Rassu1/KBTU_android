package com.example.afinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.databinding.ActivityBookAdminBinding
import com.example.afinal.databinding.AddBookBinding

class BookAdmin: AppCompatActivity() {

    private lateinit var binding: ActivityBookAdminBinding

    private var bookId: Long = -1

    private var book: String? = null

    private var category: String? = null

    private var description: String? = null

    private var image: String? = null

    private var price: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bookId = intent.getLongExtra("bookId", -1)

        book = intent.getStringExtra("book")

        category = intent.getStringExtra("category")

        description = intent.getStringExtra("description")

        image = intent.getStringExtra("image")

        price = intent.getStringExtra("price")
    }
}