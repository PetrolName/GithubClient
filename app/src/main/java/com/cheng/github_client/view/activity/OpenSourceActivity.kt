package com.cheng.github_client.view.activity

import android.os.Bundle
import com.cheng.basic.base.BaseActivity
import com.cheng.github_client.R
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.adapter.OpenSourceAdapter
import com.cheng.github_client.view.model.OpenSourceEntity
import kotlinx.android.synthetic.main.activity_open_source.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class OpenSourceActivity: BaseActivity() {

    private val openSourceData = listOf(
        OpenSourceEntity(
            title = "OkHttp",
            link = "https://github.com/square/okhttp"
        ),
        OpenSourceEntity(
            title = "Retrofit",
            link = "https://github.com/square/retrofit"
        ),
        OpenSourceEntity(
            title = "BaseRecyclerViewAdapterHelper",
            link = "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"
        ),
        OpenSourceEntity(
            title = "Glide",
            link = "https://github.com/bumptech/glide"
        ),
        OpenSourceEntity(
            title = "RxPermissions",
            link = "https://github.com/tbruyelle/RxPermissions"
        ),
        OpenSourceEntity(
            title = "CircleImageView",
            link = "https://github.com/hdodenhof/CircleImageView"
        ),
        OpenSourceEntity(
            title = "Moshi",
            link = "https://github.com/square/moshi"
        ),
        OpenSourceEntity(
            title = "ImmersionBar",
            link = "https://github.com/gyf-dev/ImmersionBar"
        ),
        OpenSourceEntity(
            title = "AgentWeb",
            link = "https://github.com/Justson/AgentWeb"
        ),
        OpenSourceEntity(
            title = "LiveEventBus",
            link = "https://github.com/JeremyLiao/LiveEventBus"
        ),
        OpenSourceEntity(
            title = "PersistentCookieJar",
            link = "https://github.com/franmontiel/PersistentCookieJar"
        )
    )

    override fun getLayoutId(): Int = R.layout.activity_open_source

    override fun initView(savedInstanceState: Bundle?) {
        OpenSourceAdapter().also {
            it.setList(openSourceData)
            it.setOnItemClickListener { _, _, position ->
                val openSourceEntity = it.data[position]
                ActivityHelper.startActivity(DetailActivity::class.java, mapOf(DetailActivity.PARAM_TITLE to openSourceEntity.title, DetailActivity.PARAM_URL to openSourceEntity.link))
            }
            recyclerView.adapter = it
        }

        ivBack.setOnClickListener { ActivityHelper.finish(OpenSourceActivity::class.java) }
    }
}