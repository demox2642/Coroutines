package com.skillbox.github.ui.current_user.following_list_adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.following_view_for_list.*
import kotlinx.android.synthetic.main.following_view_for_list.view.*
import kotlinx.android.synthetic.main.repository_view_for_list.*
import kotlinx.android.synthetic.main.repository_view_for_list.view.*
import java.util.*

abstract class BaseHolderFollowing(
    view: View
) : RecyclerView.ViewHolder(view), LayoutContainer {

    @SuppressLint("SimpleDateFormat")
    protected fun bindMainInfo(
        login: String,
        id: Long,
        avatar_url: String,
        url: String,
        repos_url: String,
        type: String

    ) {

        itemView.following_name.text = login
        itemView.following_id.text = id.toString()
        itemView.following_url.text = url
        itemView.following_repos_url.text = repos_url
        itemView.following_type.text = type

        Glide.with(following_avatar)
            .load(avatar_url)
            .placeholder(R.drawable.load_imege)
            .into(itemView.following_avatar)
            .view
    }
}
