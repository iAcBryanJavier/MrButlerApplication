package com.example.mrbutlerapplication.authentication

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mrbutlerapplication.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {

    //initialize control variables
    private lateinit var loginUser: TextView
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button
    private lateinit var emailHint: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        //setup firebase database
        val database = Firebase.database
        val userRef = database.getReference("Employees")

        //get data from MainActivity.kt
        val username: String? = intent.getStringExtra("USERNAME")
        val email: String? = intent.getStringExtra("EMAIL")

        //set control variables by id
        loginUser = findViewById(R.id.loginUser)
        etPassword = findViewById(R.id.et_password)
        btnSubmit = findViewById(R.id.btn_submit)
        btnReset = findViewById(R.id.btn_reset)
        emailHint = findViewById(R.id.et_email)

        //set login username textview
        loginUser.text = username

        //set default email of user
        emailHint.hint = email


        //access register page
        btnSubmit.setOnLongClickListener {
            var intent = Intent(this@LoginPage, RegisterPage::class.java)
            startActivity(intent)
            true
        }

    }

    fun submitCredentials(view: View){

    }

    fun resetFields(view: View){
        etPassword.setText("")
    }




}