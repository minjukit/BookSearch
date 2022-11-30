package com.example.booksearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.booksearch.databinding.FragmentWebViewBinding
import com.example.booksearch.ui.viewModel.BookSearchViewModel
import com.google.android.material.snackbar.Snackbar

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var web_url: String
    private lateinit var bookSearchViewModel: BookSearchViewModel

    //웹뷰 args
    private val args by navArgs<WebViewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web_url = "https://naver.com"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)

        //webviewFragment args
        val book = args.book
        binding.bookWebview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url) //전달받은 args 아이템의 url 페이지 load
        }
        //main의 뷰모델
        bookSearchViewModel = (activity as MainActivity).bookViewModel
        //fab click event
        binding.fabBookmark.setOnClickListener {
            //뷰모델에 현재 book을 북마크저장
            bookSearchViewModel.insertBook(book)
            Snackbar.make(it, "북마크 저장 완료", Snackbar.LENGTH_SHORT).show()
        }

        /* bundle로 데이터전달
         setFragmentResultListener("requestKey") { requestKey, bundle ->
             if (bundle.getString("bundleKey") != null) {
                 web_url = bundle.getString("bundleKey").toString()
                 Log.d("urltest", web_url)
                 binding.bookWebview.loadUrl(web_url)
             } else {
                 binding.bookWebview.loadUrl(web_url)
             }
         }
         */

        return binding.root
    }

    //lifecycle 따라서 오버라이딩

    override fun onPause() {
        binding.bookWebview.onPause()
        super.onPause()
    }

    override fun onResume() {
        binding.bookWebview.onResume()
        super.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

/*
    override fun onStop() { //bottom nav 전환 시
        super.onStop()
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_webViewFragment_to_searchFragment)
    }*/

}