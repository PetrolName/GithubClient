package com.cheng.github_client.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmFragment
import com.cheng.github_client.view.activity.DetailActivity
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.adapter.SearchHistoryAdapter
import com.cheng.github_client.viewmodel.SearchHistoryViewModel
import kotlinx.android.synthetic.main.fragment_search_history.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
class SearchHistoryFragment : BaseVmFragment<SearchHistoryViewModel>() {

    companion object {
        fun newInstance(type: String): SearchHistoryFragment {
            return SearchHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(SearchActivity.PARAM_TYPE, type)
                }
            }
        }
    }

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun getLayoutId(): Int = R.layout.fragment_search_history

    override fun viewModelClass() = SearchHistoryViewModel::class.java

    private lateinit var mType: String

    override fun initView() {
        arguments?.run {
            mType = getString(SearchActivity.PARAM_TYPE, "")
            val fragmentActivity = activity as? SearchActivity ?: return
            searchHistoryAdapter = SearchHistoryAdapter().also {
                it.setOnItemClickListener { _, _, position -> fragmentActivity.fillSearchInput(it.data[position])}

                it.setOnItemChildClickListener { _, view, position ->
                    if (view.id == R.id.ivDelete) {
                        mViewModel.deleteSearchHistory(it.data[position], mType)
                    }
                }
                rvSearchHistory.adapter = it
            }
        }
    }

    override fun initData() {
        mViewModel.getSearchHistory(mType)
    }

    override fun observe() {
        super.observe()
        mViewModel.searchHistory.observe(viewLifecycleOwner, {
            tvSearchHistory.isGone = it.isEmpty()
            searchHistoryAdapter.setList(it)
        })
    }

    fun addSearchHistory(keywords: String) {
        mViewModel.addSearchHistory(keywords, mType)
    }

}
