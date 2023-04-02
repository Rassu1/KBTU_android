package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinal.databinding.ActivityRegisterBinding

class ActivityRegister : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sigNoVer.setOnClickListener {
            startActivity(Intent(this, ActivitySignIn::class.java))
        }

        binding.registrButton.setOnClickListener {
            startActivity(Intent(this, ActivitySignIn::class.java))
        }
    }
}