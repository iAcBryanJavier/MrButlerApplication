package com.example.mrbutlerapplication.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mrbutlerapplication.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var phoneNum: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText

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

    fun register(view: View?){
        var toast: Toast
        if(fullName.text.toString() == "" || phoneNum.text.toString() == "" || email.text.toString() == "" || password.text.toString() == ""){

            //make this toast text align center
            toast = Toast.makeText(this,"There are some missing credentials! Please Try Again", Toast.LENGTH_SHORT)
            toast.show()
        }else if(password.text.toString().length < 6){

            //make this toast text align center
            toast = Toast.makeText(this,"Please Enter A Password With 6 or More Characters!", Toast.LENGTH_SHORT)
            toast.show()
        }else{
            registerUser(fullName.text.toString(), phoneNum.text.toString(), email.text.toString(), password.text.toString())
        }

    }

    private fun registerUser(name: String, phone: String, email: String, password: String){
        var toast: Toast
        var intent: Intent = Intent(this@RegisterPage, LoginPage::class.java)
        intent.putExtra("USERNAME", "Welcome ${name} to Mr. Butler!")
        intent.putExtra("EMAIL", email)

        authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this@RegisterPage, object: OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful){
                    startActivity(intent)
                    Toast.makeText(this@RegisterPage, "User Registered Successfully!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterPage, "Error Occurred! Try Again.", Toast.LENGTH_SHORT).show()
                }
            }

        })


    }

}