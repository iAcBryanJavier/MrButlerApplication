package com.example.mrbutlerapplication.chats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.model.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class NewMessageActivity : AppCompatActivity() {
    private lateinit var recyclerViewNewMessage: RecyclerView
    private lateinit var userNameTextView: TextView
    private lateinit var userPicImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        recyclerViewNewMessage = findViewById(R.id.recyclerview_newmessage)

        supportActionBar?.title = "Select User"

        fetchUsers()
    }

    companion object{
        const val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

               snapshot.children.forEach{
                   Log.d("NewMessage",it.toString())
                   val user = it.getValue(CurrentUser::class.java)
                   if(user != null){
                       if(user.uid != FirebaseAuth.getInstance().uid){
                           Log.d("NewMessageActivity", "Username: ${user.username}")
                           adapter.add(UserItem(user))
                       }

                   }

               }

                adapter.setOnItemClickListener{ item, view->
                    val userItem = item as UserItem

                    val intent = Intent(this@NewMessageActivity,ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()
                }

                recyclerViewNewMessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}

class UserItem(val user: CurrentUser): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.username_textview).text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.findViewById<ImageView>(R.id.user_pic_new_message))
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }

}