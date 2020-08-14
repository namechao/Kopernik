package com.kopernik.ui.Ecology.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.ui.Ecology.adapter.EcologyTabAddrAdapter
import com.kopernik.ui.Ecology.viewModel.NodeViewModel
import kotlinx.android.synthetic.main.fragment_ecology.*


class EcologyFragment : NewBaseFragment<NodeViewModel, ViewDataBinding>() {
    var list= arrayListOf("全球健康骑行基金会--骑游世界","THN","七星海","大其力")
    companion object {
        fun newInstance() = EcologyFragment()
    }

    override fun layoutId() = R.layout.fragment_ecology
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        var layoutManager=LinearLayoutManager(activity)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager=layoutManager
        tvEcologyContent.text = resources.getText(R.string.ecology_tab_content1)
        tvEcologyTitle1.text = resources.getText(R.string.ecology_tab_title1_1)
        var mAdapter=EcologyTabAddrAdapter(list)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.positon=position
            mAdapter.notifyDataSetChanged()
            when(position){
                0->{
                    tvEcologyContent.text = resources.getText(R.string.ecology_tab_content1)
                    tvEcologyTitle1.text = resources.getText(R.string.ecology_tab_title1_1)
                }
                1->{
                    tvEcologyContent.text = resources.getText(R.string.ecology_tab_content2)
                    tvEcologyTitle1.text = resources.getText(R.string.ecology_tab_title1_2)
                }
                2->{
                    tvEcologyContent.text = resources.getText(R.string.ecology_tab_content3)
                    tvEcologyTitle1.text = resources.getText(R.string.ecology_tab_title1_3)
                }
                3->{
                    tvEcologyContent.text = resources.getText(R.string.ecology_tab_content4)
                    tvEcologyTitle1.text = resources.getText(R.string.ecology_tab_title1_4)
                }
            }
        }
        recyclerView.adapter=mAdapter
    }
}