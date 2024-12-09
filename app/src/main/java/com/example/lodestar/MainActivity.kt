package com.example.lodestar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var buttonMaps: Button
    private lateinit var buttonQr: Button
    private lateinit var test: TextView

    private lateinit var address: String
    private lateinit var startRoom: String
    private lateinit var endRoom: String
    private lateinit var coordinates: String
    private lateinit var isLadder: String

    companion object {
        private const val REQUEST_CODE_CAMERA = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        buttonMaps = findViewById(R.id.button_maps)
        buttonQr = findViewById(R.id.button_qr)
        test = findViewById(R.id.test)

        buttonMaps.setOnClickListener {
            val url = "https://www.google.ru/maps"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        buttonQr.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val arguments = intent.extras

        if (arguments != null) {
            val intent = Intent(this@MainActivity, ImageDisplayActivity::class.java)
            startActivity(intent)
        } else {
            if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
                val qrData = data?.getStringExtra("QR_RESULT") ?: ""
                val arr = qrData.split("\n")

                openFileOutput("data.txt", MODE_APPEND).use { fos ->
                    OutputStreamWriter(fos).use { osw ->
                        address = arr[0]
                        osw.write(arr[0])
                        osw.write("\n")
                        startRoom = arr[1]
                        endRoom = arr[1]
                        osw.write(arr[1])
                        osw.write("\n")
                        coordinates = arr[2]
                        osw.write(arr[2])
                        osw.write("\n")
                        isLadder = arr[3]
                        osw.write(arr[3])
                    }
                }

                toProcessing()
            }
        }
    }

    private fun toProcessing() {
        val intent = Intent(this@MainActivity, ProcessingData::class.java)
        intent.putExtra("address", address)
        intent.putExtra("startRoom", startRoom)
        intent.putExtra("endRoom", endRoom)
        intent.putExtra("coordinates", coordinates)
        intent.putExtra("isLadder", isLadder)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }
}