package com.cheng.github_client.utils.store

import com.cheng.github_client.GithubApplication
import com.cheng.github_client.utils.MoshiHelper
import com.cheng.github_client.utils.clearSpValue
import com.cheng.github_client.utils.getSpValue
import com.cheng.github_client.utils.putSpValue
import com.cheng.github_client.view.model.UserInfo


/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe: 用户信息存储
 */
object UserInfoStore {

    private const val SP_USER_INFO = "sp_user_info"
    private const val KEY_USER_INFO = "userInfo"

    /**
     * 获取本地sp存储的用户信息
     */
    fun getUserInfo(): UserInfo? {
        val userInfoStr = getSpValue(SP_USER_INFO, GithubApplication.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            MoshiHelper.fromJson<UserInfo>(userInfoStr)
        } else {
            null
        }
    }

    /**
     * 设置用户信息、保存本地sp
     */
    fun setUserInfo(userInfo: UserInfo) =
        putSpValue(SP_USER_INFO, GithubApplication.instance, KEY_USER_INFO, MoshiHelper.toJson(userInfo))

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        clearSpValue(SP_USER_INFO, GithubApplication.instance)
    }

}