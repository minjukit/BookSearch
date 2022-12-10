package com.example.booksearch.data.repository

import com.example.booksearch.api.RetrofitInstance
import com.example.booksearch.data.db.BookDatabase
import com.example.booksearch.data.model.Book
import com.example.booksearch.data.model.BookSearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

//BookSearchRepository 인터페이스를 구현할 클래스
class BookSearchRepositoryImpl(private val db: BookDatabase) : BookSearchRepository {

    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<BookSearchResponse> {
        return RetrofitInstance.api.searchBooks(query, sort, page, size) //api실행하여 반환값 4개 받기
    }

    override suspend fun insertBooks(book: Book) {
        db.bookDao().insertBook(book)
    }

    override suspend fun deleteBooks(book: Book) {
        db.bookDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookDao().getFavoriteBooks()
    }

}