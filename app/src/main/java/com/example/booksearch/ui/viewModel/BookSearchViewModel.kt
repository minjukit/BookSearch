package com.example.booksearch.ui.viewModel

import androidx.lifecycle.*
import com.example.booksearch.data.model.Book
import com.example.booksearch.data.model.BookSearchResponse
import com.example.booksearch.data.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


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

    //Room
    fun insertBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.deleteBooks(book)
    }

    val bookmarkBook: LiveData<List<Book>> = bookSearchRepository.getFavoriteBooks()


    //savedState
    var query = String()
        set(value) { //Backing Fields: setter 재정의
            field = value
            savedStateHandle.set(SAVE_STATE_KEY, value); //savedStateHandle은 map형태로 뷰모델에 저장
        }

    init { // Viewmodel 초기화시 savedStateHandle에서 get
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object {
        private const val SAVE_STATE_KEY = "query";
    }
}