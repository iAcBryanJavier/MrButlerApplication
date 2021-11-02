package com.example.mrbutlerapplication.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.mrbutlerapplication.ButlerProfileCard
import com.example.mrbutlerapplication.R

class MenuPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)

        val  profile = findViewById<ImageView>(R.id.profile)

        profile.setOnClickListener{

            val Intent = Intent(this,ButlerProfileCard::class.java)
            startActivity(Intent)
        }




    }
}