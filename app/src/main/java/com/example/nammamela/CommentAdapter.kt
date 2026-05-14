package com.example.nammamela

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter(private val list: List<String>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.avatar)
        val userName: TextView = view.findViewById(R.id.userName)
        val userComment: TextView = view.findViewById(R.id.userComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val comment = list[position]

        // Default user name
        holder.userName.text = "User"

        // Comment text
        holder.userComment.text = comment

        // Avatar first letter
        val firstLetter = comment.firstOrNull()?.uppercase() ?: "U"
        holder.avatar.text = firstLetter
    }
}
