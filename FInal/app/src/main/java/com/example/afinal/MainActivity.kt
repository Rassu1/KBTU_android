package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var button_sig : Button

    private lateinit var button_reg : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_sig = findViewById(R.id.sign_in_button)

        button_sig.setOnClickListener {
            val intent = Intent(this@MainActivity, activity_sign_in::class.java)
            startActivity(intent)
        }

        button_reg = findViewById(R.id.registr_button)

        button_reg.setOnClickListener {
            val intent = Intent(this@MainActivity, activity_register::class.java)
            startActivity(intent)
        }
    }

}