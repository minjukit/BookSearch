package com.example.booksearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearch.R
import com.example.booksearch.databinding.FragmentSearchBinding
import com.example.booksearch.ui.adapter.BookSearchAdapter
import com.example.booksearch.ui.viewModel.BookSearchViewModel

class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bsViewModel: BookSearchViewModel
    private lateinit var bsAdapter: BookSearchAdapter

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

/*
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
*/


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bsViewModel = (activity as MainActivity).bookViewModel
        setUpRecyclerView()
        searchBooks()
        //갱신되는 Livedata형태의 BookSearchViewModel.searchResult 옵저빙
        bsViewModel.searchResult.observe(viewLifecycleOwner, Observer { response ->
            val books = response.documents
            bsAdapter.submitList(books)
        })


        bsAdapter.itemClick = object : BookSearchAdapter.ItemClick {
            override fun onClick(view: View, position: Int, url: String) {

                setFragmentResult("requestKey", bundleOf("bundleKey" to url))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, WebViewFragment())
                    .addToBackStack(null) //backbtn -> searchFragment
                    .commit()

            }

        }


/*
        bsAdapter.setOnItemClickListener(object : BookSearchAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: String, pos: Int) {
                Log.d("itemclick", "hih")
                val url = data
                setFragmentResult("requestKey", bundleOf("bundleKey" to url))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.webViewFragment, WebViewFragment())
                    .commit()

                /*navigation으로 data전달..ing
                val action = WebViewFragment.passAToB(data)
                findNavController().navigate(R.id.action_searchFragment_to_webViewFragment)
                */
                Log.d("itemurl", "클릭$url")

            }

        })
*/


    }

    private fun setUpRecyclerView() {
        bsAdapter = BookSearchAdapter()
        binding.rvBook.apply {
            //리사이클러뷰 설정
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            this.adapter = bsAdapter
        }

    }


    private fun searchBooks() {
        var startTime = System.currentTimeMillis()

        binding.editTextBook.addTextChangedListener {
            var endTime = System.currentTimeMillis()
            if (endTime >= startTime + 100L) {
                it?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        bsViewModel.searchBooks(query) //api 호출
                    }
                }
            }
            startTime = endTime
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

