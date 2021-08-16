package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.Project
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class ProjectViewModel : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("CoroutineException", "ERROR:", throwable)
    }

    private val fragmentScope = CoroutineScope(Dispatchers.Main + errorHandler)

    private val repository = ProjectRepository()
    private val projectListLiveData = MutableLiveData<List<Project>>(repository.getProjectList())
    private val isLoadingLiveDataProject = MutableLiveData(false)
    private val serverErrorLiveData = MutableLiveData<String>()
    private val starStatusLiveData = MutableLiveData<Boolean>()

    val projectList: LiveData<List<Project>>
        get() = projectListLiveData

    val isLoadingProject: LiveData<Boolean>
        get() = isLoadingLiveDataProject

    val serverError: LiveData<String>
        get() = serverErrorLiveData

    val starStatus: LiveData<Boolean>
        get() = starStatusLiveData

    fun loadProgectList() {

        fragmentScope.launch {
            isLoadingLiveDataProject.postValue(true)
            try {
                val projectList = repository.getRepositoryList()
                projectListLiveData.postValue(projectList)
            } catch (t: Throwable) {
                serverErrorLiveData.postValue("loadProgectList ${t.message}")
            } finally {
                isLoadingLiveDataProject.postValue(false)
            }
        }
    }

    fun checkStarStatus(
        owner: String,
        repo: String
    ) {
        fragmentScope.launch {
            val status = repository.checkStarStatus(owner, repo)
            starStatusLiveData.postValue(status).wait()
        }
    }

    fun changeStarStatus(
        owner: String,
        repo: String
    ) {
        viewModelScope.launch {
            if (starStatusLiveData.value!!) {
                starStatusLiveData.postValue(repository.deleteStar(owner, repo))
            } else {
                starStatusLiveData.postValue(repository.addStar(owner, repo))
            }
        }
    }
}
