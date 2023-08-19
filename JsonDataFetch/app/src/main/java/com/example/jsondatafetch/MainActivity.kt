package com.example.jsondatafetch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiCall()
    }

    private fun apiCall() {
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var apiInterface = retrofit.create(apiInterface::class.java)

        var responseee = apiInterface.users()

        responseee.enqueue(object :Callback<JsonArray>{
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("Response",response.toString())


                val jsonObject = response.body()?.get(0)?.asJsonObject
                val name = jsonObject?.get("name")?.asString

                Log.d("Dinkar ",name.toString())

                print("Name: " + name)

//                var i =0
//                while (i< response.body()?.size()!!){
//                    val jsonObject = response.body()?.get(i)?.asJsonObject
//                    val name = jsonObject?.get("name")?.asString
//                    print("Name: " + name)
//                    Log.d("Name : ",name.toString())
//
//                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("Dinkar Error",t.toString())
            }

        })
    }
}