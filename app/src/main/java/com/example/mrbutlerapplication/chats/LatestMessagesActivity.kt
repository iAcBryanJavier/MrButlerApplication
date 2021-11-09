package com.example.mrbutlerapplication.chats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.authentication.LoginPage
import com.example.mrbutlerapplication.model.ChatMessage
import com.example.mrbutlerapplication.model.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        var currentUser: CurrentUser? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        recyclerview_latest_messages.adapter = adapter
        recyclerview_latest_messages.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        //set item click listener on your adapter
        adapter.setOnItemClickListener{ item, view ->
            Log.d("LatestMessagesActivity","123")
            val intent = Intent(this,ChatLogActivity::class.java)

            val row = item as LatestMessageRow
            row.chatPartnerUser

            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        listenForLatestMessages()

        verifyUserIsLoggedIn()
        fetchCurrentUser()

    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun refreshRecyclerViewMessages(){
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)?: return
                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)?: return
                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()


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

    val adapter = GroupAdapter<ViewHolder>()


    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(CurrentUser::class.java)
                Log.d("LatestMessages", "Current User ${currentUser?.username}")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        Log.d("LatestMessagesActivity", "Current User: ${uid}")
        if(uid == null){
            val intent = Intent(this, LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){
    var chatPartnerUser: CurrentUser? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.latest_message_textview.text = chatMessage.text
        val chatPartnerId: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatPartnerId = chatMessage.toId
        }else{
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser = snapshot.getValue(CurrentUser::class.java)
                if (chatPartnerUser != null) {
                    viewHolder.itemView.username_textview_latest_messages.text = chatPartnerUser!!.username

                    val targetImageView = viewHolder.itemView.user_image_view
                    Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

}