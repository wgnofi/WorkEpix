package com.example.standardprotocols.ui.screens.home.employee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.AuthResult
import com.example.standardprotocols.data.FirebaseAuthRepository
import com.example.standardprotocols.data.FirebaseIssueRepository
import com.example.standardprotocols.data.Issue
import com.example.standardprotocols.data.IssueListResult
import com.example.standardprotocols.data.IssueResult
import com.example.standardprotocols.data.LeaveListResult
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
import java.lang.Thread.State
import javax.inject.Inject

const val TAG = "ISSUE"

@HiltViewModel
class IssueViewModel @Inject
constructor(private val issueRepository: FirebaseIssueRepository,
            private val authRepository: FirebaseAuthRepository
): ViewModel() {


    private var _issueCountForUser = MutableStateFlow(0)
    val issueCountForUser: StateFlow<Int> = _issueCountForUser.asStateFlow()

    private var _issues = MutableStateFlow<List<Issue>>(emptyList())
    val issues: StateFlow<List<Issue>> = _issues.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeIssues: StateFlow<IssueListResult> = authRepository.getCurrentUser().map { authRes ->
        if (authRes is AuthResult.Success && authRes.data?.uid != null) {
            issueRepository.activeIssuesForManager(id = authRes.data.uid)
        } else {
            flowOf(IssueListResult.Error("Not authenticated"))
        }
    }.flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = IssueListResult.Loading
        )

    fun applyIssue(issue: Issue) {
        viewModelScope.launch {
            issueRepository.submitIssue(issue).collectLatest { res ->
                when(res) {
                    is IssueResult.Loading -> Log.d(TAG, "Loading")
                    is IssueResult.Error -> Log.d(TAG, res.e)
                    is IssueResult.Success -> Log.d(TAG, res.issue.toString())
                }
            }
        }
    }

    fun fixIssue(id: String) {
        viewModelScope.launch {
            issueRepository.fixIssue(id).collectLatest { res ->
                Log.d("FIX STATUS","Succeeded ? ${res.isSuccess}")
            }
        }
    }

    fun getRaisedIssuesCount(id: String) {
        viewModelScope.launch {
            issueRepository.getIssueListForUserOnce(id).collectLatest { res ->
                when(res) {
                    is IssueListResult.Success -> _issueCountForUser.value = res.issueList.count()
                    else -> {Log.d(TAG, "getting Problem")}
                }
            }
        }
    }

    fun getRaisedIssues(id: String) {
        viewModelScope.launch {
            issueRepository.getIssueListForUserOnce(id).collectLatest { res ->
                when(res) {
                    is IssueListResult.Success -> _issues.value = res.issueList
                    else -> {Log.d(TAG, "getting Problem")}
                }
            }
        }
    }
}