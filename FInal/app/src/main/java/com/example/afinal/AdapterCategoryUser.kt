import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.ModelUserCategory
import com.example.afinal.PdfListAdminActivity
import com.example.afinal.R
import com.example.afinal.databinding.RowCategoryUserBinding

class AdapterCategoryUser(
    private val context: Context,
    private var categoryArrayList: ArrayList<ModelUserCategory>
) : RecyclerView.Adapter<AdapterCategoryUser.HolderCategoryUser>(), Filterable {

    private var filterlist: ArrayList<ModelUserCategory> = categoryArrayList

    inner class HolderCategoryUser(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTv: TextView = itemView.findViewById(R.id.categoryTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategoryUser {
        val binding =
            RowCategoryUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCategoryUser(binding.root)
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: HolderCategoryUser, position: Int) {
        val model = categoryArrayList[position]
        val id = model.id
        val category = model.category

        holder.categoryTv.text = category

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PdfListAdminActivity::class.java)
            intent.putExtra("categoryId", id)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val tempList = ArrayList<ModelUserCategory>()

                if (constraint.isNullOrBlank()) {
                    tempList.addAll(filterlist)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    for (item in filterlist) {
                        if (item.category.toLowerCase().contains(filterPattern)) {
                            tempList.add(item)
                        }
                    }
                }

                filterResults.values = tempList
                filterResults.count = tempList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                categoryArrayList.clear()
                if (results != null && results.count > 0) {
                    categoryArrayList.addAll(results.values as ArrayList<ModelUserCategory>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
