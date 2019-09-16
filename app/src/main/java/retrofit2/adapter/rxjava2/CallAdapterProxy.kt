package retrofit2.adapter.rxjava2

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter

import java.lang.reflect.Type

internal class CallAdapterProxy<R>(
    private val adapter: CallAdapter<R, Any>?,
    private val interceptors: List<ObservableInterceptor>?
) : CallAdapter<R, Any> {

    override fun responseType(): Type? {
        return adapter?.responseType()
    }

    override fun adapt(call: Call<R>): Any? {
        if (null != adapter && null != interceptors) {
            var result: Any? = adapter.adapt(call)
            if (null != result && result is Observable<*>) {
                var observable: Observable<*> = result
                for (i in interceptors.indices) {
                    interceptors[i]?.apply {
                        filter(observable)?.let {
                            observable = it
                        }
                    }
                }
                result = observable
            }
            return result
        }
        return null
    }
}