package com.cheng.github_client.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.cheng.basic.base.BaseFragment
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.isLogin
import com.cheng.github_client.view.activity.LoginActivity

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIntent()
        initViewModel()
        observe()
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    /**
     * 获取Intent的参数
     */
    open fun initIntent() {

    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 获取ViewModel的class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {
        // 登录失效，跳转登录页
//        mViewModel.mRequestStatus.observe(viewLifecycleOwner, {
//            if (it) {
//                Bus.post(USER_LOGIN_STATE_CHANGED, false)
//                ActivityHelper.startActivity(LoginActivity::class.java)
//            }
//        })
    }

    /**
     * View初始化相关
     */
    protected abstract fun initView()

    /**
     * 数据初始化相关
     */
    open fun initData() {

    }


    /**
     * 懒加载数据
     */
    open fun lazyLoadData() {

    }

    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (isLogin()) {
            then?.invoke()
            true
        } else {
            ActivityHelper.startActivity(LoginActivity::class.java)
            false
        }
    }

}
