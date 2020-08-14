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
import com.kopernik.ui.Ecology.adapter.EcologyTabAddrAdapter
import com.kopernik.ui.Ecology.entity.ReferendumDetailsBean
import com.kopernik.ui.Ecology.viewModel.ReferendumDetailsViewModel
import com.kopernik.ui.asset.entity.ReferendumVoteSignatureBean
import kotlinx.android.synthetic.main.activity_referendum_details.*
import java.lang.String
import java.math.BigDecimal

class ReferendumDetailsActivity : NewBaseActivity<ReferendumDetailsViewModel,ViewDataBinding>() {
    private var id = 0
    private var adapter: EcologyTabAddrAdapter? = null
    private var signatureBean: ReferendumVoteSignatureBean? = null
    override fun layoutId()=R.layout.activity_referendum_details

    override fun initView(savedInstanceState: Bundle?) {
//        setTitle(getString(R.string.comm_referendum))
//        id = intent.getIntExtra("id", -1)
//        if (id == -1) finish()
//        signatureBean = ReferendumVoteSignatureBean()
//        signatureBean?.id=id
//        adapter = EcologyTabAddrAdapter()
//        recyclerView.adapter = adapter
//        agreeBtn.setOnSelectedListener(object : IrregularButton.BaseBooleanListener {
//           override fun isLeftClick(b: Boolean) {
//                signatureBean?.voteType=1
//                val signatureDialog: SignatureDialog =
//                    SignatureDialog.newInstance(SignatureType.REFERENDUM, signatureBean)
//                signatureDialog.show(supportFragmentManager, "signatureDialog")
//            }
//
//            override fun isRightClick(b: Boolean) {
//                signatureBean?.voteType=2
//                val signatureDialog: SignatureDialog =
//                    SignatureDialog.newInstance(SignatureType.REFERENDUM, signatureBean)
//                signatureDialog.show(supportFragmentManager, "signatureDialog")
//            }
//        })
    }

    override fun initData() {

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
        }
    }

}