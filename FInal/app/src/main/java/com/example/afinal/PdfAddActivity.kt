package com.example.afinal
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class PdfAddActivity : AppCompatActivity() {

    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var priceEt: EditText
    private lateinit var categoryEt: EditText
    private lateinit var buttonChooseImage: Button
    private lateinit var submitBtn: Button
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private var mImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_add)

        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("images")

        titleEt = findViewById(R.id.titleEt)
        descriptionEt = findViewById(R.id.descriptionEt)
        priceEt = findViewById(R.id.priceEt)
        categoryEt = findViewById(R.id.categoryEt)
        buttonChooseImage = findViewById(R.id.buttonChooseImage)
        submitBtn = findViewById(R.id.submitBtn)

        buttonChooseImage.setOnClickListener {
            openFileChooser()
        }

        submitBtn.setOnClickListener {

            val title = titleEt.text.toString().trim()
            val description = descriptionEt.text.toString().trim()
            val price = priceEt.text.toString().trim()
            val category = categoryEt.text.toString().trim()

            if (title.isEmpty()) {
                titleEt.error = "Введите название книги"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                descriptionEt.error = "Введите описание книги"
                return@setOnClickListener
            }
            if (price.isEmpty()) {
                priceEt.error = "Введите цену книги"
                return@setOnClickListener
            }
            if (category.isEmpty()) {
                categoryEt.error = "Введите категорию книги"
                return@setOnClickListener
            }

            database.child("Categories").child(category)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val book = Book(title, description, price, category)
                            val bookRef =
                                database.child("Categories").child(category).child("books").push()
                            val bookId = bookRef.key.toString()
                            bookRef.setValue(book).addOnSuccessListener {
                                if (mImageUri != null) {
                                    val imageRef = storageRef.child("$bookId.jpg")
                                    val imageInputStream = contentResolver.openInputStream(mImageUri!!)
                                    val uploadTask = imageRef.putStream(imageInputStream!!)
                                        .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                                database.child("Categories").child(category)
                                                    .child("books").child(bookId)
                                                    .child("image").setValue(uri.toString())
                                                    .addOnSuccessListener {
                                                        Toast.makeText(this@PdfAddActivity, "Have image", Toast.LENGTH_SHORT).show()
                                                        finish()
                                                    }
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Toast.makeText(this@PdfAddActivity, "Fail upload to data storage", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    database.child("Categories").child(category)
                                        .child("books").child(bookId)
                                        .child("image").setValue("")
                                        .addOnSuccessListener {
                                            Toast.makeText(this@PdfAddActivity, "No image but uploaded", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                }
                            }.addOnFailureListener { exception ->
                                Toast.makeText(this@PdfAddActivity, "snapshot error", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            categoryEt.error = "Категория не найдена"
                            return
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@PdfAddActivity, "Database error", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.data != null
        ) {
            mImageUri = data.data
            // Set image preview
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, mImageUri)
            buttonChooseImage.text = "Изображение выбрано"
        }
    }
}

data class Book(val title: String, val description: String, val price: String, val category: String)