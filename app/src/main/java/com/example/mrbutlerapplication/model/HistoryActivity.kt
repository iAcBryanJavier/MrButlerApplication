package com.example.mrbutlerapplication.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryActivity : AppCompatActivity() {

    private  lateinit var dbref : DatabaseReference
    private  lateinit var userRecyclerView: RecyclerView
    private  lateinit var userArrayList: ArrayList <HistoryValues>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_card)

        userRecyclerView = findViewById(R.id.historyList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<HistoryValues>()

        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("bookings/${FirebaseAuth.getInstance().uid}")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children) {
                        val historyValues = userSnapshot.getValue(HistoryValues::class.java)
                        if(historyValues!!.status == "finished"){
                            continue
                        }else{
                            userArrayList.add(historyValues!!)
                        }

                    }

                    userRecyclerView.adapter = HistoryAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}