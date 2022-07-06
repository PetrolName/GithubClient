package com.cheng.basic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheng.basic.exception.ApiException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
open class BaseViewModel : ViewModel() {

    companion object {
        const val LOGIN_STATUS_INVALID = 1  //登录状态失效
        const val NETWORK_EXCEPTION = 2     //网络请求
        const val DATA_ANALYSIS_ERROR = 3   //数据解析
        const val OTHER_ERROR = 4           //其他错误
    }

    val mRequestStatus: MutableLiveData<Int> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时只需
     * @param showErrorToast 是否弹出错误吐司
     * @return Job
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误Toast
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    //这里可以处理登录状态失效
                    -1001 -> {
                        mRequestStatus.value = LOGIN_STATUS_INVALID
                    }
                    // 其他错误
                    else -> mRequestStatus.value = OTHER_ERROR
                }
            }
            // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                mRequestStatus.value = NETWORK_EXCEPTION
            // 数据解析错误
            is JsonDataException, is JsonEncodingException ->
                mRequestStatus.value = DATA_ANALYSIS_ERROR
            // 其他错误
            else ->
                mRequestStatus.value = OTHER_ERROR
        }
    }
}