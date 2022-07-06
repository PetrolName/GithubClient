package com.cheng.github_client.common.loadmore

import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.cheng.github_client.common.loadmore.CommonLoadMoreView

object LoadMoreHelper {

    @JvmStatic
    fun init() {
        LoadMoreModuleConfig.defLoadMoreView = CommonLoadMoreView()
    }
}