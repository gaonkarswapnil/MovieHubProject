package com.example.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movie.databinding.ActivityUpcomingMoviesBinding
import com.example.movie.repository.apiimplementation.MoviesRepositoryImplementation
import com.example.movie.viewmodel.MoviesViewModel
import com.example.movie.viewmodel.MoviesViewModelFactory

class UpcomingMoviesActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpcomingMoviesBinding

    private val moviesViewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory(application, MoviesRepositoryImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpcomingMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val movieId = intent.getIntExtra("movieID", 0)

        moviesViewModel.movieDetail(movieId).observe(this, Observer { response ->
            if(response!=null){
                Glide.with(binding.ivMovieImage.context)
                    .load("https://image.tmdb.org/t/p/w500/${response.backdrop_path}")
                    .into(binding.ivMovieImage)

                binding.tvMovieTitle.text = response.title
                binding.tvMovieDescription.text = response.overview
                binding.tvReleasedDate.text = "Release Date -> ${response.release_date}"
                var data = mutableListOf<String>()
                for (i in response.genres){
                    data.add(i.name)
                }
                binding.tvGenre.text = "Category -> ${data.joinToString(", ")}"
            }
        })

    }
}