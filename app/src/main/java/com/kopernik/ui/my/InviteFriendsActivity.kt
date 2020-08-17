package com.kopernik.ui.my

import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.ui.login.viewmodel.CreateAccountViewModel
import com.kopernik.ui.my.adapter.InviteFriendsAdapter
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_invite_friends.*

class InviteFriendsActivity : NewBaseActivity<CreateAccountViewModel, ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_invite_friends

    override fun initView(savedInstanceState: Bundle?) {
        confirmBtn.setOnClickListener {
            LaunchConfig.startTradePasswordNextActivity(this)
        }
        var list= arrayListOf("ssss","sdfsdfsdfs","asasdsdasdsada")
       var adapter= InviteFriendsAdapter(list)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }


    override fun initData() {

        }


}