package com.example.booksearch.data.db

import androidx.room.*
import com.example.booksearch.data.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookSearchDAO {
    /*
    * DAO 객체 인터페이스에서 북마크db에 insert/delete/read 함수 구현
    * Query 제외 C/U/D 는 시간이 걸리기 때문에 비동기처리
    * 조회는 옵저버패턴의 북의 리스트형태를 가진 Livedata를 반환
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE) //동일 pkey 있으면 replace
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books") //Book.kt
    fun getFavoriteBooks(): Flow<List<Book>>

}