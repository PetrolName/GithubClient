package com.cheng.basic.exception
/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
class ApiException(var code: Int, override var message: String) : RuntimeException()