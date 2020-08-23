package com.kopernik.ui.asset

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.common.MapConstant
import com.kopernik.common.SignatureType
import com.kopernik.common.WithdrawConstant
import com.kopernik.ui.asset.entity.MapSignatureBean
import kotlinx.android.synthetic.main.activity_map.*
import java.math.BigDecimal

class MapActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    private var valueEt: EditText? = null
    private var mapType = -1
    private var type = 1
    private var balanaceOf: BigDecimal? = null
    private var fee: String? = null
    override fun layoutId()=R.layout.activity_map

    override fun initView(savedInstanceState: Bundle?) {
        mapType = intent.getIntExtra("mapType", -1)
        type = intent.getIntExtra("type", -1)
        if (mapType == -1 || type == -1) finish()
        valueEt = valueInputLL.findViewById(R.id.input_et)
        if (mapType == 1) {
            setTitle(getString(R.string.title_asset_map))
            valueEt?.hint = getString(R.string.please_input_map_count)
        } else if (mapType == 2) {
            setTitleAndRight(
                getString(R.string.title_asset_map_cancel),
                getString(R.string.timeline)
            )
            valueEt?.hint = getString(R.string.please_input_map_cancel_count)
        }
        valueEt?.addTextChangedListener(textWatcher)
        valueInputLL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE
        valueEt?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun initData() {
        okBtn.setOnClickListener { v: View? ->
            if (balanaceOf!!.compareTo(BigDecimal("0")) == 0) {
                if (mapType == MapConstant.MAP) {
                    ToastUtils.showShort(this, getString(R.string.tip_asset_no_map))
                } else {
                    ToastUtils.showShort(this, getString(R.string.tip_asset_no_map_cancel))
                }
                return@setOnClickListener
            }
            if (balanaceOf!!.compareTo(BigDecimal(valueEt!!.text.toString())) < 0) {
                ToastUtils.showShort(this, getString(R.string.tip_alert_max_vlue))
                return@setOnClickListener
            }
            //弹窗输入密码框需要修改
            val signatureBean = MapSignatureBean()
            signatureBean.fee = fee
            signatureBean.chainType = type
            signatureBean.mapType = mapType
            signatureBean.value = valueEt!!.text.toString()
            if (mapType == MapConstant.MAP) {
                val dialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.MAP, signatureBean)
                dialog.show(supportFragmentManager, "map")
            } else {
                val dialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.UNMAP, signatureBean)
                dialog.show(supportFragmentManager, "map")
            }
        }
        if (type == WithdrawConstant.DNS) {
            if (mapType == MapConstant.MAP) {
//                if (StringUtils.isEmpty(UserConfig.singleton?.accountBean?.nodeHash)) {
//                    dnsMapHintTv.text = resources.getString(R.string.dns_map_hint2)
//                } else {
//                    dnsMapHintTv.text = resources.getString(R.string.dns_map_hint)
//                }
                dnsMapHintTv.visibility = View.VISIBLE
                checkDnsMapStatus()
            } else {
                checkDnsCancelMapStatus()
            }
        } else {
            checkOtherChainStatus()
        }
    }
    override fun functionCall() {
        if (type == WithdrawConstant.DNS) {
            LaunchConfig.startMapTimeLineAc(this, "UYT")
        } else if (type == WithdrawConstant.BTC) {
            LaunchConfig.startMapTimeLineAc(this, "BTC")
        } else if (type == WithdrawConstant.ETH) {
            LaunchConfig.startMapTimeLineAc(this, "ETH")
        } else if (type == WithdrawConstant.USDT) {
            LaunchConfig.startMapTimeLineAc(this, "USDT")
        } else if (type == WithdrawConstant.HT) {
            LaunchConfig.startMapTimeLineAc(this, "HT")
        }
    }

    private fun checkOtherChainStatus() {
//        OkGo.< BaseBean < AssetOptBean > > get<BaseBean<AssetOptBean>>(ServiceUrl.checkMapStatus)
//            .tag(this)
//            .params("type", mapType)
//            .params("iconType", type)
//            .execute(object : DialogCallback<BaseBean<AssetOptBean?>?>(this) {
//                fun onSuccess(response: Response<BaseBean<AssetOptBean?>?>) {
//                    if (response.body().status === 200) {
//                        val bean: AssetOptBean = response.body().data
//                        fee = bean.getConfig().getLow()
//                        when (type) {
//                            WithdrawConstant.BTC -> if (mapType == MapConstant.MAP) {
//                                val mappableNumber =
//                                    BigDecimal(bean.getMappableNumber())
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getBitcoinKycount())
//                                if (balanaceOf!!.compareTo(mappableNumber) > 0) {
//                                    balanaceOf = mappableNumber
//                                }
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        balanaceOf!!.toPlainString(),
//                                        8
//                                    ).toString() + " U-BTC"
//                                )
//                            } else if (mapType == MapConstant.UNMAP) {
//                                balanaceOf = BigDecimal(bean.getBtcWithdrawable())
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getBtcWithdrawable(),
//                                        8
//                                    ).toString() + " U-BTC"
//                                )
//                            }
//                            WithdrawConstant.ETH -> if (mapType == MapConstant.MAP) {
//                                val mappableNumber =
//                                    BigDecimal(bean.getMappableNumber())
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getEthKycount())
//                                if (balanaceOf!!.compareTo(mappableNumber) > 0) {
//                                    balanaceOf = mappableNumber
//                                }
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        balanaceOf!!.toPlainString(),
//                                        8
//                                    ).toString() + " U-ETH"
//                                )
//                            } else if (mapType == MapConstant.UNMAP) {
//                                balanaceOf = BigDecimal(bean.getEthWithdrawable())
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getEthWithdrawable(),
//                                        8
//                                    ).toString() + " U-ETH"
//                                )
//                            }
//                            WithdrawConstant.USDT -> if (mapType == MapConstant.MAP) {
//                                val mappableNumber =
//                                    BigDecimal(bean.getMappableNumber())
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getUsdtKycount())
//                                if (balanaceOf!!.compareTo(mappableNumber) > 0) {
//                                    balanaceOf = mappableNumber
//                                }
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        balanaceOf!!.toPlainString(),
//                                        6
//                                    ).toString() + " U-USDT"
//                                )
//                            } else if (mapType == MapConstant.UNMAP) {
//                                balanaceOf = BigDecimal(bean.getUsdtWithdrawable())
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUsdtWithdrawable(),
//                                        6
//                                    ).toString() + " U-USDT"
//                                )
//                            }
//                            WithdrawConstant.HT -> if (mapType == MapConstant.MAP) {
//                                val mappableNumber =
//                                    BigDecimal(bean.getMappableNumber())
//                                balanaceOf = BigDecimal(bean.getUCapital().getHtKycount())
//                                if (balanaceOf!!.compareTo(mappableNumber) > 0) {
//                                    balanaceOf = mappableNumber
//                                }
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        balanaceOf!!.toPlainString(),
//                                        6
//                                    ).toString() + " U-HT"
//                                )
//                            } else if (mapType == MapConstant.UNMAP) {
//                                balanaceOf = BigDecimal(bean.getHtWithdrawable())
//                                balanceSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getHtWithdrawable(),
//                                        6
//                                    ).toString() + " U-HT"
//                                )
//                            }
//                        }
//                    } else {
//                        ToastUtils.showShort(this, response.body().errorMsg)
//                    }
//                }
//            })
    }

    private fun checkDnsMapStatus() {
//        OkGo.< BaseBean < DnsMapCheckBean > > get<BaseBean<DnsMapCheckBean>>(ServiceUrl.checkDnsMapStatus)
//            .tag(this)
//            .execute(object : DialogCallback<BaseBean<DnsMapCheckBean?>?>(this) {
//                fun onSuccess(response: Response<BaseBean<DnsMapCheckBean?>?>) {
//                    val bean: DnsMapCheckBean = response.body().data
//                    fee = bean.getLow()
//                    balanaceOf = BigDecimal(bean.getBalance())
//                    balanceSpt.setRightString(
//                        BigDecimalUtils.roundDOWN(
//                            balanaceOf!!.toPlainString(),
//                            6
//                        ).toString() + " UYT"
//                    )
//                }
//
//                fun onShowErrorMsg(code: Int, msg: String?) {
//                    ErrorMsg.showErrorMsg(this, code)
//                }
//            })
    }

    private fun checkDnsCancelMapStatus() {
//        OkGo.< BaseBean < DnsMapCheckBean > > get<BaseBean<DnsMapCheckBean>>(ServiceUrl.dnsCancelMapStatus)
//            .tag(this)
//            .execute(object : DialogCallback<BaseBean<DnsMapCheckBean?>?>(this) {
//                fun onSuccess(response: Response<BaseBean<DnsMapCheckBean?>?>) {
//                    val bean: DnsMapCheckBean = response.body().data
//                    fee = bean.getLow()
//                    balanaceOf = BigDecimal(bean.getDnsWithdrawable())
//                    balanceSpt.setRightString(
//                        BigDecimalUtils.roundDOWN(
//                            bean.getDnsWithdrawable(),
//                            6
//                        ).toString() + " UYT"
//                    )
//                }
//
//                fun onShowErrorMsg(code: Int, msg: String?) {
//                    ErrorMsg.showErrorMsg(this, code)
//                }
//            })
    }

    var textWatcher: TextWatcher = object : TextWatcher {
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
            okBtn.isEnabled = !valueEt!!.text.toString().isEmpty()
        }
    }
}