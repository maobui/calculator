package com.me.bui.calculator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalculate.setOnClickListener {
            if (edtInput.text.isEmpty()) {
                showError("Empty value !!!")
                return@setOnClickListener
            }

            var number = 0
            try {
                number = edtInput.text.toString().trim().toInt()
            } catch (e:NumberFormatException) {
            }

            if(number > Int.MAX_VALUE || number < Int.MIN_VALUE) {
                showError("Out of range !!!")
                return@setOnClickListener
            }
            val result = printExpArray(primeFactors(number))
            edtInput.setText(result)
        }
        btnClear.setOnClickListener { edtInput.text.clear() }
    }

    private fun primeFactors(n: Int): HashMap<Int, Int> {
        var number = n
        val hm = HashMap<Int, Int>()

        if(number == 0) return hm

        while (number % 2 == 0) {
            pushTo(hm, 2)
            number /= 2
        }

        for (i: Int in 3..sqrt(number.toDouble()).toInt() step 2) {
            while (number % i == 0) {
                number /= i
                pushTo(hm, i)
            }
        }

        // is a prime number > 2.
        if (number > 2) {
            pushTo(hm, number)
        }
        return hm
    }

    /**
     * Push an number to hashmap.
     */
    private fun pushTo(hm: HashMap<Int, Int>, key: Int) {
        // Insert elements and their frequencies in hashmap.
        if (!hm.containsKey(key)) {
            hm.put(key, 1)
        } else {
            hm.put(key, hm.get(key)!! + 1)
        }
    }

    /**
     * Print result with exp array.
     */
    private fun printExpArray(hashMap: HashMap<Int, Int>): String {
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

    private fun showError(str:String) {
//        Snackbar.make(this.root, "Error", Snackbar.LENGTH_LONG);
    }
}
