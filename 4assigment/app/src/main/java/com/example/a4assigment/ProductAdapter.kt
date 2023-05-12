package com.example.a4assigment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(private val nList: List<Products>, private val onClick: (id: Int) -> Unit):RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = nList[position]

        holder.textView.setText(product.title)
        Picasso.get().load(product.images.get(0)).into(holder.imageView)
        holder.textView.setOnClickListener {
            product.id?.let { productID -> onClick(productID) }
        }
    }


    class ViewHolder(ItemView : View): RecyclerView.ViewHolder(ItemView) {

        val imageView: ImageView = ItemView.findViewById(R.id.imageview)
        val textView: TextView = ItemView.findViewById(R.id.textView)
    }

}