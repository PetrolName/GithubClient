package com.cheng.github_client.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmActivity
import com.cheng.github_client.common.loadmore.setLoadMoreStatus
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.store.UserInfoStore
import com.cheng.github_client.view.adapter.SearchResultAdapter
import com.cheng.github_client.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.layout_list.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class ReposActivity : BaseVmActivity<ReposViewModel>() {

    override fun getLayoutId() = R.layout.activity_repos

    override fun viewModelClass() = ReposViewModel::class.java

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun initView(savedInstanceState: Bundle?) {
        ivClose.setOnClickListener { ActivityHelper.finish(ReposActivity::class.java) }
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val repoEntity = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_TITLE to repoEntity.full_name.toString(), DetailActivity.PARAM_URL to repoEntity.html_url.toString())
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
        mViewModel.repoList.observe(this@ReposActivity, {
            searchResultAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(this@ReposActivity, {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(this@ReposActivity, {
            emptyView.isVisible = it
        })
        mViewModel.reloadStatus.observe(this@ReposActivity, {
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
            mViewModel.getPros(it.login!!)
        }
    }

}
