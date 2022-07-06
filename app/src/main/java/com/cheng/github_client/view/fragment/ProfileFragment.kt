package com.cheng.github_client.view.fragment

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.cheng.github_client.R
import com.cheng.github_client.base.BaseVmFragment
import com.cheng.github_client.bus.Bus
import com.cheng.github_client.bus.USER_LOGIN_STATE_CHANGED
import com.cheng.github_client.utils.ActivityHelper
import com.cheng.github_client.utils.isLogin
import com.cheng.github_client.utils.store.UserInfoStore
import com.cheng.github_client.view.activity.*
import com.cheng.github_client.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
/**
 *  Author: PengCheng
 *  Date:   2022/7/5
 *  Describe:
 */
class ProfileFragment : BaseVmFragment<ProfileViewModel>(){

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun getLayoutId() = R.layout.fragment_profile


    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun initView() {
        clHeader.setOnClickListener {
            checkLogin {  }
        }
        llMyRepos.setOnClickListener {
            checkLogin { ActivityHelper.startActivity(ReposActivity::class.java) }
        }
        llMyFollowers.setOnClickListener {
            ActivityHelper.startActivity(FollowersActivity::class.java)
        }
        llFollowing.setOnClickListener {
            checkLogin { ActivityHelper.startActivity(FollowingActivity::class.java) }
        }
        llOpenSource.setOnClickListener {
            ActivityHelper.startActivity(OpenSourceActivity::class.java)
        }
        llSetting.setOnClickListener {
            ActivityHelper.startActivity(SettingsActivity::class.java)
        }

        updateUi()
    }

    override fun observe() {
        super.observe()
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, {
            updateUi()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() {
        val isLogin = isLogin()
        tvLoginRegister.isGone = isLogin
        tvNickName.isVisible = isLogin
        tvId.isVisible = isLogin
        if (isLogin) {
            val userInfo = UserInfoStore.getUserInfo()
            if (isLogin && userInfo != null) {
                tvNickName.text = userInfo.login
                tvId.text = userInfo.url
                tvReposNum.text = userInfo.public_repos.toString()
                tvFollowerNum.text = userInfo.followers.toString()
                tvFollowingNum.text = userInfo.following.toString()
                Glide.with(requireActivity()).load(userInfo.avatar_url).placeholder(R.drawable.ic_avatar_black_96dp).into(civAvatar)
            }
        } else {
            civAvatar.setImageResource(R.drawable.ic_avatar_black_96dp)
            tvReposNum.text = ""
            tvFollowerNum.text = ""
            tvFollowingNum.text = ""
        }
    }
}