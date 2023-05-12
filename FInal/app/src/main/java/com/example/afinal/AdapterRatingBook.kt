package com.example.afinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.databinding.RowPdfAdminBinding

class AdapterRatingBook : RecyclerView.Adapter<AdapterRatingBook.HolderCategory> {

    private val context: Context
    var bookArrayList: ArrayList<BookCategory>
    private var bookFilterList: ArrayList<BookCategory>

    private lateinit var binding: RowPdfAdminBinding

    constructor(context: Context, bookArrayList: ArrayList<BookCategory>) {
        this.context = context
        this.bookArrayList = bookArrayList
        this.bookFilterList = bookArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRatingBook.HolderCategory {
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

        holder.bookTv.text = title
        holder.ratingTv.text = rating.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddBookRatings::class.java)
            intent.putExtra("book Id", id)
            intent.putExtra("book", title)
            intent.putExtra("description", description)
            intent.putExtra("image", image)
            intent.putExtra("price", price)
            intent.putExtra("category", category)
            intent.putExtra("rating", rating)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookArrayList.size
    }

    inner class HolderCategory(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var bookTv: TextView = binding.bookTv
        var ratingTv: TextView = binding.ratingTv
    }

    fun setFilteredList(list: ArrayList<BookCategory>) {
        bookArrayList = list
        notifyDataSetChanged()
    }
}
