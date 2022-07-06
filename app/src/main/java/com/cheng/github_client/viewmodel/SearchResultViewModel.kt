package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.ext.concat
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.common.loadmore.LoadMoreStatus
import com.cheng.github_client.repository.SearchResultRepository
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.model.RepoEntity
import com.cheng.github_client.view.model.SearchRepoResult
import com.cheng.github_client.view.model.SearchUserResult
import com.cheng.github_client.view.model.UserInfo

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchResultViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_PER_PAGE = 20
    }

    private val searchResultRepository by lazy { SearchResultRepository() }

    val repoList = MutableLiveData<MutableList<RepoEntity>>()
    val userInfoList = MutableLiveData<MutableList<UserInfo>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    private var currentKeywords = ""
    private var page = INITIAL_PAGE

    private lateinit var mType: String

    fun search(keywords: String = currentKeywords, type: String) {
        launch(
            block = {
                if (currentKeywords != keywords) {
                    currentKeywords = keywords
                    repoList.value = emptyList<RepoEntity>().toMutableList()
                }

                mType = type

                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val searchResult = searchResultRepository.search(keywords, INITIAL_PAGE, INITIAL_PER_PAGE, mType)
                page = 2
                if (mType == SearchActivity.PARAM_TYPE_USER) {
                    val searchUserResult = searchResult as SearchUserResult
                    userInfoList.value = searchUserResult.items
                    refreshStatus.value = false
                    emptyStatus.value = searchUserResult.items?.isEmpty()
                } else {
                    val searchRepoResult = searchResult as SearchRepoResult
                    repoList.value = searchRepoResult.items
                    refreshStatus.value = false
                    emptyStatus.value = searchRepoResult.items?.isEmpty()
                }


            },
            error = {
                println(it.message)
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMore() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING
                val searchResult = searchResultRepository.search(currentKeywords, page, INITIAL_PER_PAGE, mType)
                page++
                if (mType == SearchActivity.PARAM_TYPE_USER) {
                    val searchUserResult = searchResult as SearchUserResult
                    userInfoList.value = userInfoList.value.concat(searchUserResult.items)
                    loadMoreStatus.value = if (searchUserResult.incomplete_results) {
                        LoadMoreStatus.END
                    } else {
                        LoadMoreStatus.COMPLETED
                    }
                } else {
                    val searchRepoResult = searchResult as SearchRepoResult
                    repoList.value = repoList.value.concat(searchRepoResult.items)
                    loadMoreStatus.value = if (searchRepoResult.incomplete_results) {
                        LoadMoreStatus.END
                    } else {
                        LoadMoreStatus.COMPLETED
                    }
                }

            },
            error = {
                println(it.message)
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}