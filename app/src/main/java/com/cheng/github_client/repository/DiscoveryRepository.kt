package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.utils.store.SearchHistoryStore


class DiscoveryRepository {

    suspend fun getUsers(since: Int, perPage: Int) =
        RetrofitClient.apiService.getUsers(since, perPage)
}