package com.example.lodestar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.BufferedReader
import java.io.InputStreamReader

class ImageDisplayActivity : AppCompatActivity() {

    private lateinit var btnNext: Button;
    private lateinit var btnReload: Button;
    private lateinit var imageMaps: ImageView
    private lateinit var inputRoom: EditText

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
        setContentView(R.layout.activity_image_display)

        btnNext = findViewById(R.id.button2)
        btnReload = findViewById(R.id.button)
        imageMaps = findViewById(R.id.imageView1)
        inputRoom = findViewById(R.id.editText1)

        reloadNullMaps()

//        val data = getDataFromFile()
//        btnNext.text = data

        btnNext.setOnClickListener {
//            getPhoto("photo2")
            reloadNullMaps()
        }

        btnReload.setOnClickListener {
            endRoom = inputRoom.text.toString()
            val data = getDataFromFile()
            val arr = data.split("\n")
            address = arr[0]
            startRoom = arr[1]
            coordinates = arr[2]
            isLadder = arr[3]

            toProcessing()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) getPhoto("photo1")
    }

    private fun reloadNullMaps() {
        val data = getDataFromFile()
        val arr = data.split("\n")
        address = arr[0]
        startRoom = arr[1]
        coordinates = arr[2]
        isLadder = arr[3]
        endRoom = arr[1]

        toProcessing()
    }

    private fun getPhoto(photo: String) {
        Glide.with(this)
            .load("http://62.113.103.41:35366/$photo")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageMaps)
    }

    private fun toProcessing() {
        val intent = Intent(this@ImageDisplayActivity, ProcessingDataImage::class.java)
        intent.putExtra("address", address)
        intent.putExtra("startRoom", startRoom)
        intent.putExtra("endRoom", endRoom)
        intent.putExtra("coordinates", coordinates)
        intent.putExtra("isLadder", isLadder)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    private fun getDataFromFile(): String {
        val sb = StringBuilder()
        openFileInput("data.txt").use { fis ->
            InputStreamReader(fis).use { isr ->
                BufferedReader(isr).use { br ->
                    var line: String?
                    while ((br.readLine().also { line = it }) != null) sb.append(line)
                        .append('\n')
                }
            }
        }
        val content = sb.toString()
        return content
    }
}