package com.chocolatecake.movieapp.ui.home

import androidx.lifecycle.viewModelScope
import com.chocolatecake.movieapp.domain.mappers.NowPlayingUiMapper
import com.chocolatecake.movieapp.domain.mappers.PopularMoviesUiMapper
import com.chocolatecake.movieapp.domain.mappers.PopularPeopleUiMapper
import com.chocolatecake.movieapp.domain.mappers.TopRatedUiMapper
import com.chocolatecake.movieapp.domain.mappers.TrendingUiMapper
import com.chocolatecake.movieapp.domain.mappers.UpComingUiMapper
import com.chocolatecake.movieapp.domain.usecases.home.GetNowPlayingUseCase
import com.chocolatecake.movieapp.domain.usecases.home.GetPopularMoviesUseCase
import com.chocolatecake.movieapp.domain.usecases.home.GetPopularPeopleUsecase
import com.chocolatecake.movieapp.domain.usecases.home.GetTopRatedUseCase
import com.chocolatecake.movieapp.domain.usecases.home.GetTrendingMoviesUseCase
import com.chocolatecake.movieapp.domain.usecases.home.GetUpcomingMoviesUseCase
import com.chocolatecake.movieapp.home.adapter.HomeListener
import com.chocolatecake.movieapp.ui.base.BaseViewModel
import com.chocolatecake.movieapp.ui.home.ui_state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nowPlayingUseCase: GetNowPlayingUseCase,
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularPeopleUseCase: GetPopularPeopleUsecase,
    private val topRatedUseCase: GetTopRatedUseCase,
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val upComingUiMapper: UpComingUiMapper,
    private val nowPlayingUiMapper: NowPlayingUiMapper,
    private val trendingUiMapper: TrendingUiMapper,
    private val topRatedUiMapper: TopRatedUiMapper,
    private val popularPeopleUiMapper: PopularPeopleUiMapper,
    private val popularMoviesUiMapper: PopularMoviesUiMapper,
) : BaseViewModel(), HomeListener {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _uiState.update { it.copy(isLoading = true) }
        getUpComingMovies()
        getPopularPeople()
        getNowPlayingMovies()
        getTrendingMovies()
        getPopularMovies()
        getTopRatedMovies()

    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                popularMoviesUseCase().collect { popularMoviesList ->
                    val items = popularMoviesList.map(popularMoviesUiMapper::map)
                    _uiState.update {
                        it.copy(
                            popularMovies = HomeItem.PopularMovies(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                topRatedUseCase().collect { topRatedList ->
                    val items = topRatedList.map(topRatedUiMapper::map)
                    _uiState.update {
                        it.copy(
                            topRated = HomeItem.TopRated(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }


    private fun getUpComingMovies() {
        viewModelScope.launch {
            try {
                upcomingMoviesUseCase().collect { upComingList ->
                    val items = upComingList.map(upComingUiMapper::map)
                    _uiState.update {
                        it.copy(
                            upComingMovies = HomeItem.Slider(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            try {
                popularPeopleUseCase().collect { popularPeopleList ->
                    val items = popularPeopleList.map(popularPeopleUiMapper::map)
                    _uiState.update {
                        it.copy(
                            popularPeople = HomeItem.PopularPeople(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                nowPlayingUseCase().collect { nowPlayingList ->
                    val items = nowPlayingList.map(nowPlayingUiMapper::map)
                    _uiState.update {
                        it.copy(
                            nowPlayingMovies = HomeItem.NowPlaying(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                trendingMoviesUseCase().collect { trendingList ->
                    val items = trendingList.map(trendingUiMapper::map)
                    _uiState.update {
                        it.copy(
                            trendingMovies = HomeItem.Trending(items), isLoading = false
                        )
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun onError(message: String) {
        val errors = _uiState.value.onErrors.toMutableList()
        errors.add(message)
        _uiState.update { it.copy(onErrors = errors, isLoading = false) }
    }

    override fun onClickNowPlaying(itemId: Int) {

    }

    override fun onClickTrending(itemId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickPopularMovies(itemId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickTopRated(itemId: Int) {
        TODO("Not yet implemented")
    }


    override fun onClickUpComing(itemId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickPopularPeople(itemId: Int) {
        TODO("Not yet implemented")
    }

}