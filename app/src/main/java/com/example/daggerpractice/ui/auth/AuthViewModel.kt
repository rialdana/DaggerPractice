package com.example.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.models.User
import com.example.daggerpractice.network.auth.AuthApi
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(val authApi: AuthApi) : ViewModel() {

    val authUser = MediatorLiveData<User>()

    init {
        Log.i(TAG, "Hi from authViewModel, I was injected!")
        Log.i(TAG, "Hi I'm the authApi and was injected from: $authApi")
    }

    fun authenticateWithId(userId: Int) {
        val source = LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId).subscribeOn(Schedulers.io())
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