package com.cheng.github_client.view.adapter

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cheng.github_client.R
import com.cheng.github_client.utils.load
import com.cheng.github_client.view.model.FollowersEntity
import com.cheng.github_client.view.model.OpenSourceEntity
import kotlinx.android.synthetic.main.fragment_profile.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class OpenSourceAdapter : BaseQuickAdapter<OpenSourceEntity, BaseViewHolder>(R.layout.item_open_source) {

    override fun convert(helper: BaseViewHolder, item: OpenSourceEntity) {
        helper.setText(R.id.tvTitle, item.title)
        helper.setText(R.id.tvLink, item.link)
    }
}
