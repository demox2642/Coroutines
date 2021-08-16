package com.skillbox.github.ui.current_user

import com.skillbox.github.data.Following
import com.skillbox.github.data.User
import com.skillbox.github.data.UserForChange
import network.NetworkRetrofit

class UserRepository {

    suspend fun followingList(): List<Following> {
        return NetworkRetrofit.guitHabAPI.getFollowing()
    }

    suspend fun user(): User {
        return NetworkRetrofit.guitHabAPI.getUserInformation()
    }

    suspend fun updateUserInfo(user: UserForChange): User {
        return NetworkRetrofit.guitHabAPI.updateUserInfo(user)
    }
}
