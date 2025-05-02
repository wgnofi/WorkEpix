package com.example.standardprotocols.ui.screens.home.employee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.AuthResult
import com.example.standardprotocols.data.FirebaseAuthRepository
import com.example.standardprotocols.data.FirebaseLeaveRepository
import com.example.standardprotocols.data.Leave
import com.example.standardprotocols.data.LeaveListResult
import com.example.standardprotocols.data.LeaveResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LeaveViewModel @Inject
constructor(private val leaveRepository: FirebaseLeaveRepository,
    private val authRepository: FirebaseAuthRepository)
    : ViewModel() {

    private val _leaveList = MutableStateFlow<List<Leave>>(emptyList())
    val leaveList = _leaveList.asStateFlow()

    private val _approvedLeaveList = MutableStateFlow<List<Leave>>(emptyList())
    val approvedLeaveList = _approvedLeaveList.asStateFlow()

    private val _rejectedLeaveList = MutableStateFlow<List<Leave>>(emptyList())
    val rejectedLeaveList = _rejectedLeaveList.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val activePendingLeaves: StateFlow<LeaveListResult> = authRepository.getCurrentUser().map { authResult ->
        if (authResult is AuthResult.Success && authResult.data?.uid != null) {
            leaveRepository.getActivePendingLeaves(authResult.data.uid)
        } else {
            flowOf(LeaveListResult.Error("Not authenticated"))
        }
    }.flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LeaveListResult.Loading
        )

    fun getLeaveListForUser(id: String) {
        viewModelScope.launch {
            val leaveRes = leaveRepository.getLeaveListForUserOnce(id)
            leaveRes.collectLatest { res ->
                when(res) {
                    is LeaveListResult.Loading -> _leaveList.value = emptyList()
                    is LeaveListResult.Success -> _leaveList.value = res.leaveList
                    else -> {}
                }
            }
        }
    }

    fun getApprovedLeaves(id: String) {
        viewModelScope.launch {
            val leaveRes = leaveRepository.getApprovedLeavesForUserOnce(id)
            leaveRes.collectLatest { res ->
                when(res) {
                    is LeaveListResult.Loading -> _approvedLeaveList.value = emptyList()
                    is LeaveListResult.Success -> _approvedLeaveList.value = res.leaveList
                    else -> {}
                }
            }
        }
    }
    fun getRejectedLeaves(id: String) {
        viewModelScope.launch {
            val leaveRes = leaveRepository.getRejectedLeavesForUserOnce(id)
            leaveRes.collectLatest { res ->
                when(res) {
                    is LeaveListResult.Loading -> _rejectedLeaveList.value = emptyList()
                    is LeaveListResult.Success -> _rejectedLeaveList.value = res.leaveList
                    else -> {}
                }
            }
        }
    }

    fun approveLeave(leaveId: String) {
        viewModelScope.launch {
           leaveRepository.approveLeave(leaveId).collectLatest { res ->
               Log.d("Approvallll",res.isSuccess.toString())
           }
        }
    }
    fun rejectLeave(leaveId: String) {
        viewModelScope.launch {
            leaveRepository.rejectLeave(leaveId).collectLatest { res ->
                Log.d("Rejecteddd",res.isSuccess.toString())
            }
        }
    }

    fun applyLeaveForUser(leave: Leave) {
        viewModelScope.launch {
            val f = leaveRepository.applyLeave(leave)
            f.collectLatest { value ->
                when(value) {
                    is LeaveResult.Success -> {
                        Log.d("LEave", "Success ${value.leave}")
                    }
                    is LeaveResult.Loading -> {
                        Log.d("LEave", "Loading")
                    }
                    is LeaveResult.Error -> {
                        Log.d("LEave", value.e)
                    }
                }
            }
        }
    }


}