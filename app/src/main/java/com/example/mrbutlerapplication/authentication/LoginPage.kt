package com.example.mrbutlerapplication.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.controller.MenuPage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val USERNAME = intent.getStringExtra("USERNAME")
        val EMAIL = intent.getStringExtra("EMAIL")

        username_textview_login.setText("Hi $USERNAME! Welcome Back.")
        email_edittext_login.setText(EMAIL)

        login_button.setOnLongClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
            true
        }

    }

    fun login(view: View?){
        if (email_edittext_login.text.toString() == "" || password_edittext_login.text.toString() == ""){
            Toast.makeText(this@LoginPage, "Empty Fields! Please Fill Up Fields", Toast.LENGTH_LONG).show()
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email_edittext_login.text.toString(),password_edittext_login.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this@LoginPage,"Login Success!",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginPage, MenuPage::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this@LoginPage,"Login Failed!: ${it.message}",Toast.LENGTH_SHORT).show()
                }
        }


    }

    fun register(view: View?){
        val intent = Intent(this@LoginPage, RegisterPage::class.java)
        startActivity(intent)
    }
}