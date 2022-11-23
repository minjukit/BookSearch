package com.example.booksearch.ui.viewModel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.booksearch.data.repository.BookSearchRepository

/* SavedState 넣기 전
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
 */

class BSViewModelProviderFactory(
    private val bookSearchRepository: BookSearchRepository,
    owner: SavedStateRegistryOwner, //오너 추가했으니 mainactivity에 전달
    defaultArgs: Bundle? = null,
) :
    AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
            return BookSearchViewModel(bookSearchRepository, handle) as T
        }
        throw IllegalArgumentException("viewmodel class not found")
    }

}