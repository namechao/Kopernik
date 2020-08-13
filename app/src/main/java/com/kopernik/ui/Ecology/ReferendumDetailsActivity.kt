package com.kopernik.ui.Ecology

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.TimeUtils
import com.kopernik.app.widget.IrregularButton
import com.kopernik.common.SignatureType
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.adapter.ReferendumAddrAdapter
import com.kopernik.ui.Ecology.entity.ReferendumDetailsBean
import com.kopernik.ui.Ecology.viewModel.ReferendumDetailsViewModel
import com.kopernik.ui.asset.entity.ReferendumVoteSignatureBean
import kotlinx.android.synthetic.main.activity_referendum_details.*
import java.lang.String
import java.math.BigDecimal

class ReferendumDetailsActivity : NewBaseActivity<ReferendumDetailsViewModel,ViewDataBinding>() {
    private var id = 0
    private var adapter: ReferendumAddrAdapter? = null
    private var signatureBean: ReferendumVoteSignatureBean? = null
    override fun layoutId()=R.layout.activity_referendum_details

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.comm_referendum))
        id = intent.getIntExtra("id", -1)
        if (id == -1) finish()
        signatureBean = ReferendumVoteSignatureBean()
        signatureBean?.id=id
        adapter = ReferendumAddrAdapter(R.layout.item_referendum_addr)
        recyclerView.adapter = adapter
        agreeBtn.setOnSelectedListener(object : IrregularButton.BaseBooleanListener {
           override fun isLeftClick(b: Boolean) {
                signatureBean?.voteType=1
                val signatureDialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.REFERENDUM, signatureBean)
                signatureDialog.show(supportFragmentManager, "signatureDialog")
            }

            override fun isRightClick(b: Boolean) {
                signatureBean?.voteType=2
                val signatureDialog: SignatureDialog =
                    SignatureDialog.newInstance(SignatureType.REFERENDUM, signatureBean)
                signatureDialog.show(supportFragmentManager, "signatureDialog")
            }
        })
    }

    override fun initData() {
        getDetails()
    }
    override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.referendumVote)) {
//            if ((int)event.data == 1) {
//                agreeBtn.setLeftSelected(true);
//                agreeBtn.setTouch(false);
//            } else {
//                agreeBtn.setRightSelected(true);
//                agreeBtn.setTouch(false);
//            }
            getDetails()
        }
    }
    private fun getDetails() {
        viewModel.run {
            getReferendumDetails(Api.referendumDetails + id).observe(this@ReferendumDetailsActivity, Observer {
             if (it.status==200){
                 val bean: ReferendumDetailsBean = it.data
                    signatureBean?.fee=bean.config?.low
                 titleTv.text = bean.uReferendum?.title
                 timeTv.text = TimeUtils.normalTimeStampToData(
                     bean.uReferendum?.createTime.toString() + ""
                 )
                    if (Build.VERSION.SDK_INT > 23) {
                        theWayTv.text = Html.fromHtml(
                            bean.uReferendum?.participationMode,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                        contentTv.text = Html.fromHtml(
                            bean.uReferendum?.content,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                        ruleTv.text = Html.fromHtml(
                            bean.uReferendum?.rule,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    } else {
                        theWayTv.text = bean.uReferendum?.participationMode
                        contentTv.text = bean.uReferendum?.content
                        ruleTv.text = bean.uReferendum?.rule
                    }
                 agreeTv.text = String.format(
                     getString(R.string.agree_hint),
                     bean.favorAmount.toString() + ""
                 )
                 opposeTv.text = String.format(
                     getString(R.string.oppose_hint),
                     bean.oppositionAmount.toString() + ""
                 )
                    val total =
                        BigDecimal(bean.favorAmount + bean.oppositionAmount)
                    if (total.compareTo(BigDecimal(0)) > 0) {
                        val agreeNum: BigDecimal =
                            BigDecimal(bean.favorAmount)
                                .divide(total, 2, BigDecimal.ROUND_HALF_EVEN)
                        progress.setProgress((agreeNum.toDouble() * 100) as Int)
                    } else {
                        progress.setProgress(0)
                    }
                    if (Integer.valueOf(bean.uReferendum?.status!!) == 0) {
                        if (bean.checkVote == 1) {
                            agreeBtn.setLeftSelected(true)
                            //                                agreeBtn.setTouch(false);
                            agreeBtn.setLeftString(getActivity()!!.getString(R.string.already_agree))
                            agreeBtn.setRightString(getActivity()!!.getString(R.string.oppose))
                        } else if (bean.checkVote == 2) {
                            agreeBtn.setRightSelected(true)
                            //                                agreeBtn.setTouch(false);
                            agreeBtn.setLeftString(getActivity()!!.getString(R.string.agree))
                            agreeBtn.setRightString(getActivity()!!.getString(R.string.opposed))
                        }
                    } else {
                        agreeBtn.setAllUnSelected(false)
                        agreeBtn.setLeftString(getActivity()!!.getString(R.string.agree))
                        agreeBtn.setRightString(getActivity()!!.getString(R.string.oppose))
                    }
                    adapter?.setNewData(bean.detailList)
             }else{
                 ErrorCode.showErrorMsg(getActivity(), it.status)
             }
            })
        }
    }
}