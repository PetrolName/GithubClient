package com.cheng.github_client.utils

import android.app.Activity
import androidx.core.app.ShareCompat
import com.cheng.github_client.R

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
fun share(
    activity: Activity,
    title: String? = activity.getString(R.string.app_name),
    content: String?
) {
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setSubject(title)
        .setText(content)
        .setChooserTitle(title)
        .startChooser()
}