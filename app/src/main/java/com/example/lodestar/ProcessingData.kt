package com.example.lodestar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProcessingData : AppCompatActivity() {

    private lateinit var networkService: NetworkService
    private lateinit var address: String
    private lateinit var startRoom: String
    private lateinit var endRoom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_processing_data)

        val arguments = intent.extras

        if (arguments != null) {
            address = arguments.getString("address").toString()
            startRoom = arguments.getString("startRoom").toString()
            endRoom = arguments.getString("endRoom").toString()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://62.113.103.41:35366/upload/")  // Локальный сервер на эмуляторе Android
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        networkService = retrofit.create(NetworkService::class.java)

        val data = collectFormData()
        sendDataToServer(data)
    }

    private fun collectFormData(): Map<String, String> {
        return mapOf(
            "address" to address,
            "start_room_name" to startRoom,
            "end_room_name" to endRoom,
        )
    }

    private fun sendDataToServer(data: Map<String, String>) {
        val call = networkService.uploadData(data)
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    returnResult()
                } else {
                    Toast.makeText(this@ProcessingData, "Ошибка сервера", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(this@ProcessingData, "Ошибка сети: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun returnResult() {
        val resultIntent = Intent(this@ProcessingData, MainActivity::class.java).apply {
            putExtra("Test", "Hello, Egor!")
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}