package com.example.daggerpractice.ui.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerpractice.R
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    @JvmField
    var logo: Drawable? = null

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        login_button.setOnClickListener {
            attemptLogin()
        }

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)

        setLogo()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.authUser.observe(this, Observer {
            it?.let {
                Log.d(TAG, "User was authenticated: ${it.username}")
            }
        })
    }

    private fun attemptLogin() {
        if (user_id_input.text.toString().isEmpty()) {
            return
        } else {
            viewModel.authenticateWithId(user_id_input.text.toString().toInt())
        }
    }

    fun setLogo() {
        requestManager.load(logo).into(login_logo)
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}