package com.example.afinal

import AdapterBucket
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.FragmentBucketBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BucketFragment : Fragment() {

    private lateinit var binding: FragmentBucketBinding
    private lateinit var bookArrayList: ArrayList<String>
    private lateinit var adapterBucket: AdapterBucket
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBucketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        bookArrayList = ArrayList()
        adapterBucket = AdapterBucket(requireContext(), bookArrayList)
        binding.categoriesRv.adapter = adapterBucket

        loadBooksFromBucket()

        binding.addCategoryBtn.setOnClickListener{
            val firebaseUser = firebaseAuth.currentUser

            val uid = firebaseUser?.uid

            val ref = FirebaseDatabase.getInstance().getReference("Users").child(uid.toString()).child("bucket")
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Оформлено", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {e->
                    Toast.makeText(context, "Unable to delete due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }


    }

    private fun loadBooksFromBucket() {
        val userId = firebaseAuth.currentUser?.uid
        val bucketRef = databaseReference.child(userId.toString()).child("bucket")

        bucketRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookArrayList.clear()
                for (ds in snapshot.children) {
                    val bookTitle = ds.getValue(String::class.java)
                    if (bookTitle != null) {
                        bookArrayList.add(bookTitle)
                    }
                }
                adapterBucket.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибки при отмене операции чтения из базы данных Firebase
            }
        })
    }


}
