package com.cheng.github_client.view.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cheng.github_client.R
import com.cheng.github_client.common.loadmore.BaseLoadMoreAdapter
import com.cheng.github_client.utils.TimeConverter
import com.cheng.github_client.utils.load
import com.cheng.github_client.view.model.RepoEntity
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class SearchResultAdapter : BaseLoadMoreAdapter<RepoEntity, BaseViewHolder>(R.layout.item_serach_result) {

    override fun convert(helper: BaseViewHolder, item: RepoEntity) {
        val imageView = helper.getView<ImageView>(R.id.ivAvatar)
        Glide.with(context).load(item.owner?.avatar_url).placeholder(R.drawable.ic_github).into(imageView)

        helper.setText(R.id.tvOwnerName, item.owner?.login)
        helper.setText(R.id.tvRepoName, item.full_name)
        helper.setText(R.id.tvRepoDesc, item.description)
        helper.setText(R.id.tvStarCount, item.stargazers_count?.toString())
        helper.setText(R.id.tvOwnerName, item.owner?.login)

        val ivLanguage = helper.getView<ImageView>(R.id.ivLanguage)
        ivLanguage.setImageDrawable(getLanguageColor(item.language))
        helper.setText(R.id.tvLanguage, item.language)
        helper.setText(R.id.tvEventTime, TimeConverter.tramsTimeAgo(item.updated_at))
    }

    private fun getLanguageColor(language: String?): Drawable {
        if (language == null) return ColorDrawable(Color.TRANSPARENT)

        val colorProvider: (Int) -> Drawable = { resId ->
            ColorDrawable(ContextCompat.getColor(context, resId))
        }

        return colorProvider(
            when (language) {
                "Kotlin" -> R.color.color_language_kotlin
                "Java" -> R.color.color_language_java
                "JavaScript" -> R.color.color_language_js
                "Python" -> R.color.color_language_python
                "HTML" -> R.color.color_language_html
                "CSS" -> R.color.color_language_css
                "Shell" -> R.color.color_language_shell
                "C++" -> R.color.color_language_cplus
                "C" -> R.color.color_language_c
                "ruby" -> R.color.color_language_ruby
                else -> R.color.color_language_other
            })
    }
}
