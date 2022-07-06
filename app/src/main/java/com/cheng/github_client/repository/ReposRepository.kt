package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.utils.store.SearchHistoryStore


class ReposRepository {

    suspend fun getPros(userName: String) =
        RetrofitClient.apiService.getPros(userName)
}