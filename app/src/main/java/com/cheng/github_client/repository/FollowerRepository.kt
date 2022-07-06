package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.utils.store.SearchHistoryStore


class FollowerRepository {

    suspend fun getFollowers(userName: String) =
        RetrofitClient.apiService.getFollowers(userName)
}