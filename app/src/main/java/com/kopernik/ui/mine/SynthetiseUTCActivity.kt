package com.kopernik.ui.mine

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.UDMTDialog
import com.kopernik.app.dialog.UTCDialog
import com.kopernik.app.dialog.UTCSynthetiseProgerssDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.AssetConfitEntity
import com.kopernik.ui.mine.viewModel.MineViewModel
import dev.utils.common.encrypt.MD5Utils

import kotlinx.android.synthetic.main.activity_snythetise_utc.*
import kotlinx.android.synthetic.main.activity_snythetise_utc.ivSound
import kotlinx.android.synthetic.main.activity_snythetise_utc.lottieAnimationView
import java.io.IOException
import java.math.BigDecimal


class SynthetiseUTCActivity : NewFullScreenBaseActivity<MineViewModel,ViewDataBinding>() {
    var isOpenSound=false
    var inputCounts=0
    var maxCounts=0
    var utcEntity: AllConfigEntity?=null
    override fun layoutId()=R.layout.activity_snythetise_utc
    var player = MediaPlayer()
    override fun initView(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener { finish() }
        lottieAnimationView.imageAssetsFolder = "synthetise";
        lottieAnimationView.setAnimation("synthetise.json");
        ivInputMinus.isEnabled=false
        ivInputAdd.isEnabled=false
        ivSnythetise.isEnabled=false
        val assetManager: AssetManager = resources.assets
        try {
            val fileDescriptor =
                assetManager.openFd("mining.mp3")
            player.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.startOffset
            )
            player.prepare()
            player.isLooping=true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //合成utc
        ivSnythetise.setOnClickListener {
            if(editText.text.toString().trim().isBlank()) {
                ToastUtils.showShort(this,getString(R.string.utc_input_counts))
                return@setOnClickListener
            }
            if(editText.text.toString().trim()=="0") {
                ToastUtils.showShort(this,getString(R.string.utc_input_syth_limit))
                return@setOnClickListener
            }
            //判断是否设置交易密码
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                       LaunchConfig.startTradePasswordActivity(this, 1,1)
                        return@setOnClickListener
                    }
                }else{
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                        LaunchConfig.startTradePasswordActivity(this, 1,1)
                        return@setOnClickListener
                    }
                }
            }
            ///兑换
                var dialog = UTCDialog.newInstance(1)
            dialog!!.setOnRequestListener(object : UTCDialog.RequestListener {
                    override fun onRequest(type: Int, params: String) {
                      viewModel.run {
                          var amountUtc=editText.text.toString().trim().toInt()
                         var amountUtk=BigDecimalUtils.divide(amountUtc.toString(),utcEntity?.config?.utkCompose,8)
                         var amountUtdm=BigDecimalUtils.divide(amountUtc.toString(),utcEntity?.config?.utdmCompose,8)
                          var  map= mapOf(
                              "amountUtc" to "$amountUtc",
                              "amountUtk" to "$amountUtk",
                              "amountUtdm" to "$amountUtdm",
                              "rate" to "1",
                              "pwd" to MD5Utils.md5(MD5Utils.md5(params)))
                          compose(map).observe(this@SynthetiseUTCActivity, Observer {
                              if (it.status==200){
                                  var dialog = UTCSynthetiseProgerssDialog.newInstance(editText.text.toString().trim().toInt())
                                  smartRefreshLayout.autoRefresh()
                                  dialog!!.show(supportFragmentManager, "progress")
                              }else{
                                  ErrorCode.showErrorMsg(this@SynthetiseUTCActivity,it.status)
                              }
                          })
                      }
                    }
                })
            dialog!!.show(supportFragmentManager, "withdrawRecommed")
        }
        ivSound.setOnClickListener {
            if (!isOpenSound){
                isOpenSound=true
                //打开音乐
                player.start()
                ivSound.setImageResource(R.mipmap.ic_open_sound)
            }else{
                //关闭音乐
                isOpenSound=false
                player.pause()
                ivSound.setImageResource(R.mipmap.ic_close_sound)
            }
        }

        smartRefreshLayout.setOnRefreshListener {
            getData()
        }
        smartRefreshLayout.autoRefresh()
    }
    //获取数据
    fun getData(){
      viewModel.run {
          getAssetConfig().observe(this@SynthetiseUTCActivity, Observer {
          smartRefreshLayout.finishRefresh()
          if (it.status==200) {
              ivInputMinus.isEnabled=true
              ivInputAdd.isEnabled=true
              ivSnythetise.isEnabled=true
              utcEntity=it.data
              tvUDMTCoin.text = ""+BigDecimalUtils.getRound(it.data?.utdm)
              tvUTKtCoin.text = ""+ BigDecimalUtils.getRound(it.data?.utk)
              currentScale.text=resources.getString(R.string.current_exchange_precent)+"UTDM:UTK=${BigDecimalUtils.getRound(utcEntity?.config?.utdmCompose)}:${BigDecimalUtils.getRound(utcEntity?.config?.utkCompose)}"
              var utdmToUtc=BigDecimalUtils.multiply(utcEntity?.utdm,it.data?.config?.utdmCompose)
              var utkToUtc =BigDecimalUtils.multiply(utcEntity?.utk,it.data?.config?.utkCompose)
              if (BigDecimal(utdmToUtc).subtract(BigDecimal(utkToUtc))>BigDecimal("0"))
                  maxCounts=BigDecimalUtils.getRound(utkToUtc.toString()).toInt()
              else  maxCounts=BigDecimalUtils.getRound(utdmToUtc.toString()).toInt()
              editText.setText(""+maxCounts)
          }else{
              ErrorCode.showErrorMsg(this@SynthetiseUTCActivity,it.status)
          }
      }) }
    }

    override fun onResume() {
        super.onResume()
        lottieAnimationView.playAnimation()
    }
    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        lottieAnimationView.cancelAnimation()
    }

    override fun initData() {
        ivInputMinus.setOnClickListener {
            inputCounts--
            editText.setText(""+inputCounts)
        }
        ivInputAdd.setOnClickListener {
            inputCounts++
            if (inputCounts>maxCounts){
                ToastUtils.showShort(this,getString(R.string.utc_max_counts))
                return@setOnClickListener
            }
            editText.setText(""+inputCounts)
        }
        editText.addTextChangedListener(watcher)
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            editText.setSelection(editText.text.toString().length)
         if (!editText.text.toString().trim().isBlank()&&editText.text.toString().trim().toInt()>maxCounts){
             inputCounts=maxCounts
             editText.setText(""+maxCounts)
         }
            if (!editText.text.toString().trim().isBlank()&&editText.text.toString().trim().toInt()<0){
                inputCounts=0
                editText.setText("0")
            }
            if (editText.text.toString().trim().isBlank()){
                inputCounts=0
            }
        }
    }
}