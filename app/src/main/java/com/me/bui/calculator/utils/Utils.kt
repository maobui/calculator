package com.me.bui.calculator.utils

object Utils {
    /**
     * Push an number to hash map with frequencies.
     */
    fun pushTo(hm: LinkedHashMap<Long, Int>, key: Long) {
        // Insert elements and their frequencies in hashmap.
        if (!hm.containsKey(key)) {
            hm.put(key, 1)
        } else {
            hm.put(key, hm.get(key)!! + 1)
        }
    }
}