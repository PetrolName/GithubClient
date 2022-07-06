package com.cheng.github_client.view.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.cheng.basic.base.BaseActivity
import com.cheng.basic.ext.hideSoftInput
import com.cheng.github_client.R
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.fragment.SearchHistoryFragment
import com.cheng.github_client.view.fragment.SearchResultFragment
import kotlinx.android.synthetic.main.activity_search.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchActivity: BaseActivity()  {

    companion object {
        const val PARAM_TYPE = "param_type"
        const val PARAM_TYPE_REPO = "param_type_repo"
        const val PARAM_TYPE_USER = "param_type_user"
    }

    private var mType: String = PARAM_TYPE_REPO

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {
        mType = intent?.getStringExtra(PARAM_TYPE)?: return
        val historyFragment = SearchHistoryFragment.newInstance(mType)
        val resultFragment = SearchResultFragment.newInstance(mType)

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, historyFragment)
            .add(R.id.flContainer, resultFragment)
            .show(historyFragment)
            .hide(resultFragment)
            .commit()

        ivBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else {
                ActivityHelper.finish(SearchActivity::class.java)
            }
        }
        ivDone.setOnClickListener {
            val searchWords = acetInput.text.toString()
            if (searchWords.isEmpty()) return@setOnClickListener
            it.hideSoftInput()
            historyFragment.addSearchHistory(searchWords)
            resultFragment.doSearch(searchWords)
            supportFragmentManager
                .beginTransaction()
                .show(resultFragment)
                .commit()
        }
        acetInput.run {
            addTextChangedListener(afterTextChanged = {
                ivClearSearch.isGone = it.isNullOrEmpty()
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ivDone.performClick()
                    true
                } else {
                    false
                }
            }
        }
        ivClearSearch.setOnClickListener { acetInput.setText("") }
        acetInput.setHint(if (mType == PARAM_TYPE_USER) R.string.search_user_hint else R.string.search_repo_hint)
    }

    fun fillSearchInput(keywords: String) {
        acetInput.setText(keywords)
        acetInput.setSelection(keywords.length)
        ivDone.performClick()
    }

    override fun onBackPressed() {
        ivBack.performClick()
    }
}