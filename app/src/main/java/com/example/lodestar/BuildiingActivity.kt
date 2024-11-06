package com.example.lodestar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildiingActivity: AppCompatActivity() {

    private lateinit var address: EditText;
    private lateinit var startRoom: EditText;
    private lateinit var startRoomLevel: EditText;
    private lateinit var endRoom: EditText;
    private lateinit var endRoomLevel: EditText;
    private lateinit var isUp: EditText;
    private lateinit var submitButton: Button;
    private lateinit var goToMain: TextView;
    private lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buildiing)

        address = findViewById(R.id.address)
        startRoom = findViewById(R.id.startRoom)
        startRoomLevel = findViewById(R.id.startRoomLevel)
        endRoom = findViewById(R.id.endRoom)
        endRoomLevel = findViewById(R.id.endRoomLevel)
        isUp = findViewById(R.id.isUp)
        goToMain = findViewById(R.id.link_to_main)
        submitButton = findViewById(R.id.button)

        // Настройка Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/upload/")  // Локальный сервер на эмуляторе Android
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        networkService = retrofit.create(NetworkService::class.java)

        // Обработка нажатия кнопки "Отправить"
        submitButton.setOnClickListener {
            val data = collectFormData()
            sendDataToServer(data)
        }
    }

    private fun collectFormData(): Map<String, String> {
        return mapOf(
            "address" to address.text.toString(),
            "start_room_name" to startRoom.text.toString(),
            "start_room_level" to startRoomLevel.text.toString(),
            "end_room_name" to endRoom.text.toString(),
            "end_room_level" to endRoomLevel.text.toString(),
            "isUp" to isUp.text.toString()

        )
    }

    private fun sendDataToServer(data: Map<String, String>) {
        val call = networkService.uploadData(data)
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful && response.body() != null) {
//                    val photoUrl = response.body()!!.photoUrl
                    val photoUrl = "http://10.0.2.2:5000/photo1"
                    openImageDisplayActivity(photoUrl)
                } else {
                    Toast.makeText(this@BuildiingActivity, "Ошибка сервера", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(this@BuildiingActivity, "Ошибка сети: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Переход к com.example.lodestar.ImageDisplayActivity с передачей URL фотографии
    private fun openImageDisplayActivity(photoUrl: String) {
        val intent = Intent(this, ImageDisplayActivity::class.java)
        intent.putExtra("PHOTO_URL", photoUrl)
        startActivity(intent)
    }
}
