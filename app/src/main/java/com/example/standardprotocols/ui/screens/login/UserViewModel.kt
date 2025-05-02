package com.example.standardprotocols.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.FirebaseUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject constructor(private val userRepository: FirebaseUserRepository): ViewModel() {

    private var _managerName = MutableStateFlow<String?>(null)
    val managerName: StateFlow<String?> = _managerName.asStateFlow()

    fun addUserToFireStore(
        uid: String,
        displayName: String,
        email: String,
        role: String,
        managerId: String?
    ) {
        viewModelScope.launch {
            if (!userRepository.checkUserExists(uid)) {
                userRepository.createUserProfile(
                    uid, displayName, email, role, managerId
                )
            }
        }
    }

    fun getManagerName(id: String) {
        viewModelScope.launch {
            _managerName.value = userRepository.getNameByID(id)
        }
    }



}