package com.me.bui.calculator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.me.bui.calculator.utils.PrimeFactorization
import com.me.bui.calculator.utils.PrimeFactors
import com.me.bui.calculator.utils.RxUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Callable

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    val mCompositeDisposable = CompositeDisposable()
    var mUseFasterFactory = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerListener()
    }

    override fun onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose()
        }
        super.onDestroy()
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    private fun registerListener() {
        btnCalculate.setOnClickListener {
            onCalculate()
        }
        btnClear.setOnClickListener { onClear() }
    }

    private fun onCalculate() {
        if (edtInput.text.isEmpty()) {
            showError("Empty value !!!")
            return
        }

        var number: Long
        try {
            number = edtInput.text.toString().trim().toLong()
        } catch (e: NumberFormatException) {
            showError("Out of range !!!")
            return
        }

        getPrimeFactors(number)
    }

    private fun getPrimeFactors(number: Long) {
        getCompositeDisposable().add(
            getPrimeFactorsRx(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LinkedHashMap<Long, Int>>() {
                    override fun onComplete() {
                        Log.d(TAG , "Done !!!")
                    }

                    override fun onNext(t: LinkedHashMap<Long, Int>) {
                        updateUI(t)
                    }

                    override fun onError(e: Throwable) {
                        showError(e.toString())
                    }
                })
        )
    }

    private fun updateUI(hm: LinkedHashMap<Long, Int>) {
        if (hm.size > 0) {
            val result = printExpArray(hm)
            edtOutput.text.clear()
            edtOutput.setText(result)
        } else {
            showError("Don't support this number !!!")
        }
    }

    private fun onClear() {
        edtInput.text.clear()
        edtOutput.text.clear()
    }

    /**
     * Print result with exp array.
     */
    private fun printExpArray(hashMap: LinkedHashMap<Long, Int>): String {
        var result = ""
        for (item in hashMap) {
            result += item.key.toString()
            if (item.value != 1) {
                result += "^" + item.value.toString()
            }
            result += " x "
        }
        // Remove ' x ' end of result.
        result = result.substringBeforeLast("x", result)
        return result.trim()
    }

    private fun showError(str: String) {
        onClear()
        Snackbar.make(findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG).show()
    }

    private fun primeFactorsRx(number: Long): Observable<LinkedHashMap<Long, Int>> {
        return RxUtils.makeObservable(Callable { PrimeFactors.primeFactors(number) })
    }

    private fun getFactorsRx(number: Long): Observable<LinkedHashMap<Long, Int>> {
        return RxUtils.makeObservable(Callable { PrimeFactorization.getFactors(number) })
    }

    private fun getPrimeFactorsRx(number: Long): Observable<LinkedHashMap<Long, Int>> {
        return if (mUseFasterFactory) getFactorsRx(number) else primeFactorsRx(number)
    }
}
