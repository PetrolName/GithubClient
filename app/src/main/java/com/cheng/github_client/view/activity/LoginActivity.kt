package com.cheng.github_client.view.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmActivity
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {

    override fun getLayoutId() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        ivClose.setOnClickListener {
            ActivityHelper.finish(LoginActivity::class.java)
        }
        tietPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_GO) {
                btnLogin.performClick()
                true
            } else {
                false
            }
        }
        btnLogin.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@LoginActivity, {
                if (it) showProgressDialog(R.string.logging_in) else dismissProgressDialog()
            })
            loginResult.observe(this@LoginActivity, {
                if (it) {
                    ActivityHelper.finish(LoginActivity::class.java)
                }
            })
        }
    }

}
