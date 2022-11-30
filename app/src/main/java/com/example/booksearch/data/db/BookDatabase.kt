package com.example.booksearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booksearch.data.model.Book

//https://github.com/android/architecture-components-samples/blob/main/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt

@Database(entities = [Book::class], version = 1, exportSchema = false) //room에서 사용할 엔티티,버전 설정
@TypeConverters(OrmConverter::class) //컨버터 생성 후 룸데이터베이스에 등록해주기
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookSearchDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        //singleton
        fun getInstance(context: Context): BookDatabase =
            BookDatabase.INSTANCE ?: synchronized(this) {
                BookDatabase.INSTANCE ?: buildDatabase(context).also { BookDatabase.INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java, "MyBook.db"
            )
                .build()

    }

}
