package com.cheng.github_client.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmActivity
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.store.UserInfoStore
import com.cheng.github_client.view.adapter.FollowersAdapter
import com.cheng.github_client.viewmodel.FollowerViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.layout_list.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class FollowersActivity : BaseVmActivity<FollowerViewModel>() {

    override fun getLayoutId() = R.layout.activity_follower

    override fun viewModelClass() = FollowerViewModel::class.java

    private lateinit var followerAdapter: FollowersAdapter

    override fun initView(savedInstanceState: Bundle?) {
        ivClose.setOnClickListener { ActivityHelper.finish(FollowersActivity::class.java) }
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        followerAdapter = FollowersAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val followersEntity = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_TITLE to followersEntity.login.toString(), DetailActivity.PARAM_URL to followersEntity.html_url.toString())
                )
            }
            recyclerView.adapter = it
        }
    }

    private fun initRefresh() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)

                setOnRefreshListener {
                    getData()
                }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.followerList.observe(this@FollowersActivity, {
            followerAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(this@FollowersActivity, {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(this@FollowersActivity, {
            emptyView.isVisible = it
        })
        mViewModel.reloadStatus.observe(this@FollowersActivity, {
            reloadView.isVisible = it
        })

    }

    private fun initListeners() {
        btnReload.setOnClickListener {
            getData()
        }
    }

    override fun initViewModelData() {
        getData()
    }

    private fun getData() {
        val userInfo = UserInfoStore.getUserInfo()
        userInfo?.let {
            mViewModel.getFollowers(it.login!!)
        }
    }

}
