package com.example.mrbutlerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.model.Bookings
import com.example.mrbutlerapplication.model.BookingsAdapter
import com.example.mrbutlerapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BookingsCard : AppCompatActivity() {
    private  lateinit var dbref : DatabaseReference
    private  lateinit var userRecyclerView: RecyclerView
    private  lateinit var userArrayList: ArrayList <Bookings>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings_card)


        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Bookings>()

        getUserData()

    }

    private fun getUserData() {
        var bookings: Bookings? = null

        dbref = FirebaseDatabase.getInstance().getReference("bookings/${FirebaseAuth.getInstance().uid}")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        bookings = userSnapshot.getValue(Bookings::class.java)

                        if(bookings!!.status == "finished"){
                            continue
                        }else{
                            userArrayList.add(bookings!!)
                        }
                    }

                    userRecyclerView.adapter = BookingsAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}