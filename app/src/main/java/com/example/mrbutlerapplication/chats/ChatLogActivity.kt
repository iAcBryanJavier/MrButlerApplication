package com.example.mrbutlerapplication.chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.model.ChatMessage
import com.example.mrbutlerapplication.model.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object{
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    var toUser: CurrentUser? = null

    private lateinit var recyclerViewChatLog: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        recyclerViewChatLog = findViewById(R.id.recyclerview_chat_log)
        recyclerViewChatLog.adapter = adapter

        toUser = intent.getParcelableExtra<CurrentUser>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username

        listenForMessages()

        send_button_chat_log.setOnClickListener{
            Log.d(TAG, "Attempt to send a message....")
            performSendMessage()
        }


    }

    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))


                    }else{
                        if(toUser != null){
                            adapter.add(ChatToItem(chatMessage.text,toUser!!))
                        }

                    }
                    recyclerViewChatLog.scrollToPosition(adapter.itemCount-1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



    private fun performSendMessage() {
        //send a message to firebase
        val text = edittext_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<CurrentUser>(NewMessageActivity.USER_KEY)
        val toId = user?.uid.toString()

        if (fromId == null)return

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(reference.key!!, text,fromId,toId,System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG,"SAVED our chat message: ${reference.key}")
                edittext_chat_log.text.clear()
                recyclerViewChatLog.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)
    }

}

class ChatFromItem(val text: String, val user: CurrentUser): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.text_view_from_row).text = text.trim()

        // load our user image
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.image_view_from_row
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val user: CurrentUser): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.text_view_to_row).text = text.trim()

        // load our user image
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.image_view_to_row
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}