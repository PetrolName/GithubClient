package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.utils.store.SearchHistoryStore


class FollowingRepository {

    suspend fun getFollowing(userName: String) =
        RetrofitClient.apiService.getFollowing(userName)
}