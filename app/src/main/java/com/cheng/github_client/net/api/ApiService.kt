package com.cheng.github_client.net.api

import com.cheng.github_client.view.model.*
import retrofit2.http.*

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    /**
     * 认证
     */
    @GET("user")
    suspend fun fetchUserOwner(@Header("Authorization") authorization: String): UserInfo

    /**
     * 搜索仓库
     */
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query(value="q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): SearchRepoResult

    /**
     * 搜索用户
     */
    @GET("search/users")
    suspend fun searchUsers(
        @Query(value="q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): SearchUserResult

    /**
     * 获取用户集合
     */
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int): MutableList<UserInfo>

    /**
     * 获取用户仓库
     */
    @GET("/users/{username}/repos")
    suspend fun getPros(@Path("username") username: String): MutableList<RepoEntity>

    /**
     * 获取用户following
     */
    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): MutableList<FollowersEntity>

    /**
     * 获取用户followers
     */
    @GET("/users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): MutableList<FollowersEntity>

}