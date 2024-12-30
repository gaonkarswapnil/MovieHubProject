package com.example.movie.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.model.GenresResponse
import com.example.movie.model.MovieDetail
import com.example.movie.model.MovieDetailsResponse
import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.TrendingMovieResponse
import com.example.movie.model.UpcomingMovieResponse
import com.example.movie.services.database.converters.BelongConverter
import com.example.movie.services.database.converters.Converters
import com.example.movie.services.database.converters.DatesConverter
import com.example.movie.services.database.converters.GenreConverter
import com.example.movie.services.database.converters.OriginCountryConverter
import com.example.movie.services.database.converters.ProductionCompanyConverter
import com.example.movie.services.database.converters.ProductionCountryConverter
import com.example.movie.services.database.converters.SpokenLanguageConverter
import com.example.movie.services.database.dao.DiscoverMovieDao
import com.example.movie.services.database.dao.GenreDao
import com.example.movie.services.database.dao.MovieDetailsDao
import com.example.movie.services.database.dao.NowPlayingMovieDao
import com.example.movie.services.database.dao.PopularMoviesDao
import com.example.movie.services.database.dao.TopRatedMovieDao
import com.example.movie.services.database.dao.TrendingMovieDao
import com.example.movie.services.database.dao.UpcomingMoviesDao

@Database(
    entities = [TrendingMovieResponse::class, NowPlayingResponse::class, PopularResponse::class, TopRatedResponse::class, UpcomingMovieResponse::class, GenresResponse::class, DiscoverMovieResponse::class, MovieDetail::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class, DatesConverter::class, GenreConverter::class, BelongConverter::class, OriginCountryConverter::class, ProductionCompanyConverter::class, ProductionCountryConverter::class, SpokenLanguageConverter::class)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun trendingMovies(): TrendingMovieDao

    abstract fun nowPlayingMovies(): NowPlayingMovieDao

    abstract fun popularMovies(): PopularMoviesDao

    abstract fun topRatedMovies(): TopRatedMovieDao

    abstract fun upcomingMovies(): UpcomingMoviesDao

    abstract fun genre(): GenreDao

    abstract fun discoverMovie(): DiscoverMovieDao

    abstract fun movieDetails(): MovieDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}