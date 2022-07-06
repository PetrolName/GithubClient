package com.cheng.github_client.view.activity

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmActivity
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.Logger
import com.cheng.github_client.utils.htmlToSpanned
import com.cheng.github_client.utils.store.SettingsStore
import com.cheng.github_client.utils.whiteHostList
import com.cheng.github_client.view.fragment.ActionFragment
import com.cheng.github_client.view.model.RepoEntity
import com.cheng.github_client.viewmodel.DetailViewModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_detail.*

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class DetailActivity : BaseVmActivity<DetailViewModel>() {

    companion object {
        const val PARAM_REPO = "param_repo"
        const val PARAM_TITLE = "param_title"
        const val PARAM_URL = "param_url"
    }

    private lateinit var mTitle: String
    private lateinit var mUrl: String

    private var agentWeb: AgentWeb? = null

    override fun getLayoutId() = R.layout.activity_detail

    override fun viewModelClass() = DetailViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {

        mTitle = intent?.getStringExtra(PARAM_TITLE) ?: return
        mUrl = intent?.getStringExtra(PARAM_URL) ?: return

        setDetailTitle(mTitle.htmlToSpanned())

        ivBack.setOnClickListener {
            ActivityHelper.finish(DetailActivity::class.java)
        }
        ivMore.setOnClickListener {
            ActionFragment.newInstance(mTitle, mUrl).show(supportFragmentManager)
        }

    }

    override fun initData() {
//        if (repo.id != 0L) {
//            mViewModel.saveReadHistory(repo)
//        }
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContainer, LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.btnReload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(webChromeClient)
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                textZoom = SettingsStore.getWebTextZoom()
            }
        }
        agentWeb?.urlLoader?.loadUrl(mUrl)
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            setDetailTitle(title, true)
            super.onReceivedTitle(view, title)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Logger.d("WebView", consoleMessage?.message())
            return super.onConsoleMessage(consoleMessage)
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return !whiteHostList().contains(request?.url?.host)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view?.loadUrl(customJs(url))
        }
    }

    /**
     * 加载js，去掉掘金、简书、CSDN等H5页面的Title、底部操作栏，以及部分广告
     */
    private fun customJs(url: String? = mUrl): String {
        val js = StringBuilder()
        js.append("javascript:(function(){")
        when (Uri.parse(url).host) {
            "juejin.im", "juejin.cn" -> {
                js.append("var followButtonList = document.getElementsByClassName('follow-button');")
                js.append("if(followButtonList&&followButtonList.length){followButtonList[0].parentNode.removeChild(followButtonList[0])}")
                js.append("var articleBannerList = document.getElementsByClassName('article-banner');")
                js.append("if(articleBannerList&&articleBannerList.length){articleBannerList[0].parentNode.removeChild(articleBannerList[0])}")
                js.append("var subscribeBtnList = document.getElementsByClassName('subscribe-btn');")
                js.append("if(subscribeBtnList&&subscribeBtnList.length){subscribeBtnList[0].parentNode.removeChild(subscribeBtnList[0])}")
                js.append("var headerList = document.getElementsByClassName('main-header-box');")
                js.append("if(headerList&&headerList.length){headerList[0].parentNode.removeChild(headerList[0])}")
                js.append("var openAppList = document.getElementsByClassName('open-in-app');")
                js.append("if(openAppList&&openAppList.length){openAppList[0].parentNode.removeChild(openAppList[0])}")
                js.append("var actionBox = document.getElementsByClassName('action-box');")
                js.append("if(actionBox&&actionBox.length){actionBox[0].parentNode.removeChild(actionBox[0])}")
                js.append("var actionBarList = document.getElementsByClassName('action-bar');")
                js.append("if(actionBarList&&actionBarList.length){actionBarList[0].parentNode.removeChild(actionBarList[0])}")
                js.append("var columnViewList = document.getElementsByClassName('column-view');")
                js.append("if(columnViewList&&columnViewList.length){columnViewList[0].style.margin = '0px'}")
            }
            "www.jianshu.com" -> {
                js.append("var badgeItemList = document.getElementsByClassName('badge-item');")
                js.append("if(badgeItemList&&badgeItemList.length){badgeItemList[0].parentNode.removeChild(badgeItemList[0])}")
                js.append("var appOpenList = document.getElementsByClassName('app-open');")
                js.append("if(appOpenList&&appOpenList.length){appOpenList[0].parentNode.removeChild(appOpenList[0])}")
                js.append("var jianshuHeader = document.getElementById('jianshu-header');")
                js.append("if(jianshuHeader){jianshuHeader.parentNode.removeChild(jianshuHeader)}")
                js.append("var commentMain = document.getElementById('comment-main');")
                js.append("if(commentMain){commentMain.parentNode.removeChild(commentMain)}")
                js.append("var footer = document.getElementById('footer');")
                js.append("if(footer){footer.parentNode.removeChild(footer)}")
                js.append("var revealAd = document.getElementById('reveal-ad');")
                js.append("if(revealAd){revealAd.parentNode.removeChild(revealAd)}")
                js.append("var headerShimList = document.getElementsByClassName('header-shim');")
                js.append("if(headerShimList&&headerShimList.length){headerShimList[0].parentNode.removeChild(headerShimList[0])}")
                js.append("var fubiaoList = document.getElementsByClassName('fubiao-dialog');")
                js.append("if(fubiaoList&&fubiaoList.length){fubiaoList[0].parentNode.removeChild(fubiaoList[0])}")
                js.append("var ads = document.getElementsByClassName('note-comment-above-ad-wrap');")
                js.append("if(ads&&ads.length){ads[0].parentNode.removeChild(ads[0])}")
                js.append("var lazyShimList = document.getElementsByClassName('v-lazy-shim');")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[0]){lazyShimList[0].parentNode.removeChild(lazyShimList[0])}")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[1]){lazyShimList[1].parentNode.removeChild(lazyShimList[1])}")
                js.append("var callAppBtnList = document.getElementsByClassName('call-app-btn');")
                js.append("if(callAppBtnList&&callAppBtnList.length){callAppBtnList[0].parentNode.removeChild(callAppBtnList[0])}")
            }
            "blog.csdn.net" -> {
                js.append("var detailFollow = document.getElementById('detailFollow');")
                js.append("if(detailFollow){detailFollow.parentNode.removeChild(detailFollow)}")
                js.append("var csdnToolBar = document.getElementById('csdn-toolbar');")
                js.append("if(csdnToolBar){csdnToolBar.parentNode.removeChild(csdnToolBar)}")
                js.append("var csdnMain = document.getElementById('main');")
                js.append("if(csdnMain){csdnMain.style.margin='0px'}")
                js.append("var operate = document.getElementById('operate');")
                js.append("if(operate){operate.parentNode.removeChild(operate)}")
                js.append("var haveHeartCountList = document.getElementsByClassName('have-heart-count');")
                js.append("if(haveHeartCountList&&haveHeartCountList.length){haveHeartCountList[0].parentNode.removeChild(haveHeartCountList[0])}")
                js.append("var asideHeaderFixedList = document.getElementsByClassName('aside-header-fixed');")
                js.append("if(asideHeaderFixedList&&asideHeaderFixedList.length){asideHeaderFixedList[0].parentNode.removeChild(asideHeaderFixedList[0])}")
                js.append("var feedSignSpanList = document.getElementsByClassName('feed-Sign-span');")
                js.append("if(feedSignSpanList&&feedSignSpanList.length){feedSignSpanList[0].parentNode.removeChild(feedSignSpanList[0])}")
            }
        }
        js.append("})()")
        return js.toString()
    }

    /**
     * 设置标题
     */
    private fun setDetailTitle(title: CharSequence?, isSelected: Boolean = false) {
        tvTitle.text = title
        tvTitle.postDelayed({ tvTitle.isSelected = isSelected }, 500)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()

    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    fun refreshPage() {
        agentWeb?.urlLoader?.reload()
    }

}
