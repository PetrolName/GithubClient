package com.cheng.github_client.view.adapter

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cheng.github_client.R
import com.cheng.github_client.common.loadmore.BaseLoadMoreAdapter
import com.cheng.github_client.utils.load
import com.cheng.github_client.view.model.FollowersEntity
import com.cheng.github_client.view.model.UserInfo
import kotlinx.android.synthetic.main.fragment_profile.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class UserAdapter : BaseLoadMoreAdapter<UserInfo, BaseViewHolder>(R.layout.item_follower) {

    override fun convert(helper: BaseViewHolder, item: UserInfo) {
        helper.setText(R.id.tvOwnerName, item.login)
        val ivAvatar = helper.getView<ImageView>(R.id.ivAvatar)
        Glide.with(context).load(item.avatar_url).placeholder(R.drawable.ic_github).into(ivAvatar)
    }
}
