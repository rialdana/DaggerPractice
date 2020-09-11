package com.example.daggerpractice

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.daggerpractice.di.auth.AuthResource
import com.example.daggerpractice.ui.auth.AuthActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this, Observer {
            it?.let {
                when (it) {
                    is AuthResource.Loading -> {

                    }
                    is AuthResource.Authenticated -> {

                    }
                    is AuthResource.NotAuthenticated -> {
                        navLoginScreen()
                    }
                }
            }
        })
    }

    private fun navLoginScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}