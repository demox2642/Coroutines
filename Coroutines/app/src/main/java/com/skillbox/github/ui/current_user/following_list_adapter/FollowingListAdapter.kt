package com.skillbox.github.ui.current_user.following_list_adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.data.Following

class FollowingListAdapter() : AsyncListDifferDelegationAdapter<Following>(
    MovieDiffUtilCallBack()
) {

    init {
        delegatesManager.addDelegate(FollowingDelegateAdapter())
    }

    class MovieDiffUtilCallBack : DiffUtil.ItemCallback<Following>() {
        override fun areItemsTheSame(oldItem: Following, newItem: Following): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Following, newItem: Following): Boolean {
            Log.e("check movie", "${oldItem == newItem}")
            return oldItem == newItem
        }
    }
}
