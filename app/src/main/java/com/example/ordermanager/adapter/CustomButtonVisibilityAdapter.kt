package com.example.ordermanager.adapter

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setButtonVisibility")
fun setButtonVisibility(button: Button,view: Boolean){
    if (view){
        button.visibility= View.INVISIBLE
    }else{
        button.visibility= View.VISIBLE
    }
}