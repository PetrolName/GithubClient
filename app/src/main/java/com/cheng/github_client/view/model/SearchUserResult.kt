package com.cheng.github_client.view.model
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
data class SearchUserResult(
    val incomplete_results: Boolean,
    val items: MutableList<UserInfo>,
    val total_count: Int
)