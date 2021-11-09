package com.example.mrbutlerapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mrbutlerapplication.authentication.EmailAdds
import com.example.mrbutlerapplication.authentication.LoginPage
import com.example.mrbutlerapplication.controller.MenuPage
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    //initialize control variables
    private lateinit var username1: TextView
    private lateinit var username2: TextView
    private lateinit var username3: TextView
    private lateinit var username4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set control variables
        username1 = findViewById(R.id.username_1)
        username2 = findViewById(R.id.username_2)
        username3 = findViewById(R.id.username_3)
        username4 = findViewById(R.id.username_4)

        // checks if a user is currently logged in
        if (FirebaseAuth.getInstance().uid != null){
            val intent = Intent(this, MenuPage::class.java)
            startActivity(intent)
        }

    }

    fun login(view: View){
        //initializes Email Address class to access employee email addresses
        var user = EmailAdds()
        when{
            view.resources.getResourceEntryName(view.id) == "loginBtn1" -> switchActivity(username1.text.toString(), user.butler1)
            view.resources.getResourceEntryName(view.id) == "loginBtn2" -> switchActivity(username2.text.toString(), user.butler2)
            view.resources.getResourceEntryName(view.id) == "loginBtn3" -> switchActivity(username3.text.toString(), user.butler3)
            view.resources.getResourceEntryName(view.id) == "loginBtn4" -> switchActivity(username4.text.toString(), user.butler4)
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


