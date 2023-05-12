package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.afinal.databinding.ActivityAddBookUserBinding
import com.example.afinal.databinding.ActivityDashboardUserBinding
import com.example.afinal.databinding.ActivityPdfListAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddBookUser : AppCompatActivity(){

    private lateinit var binding: ActivityAddBookUserBinding

    private var bookId: Long = -1

    private var book: String? = null

    private var category: String? = null

    private var description: String? = null

    private var image: String? = null

    private var price: String? = null

    private var uid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBookUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bookId = intent.getLongExtra("bookId", -1)

        book = intent.getStringExtra("book")

        category = intent.getStringExtra("category")

        description = intent.getStringExtra("description")

        image = intent.getStringExtra("image")

        price = intent.getStringExtra("price")

        uid = intent.getStringExtra("uid")



        binding.editBtn.setOnClickListener {

            val timestamp = System.currentTimeMillis()

            var ref = FirebaseDatabase.getInstance().getReference("Users")

            ref.child(uid.toString()).child("bucket").child(timestamp.toString())
                .setValue(book)

        }

        binding.titleTv.text = book
        binding.multilineText.text = description
        binding.priceTv.text = price

        Glide.with(this)
            .load(image)
            .into(binding.picIv)

    }



}