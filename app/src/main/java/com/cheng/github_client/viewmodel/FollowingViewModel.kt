package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.common.loadmore.LoadMoreStatus
import com.cheng.github_client.repository.FollowingRepository
import com.cheng.github_client.view.model.FollowersEntity

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class FollowingViewModel : BaseViewModel() {


    private val followingRepository by lazy { FollowingRepository() }

    val followingList = MutableLiveData<MutableList<FollowersEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getFollowing(userName: String) {
        launch(
            block = {

                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val tempFollowerList = followingRepository.getFollowing(userName)
                followingList.value = tempFollowerList
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