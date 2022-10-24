package com.example.booksearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.booksearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController) //id 자동 매핑해줌

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


