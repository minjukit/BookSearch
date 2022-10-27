package com.example.booksearch.data.repository

import com.example.booksearch.data.model.BookSearchResponse
import retrofit2.Response

interface BookSearchRepository {
    suspend fun searchBooks( //일시중단가능한 함수
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<BookSearchResponse> //응답은 BookSearchResponse 형태로
}