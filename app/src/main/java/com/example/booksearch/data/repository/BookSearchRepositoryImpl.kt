package com.example.booksearch.data.repository

import com.example.booksearch.api.RetrofitInstance
import com.example.booksearch.data.model.BookSearchResponse
import retrofit2.Response

//BookSearchRepository 인터페이스를 구현할 클래스
class BookSearchRepositoryImpl : BookSearchRepository {

    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<BookSearchResponse> {
        return RetrofitInstance.api.searchBooks(query, sort, page, size) //api실행하여 반환값 4개 받기
    }


}