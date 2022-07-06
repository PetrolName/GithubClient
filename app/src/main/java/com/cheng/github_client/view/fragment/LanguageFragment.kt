package com.cheng.github_client.view.fragment

import androidx.core.view.isVisible
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmFragment
import com.cheng.github_client.common.ScrollToTop
import com.cheng.github_client.common.loadmore.setLoadMoreStatus
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.activity.DetailActivity
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.adapter.SearchResultAdapter
import com.cheng.github_client.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.include_reload.*
import kotlinx.android.synthetic.main.layout_list.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class LanguageFragment : BaseVmFragment<SearchResultViewModel>(), ScrollToTop {

    companion object {
        fun newInstance() = LanguageFragment()
    }

    override fun getLayoutId(): Int = R.layout.layout_list

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun viewModelClass() = SearchResultViewModel::class.java

    private lateinit var mKeyWords: String

    override fun initView() {
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMore()
            }

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
            setOnRefreshListener { mViewModel.search(type = SearchActivity.PARAM_TYPE_REPO) }
        }
    }

    private fun initListeners() {
        btnReload.setOnClickListener {
            mViewModel.search(type = SearchActivity.PARAM_TYPE_REPO)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.repoList.observe(viewLifecycleOwner, {
            searchResultAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(viewLifecycleOwner, {
            emptyView.isVisible = it
        })
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            searchResultAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
        mViewModel.reloadStatus.observe(viewLifecycleOwner, {
            reloadView.isVisible = it
        })

    }

    //懒加载，提升体验
    override fun lazyLoadData() {
        mKeyWords?.let {
            mViewModel.search(mKeyWords, SearchActivity.PARAM_TYPE_REPO)
        }
    }

    fun setKeyWord(searchWords: String) :LanguageFragment {
        mKeyWords = searchWords
        return this
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

}