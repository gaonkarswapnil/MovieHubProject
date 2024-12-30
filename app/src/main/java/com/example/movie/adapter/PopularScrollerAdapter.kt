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

class PopularScrollerAdapter(
    private val result: List<Result>,
    private val listener: AllMovieDetails
): RecyclerView.Adapter<PopularScrollerAdapter.PopularCatelogViewHolder>() {
    class PopularCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ivPopularMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movie, parent, false)
        return PopularCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: PopularCatelogViewHolder, position: Int) {
        val imageUrl = result[position]
        Glide.with(holder.imageView.context)
            .load("https://image.tmdb.org/t/p/w500/${imageUrl.poster_path}")
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            listener.getMovieName(imageUrl.id)
        }
    }


}