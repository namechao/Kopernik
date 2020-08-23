package com.kopernik.ui.Ecology

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.common.SignatureType
import com.kopernik.ui.Ecology.entity.VoteOperateCheckBean
import com.kopernik.ui.Ecology.viewModel.VoteOperateViewModel
import com.kopernik.ui.asset.entity.DeductSignatureBean
import com.kopernik.ui.asset.entity.RedeemSignatureBean
import com.kopernik.ui.asset.entity.TurnSignatureBean
import kotlinx.android.synthetic.main.activity_vote_operate.*
import java.math.BigDecimal

class VoteOperateActivity : NewBaseActivity<VoteOperateViewModel,ViewDataBinding>() {
    companion object{
        const val REDEEM = 1 //赎回

        const val TURN = 2 //转投

        const val DEDUCT = 3 //提息

    }

    private var inputEt1: EditText? = null
    private var inputEt2: EditText? = null
    private var type = -1
    private var requestType = ""
    private var nodeHash = ""
    private var nodeAddress = ""
    private var checkBean: VoteOperateCheckBean? = null
    private var deductBalance: String? = null
    override fun layoutId()=R.layout.activity_vote_operate

    override fun initView(savedInstanceState: Bundle?) {
        type = intent.getIntExtra("type", -1)
        nodeHash = intent.getStringExtra("nodeHash")
        nodeAddress = intent.getStringExtra("nodeAddress")
        inputEt1 = input1.findViewById(R.id.input_et)
        inputEt2 = input2.findViewById(R.id.input_et)
        if (type == -1) finish()
        inputEt1?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun initData() {
        if (type == REDEEM) {
            initTvFunctionHead(getString(R.string.title_vote_redeem), getString(R.string.timeline))
            requestType = "REDEEM"
            inputEt1!!.hint = getString(R.string.please_input_redeem_count)
            input1.findViewById<View>(R.id.edit_require_tv).visibility = View.GONE
            input2.visibility = View.GONE
            inputEt1!!.addTextChangedListener(input1TextWatcher)
        } else if (type == TURN) {
//            if (UserConfig.singleton?.getAccount()?.loginAcountHash.equals(nodeAddress)) {
//                initTvFunctionHead(
//                    getString(R.string.title_vote_turn),
//                    getString(R.string.timeline)
//                )
//            } else {
//                setTitle(getString(R.string.title_vote_turn))
//            }
            requestType = "SWITCH_TO"
            inputEt1!!.hint = getString(R.string.please_input_turn_count)
            inputEt2!!.hint = getString(R.string.please_input_turn_address)
            input1.findViewById<View>(R.id.edit_require_tv).visibility = View.GONE
            input2.findViewById<View>(R.id.edit_require_tv).visibility = View.GONE
            inputEt1!!.addTextChangedListener(input2TextWatcher)
            inputEt2!!.addTextChangedListener(input2TextWatcher)
        } else {
            setTitle(getString(R.string.title_vote_deduct))
            deductBalance = intent.getStringExtra("interest")
            requestType = "CLAIM"
            titleSpt.setLeftString(getString(R.string.balance_of2))
                .setRightString(BigDecimalUtils.roundDOWN(deductBalance, 4))
            inputEt1!!.hint = getString(R.string.please_input_deduct_count)
            input1.findViewById<View>(R.id.edit_require_tv).visibility = View.GONE
            input2.visibility = View.GONE
            inputEt1!!.addTextChangedListener(input1TextWatcher)
        }
        checkInfo()

        okBtn.setOnClickListener {
            if (checkBean == null) return@setOnClickListener
            if (inputEt1!!.text.toString() == "0") {
                ToastUtils.showShort(getActivity(), getString(R.string.input_can_not_zero))
                return@setOnClickListener
            }
            if (BigDecimal(checkBean?.redeemAmount) < BigDecimal(inputEt1!!.text.toString())
            ) {
                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
                return@setOnClickListener
            }
            if (type == REDEEM) {
                val bean = RedeemSignatureBean()
                bean.fee=checkBean?.low
                bean.nodeHash=nodeHash
                bean.redeemValue=(inputEt1!!.text.toString())
                val dialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.REDEEM, bean)
                dialog.show(supportFragmentManager, "redeem")
            } else if (type == TURN) {
                val bean = TurnSignatureBean()
                bean.fee=checkBean?.low
                bean.nodeHash=nodeHash
                bean.turnValue=inputEt1!!.text.toString()
                bean.targetNodeHash=inputEt2!!.text.toString()
                val dialog: SignatureDialog = SignatureDialog.newInstance(SignatureType.TURN, bean)
                dialog.show(supportFragmentManager, "turn")
            } else {
                val bean = DeductSignatureBean()
                bean.fee=checkBean?.low
                bean.nodeHash=nodeHash
                bean.deductValue=inputEt1!!.text.toString()
                val dialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.DEDUCT, bean)
                dialog.show(supportFragmentManager, "deduct")
            }
        }
    }

    override fun functionCall() {
        LaunchConfig.startRedeemTimeLineAc(this, type, nodeHash)
    }

    private var input1TextWatcher: TextWatcher = object : TextWatcher {
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
            if (!inputEt1!!.text.toString().isEmpty()) {
                enableBtn()
            } else {
                disableBtn()
            }
        }
    }

    private var input2TextWatcher: TextWatcher = object : TextWatcher {
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
            if (inputEt1!!.text.toString().isNotEmpty() && inputEt2!!.text.toString().isNotEmpty()
            ) {
                enableBtn()
            } else {
                disableBtn()
            }
        }
    }

    private fun disableBtn() {
        okBtn.isEnabled = false
    }

    private fun enableBtn() {
        okBtn.isEnabled = true
    }


    private fun checkInfo() {
        viewModel.run {
            var map= mapOf( "type" to requestType,
                    "nodeHash" to nodeHash)
            getNodeCheckInfo(map).observe(this@VoteOperateActivity, Observer {
                if (it.status==200){
                    checkBean = it.data
                    when (type) {
                        REDEEM -> {
                            titleSpt.setLeftString(getString(R.string.title_redeem_balance))
                                .setRightString(
                                    BigDecimalUtils.roundDOWN(
                                        checkBean?.redeemAmount,
                                        4
                                    )
                                )
                        }
                        TURN -> {
                            titleSpt.setLeftString(getString(R.string.balance_of2))
                                .setRightString(
                                    BigDecimalUtils.roundDOWN(
                                        checkBean?.redeemAmount,
                                        4
                                    )
                                )
                        }
                        DEDUCT -> {
                            checkBean?.redeemAmount=deductBalance
                        }
                    }
                }else{
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }
            })
        }
    }
}