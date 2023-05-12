package com.example.a4assigment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4assigment.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var data = ArrayList<Products>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        getAllProducts()
    }

    private fun getAllProducts() {

        val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

        retrofit.getAllProducts().enqueue(object : retrofit2.Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                try{
                    val responseBody = response.body()!!
                    data = responseBody.products
                    val adapter = ProductAdapter(data) { id ->
                        // todo something
                        val intent = Intent(this@MainActivity, Addbook::class.java)
                        intent.putExtra("Id", id)
                        startActivity(intent)
                    }
                    binding.recyclerview.adapter = adapter

                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                }
            }


            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }


        })
    }
}