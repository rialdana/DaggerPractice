package com.example.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.di.auth.AuthResource
import com.example.daggerpractice.models.User
import com.example.daggerpractice.network.auth.AuthApi
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(val authApi: AuthApi) : ViewModel() {

    val authUser = MediatorLiveData<AuthResource<User>>()

    init {
        Log.i(TAG, "Hi from authViewModel, I was injected!")
        Log.i(TAG, "Hi I'm the authApi and was injected from: $authApi")
    }

    fun authenticateWithId(userId: Int) {

        authUser.value = AuthResource.Loading()

        val source: LiveData<AuthResource<User>> = LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId).onErrorReturn {
                return@onErrorReturn User(-1, "", "", "")
            }.map {
                if (it.id == -1) {
                    return@map AuthResource.Error<User>("Something went wrong")
                }
                return@map AuthResource.Authenticated(it)
            }.subscribeOn(Schedulers.io())
        )

        authUser.addSource(source) {
            authUser.value = it
            authUser.removeSource(source)
        }
    }


    companion object {
        private const val TAG = "AuthViewModel"
    }
}