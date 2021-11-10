package com.example.mrbutlerapplication.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mrbutlerapplication.R

class HistoryAdapter(private val userList:ArrayList<HistoryValues>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.request.text = currentitem.request
        holder.type.text = currentitem.type
        holder.due.text = currentitem.due
        holder.status.text = currentitem.status
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val request : TextView = itemView.findViewById(R.id.request)
        val type  : TextView = itemView.findViewById(R.id.type)
        val due : TextView = itemView.findViewById(R.id.due)
        val status : TextView = itemView.findViewById(R.id.status)
    }

}