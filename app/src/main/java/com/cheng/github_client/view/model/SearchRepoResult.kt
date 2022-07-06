package com.cheng.github_client.view.model
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
data class SearchRepoResult(
    val incomplete_results: Boolean,
    val items: MutableList<RepoEntity>,
    val total_count: Int
)