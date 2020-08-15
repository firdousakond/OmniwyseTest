package com.omniwyse.firdous.view.details

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.omniwyse.firdous.R
import com.omniwyse.firdous.model.UserModel
import com.omniwyse.firdous.util.USER_MODEL
import com.omniwyse.firdous.util.loadImageFromUrl
import com.omniwyse.firdous.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        getBundle()
        setListener()
    }

    private fun getBundle(){
        val bundle = intent.extras
        if(bundle != null){
            val userModel : UserModel? = bundle.getParcelable(USER_MODEL)
            setUserData(userModel)
        }
    }

    private fun setUserData(userModel: UserModel?) {
        if(userModel != null){
            ivImage.loadImageFromUrl(this,userModel.avatarUrl!!)
            tvName.text = userModel.login
            tvFollowersLink.text = userModel.followersUrl
            tvSubscriptionLink.text = userModel.subscriptionsUrl
            tvOrganizationLink.text = userModel.organizationsUrl
            tvReposLink.text = userModel.reposUrl
            tvFollowersLink.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setListener(){
        ivBack.setOnClickListener { finish() }
    }
}