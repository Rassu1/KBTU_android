package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.afinal.databinding.ActivityDashboardAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var container: FrameLayout

    private lateinit var homeFragment: MainFragment
    private lateinit var dashboardFragment: RatingsFragment
    private lateinit var notificationsFragment: BucketFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logOutBut.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        container = findViewById(R.id.flFragment)

        homeFragment = MainFragment()
        dashboardFragment = RatingsFragment()
        notificationsFragment = BucketFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, homeFragment)
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .addToBackStack(null) // Добавляем транзакцию в стек возврата
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ratings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, dashboardFragment)
                        .addToBackStack(null) // Добавляем транзакцию в стек возврата
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bucket -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, notificationsFragment)
                        .addToBackStack(null) // Добавляем транзакцию в стек возврата
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this@DashboardAdminActivity, MainActivity::class.java))
            finish()
        }
        else{
            val email = firebaseUser.email
            binding.subtitleTv.text = email
        }
    }
}