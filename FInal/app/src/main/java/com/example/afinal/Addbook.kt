package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.afinal.databinding.ActivityDashboardUserBinding
import com.example.afinal.databinding.ActivityPdfListAdminBinding
import com.example.afinal.databinding.AddBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddBook : AppCompatActivity(){

    private lateinit var binding: AddBookBinding

    private var bookId: Long = -1

    private var book: String? = null

    private var category: String? = null

    private var description: String? = null

    private var image: String? = null

    private var price: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddBookBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bookId = intent.getLongExtra("bookId", -1)

        book = intent.getStringExtra("book")

        category = intent.getStringExtra("category")

        description = intent.getStringExtra("description")

        image = intent.getStringExtra("image")

        price = intent.getStringExtra("price")


        binding.deleteBtn.setOnClickListener {

            FirebaseDatabase.getInstance().getReference("Categories").child(category.toString()).child("books").child(book.toString()).removeValue()
            FirebaseDatabase.getInstance().getReference("Library").child(book.toString()).removeValue()
            val intent = Intent(this@AddBook, DashboardAdminActivity::class.java)
            startActivity(intent)
        }

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, BookAdmin::class.java)
            intent.putExtra("bookId", bookId)
            intent.putExtra("book", book)
            intent.putExtra("category", category)
            intent.putExtra("description", description)
            intent.putExtra("image", image)
            intent.putExtra("price", price)
            startActivity(intent)
        }


        binding.titleTv.text = book
        binding.multilineText.text = description
        binding.priceTv.text = price

        Glide.with(this)
            .load(image)
            .into(binding.picIv)

    }



}