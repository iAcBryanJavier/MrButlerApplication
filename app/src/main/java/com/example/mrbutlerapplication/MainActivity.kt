package com.example.mrbutlerapplication

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mrbutlerapplication.authentication.LoginPage
class MainActivity : AppCompatActivity() {

    //initialize control variables
    private lateinit var username1: TextView
    private lateinit var username2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set control variables
        username1 = findViewById(R.id.username_1)
        username2 = findViewById(R.id.username_2)

    }

    fun login(view: View){
        //displays the view id (for testing purposes only)

        Toast.makeText( this,"View ID: ${view.resources.getResourceEntryName(view.id)}", Toast.LENGTH_SHORT).show()
        when{
            view.resources.getResourceEntryName(view.id) == "loginBtn1" -> switchActivity(username1.text.toString(), username1.hint.toString())
            view.resources.getResourceEntryName(view.id) == "loginBtn2" -> switchActivity(username2.text.toString(), username2.hint.toString())
        }
    }

    private fun switchActivity(username: String, email: String){
        var intent = Intent(this@MainActivity, LoginPage::class.java).apply {
            putExtra("USERNAME", username)
            putExtra("EMAIL", email)
        }
        startActivity(intent)
    }



}


