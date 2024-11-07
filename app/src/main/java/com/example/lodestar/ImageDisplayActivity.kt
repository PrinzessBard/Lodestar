package com.example.lodestar

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageDisplayActivity : AppCompatActivity() {

    private lateinit var imageView1: ImageView // Кабинет директора Кабинет завуча лестница
//    private lateinit var imageView2: ImageView
    private lateinit var button: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        imageView1 = findViewById(R.id.imageView1)
//        imageView2 = findViewById(R.id.imageView2)
        button = findViewById(R.id.button2)

        // Получаем URL фотографии из Intent
        val level = intent.getStringExtra("LEVEL")
//
//        // Загружаем фотографию, если URL не пустой учительская музыка лестница
//        if (photoUrl != null) {
//            Glide.with(this)
//                .load("http://10.0.2.2:5000/photo1/")
//                .into(imageView)
//        }

//        clearOldPhotos("level_path_10.png")

        Glide.with(this)
            .load("http://95.107.48.233:34560/photo1")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView1)

        button.setOnClickListener{
            Glide.with(this)
                .load("http://95.107.48.233:34560/photo2")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView1)
        }

//        clearOldPhotos("level_path_20.png")
    }

    private fun clearOldPhotos(file: String) {
        val files = filesDir.listFiles { _, name -> name.startsWith(file) }
        files?.forEach { it.delete() }
    }
}
