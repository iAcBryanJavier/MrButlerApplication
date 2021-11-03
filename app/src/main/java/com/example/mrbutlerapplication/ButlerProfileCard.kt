package com.example.mrbutlerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.model.MyAdapter
import com.example.mrbutlerapplication.model.User
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

        dbref = FirebaseDatabase.getInstance().getReference("Employee")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter = MyAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}