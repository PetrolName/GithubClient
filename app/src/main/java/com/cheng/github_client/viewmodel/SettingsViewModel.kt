package com.cheng.github_client.viewmodel

import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.bus.Bus
import com.cheng.github_client.bus.USER_LOGIN_STATE_CHANGED
import com.cheng.github_client.net.RetrofitClient
import com.cheng.github_client.utils.store.UserInfoStore
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SettingsViewModel : BaseViewModel() {
    fun logout() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
        Bus.post(USER_LOGIN_STATE_CHANGED, false)
    }
}