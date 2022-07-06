package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.ext.concat
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.common.loadmore.LoadMoreStatus
import com.cheng.github_client.repository.ReposRepository
import com.cheng.github_client.view.model.RepoEntity

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class ReposViewModel : BaseViewModel() {


    private val reposRepository by lazy { ReposRepository() }

    val repoList = MutableLiveData<MutableList<RepoEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getPros(userName: String) {
        launch(
            block = {

                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val reposList = reposRepository.getPros(userName)
                repoList.value = reposList
                refreshStatus.value = false
                emptyStatus.value = reposList?.isEmpty()
            },
            error = {
                println(it.message)
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}