package com.skillbox.github.ui.current_user

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.data.User
import com.skillbox.github.ui.current_user.following_list_adapter.FollowingListAdapter
import kotlinx.android.synthetic.main.current_user_fragment.*
import kotlinx.android.synthetic.main.repository_list_fragment.*

class CurrentUserFragment : Fragment(R.layout.current_user_fragment) {

    private var repositoryAdapter: FollowingListAdapter? = null

    private val viewModel: UserViewModel by navGraphViewModels(R.id.user) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        group_view.isVisible = false
        viewModel.loadingUser()
        bindViewModel()
        initList()

        change_info_button.setOnClickListener {
            findNavController().navigate(R.id.action_currentUserFragment_to_changeUser)
        }
    }

    private fun bindViewModel() {
        error_text.isVisible = false
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.userViewModel.observe(viewLifecycleOwner, ::updateUser)
        viewModel.serverError.observe(viewLifecycleOwner, ::postErrorMassage)
        viewModel.followingViewModel.observe(viewLifecycleOwner) { repositoryAdapter?.items = it }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        group_view.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
    }

    private fun updateUser(user: User) {
        user_name.text = user.user_name
        user_id.text = user.id.toString()
        name.text = user.name
        user_email.text = user.email
        user_location.text = user.location_user
        user_blog.text = user.blog
        user_twitter_username.text = user.twitter_username
        user_company.text = user.company
        user_hireable.isChecked = user.hireable == true

        Glide.with(user_avatar)
            .load(user.avatar_url)
            .placeholder(R.drawable.load_imege)
            .into(user_avatar)
            .view
    }

    private fun postErrorMassage(errorServerMassage: String) {
        if (errorServerMassage.length> 1) {
            group_view.isVisible = false
            error_text.text = errorServerMassage
            error_text.isVisible = true
        }
    }

    private fun initList() {
        repositoryAdapter = FollowingListAdapter()

        with(following_view) {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
