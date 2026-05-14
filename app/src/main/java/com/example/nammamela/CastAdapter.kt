package com.example.nammamela

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CastAdapter(
    private val list: List<Cast>
) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.castName)

        val role: TextView =
            view.findViewById(R.id.castRole)

        val image: ImageView =
            view.findViewById(R.id.castImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_cast,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val cast = list[position]

        holder.name.text = cast.name
        holder.role.text = cast.role

        Glide.with(holder.itemView.context)
            .load(cast.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.image)
    }
}