package com.chocolatecake.viewmodel.profile

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewModelScope
import com.chocolatecake.bases.BaseViewModel
import com.chocolatecake.usecase.profile.CheckIsUserLoggedInUseCase
import com.chocolatecake.usecase.profile.GetAccountDetailsUseCase
import com.chocolatecake.usecase.profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val profileUiMapper: ProfileUiMapper,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase
) : BaseViewModel<ProfileUIState, ProfileUiEvent>(ProfileUIState()), ProfileListener {

    init {
        getAccountDetails()
    }

    private fun getAccountDetails() {
        viewModelScope.launch {
            try {
                val profileEntity = profileUiMapper.map(getAccountDetailsUseCase())
                _state.update {
                    it.copy(
                        username = profileEntity.username,
                        avatarUrl = profileEntity.avatarUrl,
                        error = null,
                        isLoggedIn = true
                    )
                }
            } catch (th: Throwable) {
                onError(th)
            }
        }
    }

    private fun onError(th: Throwable) {
        val errors = _state.value.error
        _state.update { it.copy(error = errors, isLoading = false) }
    }

    override fun onClickFavorite() {
        sendEvent(ProfileUiEvent.FavoriteEvent)
    }

    override fun onClickWatchlist() {
        sendEvent(ProfileUiEvent.WatchlistEvent)
    }

    override fun onClickWatchHistory() {
        sendEvent(ProfileUiEvent.WatchHistoryEvent)
    }

    override fun onClickMyLists() {
        sendEvent(ProfileUiEvent.MyListsEvent)
    }

    override fun onClickPopcornPuzzles() {
        sendEvent(ProfileUiEvent.PopcornPuzzlesEvent)
    }

    override fun onClickLogout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggedIn = false) }
            if (_state.value.isLoggedIn == logoutUseCase()) {
                sendEvent(ProfileUiEvent.LogoutEvent)
            }
        }
    }

    override fun onUserNotLoggedIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggedIn = true) }
            if (checkIsUserLoggedInUseCase()) {
                _state.update {
                    it.copy(isLoggedIn = false)
                }
            }

        }
    }
}