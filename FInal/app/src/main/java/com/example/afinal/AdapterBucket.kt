import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.databinding.RowPdfAdminBinding

class AdapterBucket(private val context: Context, private val bookArrayList: ArrayList<String>) :
    RecyclerView.Adapter<AdapterBucket.HolderCategory>() {

    private lateinit var binding: RowPdfAdminBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {
        val title = bookArrayList[position]
        holder.bookTv.text = title
    }

    override fun getItemCount(): Int {
        return bookArrayList.size
    }

    inner class HolderCategory(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTv: TextView = binding.bookTv
    }
}
