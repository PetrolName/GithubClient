package com.cheng.github_client.repository

import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.view.activity.SearchActivity


/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchResultRepository {

    suspend fun search(keywords: String, page: Int, perPage: Int, type: String) =
        when(type) {
            SearchActivity.PARAM_TYPE_USER -> RetrofitClient.apiService.searchUsers(keywords, page, perPage)
            else -> RetrofitClient.apiService.searchRepos(keywords, page, perPage)
        }


}