package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.afinal.databinding.ActivityDashboardUserBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var container: FrameLayout

    private lateinit var homeFragment: UserFragment
    private lateinit var dashboardFragment: RatingsFragment
    private lateinit var bucketFragment: BucketFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        container = findViewById(R.id.flFragment)

        homeFragment = UserFragment()
        dashboardFragment = RatingsFragment()
        bucketFragment = BucketFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, homeFragment)
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .addToBackStack(null)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ratings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, dashboardFragment)
                        .addToBackStack(null)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bucket -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, bucketFragment)
                        .addToBackStack(null)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }


        binding.logOutBut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this@DashboardUserActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            binding.subtitleTv.text = "Not Login in"
        }
        else{
            val email = firebaseUser.email
            binding.subtitleTv.text = email
        }
    }
}