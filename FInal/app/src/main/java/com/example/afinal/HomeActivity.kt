package com.example.afinal

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var container: FrameLayout

    private lateinit var homeFragment: MainFragment
    private lateinit var dashboardFragment: RatingsFragment
    private lateinit var notificationsFragment: BucketFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        container = findViewById(R.id.flFragment)

        homeFragment = MainFragment()
        dashboardFragment = RatingsFragment()
        notificationsFragment = BucketFragment()

        // По умолчанию выбран первый элемент навигации
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, homeFragment)
            .commit()

        // Установка обработчика нажатия элементов навигации
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ratings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, dashboardFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bucket -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, notificationsFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

    }
}