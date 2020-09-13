package com.example.daggerpractice.ui.main.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.SessionManager
import com.example.daggerpractice.di.auth.AuthResource
import com.example.daggerpractice.models.Post
import com.example.daggerpractice.network.main.MainApi
import com.example.daggerpractice.ui.main.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mainApi: MainApi
) : ViewModel() {

    private val posts = MediatorLiveData<Resource<List<Post>>>()

    init {
        Log.d("PostsViewModel", "The viewmodel is working")
    }

    fun observePosts(): LiveData<Resource<List<Post>>> {
        posts.value = Resource.Loading()

        val source = LiveDataReactiveStreams.fromPublisher(
            mainApi.getPostsFromUser((sessionManager.getAuthUser().value as AuthResource.Authenticated).data.id)
                .onErrorReturn {
                    val post = Post(-1, -1, "", "")

                    return@onErrorReturn listOf(post)
                }
                .map {
                    if (it.isNotEmpty()) {
                        if (it[0].id == -1) {
                            return@map Resource.Error<List<Post>>("Something went wrong")
                        }
                    }
                    return@map Resource.Success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        posts.addSource(source) {
            posts.value = it
            posts.removeSource(source)
        }

        return posts
    }
}