package com.cheng.github_client.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cheng.basic.base.BaseFragment
import com.cheng.github_client.R
import com.cheng.github_client.common.ScrollToTop
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.view.activity.MainActivity
import com.cheng.github_client.view.activity.SearchActivity
import com.cheng.github_client.view.adapter.SimpleFragmentPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class HomeFragment : BaseFragment(), ScrollToTop {

    private lateinit var fragments: List<Fragment>
    private var currentOffset = 0

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        fragments = listOf(
            LanguageFragment.newInstance().setKeyWord("kotlin"),
            LanguageFragment.newInstance().setKeyWord("Java"),
            LanguageFragment.newInstance().setKeyWord("PHP"),
            LanguageFragment.newInstance().setKeyWord("HTML"),
            LanguageFragment.newInstance().setKeyWord("Go")
        )
        val titles = listOf<CharSequence>(
            getString(R.string.fragment_kotlin),
            getString(R.string.fragment_Java),
            getString(R.string.fragment_PHP),
            getString(R.string.fragment_HTML),
            getString(R.string.fragment_Go),
        )
        viewPager.adapter = SimpleFragmentPagerAdapter(
            fm = childFragmentManager,
            fragments = fragments,
            titles = titles
        )
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(viewPager)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
        llSearch.setOnClickListener { ActivityHelper.startActivity(SearchActivity::class.java, mapOf(SearchActivity.PARAM_TYPE to SearchActivity.PARAM_TYPE_REPO)) }
    }

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }
}