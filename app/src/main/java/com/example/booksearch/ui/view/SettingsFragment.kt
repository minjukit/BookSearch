package com.example.booksearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.booksearch.databinding.FragmentSettingsBinding
import com.example.booksearch.ui.viewModel.BookSearchViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var bsViewModel: BookSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bsViewModel = (activity as MainActivity).bookViewModel
        //saveSettings()
        //loadSettings()
    }
/*
    private fun saveSettings() { // radioButton check된 모드를 뷰모델에 저장
        binding.rgSort.setOnCheckedChangeListener { radioGroup, i ->
            val value = when (i) {
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            bsViewModel.saveSortMode(value)
        }
    }

    private fun loadSettings() { //뷰모델에서 불러온 값을 확인 후 radioButton에 체크
        lifecycleScope.launch {
            val buttonId = when (bsViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }
    }


 */

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}