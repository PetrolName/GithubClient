package com.cheng.github_client.repository

import com.cheng.github_client.BuildConfig
import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.view.model.UserInfo

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class LoginRepository {

    suspend fun login(): UserInfo {
        val auth = "token ${BuildConfig.USER_ACCESS_TOKEN}"
        return RetrofitClient.apiService.fetchUserOwner(auth)
    }

}


