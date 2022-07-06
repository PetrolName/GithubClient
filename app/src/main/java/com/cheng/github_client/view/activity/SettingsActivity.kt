package com.cheng.github_client.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.cheng.basic.ext.showToast
import com.cheng.github_client.BuildConfig
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmActivity
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.clearCache
import com.cheng.github_client.utils.getCacheSize
import com.cheng.github_client.utils.isLogin
import com.cheng.github_client.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
@SuppressLint("SetTextI18n")
class SettingsActivity : BaseVmActivity<SettingsViewModel>() {

    override fun getLayoutId() = R.layout.activity_settings

    override fun viewModelClass() = SettingsViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {

        tvClearCache.text = getCacheSize(this)
        tvAboutUs.text = getString(R.string.current_version, BuildConfig.VERSION_NAME)

        ivBack.setOnClickListener { ActivityHelper.finish(SettingsActivity::class.java) }
        llClearCache.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_clear_cache)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    clearCache(this)
                    tvClearCache.text = getCacheSize(this)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
        llCheckVersion.setOnClickListener {
            showToast(getString(R.string.already_latest_version))
        }

        llAboutUs.setOnClickListener {
//            ActivityHelper.startActivity(
//                DetailActivity::class.java,
//                mapOf(
//                    PARAM_ARTICLE to Article(
//                        title = getString(R.string.abount_us),
//                        link = "https://github.com/jianjunxiao/wanandroid"
//                    )
//                )
//            )
        }
        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    mViewModel.logout()
                    ActivityHelper.startActivity(LoginActivity::class.java)
                    ActivityHelper.finish(SettingsActivity::class.java)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
        tvLogout.isVisible = isLogin()
    }

}
