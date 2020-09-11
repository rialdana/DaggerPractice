package com.example.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.models.User
import com.example.daggerpractice.network.auth.AuthApi
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(val authApi: AuthApi) : ViewModel() {
    init {
        Log.i(TAG, "Hi from authViewModel, I was injected!")
        Log.i(TAG, "Hi I'm the authApi and was injected from: $authApi")

        authApi.getUser(1)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<User> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: User) {
                    Log.i(TAG, "onNext: ${t.username}")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }

            })
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}