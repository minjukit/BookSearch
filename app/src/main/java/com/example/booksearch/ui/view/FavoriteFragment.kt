package com.example.booksearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearch.databinding.FragmentFavoriteBinding
import com.example.booksearch.ui.adapter.BookSearchAdapter
import com.example.booksearch.ui.viewModel.BookSearchViewModel
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    private lateinit var bsViewModel: BookSearchViewModel
    private lateinit var bsAdapter: BookSearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bsViewModel = (activity as MainActivity).bookViewModel
        setUpRecyclerView()
        setUptouchHelper(view)
        //뷰모델의 북마크변수를 옵저빙
        bsViewModel.bookmarkBook.observe(viewLifecycleOwner) {
            bsAdapter.submitList(it)
        }

    }

    private fun setUpRecyclerView() {
        bsAdapter = BookSearchAdapter()
        binding.rvFav.apply {
            //리사이클러뷰 설정
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            this.adapter = bsAdapter
        }
        //BookSearchAdapter에서 구현한 setOnItemClickListener
        bsAdapter.setOnItemClickListener { //search,fav 모두 아이템 클릭 시 웹뷰로
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //fav rv touch callback
    private fun setUptouchHelper(view: View) {

        val itemTouchHelperCallBack =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { //드래그 사용안함, 스와이프방향 left
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true //onMove함수 사용안함
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //스와이프하면 해당 인덱스 북 delete
                    val pos = viewHolder.bindingAdapterPosition //터치한 뷰홀더 위치
                    val book = bsAdapter.currentList[pos]
                    bsViewModel.deleteBook(book)
                    Snackbar.make(view, "[" + book.title + "] 북마크 해제", Snackbar.LENGTH_SHORT)
                        .apply {
                            //스낵바에서 취소 클릭 시 다시 저장
                            setAction("취소") {
                                bsViewModel.insertBook(book)
                            }
                        }.show()
                }

            }

        //콜백에 리사이클러뷰 연결해서 동작인식하기
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvFav)
        }

    }

}