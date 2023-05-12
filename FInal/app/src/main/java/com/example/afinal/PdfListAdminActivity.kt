package com.example.afinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afinal.databinding.ActivityPdfListAdminBinding
import com.example.afinal.databinding.ActivitySignInBinding
import com.example.afinal.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PdfListAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfListAdminBinding
    private lateinit var bookArrayList: ArrayList<BookCategory>
    private lateinit var adapterBook: AdapterBook
    private lateinit var firebaseAuth: FirebaseAuth

    private var categoryId: Long = -1
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        categoryId = intent.getLongExtra("categoryId", -1)
        category = intent.getStringExtra("category")

        binding.subtitleTv.text = category

        loadBooks()
    }

    private fun loadBooks() {
        bookArrayList = ArrayList()
        adapterBook = AdapterBook(this@PdfListAdminActivity, bookArrayList)

        val ref = FirebaseDatabase.getInstance().getReference("Categories").child(category.toString()).child("books")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookArrayList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(BookCategory::class.java)

                    adapterBook.bookArrayList.add(model!!)
                }

                binding.booksRv.adapter = adapterBook
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}




