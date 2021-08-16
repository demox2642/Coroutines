package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.Following
import com.skillbox.github.data.User
import com.skillbox.github.data.UserForChange
import kotlinx.coroutines.*

class UserViewModel : ViewModel() {

    private val repository = UserRepository()
    private val userLiveData = MutableLiveData <User>()
    private val serverErrorLiveData = MutableLiveData<String>()
    private val isLoadingLiveData = MutableLiveData(false)
    private val followingLiveData = MutableLiveData<List<Following>>()

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("CoroutineException", "UserViewModel ERROR:", throwable)
        serverErrorLiveData.postValue(throwable.message)
    }

    val userViewModel: LiveData<User>
        get() = userLiveData

    val serverError: LiveData<String>
        get() = serverErrorLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val followingViewModel: LiveData<List<Following>>
        get() = followingLiveData

    fun loadingUser() {

        viewModelScope.launch(SupervisorJob() + Dispatchers.IO + errorHandler) {

            isLoadingLiveData.postValue(true)
            Log.e("loadingUser user", "start")

            try {
                val newUser = async { repository.user() }
                val following = async { repository.followingList() }

                val resultNewUser = newUser.await()
                val resultFollowing = following.await()
                userLiveData.postValue(resultNewUser)
                followingLiveData.postValue(resultFollowing)
            } catch (t: Throwable) {
                serverErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun changeUser(
        newUser: UserForChange
    ) {

        viewModelScope.launch(SupervisorJob() + Dispatchers.IO + errorHandler) {
            isLoadingLiveData.postValue(true)
            try {
                val newUserAfterUpdate = async { repository.updateUserInfo(newUser) }
                val resultNewUser = newUserAfterUpdate.await()

                userLiveData.postValue(resultNewUser)
            } catch (t: Throwable) {
                serverErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}
