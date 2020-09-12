package com.example.daggerpractice.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.daggerpractice.R
import com.example.daggerpractice.di.auth.AuthResource
import com.example.daggerpractice.models.User
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    private lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel::class.java)
        subscribeObservers()
    }

    fun subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner, Observer {
            it?.let { userAuthResource ->
                when (userAuthResource) {
                    is AuthResource.Authenticated -> {
                        setUserDetails(userAuthResource.data)
                    }
                    is AuthResource.Error -> {
                        setUserError(userAuthResource.msg)
                    }
                }
            }
        })
    }

    private fun setUserError(message: String) {
        email.text = message
    }

    private fun setUserDetails(data: User) {
        email.text = data.email
        username.text = data.username
        website.text = data.website
    }
}