package com.example.daggerpractice.ui.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerpractice.R
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

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

        authViewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)

        setLogo()
    }

    fun setLogo() {
        requestManager.load(logo).into(login_logo)
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}