package com.cheng.github_client

import android.app.Application
import com.cheng.github_client.common.loadmore.LoadMoreHelper
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.CoilHelper
import com.cheng.github_client.utils.isMainProcess
/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
class GithubApplication: Application() {

    companion object {
        lateinit var instance: GithubApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 主进程初始化
        if (isMainProcess(this)) {
            handleInit()
        }
    }

    private fun handleInit() {
        LoadMoreHelper.init()
        CoilHelper.init(this)
        ActivityHelper.init(this)
//        DayNightHelper.init()
    }
}