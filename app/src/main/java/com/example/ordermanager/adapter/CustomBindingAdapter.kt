package com.example.ordermanager.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:setImageUrl")
fun setImageUrl(imageView: ImageView, url: String){
    url?.let {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}