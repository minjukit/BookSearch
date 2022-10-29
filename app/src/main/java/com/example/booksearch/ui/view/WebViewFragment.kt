package com.example.booksearch.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.booksearch.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var web_url: String


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

        binding.bookWebview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            if (bundle.getString("bundleKey") != null) {
                web_url = bundle.getString("bundleKey").toString()
                Log.d("urltest", web_url)
                binding.bookWebview.loadUrl(web_url)
            } else {
                binding.bookWebview.loadUrl(web_url)
            }
        }



        return binding.root
    }
/*
    override fun onStop() { //bottom nav 전환 시
        super.onStop()
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_webViewFragment_to_searchFragment)
    }*/

    override fun onDestroyView() {
        _binding = null


        super.onDestroyView()
    }
}