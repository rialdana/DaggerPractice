package com.example.daggerpractice.ui.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.SessionManager
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager) :
    ViewModel() {
    init {
        Log.d("ProfileViewModel", "Profile Viewmodel is ready")
    }

    fun getAuthenticatedUser() = sessionManager.getAuthUser()
}