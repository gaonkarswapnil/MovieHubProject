package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.interfaces.UpcomingMovie
import com.example.movie.model.Result

class UpcomingScrollerAdapter(
    val result: List<Result>,
    private val listener: UpcomingMovie
): RecyclerView.Adapter<UpcomingScrollerAdapter.UpcomingMovieCatelogViewHolder>() {
    class UpcomingMovieCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ivUpcomingMovie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingMovieCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_movie, parent, false)
        return UpcomingMovieCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: UpcomingMovieCatelogViewHolder, position: Int) {
        val imageUrl = result[position]
        Glide.with(holder.imageView.context)
            .load("https://image.tmdb.org/t/p/w500/${imageUrl.poster_path}")
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            listener.getUpcomingMovie(imageUrl.id)
        }
    }
}