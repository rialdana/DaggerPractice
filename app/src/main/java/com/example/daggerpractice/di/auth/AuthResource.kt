package com.example.daggerpractice.di.auth

sealed class AuthResource<T> {
    class Authenticated<T>(val data: T) : AuthResource<T>()
    class Error<T>(val msg: String, val data: T? = null): AuthResource<T>()
    class Loading<T>(val data: T? = null): AuthResource<T>()
    class NotAuthenticated<T>: AuthResource<T>()
}