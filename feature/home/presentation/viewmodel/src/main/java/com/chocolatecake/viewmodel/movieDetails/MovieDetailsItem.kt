package com.chocolatecake.viewmodel.movieDetails

sealed class MovieDetailsItem(val type: MovieDetailsType) {
    data class Upper(val upperUiState: UpperUiState?) : MovieDetailsItem(MovieDetailsType.UPPER)
    data class People(val list: List<CastUiState>?) : MovieDetailsItem(MovieDetailsType.PEOPLE)

    data class Recommended(val list: List<RecommendedMoviesUiState>?) : MovieDetailsItem(MovieDetailsType.RECOMMENDED)

    data class Reviews(val list: List<ReviewUiState>?) : MovieDetailsItem(MovieDetailsType.REVIEWS)
}

enum class MovieDetailsType {UPPER, PEOPLE, RECOMMENDED, REVIEWS }