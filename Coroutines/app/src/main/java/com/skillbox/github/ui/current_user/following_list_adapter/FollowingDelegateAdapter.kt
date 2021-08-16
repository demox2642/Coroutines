package com.skillbox.github.ui.current_user.following_list_adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skilbox.networking.data.network.adapter.inflate
import com.skillbox.github.R
import com.skillbox.github.data.Following
import java.util.*

class FollowingDelegateAdapter :
    AbsListItemAdapterDelegate<Following, Following, FollowingDelegateAdapter.FollowingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): FollowingViewHolder {
        return FollowingViewHolder(parent.inflate(R.layout.following_view_for_list))
    }

    class FollowingViewHolder(view: View) : BaseHolderFollowing(view) {

        fun bind(following: Following) {
            bindMainInfo(
                login = following.login,
                id = following.id,
                avatar_url = following.avatar_url,
                url = following.url,
                repos_url = following.repos_url,
                type = following.type

            )
        }
        override val containerView: View
            get() = itemView
    }

    override fun isForViewType(
        item: Following,
        items: MutableList<Following>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Following,
        holder: FollowingViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
