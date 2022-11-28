package com.example.booksearch.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearch.databinding.FragmentSearchBinding
import com.example.booksearch.ui.adapter.BookSearchAdapter
import com.example.booksearch.ui.viewModel.BookSearchViewModel

class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bsViewModel: BookSearchViewModel
    private lateinit var bsAdapter: BookSearchAdapter

    private var webFrag: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

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

/*
        bsAdapter.itemClick = object : BookSearchAdapter.ItemClick {
            override fun onClick(view: View, position: Int, url: String) {

                //WebViewFragment에 url 전달
                webFrag = WebViewFragment()
                setFragmentResult("requestKey", bundleOf("bundleKey" to url))
                parentFragmentManager.beginTransaction()
                    .add(R.id.nav_host_fragment, WebViewFragment())
                    .addToBackStack(null)
                    .commit();

                //.replace(R.id.nav_host_fragment, WebViewFragment())
                //.addToBackStack(null) //backbtn -> searchFragment
                //.commit()


            }
        }*/


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
        //BookSearchAdapter에서 구현한 setOnItemClickListener
        bsAdapter.setOnItemClickListener { //리스너 액션 이동
            val action = SearchFragmentDirections.actionSearchFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }

    }


    private fun searchBooks() {
        var startTime = System.currentTimeMillis()

        //editText savedStateHandle에서 가져오기
        binding.editTextBook.text = Editable.Factory.getInstance().newEditable(bsViewModel.query)

        binding.editTextBook.addTextChangedListener {
            var endTime = System.currentTimeMillis()
            if (endTime >= startTime + 100L) {
                it?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        bsViewModel.searchBooks(query) //api 호출
                        bsViewModel.query = query
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

