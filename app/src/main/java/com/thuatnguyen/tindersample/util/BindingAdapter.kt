package com.thuatnguyen.tindersample.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.thuatnguyen.tindersample.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:customTint")
    fun ImageView.setTint(selectedId: Int?) {
        val color = if (id == selectedId) R.color.green else R.color.gray
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, color))
    }

    @JvmStatic
    @BindingAdapter("app:customSrc")
    fun ImageView.setImageSrc(url: String) {
        Glide.with(this).load(url).placeholder(R.drawable.ic_placeholder).circleCrop().into(this)
    }
}