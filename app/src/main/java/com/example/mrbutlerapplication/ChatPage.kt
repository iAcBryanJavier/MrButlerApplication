package com.example.mrbutlerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

private lateinit var toolbar:Toolbar
private lateinit var viewPager: ViewPager
private lateinit var tabLayout: TabLayout

class ChatPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_page)

        toolbar = findViewById(R.id.toolbar)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        toolbar.setTitle("Chats")
        setSupportActionBar(toolbar)

        val fragmentAdapter = ChatPageAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)




    }
}