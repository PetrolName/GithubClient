package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.common.loadmore.LoadMoreStatus
import com.cheng.github_client.repository.FollowerRepository
import com.cheng.github_client.view.model.FollowersEntity

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class FollowerViewModel : BaseViewModel() {


    private val followerRepository by lazy { FollowerRepository() }

    val followerList = MutableLiveData<MutableList<FollowersEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getFollowers(userName: String) {
        launch(
            block = {

                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val tempFollowerList = followerRepository.getFollowers(userName)
                followerList.value = tempFollowerList
                refreshStatus.value = false
                emptyStatus.value = tempFollowerList?.isEmpty()
            },
            error = {
                println(it.message)
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}