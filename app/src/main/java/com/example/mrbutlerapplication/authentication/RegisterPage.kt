package com.example.mrbutlerapplication.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mrbutlerapplication.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {

    private lateinit var fullName: TextInputEditText
    private lateinit var phoneNum: TextInputEditText
    private lateinit var email:    TextInputEditText
    private lateinit var password: TextInputEditText

    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        fullName = findViewById(R.id.fullName)
        phoneNum = findViewById(R.id.phoneNum)
        email = findViewById(R.id.emailAdd)
        password = findViewById(R.id.password)

        authentication = FirebaseAuth.getInstance()




    }

    fun register(view: View?) {
        registerUser(fullName.text.toString(), phoneNum.text.toString(), email.text.toString(), password.text.toString())
    }

    private fun registerUser(name: String, phone: String, email: String, password: String){
        var intent: Intent = Intent(this@RegisterPage, LoginPage::class.java)
        intent.putExtra("USERNAME", "Welcome ${name} to Mr. Butler!")
        intent.putExtra("EMAIL", email)

        authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this@RegisterPage, object: OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful){
                    startActivity(intent)
                    Toast.makeText(this@RegisterPage, "User Registered Successfully!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterPage, "Please Check Fields! Try Again.", Toast.LENGTH_SHORT).show()
                }
            }

        })


    }

}