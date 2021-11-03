package com.example.mrbutlerapplication.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R
import kotlin.collections.ArrayList

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.butler,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.age.text = currentitem.age
        holder.contact.text = currentitem.contactNum
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.butlerName)
        val age  : TextView = itemView.findViewById(R.id.butlerAge)
        val contact  : TextView = itemView.findViewById(R.id.butlerNumber)

    }


}