package com.kopernik.ui.mine.adapter


import android.os.CountDownTimer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.mine.ViewHodler.MineMachineryViewHolder
import com.kopernik.ui.mine.entity.Machine


class PurchaseMiningMAdapter(
    data: List<Machine>
) :BaseQuickAdapter<Machine, MineMachineryViewHolder>(R.layout.item_purchase_mining_m,data){
    override fun convert(helper: MineMachineryViewHolder?, item: Machine?) {
       when(item?.type){
           1->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type1)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type1)
           }
           2->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type2)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type2)
           }
           3->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type3)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type3)
           }
           4->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type4)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type4)
           }
           5->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type5)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type5)
           }
           6->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type6)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type6)
           }
       }
        helper?.tvMiningPrice?.text="${mContext.getString(R.string.mining_machine_price)}：${item?.price} USDT"
        helper?.tvMiningSpeed?.text="${mContext.getString(R.string.mining_power)}：${item?.power}${mContext.getString(R.string.mining_machine_times)}"
        helper?.tvWaveCounts?.text="${mContext.getString(R.string.mining_machine_speed)}：${item?.rateMin}%-${item?.rateMax}%"
        helper?.addOnClickListener(R.id.tv_purchase)

        //switchStatus 1带边开关开放 2未开放
        if (item?.switchStatus==1){
            helper?.tvPurchase?.setTextColor(mContext.getColor(R.color.white))
            //判断是否有库存 无库存为0
            if (item?.stock==0){
                helper?.tvPurchase?.isEnabled=false
                helper?.tvPurchase?.text=mContext.getText(R.string.button_purchase_none)
            }else{
                var currentTime=TimeUtils.currentTimeMillisecond()
                //判断开抢时间 未到则显示计时器 过了则提示 已结束
                if (item?.startTime!=null) {
                    if (currentTime - item?.startTime!! < 0) {
                        helper?.tvPurchase?.isEnabled=false
                        var countDownTime = item?.startTime - currentTime
                        var countDownTimer = object : CountDownTimer(countDownTime, 1000) {
                            override fun onFinish() {
                                helper?.tvPurchase?.isEnabled=true
                                helper?.tvPurchase?.text=mContext.getText(R.string.button_purchase)
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                var day = millisUntilFinished / (1000 * 60 * 60 * 24)//天
                                var hour =
                                    (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)//时
                                var hourStr = ""
                                if (hour < 10) hourStr = "0$hour" else hourStr = "$hour"
                                var minute =
                                    (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)//分
                                var minuteStr = ""
                                if (minute < 10) minuteStr = "0$minute" else minuteStr = "$minute"
                                var second =
                                    (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000//秒
                                var secondStr = ""
                                if (second < 10) secondStr = "0$second" else secondStr = "$second"
                                helper?.tvPurchase?.text = "$hourStr:$minuteStr:$secondStr"
                            }
                        }.start()
                    } else if (currentTime - item?.endTime!! >0) {
                        helper?.tvPurchase?.isEnabled=false
                        helper?.tvPurchase?.text = mContext.getText(R.string.button_purchase_over)
                    }else{
                        helper?.tvPurchase?.isEnabled=true
                        helper?.tvPurchase?.text=mContext.getText(R.string.button_purchase)
                    }
                }
            }
        }else if (item?.switchStatus==2){
            //开关关闭状态
            helper?.tvPurchase?.isEnabled=false
            helper?.tvPurchase?.setTextColor(mContext.getColor(R.color.color_5D5386))
            helper?.tvPurchase?.text=mContext.getText(R.string.button_purchase_not_open)
        }else{
            helper?.tvPurchase?.isEnabled=false
        }


        }
    }

