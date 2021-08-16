package network

import com.skillbox.github.data.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitHabAPI {
    @GET("user")
    suspend fun getUserInformation(): User

    @GET("user/following")
    suspend fun getFollowing(): List<Following>

    @GET("user/repos")
    suspend fun getProjectList(): List<Project>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String

    ): Call<Unit>

    @PUT("user/starred/{owner}/{repo}")
    suspend fun addStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String

    ): Response<Unit>

    @DELETE("user/starred/{owner}/{repo}")
    suspend fun deliteStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String

    ): Response<Unit>

    @PATCH("user")
    suspend fun updateUserInfo(
        @Body name: UserForChange
    ): User
}
