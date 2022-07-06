package com.cheng.github_client.net.api

import androidx.annotation.Keep
import com.cheng.basic.exception.ApiException

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
@Keep
data class ApiResult<T>(
    val errorCode: Int,
    val errorMsg: String,
    private val data: T?
) {
    fun apiData(): T {
        if (errorCode == 0 && data != null) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}
