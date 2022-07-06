package com.cheng.github_client.view.fragment

import androidx.core.view.isVisible
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmFragment
import com.cheng.github_client.common.ScrollToTop
import com.cheng.github_client.common.loadmore.setLoadMoreStatus
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.activity.DetailActivity
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.adapter.FollowersAdapter
import com.cheng.github_client.view.adapter.SearchResultAdapter
import com.cheng.github_client.view.adapter.UserAdapter
import com.cheng.github_client.viewmodel.DiscoveryViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.layout_list.*

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class DiscoveryFragment : BaseVmFragment<DiscoveryViewModel>(), ScrollToTop {

    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_discovery

    private lateinit var mAdapter: UserAdapter

    override fun viewModelClass() = DiscoveryViewModel::class.java

    override fun initView() {
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        mAdapter = UserAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val userInfo = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_TITLE to userInfo.login.toString(), DetailActivity.PARAM_URL to userInfo.html_url.toString())
                )
            }
            recyclerView.adapter = it
        }
    }

    private fun initRefresh() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { getData() }
        }
    }

    private fun initListeners() {
        btnReload.setOnClickListener {
            mViewModel.getData(100)
        }
        llSearch.setOnClickListener { ActivityHelper.startActivity(SearchActivity::class.java, mapOf(SearchActivity.PARAM_TYPE to SearchActivity.PARAM_TYPE_USER))}
    }

    override fun observe() {
        super.observe()
        mViewModel.repoList.observe(viewLifecycleOwner, {
            mAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(viewLifecycleOwner, {
            emptyView.isVisible = it
        })
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            mAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
        mViewModel.reloadStatus.observe(viewLifecycleOwner, {
            reloadView.isVisible = it
        })

    }

    override fun initData() {
        getData()
    }

    private fun getData() {
        mViewModel.getData(100)
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}