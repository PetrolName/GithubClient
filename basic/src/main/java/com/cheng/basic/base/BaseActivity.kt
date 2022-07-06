package com.cheng.basic.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cheng.basic.R
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStatusBar()
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData()
    }

    protected abstract fun getLayoutId(): Int

    protected open fun setupStatusBar() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
        }
    }

    /**
     * View初始化相关
    */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 数据初始化相关
     */
    open fun initData() {

    }


}