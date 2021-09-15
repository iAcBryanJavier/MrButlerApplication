package com.example.mrbutlerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity() {

    private lateinit var db : FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var name : EditText
    private lateinit var addBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseDatabase.getInstance("https://mrbutlerapplication-default-rtdb.firebaseio.com/")
        dbRef = db.getReference("message")

        //tite



        addBtn.setOnClickListener(){
            doAdd()
        }
    }

    private fun doAdd(){
        var data : String = name.text.toString()
        dbRef.push().setValue(data)

    }
}


