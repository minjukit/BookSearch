package com.example.booksearch.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearch.data.model.Book
import com.example.booksearch.databinding.ItemBookPreviewBinding

class BookSearchViewHolder
    (private var binding: ItemBookPreviewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        val author = book.authors.toString().removeSurrounding("[", "]") //저자 delimiter 지우기
        val publisher = book.publisher
        val date = book.datetime?.substring(0, 10) ?: "" //null이면 ""

        itemView.apply {
            //binding.bookImg.load(book.thumbnail) //코일
            //글라이드
            Glide.with(this)
                .load(book.thumbnail)
                .into(binding.bookImg)

            binding.bookAuthor.text = "$author | $publisher" //저자 | 출판사 형식
            binding.bookDate.text = date
        }

    }
}