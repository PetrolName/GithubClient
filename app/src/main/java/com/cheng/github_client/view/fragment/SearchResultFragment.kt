package com.cheng.github_client.view.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmFragment
import com.cheng.github_client.common.loadmore.setLoadMoreStatus
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.activity.DetailActivity
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.adapter.SearchResultAdapter
import com.cheng.github_client.view.adapter.UserAdapter
import com.cheng.github_client.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.layout_list.*

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchResultFragment : BaseVmFragment<SearchResultViewModel>() {

    companion object {
        fun newInstance(type: String): SearchResultFragment {
                return SearchResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(SearchActivity.PARAM_TYPE, type)
                    }
                }
        }
    }
    private lateinit var mType: String

    override fun getLayoutId(): Int = R.layout.layout_list

    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var userAdapter: UserAdapter

    override fun viewModelClass() = SearchResultViewModel::class.java

    override fun initIntent() {
        arguments?.run { mType = getString(SearchActivity.PARAM_TYPE, "") }
    }

    override fun initView() {
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        if (mType == SearchActivity.PARAM_TYPE_USER) {
            userAdapter = UserAdapter().also {
                it.loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMore()
                }

                it.setOnItemClickListener { _, _, position ->
                    val userInfo = it.data[position]
                    ActivityHelper.startActivity(
                        DetailActivity::class.java,
                        mapOf(DetailActivity.PARAM_TITLE to userInfo.login.toString(), DetailActivity.PARAM_URL to userInfo.html_url.toString())
                    )
                }
                recyclerView.adapter = it
            }
        } else {
            searchResultAdapter = SearchResultAdapter().also {
                it.loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMore()
                }

                it.setOnItemClickListener { _, _, position ->
                    val repoEntity = it.data[position]
                    ActivityHelper.startActivity(
                        DetailActivity::class.java,
                        mapOf(
                            DetailActivity.PARAM_TITLE to repoEntity.full_name.toString(),
                            DetailActivity.PARAM_URL to repoEntity.html_url.toString()
                        )
                    )
                }
                recyclerView.adapter = it
            }
        }
    }

    private fun initRefresh() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search(type = mType) }
        }
    }

    private fun initListeners() {
        btnReload.setOnClickListener {
            mViewModel.search(type = mType)
        }
    }

    override fun observe() {
        super.observe()
        if (mType == SearchActivity.PARAM_TYPE_USER) {
            mViewModel.userInfoList.observe(viewLifecycleOwner, {
                userAdapter.setList(it)
            })
        } else {
            mViewModel.repoList.observe(viewLifecycleOwner, {
                searchResultAdapter.setList(it)
            })
        }

        mViewModel.refreshStatus.observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(viewLifecycleOwner, {
            emptyView.isVisible = it
        })
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            if (mType == SearchActivity.PARAM_TYPE_USER) {
                userAdapter.loadMoreModule.setLoadMoreStatus(it)
            } else {
                searchResultAdapter.loadMoreModule.setLoadMoreStatus(it)
            }
        })
        mViewModel.reloadStatus.observe(viewLifecycleOwner, {
            reloadView.isVisible = it
        })

    }

    fun doSearch(searchWords: String) {
        mViewModel.search(searchWords, mType)
    }

}