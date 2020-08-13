package com.kopernik.ui.Ecology

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.common.SignatureType
import com.kopernik.ui.Ecology.viewModel.VoteBetViewModel
import com.kopernik.ui.asset.entity.VoteSignatureBean
import kotlinx.android.synthetic.main.activity_vote_bet.*
import java.math.BigDecimal

class VoteBetActivity : NewBaseActivity<VoteBetViewModel, ViewDataBinding>() {
    companion object {
        val VOTE = 1
        val GTONVOTE = 2
    }

    private var noteEt: EditText? = null
    private var valueEt: EditText? = null
    private var fee = "1"
    private var balance = "0"

    override fun layoutId()=R.layout.activity_vote_bet

    override fun initView(savedInstanceState: Bundle?) {
        if (intent.getIntExtra("type", 1) === 1) {
            setTitle(getString(R.string.node_vote))
        } else {
            setTitle(getString(R.string.vote_again_name))
        }
        val nodeHash = intent.getStringExtra("nodeHash")
        val targetNodeHash = intent.getStringExtra("targetNodeHash")
        checkVote()
        valueEt = inputValueLL.findViewById(R.id.input_et)
        noteEt = inputNoteLL.findViewById(R.id.input_et)
        inputNoteLL?.findViewById<TextView>(R.id.edit_require_tv)?.visibility = View.GONE
        valueEt?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        valueEt?.hint = getString(R.string.hint_input_vote_num)
        noteEt?.hint = getString(R.string.hint_note)
        valueEt?.addTextChangedListener(textWatcher)
        noteEt?.addTextChangedListener(textWatcher)

        okBtn.setOnClickListener {
            if (valueEt?.text.toString() == "0") {
                ToastUtils.showShort(getActivity(), getString(R.string.input_can_not_zero))
                return@setOnClickListener
            }
            if (BigDecimal(balance) < BigDecimal(valueEt?.text.toString())
                    .add(BigDecimal(fee))
            ) {
                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
                return@setOnClickListener
            }
            val bean = VoteSignatureBean()
            bean.type = SignatureType.VOTE
            bean.fee = fee
            bean.voteValue = valueEt?.text.toString().trim()
            bean.remark = noteEt?.text.toString().trim()
            bean.nodeHash = nodeHash
            bean.targetNodeHash = targetNodeHash
            val dialog: SignatureDialog = SignatureDialog.newInstance(SignatureType.VOTE, bean)
            dialog.show(supportFragmentManager, "voteBet")
        }
    }

    override fun initData() {

    }
    private var textWatcher: TextWatcher = object : TextWatcher {
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
            okBtn.isEnabled = valueEt!!.text.toString().isNotEmpty()

        }
    }

    //检查节点投票信息
    private fun checkVote() {
        viewModel.run {

            checkVoteInfo().observe(this@VoteBetActivity, Observer {
                if (it.status == 200) {
                    balance = it.data.balance.toString()
                    balanceTv.text = it.data.balance.toString() + "UYT"
                    fee = it.data.low.toString()
                } else {
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }
            })
        }

    }
}