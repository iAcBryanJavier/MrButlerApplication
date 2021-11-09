package com.example.mrbutlerapplication.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileAdapter (private val userList : User) : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.butler,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        Picasso.get().load(userList.profileImageUrl).into(holder.profileImage)
        holder.name.text = userList.username
        holder.email.text = userList.email

    }

    override fun getItemCount(): Int {
        return 1
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val profileImage : CircleImageView = itemView.findViewById(R.id.profile_picture)
        val name : TextView = itemView.findViewById(R.id.butlerName)
        val email  : TextView = itemView.findViewById(R.id.butlerEmail)


    }
}