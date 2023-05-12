package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.afinal.databinding.ActivityDashboardUserBinding
import com.example.afinal.databinding.ActivityPdfListAdminBinding
import com.example.afinal.databinding.AddBookBinding
import com.example.afinal.databinding.AddBookRatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class AddBookRatings : AppCompatActivity(){

    private lateinit var binding: AddBookRatingBinding

    private var bookId: Long = -1

    private var book: String? = null

    private var category: String? = null

    private var description: String? = null

    private var image: String? = null

    private var price: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddBookRatingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bookId = intent.getLongExtra("bookId", -1)

        book = intent.getStringExtra("book")

        category = intent.getStringExtra("category")

        description = intent.getStringExtra("description")

        val imageRef = FirebaseDatabase.getInstance().getReference("Library")
            .child(book.toString())
            .child("image")

        imageRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val imageUrl = dataSnapshot.getValue(String::class.java)
                if (imageUrl != null) {
                    Glide.with(this@AddBookRatings)
                        .load(imageUrl)
                        .into(binding.picIv)                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        price = intent.getStringExtra("price")


        binding.titleTv.text = book
        binding.multilineText.text = description
        binding.priceTv.text = price
        val ratingRef = FirebaseDatabase.getInstance().getReference("Library")
            .child(book.toString())
            .child("rating")

        ratingRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rating = dataSnapshot.getValue(Float::class.java)
                if (rating != null) {
                    binding.ratingBar.rating = rating
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })



    }



}