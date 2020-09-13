package com.example.daggerpractice.ui.main.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggerpractice.R
import com.example.daggerpractice.ui.main.Resource
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment : DaggerFragment() {

    private lateinit var viewModel: PostsViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: PostsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel::class.java)

        initRecyclerview()
        subscribeObservers()
    }

    private fun initRecyclerview() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter
    }

    fun subscribeObservers() {
        viewModel.observePosts().removeObservers(viewLifecycleOwner)
        viewModel.observePosts().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        adapter.setPosts(it.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        })
    }
}