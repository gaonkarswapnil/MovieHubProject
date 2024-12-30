package com.example.movie.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movie.databinding.ActivityMovieDetailBinding
import com.example.movie.model.MovieDetail
import com.example.movie.repository.apiimplementation.MoviesRepositoryImplementation
import com.example.movie.services.database.MovieDatabase
import com.example.movie.utils.NetworkData
import com.example.movie.viewmodel.MoviesViewModel
import com.example.movie.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var database: MovieDatabase

    private lateinit var movieDetail: MovieDetail

    private var movieId = 0;

    private val moviesViewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory(application, MoviesRepositoryImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = MovieDatabase.getInstance(this)

        movieId = intent.getIntExtra("movieID", 0)

        moviesViewModel.movieDetail(movieId).observe(this, Observer { response ->

            when(response) {
                is NetworkData.Error -> Log.d("Movie Detail data", "BroadCastReviever")
                is NetworkData.Success -> {
                    Glide.with(binding.ivMovieImage.context)
                        .load("https://image.tmdb.org/t/p/w500/${response.data?.backdrop_path}")
                        .into(binding.ivMovieImage)

                    binding.tvMovieTitle.text = response.data?.title
                    binding.tvMovieDescription.text = response.data?.overview
                    binding.tvReleasedDate.text = "Release Date -> ${response.data?.release_date}"
                    var data = mutableListOf<String>()
                    for (i in response.data?.genres!!){
                        data.add(i.name)
                    }
                    binding.tvGenre.text = "Category -> ${data.joinToString(", ")}"

                    movieDetail = MovieDetail(
                        adult = response.data.adult,
                        backdropPath = response.data.backdrop_path,
                        budget = response.data.budget,
                        genres = response.data.genres,
                        homepage = response.data.homepage,
                        id = response.data.id,
                        imdbId = response.data.imdbId,
                        originCountry = response.data.originCountry,
                        originalLanguage = response.data.originalLanguage,
                        originalTitle = response.data.originalTitle,
                        releaseDate = response.data.release_date,
                        revenue = response.data.revenue,
                        runtime = response.data.runtime,
                        title = response.data.title,
                        video = response.data.video
                    )
                }
            }
        })

        binding.btnWatchLater.setOnClickListener {
            lifecycleScope.launch {
                database.movieDetails().insertMovieDetail(movieDetail)
            }.also {
                binding.btnRemove.visibility = View.VISIBLE
                binding.btnWatchLater.visibility = View.GONE
            }
//            setVisible(false)
        }

        binding.btnRemove.setOnClickListener {
            lifecycleScope.launch {
                database.movieDetails().deleteAllMovies(movieId)
            }.also {
                binding.btnRemove.visibility = View.GONE
                binding.btnWatchLater.visibility = View.VISIBLE
            }
        }

    }
}