package com.example.daggerpractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.daggerpractice.di.auth.AuthResource
import com.example.daggerpractice.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private val cachedUser: MediatorLiveData<AuthResource<User>> = MediatorLiveData()

    fun authenticateWithId(source: LiveData<AuthResource<User>>) {
        cachedUser.value = AuthResource.Loading()
        cachedUser.addSource(source) { userAuthResource ->
            cachedUser.value = userAuthResource
            cachedUser.removeSource(source)
        }
    }

    fun logOut() {
        cachedUser.value = AuthResource.NotAuthenticated()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> = cachedUser

    companion object {
        private const val TAG = "SessionManager"
    }
}