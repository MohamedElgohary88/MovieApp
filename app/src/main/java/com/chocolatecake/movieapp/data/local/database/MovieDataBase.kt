package com.chocolatecake.movieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chocolatecake.movieapp.data.local.database.entity.actor.PopularPeopleEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.NowPlayingMovieEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.PopularMovieEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.RecommendedMovieEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.TopRatedMovieEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.TrendingMoviesEntity
import com.chocolatecake.movieapp.data.local.database.entity.movie.UpcomingMovieEntity

@Database(
    entities = [
        PopularMovieEntity::class,
        TopRatedMovieEntity::class,
        UpcomingMovieEntity::class,
        NowPlayingMovieEntity::class,
        RecommendedMovieEntity::class,
        TrendingMoviesEntity::class,
        PopularPeopleEntity::class
        ],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase: RoomDatabase() {
    abstract val movieDao: MovieDao
}