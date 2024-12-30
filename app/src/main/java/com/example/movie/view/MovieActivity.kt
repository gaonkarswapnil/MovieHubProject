package com.example.movie.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.DiscoverScrollerAdapter
import com.example.movie.adapter.GenreScrollerAdapter
import com.example.movie.adapter.NowPlayingScrollerAdapter
import com.example.movie.adapter.PopularScrollerAdapter
import com.example.movie.adapter.TopRatedScrollerAdapter
import com.example.movie.adapter.TrendingScrollerAdaper
import com.example.movie.adapter.UpcomingScrollerAdapter
import com.example.movie.databinding.ActivityMovieBinding
import com.example.movie.interfaces.AllMovieDetails
import com.example.movie.interfaces.UpcomingMovie
import com.example.movie.model.Genre
import com.example.movie.repository.apiimplementation.AuthenticationRepositoryImplementation
import com.example.movie.repository.apiimplementation.DiscoverRepositoryImplementation
import com.example.movie.repository.apiimplementation.GenreRepositoryImplementation
import com.example.movie.repository.apiimplementation.MovieListRepositoryImplementation
import com.example.movie.repository.apiimplementation.TrendingRepositoryImplementation
import com.example.movie.services.database.MovieDatabase
import com.example.movie.services.database.dao.TrendingMovieDao
import com.example.movie.utils.NetworkData
import com.example.movie.utils.broadcastreciever.InternetReciever
import com.example.movie.utils.sharedpreference.SessionManager
import com.example.movie.viewmodel.AuthenticationViewModel
import com.example.movie.viewmodel.AuthenticationViewModelFactory
import com.example.movie.viewmodel.DiscoverViewModel
import com.example.movie.viewmodel.DiscoverViewModelFactory
import com.example.movie.viewmodel.GenreViewModel
import com.example.movie.viewmodel.GenreViewModelFactory
import com.example.movie.viewmodel.MovieListViewModel
import com.example.movie.viewmodel.MovieListViewModelFactory
import com.example.movie.viewmodel.TrendingViewModel
import com.example.movie.viewmodel.TrendingViewModelFactory

class MovieActivity : AppCompatActivity(), GenreScrollerAdapter.OnItemClickListener, AllMovieDetails,
    UpcomingMovie {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var internetReceiver: InternetReciever

    private val trendingViewModel: TrendingViewModel by viewModels {
        TrendingViewModelFactory(application, TrendingRepositoryImplementation(trendingMovieDao = MovieDatabase.getInstance(this).trendingMovies()))
    }

    private val genreViewModel: GenreViewModel by viewModels {
        GenreViewModelFactory(application, GenreRepositoryImplementation(genreDao =MovieDatabase.getInstance(this).genre()))
    }

    private val discoverViewModel: DiscoverViewModel by viewModels {
        DiscoverViewModelFactory(application, DiscoverRepositoryImplementation(discoverMovieDao = MovieDatabase.getInstance(this).discoverMovie()))
    }

    private val movieListViewModel: MovieListViewModel by viewModels {
        MovieListViewModelFactory(application, MovieListRepositoryImplementation(
            nowPlayingMovieDao = MovieDatabase.getInstance(this).nowPlayingMovies(),
            popularMoviesDao = MovieDatabase.getInstance(this).popularMovies(),
            topRatedMoviesDao =MovieDatabase.getInstance(this).topRatedMovies(),
            upcomingMoviesDao =MovieDatabase.getInstance(this).upcomingMovies()
        ))
    }

    private val authenticationViewModel: AuthenticationViewModel by viewModels {
        AuthenticationViewModelFactory(application, AuthenticationRepositoryImplementation())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



    override fun onStart() {
        super.onStart()
        internetReceiver = InternetReciever()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(internetReceiver, filter)
    }

    override fun onResume() {
        super.onResume()
        trendingViewModel.treadingMovie().observe(this, Observer { response ->
//            if(response!=null){
//                Log.d("Trending Movie Data", response.toString())
////                for(i in response.results){
////                    result.add(i)
////                }
//
////                response.results
//                binding.vpTrending.adapter = TrendingScrollerAdaper(response.results, this)
//
//            }

            when(response){
                is NetworkData.Error -> Log.d("Trending Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.vpTrending.adapter =
                        response.data?.let { TrendingScrollerAdaper(it.results, this) }
                }
            }

        })

        binding.vpTrending.setPageTransformer(){ page, position ->
            val pageMargin = 50f // Adjust this to control how much of the next slide is visible
            val scaleFactor = 0.85f // Adjust this for the size of the current page

            // Adjust the margin to make the next slide visible
            page.translationX = pageMargin * position

            // Apply a scaling effect to the current page (optional)
            if (position < -1 || position > 1) { // pages out of screen
                page.alpha = 0f
            } else if (position <= 0) { // fading out the page to the left
                page.alpha = 1 + position
            } else if (position <= 1) { // fading in the page to the right
                page.alpha = 1 - position
            }

            // Optional: Scale the page when scrolling (for effect)
            page.scaleX = Math.max(scaleFactor, 1 - Math.abs(position) * 0.15f)
            page.scaleY = Math.max(scaleFactor, 1 - Math.abs(position) * 0.15f)
        }


        genreViewModel.movieGenre().observe(this, Observer { response ->
//            if(response!=null){
//                Log.d("Genre Movie Data", "${response}")
//                binding.rvGenreName.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                binding.rvGenreName.adapter = GenreScrollerAdapter(response.genres, this)
//            }

            when(response){
                is NetworkData.Error -> Log.d("Genre Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.rvGenreName.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvGenreName.adapter =
                        response.data?.let { GenreScrollerAdapter(it.genres, this) }
                }
            }
        })


        movieListViewModel.nowPlaying().observe(this, Observer {response ->

            when(response){
                is NetworkData.Error -> Log.d("Now Playing Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.rvNowPlaying.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvNowPlaying.adapter =
                        response.data?.let { NowPlayingScrollerAdapter(it.results, this) }
                }
            }
        })


        movieListViewModel.popularMovie().observe(this, Observer { response ->

            when(response){
                is NetworkData.Error -> Log.d("Popular Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvPopular.adapter =
                        response.data?.let { PopularScrollerAdapter(it.results, this) }
                }
            }
        })

        movieListViewModel.topRatedMovies().observe(this, Observer { response ->
            when(response){
                is NetworkData.Error -> Log.d("Top Rated Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.rvTopRated.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvTopRated.adapter =
                        response.data?.let { TopRatedScrollerAdapter(it.results, this) }
                }
            }
        })

        movieListViewModel.upcomingMovies().observe(this, Observer { response ->
            when(response){
                is NetworkData.Error -> Log.d("Upcoming Movie Data", "Broadcast Reciever")
                is NetworkData.Success -> {
                    binding.rvUpcomingMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvUpcomingMovies.adapter =
                        response.data?.let { UpcomingScrollerAdapter(it.results, this) }
                }
            }
        })

        binding.ibLogoutButton.setOnClickListener {
            val sessionManager = SessionManager(this@MovieActivity)

            val sessionId = sessionManager.getSessionId()


            if (sessionId != null) {
                authenticationViewModel.deleteSession(sessionId).observe(this, Observer {response ->
                    if(response.success){
                        sessionManager.clearSessionId()
                        Intent(this@MovieActivity, LoginActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                })
            }

        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetReceiver)
    }


    override fun onItemClick(genre: Genre) {
        discoverViewModel.discoverMovie(genre.id.toString()).observe(this, Observer {response ->
//            if(response!=null){
////                Log.d("Discover Movie Data", "${response.toString()}")
//                binding.rvDiscover.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                binding.rvDiscover.adapter =
//                    response.data?.let { DiscoverScrollerAdapter(it.results, this) }
//            }

            when(response){
                is NetworkData.Error -> {
                    Log.d("Discover Movie Data", "Broadcast Reciever")
                }
                is NetworkData.Success -> {
                    binding.rvDiscover.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvDiscover.adapter =
                        response.data?.let { DiscoverScrollerAdapter(it.results, this) }
                }
            }

//            if(response.error?.status_code == 401){
//                Log.d("Discover Movie Data", "Broadcast Reciever")
//            }else{
//                binding.rvDiscover.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                binding.rvDiscover.adapter =
//                    response.data?.let { DiscoverScrollerAdapter(it.results, this) }
//            }
        })
    }

    override fun getMovieName(id: Int) {
        Log.d("Oliver Movie Data", "${id}")

        Intent(this, MovieDetailActivity::class.java).also {
            it.putExtra("movieID", id)
            startActivity(it)
        }
    }

    override fun getUpcomingMovie(id: Int) {
        Intent(this, UpcomingMoviesActivity::class.java).also {
            it.putExtra("movieID", id)
            startActivity(it)
        }
    }
}