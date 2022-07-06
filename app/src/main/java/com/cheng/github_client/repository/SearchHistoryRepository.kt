package com.cheng.github_client.repository

import com.cheng.github_client.utils.store.SearchHistoryStore


class SearchHistoryRepository {

    fun saveSearchHistory(searchWords: String) {
        SearchHistoryStore.saveSearchHistory(searchWords)
    }

    fun deleteSearchHistory(searchWords: String) {
        SearchHistoryStore.deleteSearchHistory(searchWords)
    }

    fun getSearchHistory() = SearchHistoryStore.getSearchHistory()

    fun saveSearchUserHistory(searchWords: String) {
        SearchHistoryStore.saveSearchUserHistory(searchWords)
    }

    fun deleteSearchUserHistory(searchWords: String) {
        SearchHistoryStore.deleteSearchUserHistory(searchWords)
    }

    fun getSearchUserHistory() = SearchHistoryStore.getSearchUserHistory()
}