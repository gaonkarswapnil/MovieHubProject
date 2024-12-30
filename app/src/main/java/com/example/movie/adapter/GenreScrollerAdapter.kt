package com.example.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.model.Genre

class GenreScrollerAdapter(private val genreList: List<Genre>, private val listener: OnItemClickListener): RecyclerView.Adapter<GenreScrollerAdapter.GenreCatelogViewHolder>() {
    private var selectedPosition = 0
    interface OnItemClickListener {
        fun onItemClick(genre: Genre)
    }

    class GenreCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val genreTitle: TextView = itemView.findViewById(R.id.tvGenreName)
        val cardViewBtn : CardView = itemView.findViewById(R.id.cardGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_name, parent, false)
        return GenreCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: GenreCatelogViewHolder, position: Int) {

        val genre = genreList[position]
        holder.genreTitle.text = genre.name
        Log.d("GenreAdapter", "Binding genre: ${genre.name}")

        holder.cardViewBtn.isSelected = position == selectedPosition

        if (position == 0) {
            listener.onItemClick(genre)
        }

        holder.cardViewBtn.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                selectedPosition = currentPosition
                notifyDataSetChanged()
                listener.onItemClick(genre)
            }
        }
    }

}