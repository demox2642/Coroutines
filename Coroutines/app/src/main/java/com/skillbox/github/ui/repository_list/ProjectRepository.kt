package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Project
import kotlinx.coroutines.suspendCancellableCoroutine
import network.NetworkRetrofit
import kotlin.coroutines.resume

class ProjectRepository {
    private var projectList: List<Project> = listOf()

    fun getProjectList(): List<Project> {
        return projectList
    }

    suspend fun getRepositoryList(): List<Project> {
        return NetworkRetrofit.guitHabAPI.getProjectList()
    }

    suspend fun checkStarStatus(
        owner: String,
        repo: String
    ): Boolean = suspendCancellableCoroutine { continuation ->
        findStar(owner, repo) {
            continuation.resume(it)
        }
    }

    private fun findStar(
        owner: String,
        repo: String,
        answer: (Boolean) -> Unit
    ) {
        NetworkRetrofit.guitHabAPI.checkStar(owner, repo).enqueue(
            object : retrofit2.Callback<Unit> {
                override fun onResponse(call: retrofit2.Call<Unit>, response: retrofit2.Response<Unit>) {
                    answer(response.code() == 204)
                }
                override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                }
            }
        )
    }

    suspend fun addStar(
        owner: String,
        repo: String
    ): Boolean {
        return NetworkRetrofit.guitHabAPI.addStar(owner, repo).code() == 204
    }

    suspend fun deleteStar(
        owner: String,
        repo: String
    ): Boolean {
        return NetworkRetrofit.guitHabAPI.deliteStar(owner, repo).code() == 404
    }
}
