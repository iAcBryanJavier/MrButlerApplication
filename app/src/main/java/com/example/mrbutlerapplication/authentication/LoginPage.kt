package com.example.mrbutlerapplication.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.controller.MenuPage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {

    //initialize control variables
    private lateinit var userLogin: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    private var username: String? = null
    private var email: String? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        //setup firebase database
        val database = Firebase.database
        val userRef = database.getReference("Employees")

        //authentication
        auth = FirebaseAuth.getInstance()

        //get data from MainActivity.kt
        username = intent.getStringExtra("USERNAME")
        email = intent.getStringExtra("EMAIL")

        //set control variables by id
        userLogin = findViewById(R.id.loginUser)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSubmit = findViewById(R.id.btn_submit)
        btnReset = findViewById(R.id.btn_reset)

        //set login username textview
        userLogin.text = username

        //set email field
        etEmail.setText(email)

        //access register page
        btnSubmit.setOnLongClickListener {
            val intent = Intent(this@LoginPage, RegisterPage::class.java)
            startActivity(intent)
            true
        }

    }

    private fun loginUser(email: String, password: String){
        var intent: Intent = Intent(this@LoginPage,MenuPage::class.java)
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this@LoginPage, "Login Successful!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this@LoginPage, "Login Failed! Check Credentials.", Toast.LENGTH_SHORT).show()
        }

    }


    fun submitCredentials(view: View?){
        var txtEmail: String = etEmail.text.toString()
        var txtPassword: String = etPassword.text.toString()


        if(txtEmail == "" || txtPassword == ""){
            Toast.makeText(this@LoginPage, "Missing Credentials!", Toast.LENGTH_SHORT).show()
        }else{
            loginUser(txtEmail, txtPassword)
        }
    }

    fun resetFields(view: View?){
        Toast.makeText(this@LoginPage, "Please Contact Administrator To Reset Password!", Toast.LENGTH_SHORT).show()
    }




}