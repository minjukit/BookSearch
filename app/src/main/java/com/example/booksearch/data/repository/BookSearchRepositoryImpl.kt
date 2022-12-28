package com.example.booksearch.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.booksearch.api.RetrofitInstance
import com.example.booksearch.data.db.BookDatabase
import com.example.booksearch.data.model.Book
import com.example.booksearch.data.model.BookSearchResponse
import com.example.booksearch.data.repository.BookSearchRepositoryImpl.PreferencesKeys.SORT_MODE
import com.example.booksearch.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

//BookSearchRepository 인터페이스를 구현할 클래스
class BookSearchRepositoryImpl(
    private val db: BookDatabase,
    private val dataStore: DataStore<Preferences>
) :
    BookSearchRepository {

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

    //DataStore - sortMode 바꾸기
    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[SORT_MODE] ?: Sort.ACCURACY.value
        }
    }

}