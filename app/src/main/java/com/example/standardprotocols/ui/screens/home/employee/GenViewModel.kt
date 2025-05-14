package com.example.standardprotocols.ui.screens.home.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiResponse {
    data object Loading : ApiResponse()
    data class Success(val data: String) : ApiResponse()
    data class Error(val exception: Throwable) : ApiResponse()
}

@HiltViewModel
class GenViewModel @Inject constructor(
    private val genModel: GenerativeModel
): ViewModel() {
    private var _response = MutableStateFlow<ApiResponse>(ApiResponse.Loading)
    val response = _response.asStateFlow()


    fun getResponseFor(word: String) {
        _response.value = ApiResponse.Loading
        viewModelScope.launch {
            flow {
                val result = genModel.generateContent("Summarise: \n $word \nin not more than 70 words").text ?: "No response"
                emit(ApiResponse.Success(result))
            }
                .catch { error ->
                    _response.value = ApiResponse.Error(error)
                }
                .collect { apiResponse ->
                    _response.value = apiResponse
                }
        }
    }
}