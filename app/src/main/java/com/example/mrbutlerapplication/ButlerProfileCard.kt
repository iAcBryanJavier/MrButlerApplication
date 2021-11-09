package com.example.mrbutlerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.model.ProfileAdapter
import com.example.mrbutlerapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ButlerProfileCard : AppCompatActivity() {

    private  lateinit var dbref : DatabaseReference
    private  lateinit var userRecyclerView: RecyclerView
    private  lateinit var userArrayList: ArrayList <User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_butler_profile_card)

        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()

        getUserData()

    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser : User

                if (snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        currentUser = userSnapshot.getValue(User::class.java)!!

                        if (currentUser.uid == FirebaseAuth.getInstance().uid) {
                            userRecyclerView.adapter = ProfileAdapter(currentUser)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}