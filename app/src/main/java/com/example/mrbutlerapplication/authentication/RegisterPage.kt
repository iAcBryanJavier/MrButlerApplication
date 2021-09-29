package com.example.mrbutlerapplication.authentication

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mrbutlerapplication.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

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

    fun register(view: View){
        var toast: Toast


        if(fullName.text.toString() == "" || phoneNum.text.toString() == "" || email.text.toString() == "" || password.text.toString() == ""){

            //make this toast text align center
            toast = Toast.makeText(this,"There are some missing credentials in the form!", Toast.LENGTH_SHORT)
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

        authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this@RegisterPage, object: OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful){
                    Toast.makeText(this@RegisterPage, "User Registered Successfully!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterPage, "Error Occurred! Try Again Later.", Toast.LENGTH_SHORT).show()
                }
            }

        })


    }

}