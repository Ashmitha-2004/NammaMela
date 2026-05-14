package com.example.nammamela

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FanAdapter(private val list: List<FanComment>) :
    RecyclerView.Adapter<FanAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.userName)
        val comment: TextView = view.findViewById(R.id.userComment)
        val avatar: TextView = view.findViewById(R.id.avatar) // 🔥 NEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.name.text = item.userName
        holder.comment.text = item.comment

        // 🔥 Avatar initial (first letter)
        if (item.userName.isNotEmpty()) {
            holder.avatar.text = item.userName[0].uppercase()
        } else {
            holder.avatar.text = "?"
        }
    }
}
