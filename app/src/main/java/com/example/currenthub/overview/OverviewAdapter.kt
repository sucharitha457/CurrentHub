package com.example.currenthub.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currenthub.databinding.NewsDataBinding
import com.example.currenthub.network.Article

class NewsDataAdapter (val clickListener: NewsDataListener): ListAdapter<Article, NewsDataAdapter.newsViewHolder>(newsDiffCallback()) {

    class newsViewHolder(private val binding: NewsDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Article,clickListener: NewsDataListener) {
            binding.newsData = song
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): newsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsDataBinding.inflate(layoutInflater, parent, false)
                return newsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        return newsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        when(holder){
            is newsViewHolder -> {
                val item = getItem(position)
                holder.bind(item, clickListener)
            }
        }
    }
}

class newsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.articleId == newItem.articleId
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem{
    data class newsItem(val news: Article) : DataItem(){
        override val id = news.articleId
    }
    abstract val id: String
}

class NewsDataListener(val clickListener: (id : String) -> Unit){
    fun onClick(news : Article) = clickListener(news.articleId)
}
