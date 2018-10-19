package com.me.bui.calculator.utils

import java.util.concurrent.Callable

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable

object RxUtils {

    fun <T> makeObservable(func: Callable<T>): Observable<T> {
        return Observable.create { emitter ->
            try {
                val observed = func.call()
                if (observed != null) { // to make defaultIfEmpty work
                    emitter.onNext(observed)
                }
                emitter.onComplete()
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    fun <T> makeFlowable(func: Callable<T>): Flowable<T> {
        return Flowable.create(object : FlowableOnSubscribe<T> {
            @Throws(Exception::class)
            override fun subscribe(emitter: FlowableEmitter<T>) {
                try {
                    val observed = func.call()
                    if (observed != null) {
                        emitter.onNext(observed)
                    }
                    emitter.onComplete()
                } catch (ex: Exception) {
                    emitter.onError(ex)
                }

            }
        }, BackpressureStrategy.BUFFER)
    }

}
