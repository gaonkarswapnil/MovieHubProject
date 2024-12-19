package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.model.Genre

class GenreScrollerAdapter(val result: List<Genre>, private val listener: OnItemClickListener): RecyclerView.Adapter<GenreScrollerAdapter.GenreCatelogViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    class GenreCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tvGenreName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_name, parent, false)
        return GenreCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: GenreCatelogViewHolder, position: Int) {
        holder.textView.text = result[position].name

        holder.textView.setOnClickListener {
            listener.onItemClick(result[position].id)
        }

        holder.textView.performClick()
    }

}