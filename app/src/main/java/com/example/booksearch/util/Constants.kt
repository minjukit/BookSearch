package com.example.booksearch.util

import com.example.booksearch.BuildConfig

object Constants {

    const val BASE_URL = "https://dapi.kakao.com/"
    const val BOOK_API_KEY = BuildConfig.bookApiKey //local.properties의 key

    const val SEARCH_BOOKS_TIME_DELAY = 100L
    const val DATASTORE_NAME = "preferences_datastore"
}