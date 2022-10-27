package com.example.booksearch.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearch.data.model.BookSearchResponse
import com.example.booksearch.data.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel(private val bookSearchRepository: BookSearchRepository) : ViewModel() {


    //Livedata Response
    private val _searchResult = MutableLiveData<BookSearchResponse>()
    val searchResult: LiveData<BookSearchResponse> get() = _searchResult

    //viewModel coroutine 스코프
    fun searchBooks(query: String) = viewModelScope.launch(Dispatchers.IO) {
        //정확도순 sort 1페이지에 15개
        //추후 - 필터카테고리 만들것
        val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
        if (response.isSuccessful) {
            response.body()?.let {
                _searchResult.postValue(it)
            }
        }
    }

}