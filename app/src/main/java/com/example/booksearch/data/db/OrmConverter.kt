package com.example.booksearch.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrmConverter {

    /* Room은 일반객체를 사용하지 못하기 때문에 authors의 type을 converter
    * list를 받으면 String으로 인코드
    * 스트링 받으면 List로 디코드
    * 컨버터 생성 후 룸데이터베이스에 등록해주기
     */

    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

}