package com.example.standardprotocols.ui.screens.home.employee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.FirebaseIssueRepository
import com.example.standardprotocols.data.Issue
import com.example.standardprotocols.data.IssueListResult
import com.example.standardprotocols.data.IssueResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "ISSUE"

@HiltViewModel
class IssueViewModel @Inject
constructor(private val issueRepository: FirebaseIssueRepository): ViewModel() {
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
    fun getRaisedIssuesCount(id: String): Int {
        var raisedIssuesCount = 0
        viewModelScope.launch {
            issueRepository.getIssueListForUserOnce(id).collectLatest { res ->
                when(res) {
                    is IssueListResult.Success -> raisedIssuesCount = res.issueList.count()
                    else -> {Log.d(TAG, "getting Problem")}
                }
            }
        }
        return raisedIssuesCount
    }
}