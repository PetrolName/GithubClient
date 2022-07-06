package com.cheng.github_client.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.cheng.basic.ext.copyTextIntoClipboard
import com.cheng.basic.ext.openInExplorer
import com.cheng.basic.ext.showToast
import com.cheng.github_client.R
import com.cheng.github_client.utils.share
import com.cheng.github_client.view.activity.DetailActivity
import com.cheng.github_client.view.activity.DetailActivity.Companion.PARAM_REPO
import com.cheng.github_client.view.activity.DetailActivity.Companion.PARAM_TITLE
import com.cheng.github_client.view.activity.DetailActivity.Companion.PARAM_URL
import com.cheng.github_client.view.model.RepoEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_detail_acitons.*

/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class ActionFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(title: String, url: String): ActionFragment {
            return ActionFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_TITLE, title)
                    putString(PARAM_URL, url)
                }
            }
        }
    }

    private var behavior: BottomSheetBehavior<View>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_acitons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val title = getString(PARAM_TITLE, "")
            val url = getString(PARAM_URL, "")

            llShare.setOnClickListener {
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                share(
                    activity = activity ?: return@setOnClickListener,
                    content = title + url
                )
            }
            llExplorer.setOnClickListener {
                openInExplorer(url)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
            llCopy.setOnClickListener {
                context?.copyTextIntoClipboard(url, title)
                context?.showToast(R.string.copy_success)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
            llRefresh.setOnClickListener {
                (activity as? DetailActivity)?.refreshPage()
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun show(manager: FragmentManager) {
        if (!this.isAdded) {
            super.show(manager, "ActionFragment")
        }
    }
}