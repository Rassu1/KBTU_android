package com.example.afinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.databinding.RowPdfAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdapterBook : RecyclerView.Adapter<AdapterBook.HolderCategory> {

    private val context: Context
    var bookArrayList: ArrayList<BookCategory>
    private var bookFilterList: ArrayList<BookCategory>

    private lateinit var binding: RowPdfAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth


    constructor(context: Context, bookArrayList: ArrayList<BookCategory>) {
        this.context = context
        this.bookArrayList = bookArrayList
        this.bookFilterList = bookArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBook.HolderCategory {
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {
        val model = bookArrayList[position]
        val title = model.title
        val id = model.id
        val description = model.description
        val image = model.image
        val price = model.price
        val category = model.category
        val rating = model.rating

        holder.ratingTv.text = rating.toString()
        holder.bookTv.text = title


        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser

        val uid = firebaseUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("Users").child(uid.toString()).child("userType")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userType = snapshot.value as? String

                if (userType == "user") {
                    holder.itemView.setOnClickListener {
                        val intent = Intent(context, AddBookUser::class.java)
                        intent.putExtra("book Id", id)
                        intent.putExtra("book", title)
                        intent.putExtra("description", description)
                        intent.putExtra("image", image)
                        intent.putExtra("price", price)
                        intent.putExtra("category", category)
                        intent.putExtra("rating", rating)
                        intent.putExtra("uid", uid)
                        context.startActivity(intent)
                    }
                } else if (userType == "admin") {
                    holder.itemView.setOnClickListener {

                    val intent = Intent(context, AddBook::class.java)
                    intent.putExtra("book Id", id)
                    intent.putExtra("book", title)
                    intent.putExtra("description", description)
                    intent.putExtra("image", image)
                    intent.putExtra("price", price)
                    intent.putExtra("category", category)
                    intent.putExtra("rating", rating)
                    context.startActivity(intent)    }            }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибки при отмене операции чтения из базы данных Firebase
            }

        })





    }

    override fun getItemCount(): Int {
        return bookArrayList.size
    }

    inner class HolderCategory(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var bookTv: TextView = binding.bookTv
        var ratingTv: TextView = binding.ratingTv
    }

    fun setFilteredList(list: ArrayList<BookCategory>) {
        bookArrayList = list
        notifyDataSetChanged()
    }
}
