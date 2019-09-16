package retrofit2.adapter.rxjava2


import io.reactivex.Observable

interface ObservableInterceptor {

    fun filter(observable: Observable<*>): Observable<*>
}
