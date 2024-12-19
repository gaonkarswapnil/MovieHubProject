package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.interfaces.AllMovieDetails
import com.example.movie.model.Result

class TrendingScrollerAdaper(
    val result: List<Result>,
    private val listener: AllMovieDetails
): RecyclerView.Adapter<TrendingScrollerAdaper.TrendingCatelogViewHolder>() {
    class TrendingCatelogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ivTrendingMovie)
        val movieName: TextView = itemView.findViewById(R.id.tvTrendingMovieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingCatelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_movie, parent, false)
        return TrendingCatelogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: TrendingCatelogViewHolder, position: Int) {
        val imageURL = result[position]
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${imageURL.backdrop_path}")
            .into(holder.imageView)

        holder.movieName.text = imageURL.title

        holder.imageView.setOnClickListener {
            listener.getMovieName(imageURL.id)
        }
    }
}