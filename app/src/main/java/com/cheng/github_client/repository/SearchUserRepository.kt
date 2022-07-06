package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient


/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchUserRepository {

    suspend fun search(keywords: String, page: Int, perPage: Int) =
        RetrofitClient.apiService.searchUsers(keywords, page, perPage)

//    suspend fun getUsers(since: Int, page: Int, perPage: Int) =
//        RetrofitClient.apiService.getUsers(since, page, perPage)


}