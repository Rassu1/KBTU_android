
package com.example.afinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

class PdfAddActivity : AppCompatActivity() {

    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var categoryEt: EditText
    private lateinit var submitBtn: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.afinal.R.layout.activity_pdf_add)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Find views by ID
        titleEt = findViewById(R.id.titleEt)
        descriptionEt = findViewById(R.id.descriptionEt)
        categoryEt = findViewById(R.id.categoryEt)
        submitBtn = findViewById(R.id.submitBtn)

        // Set click listener for submit button
        submitBtn.setOnClickListener {
            // Get input values
            val title = titleEt.text.toString().trim()
            val description = descriptionEt.text.toString().trim()
            val category = categoryEt.text.toString().trim()

            // Validate input values
            if (title.isEmpty()) {
                titleEt.error = "Введите название книги"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                descriptionEt.error = "Введите описание книги"
                return@setOnClickListener
            }
            if (category.isEmpty()) {
                categoryEt.error = "Введите категорию книги"
                return@setOnClickListener
            }

            // Check if category exists in database
            database.child("Categories").child(category).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Category exists, save book data to database
                        val book = Book(title, description, category)
                        database.child("Categories").child(category).child("books").push().setValue(book)
                            .addOnSuccessListener {
                                // Book data saved successfully
                                finish()
                            }
                            .addOnFailureListener {
                                // Error occurred while saving book data
                                // Handle error here
                            }
                    } else {
                        // Category does not exist, show error message
                        categoryEt.error = "Категория не существует"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Error occurred while checking if category exists
                    // Handle error here
                }
            })
        }
    }
}

// Book data class
data class Book(val title: String, val description: String, val category: String)
