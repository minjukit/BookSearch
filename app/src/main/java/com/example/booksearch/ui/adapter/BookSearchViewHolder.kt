package com.example.booksearch.ui.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearch.data.model.Book
import com.example.booksearch.databinding.ItemBookPreviewBinding

class BookSearchViewHolder
    (
    private var binding: ItemBookPreviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        lateinit var url: String
    }


    fun bind(book: Book) {
        val title = book.title
        val author = book.authors.toString().removeSurrounding("[", "]") //저자 delimiter 지우기
        val publisher = book.publisher
        val date = book.datetime?.substring(0, 10) ?: "" //null이면 ""
        url = book.url

        itemView.apply {
            //binding.bookImg.load(book.thumbnail) //코일
            //글라이드
            Glide.with(this)
                .load(book.thumbnail)
                .into(binding.bookImg)

            binding.bookTitle.text = title
            binding.bookAuthor.text = "$author | $publisher" //저자 | 출판사 형식
            binding.bookDate.text = date
            binding.bookUrl.text = url
        }

/*
        //아이템 클릭리스너
        itemView.setOnClickListener {
            object : View.OnClickListener {
                override fun onClick(v: View?) {

                    val pos = adapterPosition
                    Log.d("itemposition", pos.toString())

                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(itemView, url, pos)
                    }
                }

            }
        }

 */

    }

    init {
        this.binding = binding

        //item Click Listener
        itemView.setOnClickListener(View.OnClickListener {
            val pos = adapterPosition
            Log.d("click", pos.toString() + " : click!")
        })

    }

}





