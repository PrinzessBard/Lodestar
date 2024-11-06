package com.example.lodestar

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageDisplayActivity : AppCompatActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)

        // Получаем URL фотографии из Intent
//        val photoUrl = intent.getStringExtra("PHOTO_URL")
//
//        // Загружаем фотографию, если URL не пустой
//        if (photoUrl != null) {
//            Glide.with(this)
//                .load("http://10.0.2.2:5000/photo1/")
//                .into(imageView)
//        }

//        clearOldPhotos("level_path_10.png")

        Glide.with(this)
            .load("http://10.0.2.2:5000/photo1")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView1)

//        clearOldPhotos("level_path_20.png")

        Glide.with(this)
            .load("http://10.0.2.2:5000/photo2")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView2)
    }

    private fun clearOldPhotos(file: String) {
        val files = filesDir.listFiles { _, name -> name.startsWith(file) }
        files?.forEach { it.delete() }
    }
}
