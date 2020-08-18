package com.kopernik.ui.mine

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.ui.mine.adapter.PurchaseMiningMAdapter
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.*


class PurchaseMiningMachineryActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_purchase_mining_machinery

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_purchase_mm))
    }


    override fun initData() {
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa","ssdsdad","Asdadasdasda","asfafafafaa")
        recyclerView1.layoutManager= LinearLayoutManager(this)
        var adpter= PurchaseMiningMAdapter(list)
        recyclerView1.adapter=adpter
    }
}