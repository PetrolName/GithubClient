package com.cheng.github_client.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.cheng.basic.base.BaseActivity
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.isLogin
import com.cheng.github_client.view.activity.LoginActivity
import com.cheng.github_client.view.fragment.ProgressDialogFragment

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment
    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initViewModelData()
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
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {
        // 登录失效，跳转登录页
//        mViewModel.mRequestStatus.observe(this, {
//            if (it) {
//                Bus.post(USER_LOGIN_STATE_CHANGED, false)
//                ActivityHelper.startActivity(LoginActivity::class.java)
//            }
//        })
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

    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

    /**
     * 数据初始化相关
     */
    open fun initViewModelData() {

    }

}
