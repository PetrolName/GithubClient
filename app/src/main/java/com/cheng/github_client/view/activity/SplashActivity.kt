package com.cheng.github_client.view.activity

import android.os.Bundle
import com.cheng.basic.base.BaseActivity
import com.cheng.github_client.R
import com.cheng.github_client.utils.ActivityHelper

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        window.decorView.postDelayed({
            ActivityHelper.startActivity(MainActivity::class.java)
            ActivityHelper.finish(SplashActivity::class.java)
        }, 1000)
    }
}
