package com.example.booksearch.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.booksearch.R
import com.example.booksearch.data.repository.BookSearchRepositoryImpl
import com.example.booksearch.databinding.ActivityMainBinding
import com.example.booksearch.ui.viewModel.BSViewModelProviderFactory
import com.example.booksearch.ui.viewModel.BookSearchViewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var navController: NavController
    lateinit var bookViewModel: BookSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavView.apply {
            setupWithNavController(navController) //id 자동 매핑해줌
            isItemHorizontalTranslationEnabled
        }
//
//        binding.bottomNavView.setupWithNavController(navController)
//        binding.bottomNavView.isItemHorizontalTranslationEnabled
        //viewModel
        val bookRepostoryImpl = BookSearchRepositoryImpl()
        //Impl & owner 지정
        val factory = BSViewModelProviderFactory(bookRepostoryImpl, this)
        //ViewModel프로바이저에게 owner와 factory지정
        bookViewModel = ViewModelProvider(
            this,
            factory
        )[BookSearchViewModel::class.java] //run-time에 프로그램에서 클래스 알도록


    }


/*
    //bottom navigation 리스너 구현 (xml에 framelayout 생성 시)
    private fun setUpBottomNavigationView() {

        binding.bottomNavView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.frag_search -> {
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SearchFragment()).commit()*/
                    true

                }
                R.id.frag_fav -> {
                    /*
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, FavoriteFragment()).commit()
                     */

                    true
                }
                R.id.frag_settings -> {
                    /*
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SettingsFragment()).commit()
                     */
                    true
                }
                else -> false
            }
        }
    }
*/
}


