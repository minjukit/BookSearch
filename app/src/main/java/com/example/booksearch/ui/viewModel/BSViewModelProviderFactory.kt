package com.example.booksearch.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booksearch.data.repository.BookSearchRepository


class BSViewModelProviderFactory(private val bookSearchRepository: BookSearchRepository) :
    ViewModelProvider.Factory {
    //리포지토리를 전달받아서 providerfactory 반환
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
            return BookSearchViewModel(bookSearchRepository) as T
        }
        throw IllegalAccessException("ViewModel class not found")
    }

}