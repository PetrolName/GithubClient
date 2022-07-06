package com.cheng.github_client.utils.store

import com.cheng.github_client.GithubApplication
import com.cheng.github_client.utils.MoshiHelper
import com.cheng.github_client.utils.getSpValue
import com.cheng.github_client.utils.putSpValue


/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
object SearchHistoryStore {

    private const val SP_SEARCH_HISTORY = "sp_search_history"
    private const val KEY_SEARCH_HISTORY = "searchHistory"
    private const val KEY_SEARCH_USER_HISTORY = "searchUserHistory"

    fun saveSearchHistory(words: String) {
        val history = getSearchHistory()
        if (history.contains(words)) {
            history.remove(words)
        }
        history.add(0, words)
        val listStr = MoshiHelper.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_HISTORY, listStr)
    }

    fun deleteSearchHistory(words: String) {
        val history = getSearchHistory()
        history.remove(words)
        val listStr = MoshiHelper.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_HISTORY, listStr)
    }

    fun getSearchHistory(): MutableList<String> {
        val listStr = getSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_HISTORY, "")
        return if (listStr.isEmpty()) {
            mutableListOf()
        } else {
            MoshiHelper.fromJson<MutableList<String>>(listStr) ?: mutableListOf()
        }
    }

    fun saveSearchUserHistory(words: String) {
        val history = getSearchUserHistory()
        if (history.contains(words)) {
            history.remove(words)
        }
        history.add(0, words)
        val listStr = MoshiHelper.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_USER_HISTORY, listStr)
    }

    fun deleteSearchUserHistory(words: String) {
        val history = getSearchUserHistory()
        history.remove(words)
        val listStr = MoshiHelper.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_USER_HISTORY, listStr)
    }

    fun getSearchUserHistory(): MutableList<String> {
        val listStr = getSpValue(SP_SEARCH_HISTORY, GithubApplication.instance, KEY_SEARCH_USER_HISTORY, "")
        return if (listStr.isEmpty()) {
            mutableListOf()
        } else {
            MoshiHelper.fromJson<MutableList<String>>(listStr) ?: mutableListOf()
        }
    }
}
