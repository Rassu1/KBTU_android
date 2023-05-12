package com.example.afinal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.ActivityPdfListAdminBinding
import com.example.afinal.databinding.ActivitySignInBinding
import com.example.afinal.databinding.FragmentMainBinding
import com.example.afinal.databinding.FragmentRatingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RatingsFragment : Fragment() {

    private lateinit var binding: FragmentRatingsBinding
    private lateinit var bookArrayList: ArrayList<BookCategory>
    private lateinit var adapterBook: AdapterRatingBook
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBooks()
    }

    private fun loadBooks() {
        bookArrayList = ArrayList()
        adapterBook = AdapterRatingBook(requireContext(), bookArrayList)

        val ref = FirebaseDatabase.getInstance().getReference("Library")

        val query = ref.orderByChild("rating").limitToLast(50)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookArrayList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(BookCategory::class.java)

                    adapterBook.bookArrayList.add(model!!)
                }

                adapterBook.bookArrayList.reverse()
                binding.booksRv.adapter = adapterBook
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}




