package com.example.mrbutlerapplication.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mrbutlerapplication.*
import com.example.mrbutlerapplication.chats.LatestMessagesActivity
import com.example.mrbutlerapplication.model.HistoryCard
import com.google.firebase.auth.FirebaseAuth

class MenuPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)




    }

    fun accessChat(view: View?){
        var intent = Intent(this@MenuPage,LatestMessagesActivity::class.java)
        startActivity(intent)
    }


    fun accessProfile(view: View?){
        var intent = Intent(this@MenuPage,ButlerProfileCard::class.java)
        startActivity(intent)
    }

    fun accessBookings(view: View?){
        var intent = Intent(this@MenuPage,BookingsCard::class.java)
        startActivity(intent)
    }

    fun accessSalary(view: View?){
        var intent = Intent(this@MenuPage,SalaryPage::class.java)
        startActivity(intent)
    }

    fun accessHistory(view: View?){
        var intent = Intent(this@MenuPage,HistoryCard::class.java)
        startActivity(intent)
    }

    fun logout(view: View?){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}