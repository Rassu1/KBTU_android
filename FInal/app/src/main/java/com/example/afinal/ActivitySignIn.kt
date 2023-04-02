package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinal.databinding.ActivitySignInBinding

class ActivitySignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regNoVer.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }

        binding.signInButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        }
}