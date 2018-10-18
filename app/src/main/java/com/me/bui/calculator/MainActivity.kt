package com.me.bui.calculator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerListener()
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

        var number = 0L
        try {
            number = edtInput.text.toString().trim().toLong()
        } catch (e: NumberFormatException) {
            showError("Out of range !!!")
            return
        }

        val result = printExpArray(primeFactors(number))
        edtInput.setText(result)
    }

    private fun onClear() {
        edtInput.text.clear()
    }

    private fun primeFactors(n: Long): HashMap<Long, Int> {
        var number = n
        val hm = HashMap<Long, Int>()

        if (number == 0L) return hm

        while (number % 2 == 0L) {
            pushTo(hm, 2)
            number /= 2
        }

        for (i: Long in 3..sqrt(number.toDouble()).toLong() step 2) {
            while (number % i == 0L) {
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
    private fun pushTo(hm: HashMap<Long, Int>, key: Long) {
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
    private fun printExpArray(hashMap: HashMap<Long, Int>): String {
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
}
