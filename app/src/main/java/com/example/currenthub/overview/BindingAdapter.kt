package com.example.currenthub.overview

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.currenthub.R
import com.example.currenthub.network.Article

@BindingAdapter("title")
fun TextView.setTitle(data: Article?) {
    text = data?.title ?: "No title available"
}

@BindingAdapter("context")
fun TextView.setContext(data: Article?) {
    text = data?.content ?: "No context available"
}

@BindingAdapter("description")
fun TextView.setDescrition(data: Article?) {
    text = data?.description ?: "No description available"
}

@BindingAdapter("newsImage")
fun ImageView.setNewsImage(data: Article?) {

    data?.imageUrl?.let { url ->
        val heightInDp = 100f
        layoutParams.height = (heightInDp * resources.displayMetrics.density).toInt()
        layoutParams = layoutParams
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .error(android.R.drawable.stat_notify_error)
            .into(this)
    }
}

@BindingAdapter("newsImageDetail")
fun ImageView.setNewsImageDetail(data: Article?) {
    val heightInDp = 200f
    layoutParams.height = (heightInDp * resources.displayMetrics.density).toInt()
    layoutParams = layoutParams
    data?.imageUrl?.let { url ->
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .error(android.R.drawable.stat_notify_error)
            .into(this)
    } ?: setImageResource(android.R.drawable.stat_notify_error)
}
