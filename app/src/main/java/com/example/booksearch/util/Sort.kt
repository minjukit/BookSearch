package com.example.booksearch.util

enum class Sort(val value: String) {
    ACCURACY("accuracy"),
    LATEST("latest"),
    LOWEST("lowest"),
    HIGHEST("highest"),
    GANADA("ganada"),
}

enum class SortInt(val value: Int) {
    ACCURACY(0),
    LATEST(1),
    LOWEST(2),
    HIGHEST(3),
    GANADA(4)
}