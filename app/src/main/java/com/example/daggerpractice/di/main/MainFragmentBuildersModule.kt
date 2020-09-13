package com.example.daggerpractice.di.main

import com.example.daggerpractice.ui.main.posts.PostsFragment
import com.example.daggerpractice.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributesProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributesPostsFragment() : PostsFragment
}