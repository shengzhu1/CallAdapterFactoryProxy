package retrofit2.adapter.rxjava2

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CallAdapterFactoryProxy private constructor(
    private val interceptors: MutableList<ObservableInterceptor>,
    private val factory: CallAdapter.Factory?
) : CallAdapter.Factory() {

    companion object {
        fun create(factory: CallAdapter.Factory? = RxJava2CallAdapterFactory.create(),interceptors: MutableList<ObservableInterceptor> = arrayListOf()) = CallAdapterFactoryProxy(factory = factory,interceptors = interceptors)
    }

    fun addInterceptor(interceptor: ObservableInterceptor) :CallAdapterFactoryProxy {
        interceptors.add(interceptor)
        return this@CallAdapterFactoryProxy
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (null != factory) {
            val adapter = factory.get(returnType, annotations, retrofit)
            if (null != adapter) {
                return CallAdapterProxy<Any?>(adapter as CallAdapter<Any?, Any>?, interceptors)
            }
        }
        return null
    }
}
