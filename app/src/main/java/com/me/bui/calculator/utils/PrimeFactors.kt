package com.me.bui.calculator.utils

import com.me.bui.calculator.utils.Utils.pushTo
import kotlin.math.sqrt

/**
 * Find the prime factorization of a number.
 * Works for whole numbers between 2 and Double.MAX_VALUE
 * REF: https://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/
 */

object PrimeFactors {

    fun primeFactors(num: Long): LinkedHashMap<Long, Int> {
        var number = num
        val mapFactors = LinkedHashMap<Long, Int>()

        if (number == 0L || number == 1L ) return mapFactors

        while (number % 2 == 0L) {
            pushTo(mapFactors, 2)
            number /= 2
        }

        for (i: Long in 3..sqrt(number.toDouble()).toLong() step 2) {
            while (number % i == 0L) {
                number /= i
                pushTo(mapFactors, i)
            }
        }

        // is a prime number > 2.
        if (number > 2) {
            pushTo(mapFactors, number)
        }
        return mapFactors
    }
}
