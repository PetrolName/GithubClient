package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.ext.concat
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.common.loadmore.LoadMoreStatus
import com.cheng.github_client.repository.DiscoveryRepository
import com.cheng.github_client.view.model.RepoEntity
import com.cheng.github_client.view.model.UserInfo
import kotlin.math.sin

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class DiscoveryViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_PER_PAGE = 40
    }

    private val discoveryRepository by lazy { DiscoveryRepository() }

    val repoList = MutableLiveData<MutableList<UserInfo>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    private var mSince: Int = 0

    fun getData(since: Int) {
        launch(
            block = {
                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                mSince = since

                val userInfoList = discoveryRepository.getUsers(mSince, INITIAL_PER_PAGE)
                page = 2
                repoList.value = userInfoList
                refreshStatus.value = false
                emptyStatus.value = userInfoList?.isEmpty()
                loadMoreStatus.value = LoadMoreStatus.COMPLETED
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
                val userInfoList = discoveryRepository.getUsers(mSince, INITIAL_PER_PAGE)
                page++
                repoList.value = repoList.value.concat(userInfoList)

                loadMoreStatus.value = LoadMoreStatus.COMPLETED

            },
            error = {
                println(it.message)
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}