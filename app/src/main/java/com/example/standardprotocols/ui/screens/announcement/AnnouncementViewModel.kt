package com.example.standardprotocols.ui.screens.announcement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.Announcement
import com.example.standardprotocols.data.AnnouncementListResult
import com.example.standardprotocols.data.AnnouncementResult
import com.example.standardprotocols.data.AuthResult
import com.example.standardprotocols.data.FirebaseAnnouncementRepository
import com.example.standardprotocols.data.FirebaseAuthRepository
import com.example.standardprotocols.data.FirebaseUserRepository
import com.example.standardprotocols.data.LeaveListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val announcementRepository: FirebaseAnnouncementRepository,
    private val authRepository: FirebaseAuthRepository,
    private val userRepository: FirebaseUserRepository
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeAnnouncement: StateFlow<AnnouncementListResult> = authRepository.getCurrentUser().map { authResult ->
        if (authResult is AuthResult.Success && authResult.data?.uid != null) {
            val user = userRepository.getUserByID(authResult.data.uid)
            if (user != null) {
                if (user.role == "Manager") {
                    announcementRepository.getLiveAnnouncementForManager(authResult.data.uid)
                } else {
                    announcementRepository.getLiveAnnouncementForUser(authResult.data.managerId!!)
                }
            } else {
                flowOf(AnnouncementListResult.Error)
            }
        } else {
            flowOf(AnnouncementListResult.Error)
        }
    }.flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AnnouncementListResult.Idle
        )


    fun addAnnouncement(a: Announcement) {
        viewModelScope.launch {
            announcementRepository.addAnnouncement(a).collectLatest { res ->
                when(res) {
                    is AnnouncementResult.Success -> Log.d("VIEWMODEL", "Success")
                    is AnnouncementResult.Error -> Log.d("VIEWMODEL", "Error")
                }
            }
        }
    }

}