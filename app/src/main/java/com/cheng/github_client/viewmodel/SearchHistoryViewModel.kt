package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.repository.SearchHistoryRepository
import com.cheng.github_client.view.activity.SearchActivity
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchHistoryViewModel : BaseViewModel() {

    private val searchHistoryRepository by lazy { SearchHistoryRepository() }

    val searchHistory = MutableLiveData<MutableList<String>>()


    fun getSearchHistory(type: String) {
        searchHistory.value =  when(type) {
            SearchActivity.PARAM_TYPE_USER -> searchHistoryRepository.getSearchUserHistory()
            else -> searchHistoryRepository.getSearchHistory()
        }

    }

    fun addSearchHistory(searchWords: String, type: String) {
        val history = searchHistory.value ?: mutableListOf()
        if (history.contains(searchWords)) {
            history.remove(searchWords)
        }
        history.add(0, searchWords)
        searchHistory.value = history
        when(type) {
            SearchActivity.PARAM_TYPE_USER -> searchHistoryRepository.saveSearchUserHistory(searchWords)
            else -> searchHistoryRepository.saveSearchHistory(searchWords)
        }
    }

    fun deleteSearchHistory(searchWords: String, type: String) {
        val history = searchHistory.value ?: mutableListOf()
        if (history.contains(searchWords)) {
            history.remove(searchWords)
            searchHistory.value = history
            when(type) {
                SearchActivity.PARAM_TYPE_USER -> searchHistoryRepository.deleteSearchUserHistory(searchWords)
                else -> searchHistoryRepository.deleteSearchHistory(searchWords)
            }
        }
    }
}
