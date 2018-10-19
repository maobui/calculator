package com.me.bui.calculator.utils

import com.me.bui.calculator.utils.Utils.pushTo

/**
 * Find the prime factorization of a number.
 * Works for whole numbers between 2 and Double.MAX_VALUE
 * REF: https://www.mathsisfun.com/numbers/prime-factorization-tool.html
 */

object PrimeFactorization {
    private val lowPrimes = arrayOf(
        2,
        3,
        5,
        7,
        11,
        13,
        17,
        19,
        23,
        29,
        31,
        37,
        41,
        43,
        47,
        53,
        59,
        61,
        67,
        71,
        73,
        79,
        83,
        89,
        97,
        101,
        103,
        107,
        109,
        113,
        127,
        131,
        137,
        139,
        149,
        151,
        157,
        163,
        167,
        173,
        179,
        181,
        191,
        193,
        197,
        199,
        211,
        223,
        227,
        229,
        233,
        239,
        241,
        251,
        257,
        263,
        269,
        271,
        277,
        281,
        283,
        293,
        307,
        311,
        313,
        317,
        331,
        337,
        347,
        349,
        353,
        359,
        367,
        373,
        379,
        383,
        389,
        397,
        401,
        409,
        419,
        421,
        431,
        433,
        439,
        443,
        449,
        457,
        461,
        463,
        467,
        479,
        487,
        491,
        499,
        503,
        509,
        521,
        523,
        541,
        547,
        557,
        563,
        569,
        571,
        577,
        587,
        593,
        599,
        601,
        607,
        613,
        617,
        619,
        631,
        641,
        643,
        647,
        653,
        659,
        661,
        673,
        677,
        683,
        691,
        701,
        709,
        719,
        727,
        733,
        739,
        743,
        751,
        757,
        761,
        769,
        773,
        787,
        797,
        809,
        811,
        821,
        823,
        827,
        829,
        839,
        853,
        857,
        859,
        863,
        877,
        881,
        883,
        887,
        907,
        911,
        919,
        929,
        937,
        941,
        947,
        953,
        967,
        971,
        977,
        983,
        991,
        997,
        1009,
        1013,
        1019,
        1021,
        1031,
        1033,
        1039
    )

    private val LowPrimeN = 100
    private var numLeft = 0L
    private var mapFactors = LinkedHashMap<Long, Int>()

    fun getFactors(num: Long): LinkedHashMap<Long, Int> {
        mapFactors.clear()
        if (num > Double.MAX_VALUE) {
            return mapFactors
        }

        numLeft = num
        if (numLeft == 0L || numLeft == 1L) {
            return mapFactors
        } else {
            var doneQ = false
            var k = 1
            for (p: Int in 0..LowPrimeN) {
                if (!testFact(lowPrimes[p].toLong())) {
                    doneQ = true
                    k = p
                    break
                }
            }
            if (!doneQ) {
                var fact = (((lowPrimes[k - 1] + 5) / 6) shl 0) * 6 - 1
                while (true) {
                    if (!testFact(fact.toLong()))
                        break
                    fact += 2
                    if (!testFact(fact.toLong()))
                        break
                    fact += 4
                }
            }
            if (numLeft != 1L)
                addFact(numLeft, 1)
        }
        return mapFactors
    }

    private fun testFact(fact: Long): Boolean {
        var power = 0
        while (numLeft % fact == 0L) {
            power++
            numLeft = numLeft / fact
        }
        if (power != 0) {
            addFact(fact, power)
        }
        return numLeft / fact > fact
    }

    private fun addFact(fact: Long, power: Int) {
        for (i: Int in 0..power) {
            pushTo(mapFactors, fact)
        }
    }
}