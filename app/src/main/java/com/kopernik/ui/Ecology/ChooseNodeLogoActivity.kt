package com.kopernik.ui.Ecology

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import com.kopernik.app.widget.GridLayoutItemDecoration
import com.kopernik.app.widget.roundedimageview.RoundedImageView
import com.kopernik.ui.Ecology.entity.NodeLogoListBean
import com.kopernik.ui.Ecology.viewModel.ChooseNodeLogoViewModel
import kotlinx.android.synthetic.main.activity_choose_node_logo.*

class ChooseNodeLogoActivity : NewBaseActivity<ChooseNodeLogoViewModel, ViewDataBinding>() {

    public var choosePosition = -1
    private var nodeHash: String? = null
    private var logoDatas: List<NodeLogoListBean>? = null
    private var adapter: Adapter? = null

    override fun layoutId() = R.layout.activity_choose_node_logo

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_node_icon_select))
        nodeHash = intent.getStringExtra("nodeHash")
        adapter = Adapter(
            R.layout.item_node_logo,
            arrayListOf()
        )
        val gridLayoutManager = GridLayoutManager(getActivity(), 4)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridLayoutItemDecoration(APPHelper.dp2px(this, 10.toFloat())))
        adapter?.setOnItemClickListener { adapter1: BaseQuickAdapter<*, *>, _: View?, position: Int ->
            choosePosition = if (choosePosition == position) {
                -1
            } else {
                position
            }
            adapter1.notifyDataSetChanged()
            okBtn.isEnabled = choosePosition != -1
        }

        okBtn.setOnClickListener { modifyNodeLogo() }
    }

    override fun initData() {
        getNodeLogo()
    }

    //修改节点图标
    private fun modifyNodeLogo() {
        viewModel.run {
            var map = mapOf(
                "nodeHash" to nodeHash!!,
                "imgUrl" to logoDatas!![choosePosition].img!!
            )
            modifyNodeLogo(map).observe(this@ChooseNodeLogoActivity, androidx.lifecycle.Observer {
                if (it.status == 200) {
                    ToastUtils.showShort(getActivity(), getString(R.string.tip_change_success))
                    mEventBus.post(LocalEvent<Any>(LocalEvent.reloadVoteList))
                    finish()
                } else {
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }

            })
        }
    }

    //获取节点图标列表
    private fun getNodeLogo() {
        viewModel.run {
            getNodeLogo().observe(this@ChooseNodeLogoActivity, androidx.lifecycle.Observer {
                if (it.status == 200) {
                    logoDatas = it.data
                    if (it.data != null && it.data.isNotEmpty()) {
                        for (i in it.data.indices) {
                            if (it.data[i].img
                                    .equals(UserConfig.singleton?.accountBean?.imgUrl)
                            ) {
                                choosePosition = i
                            }
                        }
                        adapter?.setNewData(logoDatas)
                    }
                } else {
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }


            })

        }
    }


    inner  class Adapter(
        layoutResId: Int,
        @Nullable data: List<NodeLogoListBean?>?
    ) :
        BaseQuickAdapter<NodeLogoListBean, BaseViewHolder>(layoutResId, data) {
        override fun convert(
            helper: BaseViewHolder,
            item: NodeLogoListBean
        ) {
            Glide.with(mContext).load(item.img)
                .into(helper.getView<View>(R.id.node_logo_iv) as RoundedImageView)
            if (choosePosition == helper.adapterPosition) {
                helper.getView<View>(R.id.node_logo_select_iv).visibility = View.VISIBLE
            } else {
                helper.getView<View>(R.id.node_logo_select_iv).visibility = View.INVISIBLE
            }
        }
    }
}