package retrofit2.adapter.rxjava2


import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableSubscribeOn
import io.reactivex.schedulers.Schedulers

class SchedulerInterceptor : ObservableInterceptor {
    override fun filter(observable: Observable<*>): Observable<*> {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
