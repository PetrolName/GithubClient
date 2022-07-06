package com.cheng.github_client.utils

import com.cheng.github_client.utils.store.UserInfoStore


/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe: 是否登录
 */
fun isLogin() = UserInfoStore.getUserInfo() != null
