package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.interfaces.AllMovieDetails
import com.example.movie.model.Result

class DiscoverScrollerAdapter(
    private val result: List<Result>,
    private val listener: AllMovieDetails
): RecyclerView.Adapter<DiscoverScrollerAdapter.DiscoverCatelogViewHolder>() {
    class DiscoverCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ivDiscoverMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_movie, parent, false)
        return DiscoverCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: DiscoverCatelogViewHolder, position: Int) {
        val imageUrl = result[position]
        Glide.with(holder.imageView.context)
            .load("https://image.tmdb.org/t/p/w500/${imageUrl.poster_path}")
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            listener.getMovieName(imageUrl.id)
        }
    }
}