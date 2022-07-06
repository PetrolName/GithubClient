package com.cheng.basic.ext

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

/**
 *  Author: PengCheng
 *  Date:   2022/7/4
 *  Describe:
 */
fun Fragment.openInExplorer(link: String?) {
    startActivity(Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(link)
    })
}