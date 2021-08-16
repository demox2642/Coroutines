package com.skillbox.github.ui.repository_list.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.data.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repository_view_for_list.*
import kotlinx.android.synthetic.main.repository_view_for_list.view.*
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseHolder(
    view: View,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view), LayoutContainer {

    init {
        view.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    @SuppressLint("SimpleDateFormat")
    protected fun bindMainInfo(
        id: String,
        repository_name: String,
        html_url: String,
        user: User,
        language: String,
        created_at: Date?,
        updated_at: Date?
    ) {

        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

        itemView.repository_name.text = repository_name
        itemView.view_repository_id.text = id
        itemView.view_html_url.text = html_url
        itemView.user_name_for_repository.text = user.user_name
        itemView.view_language.text = language
        itemView.view_created_at.text = formatter.format(created_at)
        itemView.view_updated_at.text = formatter.format(updated_at)

        Glide.with(user_avatar_for_repository)
            .load(user.avatar_url)
            .placeholder(R.drawable.load_imege)
            .into(itemView.user_avatar_for_repository)
            .view
    }
}
