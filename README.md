# 项目初衷

因为okhttp+rxjava2+retrofit，每次都要写线程切换，虽然就两行，但是还是想骚操作一波

具体使用如下

```kotlin
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
```

代码很简单，就是做了一下代理，然后自己加了一个过滤器。也可以自己实现过滤器，做异常转换之类的

