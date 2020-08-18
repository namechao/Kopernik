package com.kopernik.ui.mine.adapter


import android.R.attr.width
import android.util.Log
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.mine.adapter.viewHolder.PurchaseMiningMachineHolder


class RuntimeMiningMAdapter(
    data: List<String>
) :BaseQuickAdapter<String, PurchaseMiningMachineHolder>(R.layout.item_runtime_mining_m,data){
   var prosssBarwith=0
    override fun convert(helper: PurchaseMiningMachineHolder?, item: String?) {


        // 得到progressBar控件的宽度
        val vto2: ViewTreeObserver? =helper?.progressBar?.viewTreeObserver
        vto2?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                helper?.progressBar?.viewTreeObserver?.removeGlobalOnLayoutListener(this)
                prosssBarwith = helper?.progressBar?.width!!
               var scrollDistance = (1.0 / helper?.progressBar?.max!!) * width as Float
                helper.tvPrecent?.translationX= (helper.tvPrecent?.x?.minus(scrollDistance))?.toFloat()!!
            }
        })
    }


}