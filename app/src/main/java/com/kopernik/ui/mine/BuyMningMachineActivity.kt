package com.kopernik.ui.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ReminderDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.mine.PurchaseMiningMachineryActivity.Companion.RESULT_BUY_MINE
import com.kopernik.ui.mine.entity.Machine
import com.kopernik.ui.mine.entity.MineBean
import com.kopernik.ui.mine.viewModel.MineMachineryViewModel
import com.kopernik.ui.mine.viewModel.MineViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_buy_mine_machine.*



class BuyMningMachineActivity : NewBaseActivity<MineMachineryViewModel, ViewDataBinding>() {

   var mineType=-1
    var minePrice=""
    var minebean: MineBean?=null
    var consumeUyt=""
    var consumeUytTest=""
    var consumeUtdm=""
    var consumeUtdmUytTest=""
    var utdmChose=true
    override fun layoutId()=R.layout.activity_buy_mine_machine

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.purchase_mine_title))
        intent.getIntExtra("mineType",-1)?.let {
            mineType=it
        }
        intent.getStringExtra("minePrice")?.let {
            minePrice=it
        }
        intent.getSerializableExtra("minebean")?.let {
            minebean=it as MineBean
        }
        //判断买矿机器类型
        when(mineType){
            1->{
              ivMiningType.setImageResource(R.mipmap.ic_mm_type1)
              tvMiningType.text=getString(R.string.mining_machine_type1)
            }
            2->{
              ivMiningType.setImageResource(R.mipmap.ic_mm_type2)
               tvMiningType.text=getString(R.string.mining_machine_type2)
            }
            3->{
               ivMiningType.setImageResource(R.mipmap.ic_mm_type3)
               tvMiningType.text=getString(R.string.mining_machine_type3)
            }
            4->{
               ivMiningType.setImageResource(R.mipmap.ic_mm_type4)
               tvMiningType.text=getString(R.string.mining_machine_type4)
            }
            5->{
               ivMiningType.setImageResource(R.mipmap.ic_mm_type5)
               tvMiningType.text=getString(R.string.mining_machine_type5)
            }
            6->{
                ivMiningType.setImageResource(R.mipmap.ic_mm_type6)
                tvMiningType.text=getString(R.string.mining_machine_type6)
            }
        }
        //矿机价格
        miningMachinePrice.text=  BigDecimalUtils.roundDOWN(minePrice,2)+"USDT"
        //计算UTDM:UYT_TEST比例关系
        var uytTestRatio = BigDecimalUtils.divide(
            minebean?.utdmUytRatio,
            BigDecimalUtils.add(minebean?.utdmUytRatio, minebean?.utdmRatio).toString(),
            2
        )
        var utdmRatio = BigDecimalUtils.divide(
            minebean?.utdmRatio,
            BigDecimalUtils.add(minebean?.utdmUytRatio , minebean?.utdmRatio).toString(),
            2
        )
        consumeUtdm=BigDecimalUtils.divide(
            BigDecimalUtils.multiply(
                minePrice,
                utdmRatio
            ).toString(), minebean?.utdmPrice, 8
        )
        consumeUtdmUytTest=BigDecimalUtils.divide(
            BigDecimalUtils.multiply(
                minePrice,
                uytTestRatio
            ).toString(), minebean?.uytPrice, 8
        )
     //计算UYT:UYT_TEST比例关系
        var uytRatio = BigDecimalUtils.divide(
            minebean?.uytRatio,
            BigDecimalUtils.add(minebean?.uytRatio, minebean?.uytproRatio).toString(),
            2
        )
        var uytproRatio = BigDecimalUtils.divide(
            minebean?.uytproRatio,
            BigDecimalUtils.add(minebean?.uytRatio, minebean?.uytproRatio).toString(),
            2
        )
        consumeUyt=BigDecimalUtils.divide(
            BigDecimalUtils.multiply(
                minePrice,
                uytproRatio
            ).toString(), minebean?.uytProPrice, 8
        )
        consumeUytTest=BigDecimalUtils.divide(
            BigDecimalUtils.multiply(
                minePrice,
                uytRatio
            ).toString(), minebean?.uytPrice, 8
        )

        //utdm :uyttest消费计算
        payUytTestCoins.text = BigDecimalUtils.roundDOWN(consumeUtdmUytTest,2)
        totalTop.text = BigDecimalUtils.roundDOWN(consumeUtdm,2)+" UTDM"
        //uyt_test 消费计算
        totalUytTest.text = BigDecimalUtils.roundDOWN(consumeUtdmUytTest,2)+" UYT_TEST"
        //uyt：uyt_test
        tvPayUtdmToUytTest.text="UTDM : UYT_TEST=${BigDecimalUtils.getRound(minebean?.utdmRatio)}:${BigDecimalUtils.getRound(minebean?.utdmUytRatio)}"

        //uyt :uyttest消费计算

        tvPayUytToUytTest.text="UYT : UYT_TEST=${BigDecimalUtils.getRound(minebean?.uytproRatio)}:${BigDecimalUtils.getRound(minebean?.uytRatio)}"

        //余额
        tvCurrentUTDM.text=minebean?.utdmCaptial?.amount
        tvCurrentUYT_TEST.text=minebean?.uytCaptial?.amount
        tvCurrentUYT.text=minebean?.uytProCaptial?.amount
       //utdm
        clUTDM.setOnClickListener {
         if (!utdmChose){
             ivIsChose.setImageResource(R.mipmap.ic_chosed)
             ivIsUytChose.setImageResource(R.mipmap.ic_no_chose)
             payUytTestCoins.text = BigDecimalUtils.roundDOWN(consumeUtdmUytTest,2)
             totalTop.text = BigDecimalUtils.roundDOWN(consumeUtdm,2)+" UTDM"
             //uyt_test 消费计算
             totalUytTest.text = BigDecimalUtils.roundDOWN(consumeUtdmUytTest,2)+" UYT_TEST"
             utdmChose=true
         }
        }
        //uyt
        clUYT.setOnClickListener {
            if (utdmChose){
                ivIsUytChose.setImageResource(R.mipmap.ic_chosed)
                ivIsChose.setImageResource(R.mipmap.ic_no_chose)
                payUytTestCoins.text = BigDecimalUtils.roundDOWN(consumeUytTest,2)
                totalTop.text = BigDecimalUtils.roundDOWN(consumeUyt,2)+" UYT"
                //uyt_test 消费计算
                totalUytTest.text = BigDecimalUtils.roundDOWN(consumeUytTest,2)+" UYT_TEST"
                utdmChose=false
            }
        }
        confirm.setOnClickListener {
            if (password_et.text.toString().trim().isEmpty()){
                ToastUtils.showShort(this@BuyMningMachineActivity,getString(R.string.login_input_password_hint))
                return@setOnClickListener
            }
            //utdm
            if (utdmChose){
                if (BigDecimalUtils.compare(consumeUtdm,minebean?.utdmCaptial?.amount)) {
                    ToastUtils.showShort(this@BuyMningMachineActivity,getString(R.string.balance_not_enough)+"UTDM"+getString(R.string.balance_not_enough_end))
                    return@setOnClickListener
                } else  if (BigDecimalUtils.compare(consumeUtdmUytTest,minebean?.uytCaptial?.amount)) {
                    ToastUtils.showShort(this@BuyMningMachineActivity,getString(R.string.balance_not_enough)+"UYT_TEST"+getString(R.string.balance_not_enough_end))
                    return@setOnClickListener
                }
            }else{
                //uyt
                if (BigDecimalUtils.compare(consumeUyt,minebean?.uytProCaptial?.amount)) {
                    ToastUtils.showShort(this@BuyMningMachineActivity,getString(R.string.balance_not_enough)+"UYT"+getString(R.string.balance_not_enough_end))
                    return@setOnClickListener
                } else  if (BigDecimalUtils.compare(consumeUytTest,minebean?.uytCaptial?.amount)) {
                    ToastUtils.showShort(this@BuyMningMachineActivity,getString(R.string.balance_not_enough)+"UYT_TEST"+getString(R.string.balance_not_enough_end))
                    return@setOnClickListener
                }
            }
            purchaseMineMac()
        }

    }
    private fun purchaseMineMac() {
        viewModel.run {
            var uid=""
            UserConfig.singleton?.accountBean?.uid?.let {
                uid=it
            }
            var iconType=""
            if (utdmChose){
                iconType="UTDM"
            }else{
                iconType="UYT"
            }
            var map= mapOf("uid" to uid ,"type" to mineType.toString(),"iconType" to iconType,"pwd" to MD5Utils.md5(
                MD5Utils.md5(password_et.text.toString().trim())))
            buyMineMachine(map).observe(this@BuyMningMachineActivity, Observer {
                if (it.status==200)
                    ReminderDialog(this@BuyMningMachineActivity).setOnRequestListener(object :ReminderDialog.RequestListener{
                        override fun onRequest() {
                            setResult(RESULT_BUY_MINE)
                            finish()
                        }
                    }).setCancelable(false)
                        .show()
                else   ErrorCode.showErrorMsg(getActivity(), it.status)
            })
        }
    }


    override fun onDestroy() {
      super.onDestroy()
    }

    override fun initData() {

    }


}