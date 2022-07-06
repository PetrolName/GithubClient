package com.cheng.github_client.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cheng.basic.viewmodel.BaseViewModel
import com.cheng.github_client.bus.Bus
import com.cheng.github_client.bus.USER_LOGIN_STATE_CHANGED
import com.cheng.github_client.repository.LoginRepository
import com.cheng.github_client.utils.store.UserInfoStore

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }
    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()


    fun login(account: String, password: String) {
        launch(
            block = {
                submitting.value = true
                val userInfo = loginRepository.login()
                UserInfoStore.setUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                println(it.message)
                submitting.value = false
                loginResult.value = false
            }
        )
    }

}