package com.cheng.github_client.utils

import androidx.core.text.HtmlCompat

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)