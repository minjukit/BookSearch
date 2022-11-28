package com.example.booksearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.booksearch.data.model.Book
import com.example.booksearch.databinding.ItemBookPreviewBinding

class BookSearchAdapter : ListAdapter<Book, BookSearchViewHolder>(BookDiffCallBack) {

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {

        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), ""
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)

        //onClickListener
        /*
        if (itemClick != null) {
            holder?.itemView.setOnClickListener(View.OnClickListener {
                itemClick?.onClick(it, position, holder.url)
                Log.d("clicktest", "${position.toString()}    ${holder.url}")
            })
        }*/

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(book) }
        }
    }

    private var onItemClickListener: ((Book) -> Unit)? = null
    fun setOnItemClickListener(listner: (Book) -> Unit) {
        onItemClickListener = listner
    }

    companion object {
        //DiffUtil: 어댑터에서 현재 데이터 리스트와 교체될 데이터 리스트를 비교하여 무엇이 바뀌었는지 알아내는 클래스
        private val BookDiffCallBack = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn //국제 표준 도서번호(International Standard Book Number) 같으면
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem //객체 같으면 true
            }

        }
    }

    //Adapter에 클릭리스너 interface 지정 (리사이클러뷰에는 아이템 클릭리스너가 없음)
    interface ItemClick {
        fun onClick(view: View, position: Int, url: String)
    }

}
