package com.example.requestdemo.util

/**
 * create you ApiService
 * Create an implementation of the API endpoints defined by the `service` interface.
 */
fun <T> Class<T>.getService(): T {
    return RetrofitClient.INSTANCE.getClient().create(this)
}