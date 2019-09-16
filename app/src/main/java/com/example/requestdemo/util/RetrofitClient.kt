package com.example.requestdemo.util

import android.text.TextUtils
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.CallAdapterFactoryProxy
import retrofit2.adapter.rxjava2.ObservableInterceptor
import retrofit2.adapter.rxjava2.SchedulerInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by goldze on 2017/5/10.
 * RetrofitClient封装单例类, 实现网络请求
 */
class RetrofitClient private constructor(url: String = baseUrl) {


    companion object {

        val TAG = RetrofitClient::class.java.simpleName
        //超时时间
        private val DEFAULT_TIMEOUT = 20
        //服务端根路径
        var baseUrl = "https://www.oschina.net/"


        val INSTANCE = RetrofitClient()
    }

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    init {
        var url = url

        if (TextUtils.isEmpty(url)) {
            url = baseUrl
        }
        val sslParams = HttpsUtils.sslSocketFactory
        okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
            .build()
        val interceptors = ArrayList<ObservableInterceptor>()
        //添加其他拦截器
        interceptors.add(SchedulerInterceptor())
        val callAdapterFactoryProxy = CallAdapterFactoryProxy.create()
            .addInterceptor(SchedulerInterceptor())
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactoryProxy)
            .baseUrl(url)
            .build()

    }


    fun getClient() = retrofit

}
