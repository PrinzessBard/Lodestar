package com.example.lodestar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val maps = findViewById<Button>(R.id.maps)
        val building = findViewById<Button>(R.id.building)

        maps.setOnClickListener {
            val url = "https://www.google.ru/maps"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        building.setOnClickListener {
            val intent = Intent(this, BuildiingActivity::class.java)
            startActivity(intent)
        }
    }
}