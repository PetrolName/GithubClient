package com.cheng.github_client.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cheng.github_client.R
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchHistoryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_history) {

    init {
        addChildClickViewIds(R.id.ivDelete)
    }
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tvContent, item)
    }
}
