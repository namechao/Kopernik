package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.security.keystore.KeyProperties
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.kopernik.R
import com.kopernik.app.config.UserConfig
import com.kopernik.common.AssetOptConstant
import com.kopernik.common.SignatureType
import com.kopernik.common.WithdrawConstant
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.KeyboardUtils

class SignatureDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {
    private var titleRootView1: LinearLayout? = null
    private var titleRootView2: LinearLayout? = null
    private var titleRootView3: LinearLayout? = null
    private var useFingerprintIv: ImageView? = null
    private var titleTv: TextView? = null
    private var usePwTv: TextView? = null
    private var titleName1: TextView? = null
    private var desc1: TextView? = null
    private var titleName2: TextView? = null
    private var desc2: TextView? = null
    private var titleName3: TextView? = null
    private var desc3: TextView? = null
    private var balanceTv: TextView? = null
    private var input1: EditText? = null
    private var passwordEt: EditText? = null
    private var okBtn: Button? = null
    private var type = 0
    private var registerNodeSignatureBean: RegisterNodeSignatureBean? = null
    private var voteSignatureBean: VoteSignatureBean? = null
    private var redeemSignatureBean: RedeemSignatureBean? = null
    private var turnSignatureBean: TurnSignatureBean? = null
    private var deductSignatureBean: DeductSignatureBean? = null
    private var transferSignatureBean: TransferSignatureBean? = null
    private var withdrawSignatureBean: WithdrawSignatureBean? = null
    private var mapSignatureBean: MapSignatureBean? = null
    private var oneKeyExtractSignatureBean: OneKeyExtractSignatureBean? = null
    private var referendumVoteSignatureBean: ReferendumVoteSignatureBean? = null
    private var useFingerprint = false
    private var fingerprintDialog: FingerprintDialog? = null


    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_signature)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =  getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.attributes = lp
        val bundle = arguments
        type = bundle!!.getInt("type")
        if (type == SignatureType.NODE_REGISTER) {
            registerNodeSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.VOTE) {
            voteSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.REDEEM) {
            redeemSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.TURN) {
            turnSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.DEDUCT) {
            deductSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.TRANSFER) {
            transferSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.WITHDRAW) {
            withdrawSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.MAP) {
            mapSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.UNMAP) {
            mapSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.ONE_KEY) {
            oneKeyExtractSignatureBean = bundle.getParcelable("signatureBean")
        } else if (type == SignatureType.REFERENDUM) {
            referendumVoteSignatureBean = bundle.getParcelable("signatureBean")
        }
        fingerprintDialog = FingerprintDialog.newInstance(2, null)
        fingerprintDialog?.setAuthenticationCallback(this)
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        usePwTv = dialog.findViewById(R.id.use_pw_tv)
        useFingerprintIv =
            dialog.findViewById(R.id.use_fingerprint_iv)
        titleTv = dialog.findViewById(R.id.title_tv)
        titleRootView1 = dialog.findViewById(R.id.tx_title1_ll)
        titleRootView2 = dialog.findViewById(R.id.tx_title2_ll)
        titleRootView3 = dialog.findViewById(R.id.tx_title3_ll)
        titleName1 = dialog.findViewById(R.id.tx_title1_name_tv)
        titleName2 = dialog.findViewById(R.id.tx_title2_name_tv)
        titleName3 = dialog.findViewById(R.id.tx_title3_name_tv)
        desc1 = dialog.findViewById(R.id.tx_desc1)
        desc2 = dialog.findViewById(R.id.tx_desc2)
        desc3 = dialog.findViewById(R.id.tx_desc3)
        input1 = dialog.findViewById(R.id.tx_input1_et)
        passwordEt = dialog.findViewById(R.id.password_et)
        balanceTv = dialog.findViewById(R.id.balance_tv)
        okBtn = dialog.findViewById(R.id.ok)
        //TODO 指纹要做的
//        useFingerprint = UserConfig.getSingleton().isUseFingerprint();
        if (!useFingerprint) {
            useFingerprintIv?.setVisibility(View.GONE)
            usePwTv?.setVisibility(View.GONE)
        } else {
            initVerifyType(true)
        }
        useFingerprintIv?.setOnClickListener(View.OnClickListener { initVerifyType(true) })
        usePwTv?.setOnClickListener(View.OnClickListener { initVerifyType(false) })
        if (type == SignatureType.NODE_REGISTER) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.node_register_node_name)
            )
            titleName2?.setText(
                activity!!.resources.getString(R.string.node_register_fee)
            )
            titleRootView3?.setVisibility(View.GONE)
            balanceTv?.setText(
                activity!!.resources
                    .getString(R.string.balance_of) + registerNodeSignatureBean!!.balance
            )
            desc1?.setText(registerNodeSignatureBean!!.desc1)
            desc2?.setText(registerNodeSignatureBean!!.desc2)
            input1?.setHint(activity!!.resources.getString(R.string.mortgage_value))
            input1?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
            input1?.addTextChangedListener(nodeRegisterWatcher)
            passwordEt?.addTextChangedListener(nodeRegisterWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.VOTE) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.title_vote_count)
            )
            titleName2?.setText(activity!!.resources.getString(R.string.fee))
            desc1?.setText(voteSignatureBean!!.voteValue + "UYT")
            desc2?.setText(BigDecimalUtils.roundDOWN(voteSignatureBean!!.fee, 4) + "UYT")
            titleRootView3?.setVisibility(View.GONE)
            input1?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.REDEEM) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.title_redeem_count)
            )
            titleName2?.setText(activity!!.resources.getString(R.string.fee))
            desc1?.setText(redeemSignatureBean!!.redeemValue + "UYT")
            desc2?.setText(
                BigDecimalUtils.roundDOWN(
                    redeemSignatureBean!!.fee,
                    4
                ) + "UYT"
            )
            titleRootView3?.setVisibility(View.GONE)
            input1?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.TURN) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.title_switch_count)
            )
            titleName2?.setText(activity!!.resources.getString(R.string.fee))
            desc1?.setText(turnSignatureBean!!.turnValue + "UYT")
            desc2?.setText(BigDecimalUtils.roundDOWN(turnSignatureBean!!.fee, 4) + "UYT")
            titleRootView3?.setVisibility(View.GONE)
            input1?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.DEDUCT) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.title_deduct_count)
            )
            titleName2?.setText(activity!!.resources.getString(R.string.fee))
            desc1?.setText(deductSignatureBean!!.deductValue + "UYT")
            desc2?.setText(
                BigDecimalUtils.roundDOWN(
                    deductSignatureBean!!.fee,
                    4
                ) + "UYT"
            )
            titleRootView3?.setVisibility(View.GONE)
            input1?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.TRANSFER) {
            titleName1?.setText(
                activity!!.resources
                    .getString(R.string.title_transfer_target_address)
            )
            titleName2?.setText(
                activity!!.resources.getString(R.string.title_asset_transfer_count)
            )
            titleName3?.setText(activity!!.resources.getString(R.string.fee))
            balanceTv?.setVisibility(View.GONE)
            desc1?.setText(transferSignatureBean!!.addressHash)
            var chainType = " UYT"
            if (transferSignatureBean!!.chainType == AssetOptConstant.BTC) {
                chainType = " U-BTC"
            } else if (transferSignatureBean!!.chainType == AssetOptConstant.ETH) {
                chainType = " U-ETH"
            } else if (transferSignatureBean!!.chainType == AssetOptConstant.USDT) {
                chainType = " U-USDT"
            } else if (transferSignatureBean!!.chainType == AssetOptConstant.HT) {
                chainType = " U-HT"
            }
            desc2?.setText(transferSignatureBean!!.value + chainType)
            desc3?.setText(
                BigDecimalUtils.roundDOWN(
                    transferSignatureBean!!.fee,
                    4
                ) + "UYT"
            )
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.WITHDRAW) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.withdraw_address)
            )
            titleName2?.setText(
                activity!!.resources.getString(R.string.title_asset_transfer_count)
            )
            titleName3?.setText(
                activity!!.resources.getString(R.string.withdraw_fee)
            )
            balanceTv?.setVisibility(View.GONE)
            desc1?.setText(withdrawSignatureBean!!.addressHash)
            var chainType = " UYT"
            if (withdrawSignatureBean!!.chainType == WithdrawConstant.BTC) {
                chainType = " U-BTC"
            } else if (withdrawSignatureBean!!.chainType == WithdrawConstant.ETH) {
                chainType = " U-ETH"
            } else if (withdrawSignatureBean!!.chainType == WithdrawConstant.USDT) {
                chainType = " U-USDT"
            } else if (withdrawSignatureBean!!.chainType == WithdrawConstant.HT) {
                chainType = " U-HT"
            }
            desc2?.setText(withdrawSignatureBean!!.value + chainType)
            desc3?.setText(
                BigDecimalUtils.roundDOWN(
                    withdrawSignatureBean!!.fee,
                    4
                ) + "UYT"
            )
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.MAP) {
            titleName1?.setText(
                activity!!.resources.getString(R.string.title_asset_map_count)
            )
            titleName2?.setText(
                activity!!.resources.getString(R.string.title_asset_map_fee)
            )
            titleRootView3?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            var chainType = ""
            if (mapSignatureBean!!.chainType == WithdrawConstant.BTC) {
                chainType = " U-BTC"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.ETH) {
                chainType = " U-ETH"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.USDT) {
                chainType = " U-USDT"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.HT) {
                chainType = " U-HT"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.DNS) {
                chainType = " UYT"
            }
            desc1?.setText(mapSignatureBean!!.value + chainType)
            desc2?.setText(BigDecimalUtils.roundDOWN(mapSignatureBean!!.fee, 4) + "UYT")
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.UNMAP) {
            titleName1?.setText(
                activity!!.resources
                    .getString(R.string.title_asset_map_cancel_count)
            )
            titleName2?.setText(
                activity!!.resources.getString(R.string.title_asset_map_cancel_fee)
            )
            titleRootView3?.setVisibility(View.GONE)
            balanceTv?.setVisibility(View.GONE)
            var chainType = ""
            if (mapSignatureBean!!.chainType == WithdrawConstant.BTC) {
                chainType = " U-BTC"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.ETH) {
                chainType = " U-ETH"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.USDT) {
                chainType = " U-USDT"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.HT) {
                chainType = " U-HT"
            } else if (mapSignatureBean!!.chainType == WithdrawConstant.DNS) {
                chainType = " UYT"
            }
            desc1?.setText(mapSignatureBean!!.value + chainType)
            desc2?.setText(BigDecimalUtils.roundDOWN(mapSignatureBean!!.fee, 4) + "UYT")
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.ONE_KEY) {
            titleTv?.setText(getString(R.string.one_click_raise_interest))
            titleName1?.setText(getString(R.string.extract_value))
            titleName2?.setText(getString(R.string.node_num))
            titleName3?.setText(getString(R.string.extract_fee))
            balanceTv?.setVisibility(View.GONE)
            desc1?.setText(oneKeyExtractSignatureBean!!.unclaimedCount.toString() + " UYT")
            desc2?.setText(oneKeyExtractSignatureBean!!.count.toString() + "")
            desc3?.setText(
                BigDecimalUtils.roundDOWN(
                    oneKeyExtractSignatureBean!!.rate.toString() + "",
                    4
                ) + " UYT"
            )
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        } else if (type == SignatureType.REFERENDUM) {
            titleTv?.setText(getString(R.string.comm_referendum))
            titleName1?.setText(getString(R.string.choose_opinions))
            titleName2?.setText(getString(R.string.title_vote_count))
            titleName3?.setText(getString(R.string.fee))
            if (referendumVoteSignatureBean!!.voteType == 1) {
                desc1?.setText(getString(R.string.agree))
            } else {
                desc1?.setText(getString(R.string.oppose))
            }
            balanceTv?.setVisibility(View.GONE)
            desc2?.setText("0 UYT")
            desc3?.setText(
                BigDecimalUtils.roundDOWN(
                    referendumVoteSignatureBean!!.fee + "",
                    4
                ) + " UYT"
            )
            input1?.setVisibility(View.GONE)
            passwordEt?.addTextChangedListener(passwordWatcher)
            okBtn?.setOnClickListener(clickFastListener)
        }
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {
            if (useFingerprint) {
                fingerprintDialog!!.show(fragmentManager!!, "fingerprint")
                return
            }
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            if (type == SignatureType.NODE_REGISTER) {
                KeyboardUtils.hideSoftKeyboard(input1)
                listener?.let { it.onRequest(type,passwordEt!!.text.toString().trim()) }
            } else {
                listener?.let { it.onRequest(type,passwordEt!!.text.toString().trim()) }
            }
        }
    }

    private fun initVerifyType(useFingerprint: Boolean) {
        this.useFingerprint = useFingerprint
        if (useFingerprint) {
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            passwordEt!!.setText("")
            usePwTv!!.visibility = View.VISIBLE
            useFingerprintIv!!.visibility = View.GONE
            passwordEt!!.visibility = View.GONE
            enableBtn()
        } else {
            usePwTv!!.visibility = View.GONE
            useFingerprintIv!!.visibility = View.VISIBLE
            passwordEt!!.visibility = View.VISIBLE
            disableBtn()
        }
    }

    var nodeRegisterWatcher: TextWatcher = object : TextWatcher {
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
            if (UserConfig.singleton?.isUseFingerprint!!) {
                if (!input1?.text.toString().isEmpty()) {
                    enableBtn();
                } else {
                    disableBtn();
                }
            } else {
                if (input1?.text.toString().isNotEmpty() && passwordEt?.text.toString().isNotEmpty()) {
                    enableBtn();
                } else {
                    disableBtn();
                }
            }
        }
    }
    var passwordWatcher: TextWatcher = object : TextWatcher {
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
            if (!useFingerprint) {
                if (!passwordEt!!.text.toString().isEmpty()) {
                    enableBtn()
                } else {
                    disableBtn()
                }
            }
        }
    }

    private fun disableBtn() {
        okBtn!!.isEnabled = false
        //        okBtn.setBackground(SkinCompatResources.getDrawable(getActivity(),R.drawable.btn_enable_bg));
    }

    private fun enableBtn() {
        okBtn!!.isEnabled = true
        //        okBtn.setBackground(SkinCompatResources.getDrawable(getActivity(),R.drawable.btn_enable_bg));
    }

    /**
     * 注册节点
     */
    private fun startRegister(pw: String) {
//        OkGo.<BaseBean<String>>post(ServiceUrl.nodeRegister)
//                .tag(this)
//                .params("name",registerNodeSignatureBean.getNodeName())
//                .params("recommendNode",registerNodeSignatureBean.getNodeAddress())
//                .params("website",registerNodeSignatureBean.getWebsite())
//                .params("pass",pw)
//                .params("count",input1.getText().toString())
//                .execute(new DialogCallback<BaseBean<String>>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBean<String>> response) {
//                        if (response.body().status == 200) {
//                            AccountBean accountBean = ECApplication.i().getAccountBean();
//                            accountBean.setNodeHash(response.body().data);
//                            UserConfig.getSingleton().setAccount(new Gson().toJson(accountBean));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getActivity().getString(R.string.tip_node_register_success));
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadVoteList));
//                            LaunchConfig.startChooseNodeLogoAc(getActivity(),response.body().data);
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 投票
     */
    private fun voteBet(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.vote)
//                .tag(this)
//                .params("accountHash", ECApplication.i().getAccountBean().getLoginAcountHash())
//                .params("nodeHash",voteSignatureBean.getNodeHash())
//                .params("count",voteSignatureBean.getVoteValue())
//                .params("rate",voteSignatureBean.getFee())
//                .params("remark",voteSignatureBean.getRemark())
//                .params("pass",pw)
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadMyVoteList));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_vote_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 赎回
     */
    private fun redeem(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.redeem)
//                .tag(this)
//                .params("accountHash", ECApplication.i().getAccountBean().getLoginAcountHash())
//                .params("nodeHash",redeemSignatureBean.getNodeHash())
//                .params("count",redeemSignatureBean.getRedeemValue())
//                .params("rate",redeemSignatureBean.getFee())
//                .params("pass",pw)
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadMyVoteList));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_vote_redeem_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 转投
     */
    private fun turn(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.turn)
//                .tag(this)
//                .params("accountHash", ECApplication.i().getAccountBean().getLoginAcountHash())
//                .params("nodeHash",turnSignatureBean.getNodeHash())
//                .params("count",turnSignatureBean.getTurnValue())
//                .params("rate",turnSignatureBean.getFee())
//                .params("pass",pw)
//                .params("targetNodeHash",turnSignatureBean.getTargetNodeHash())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadMyVoteList));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_vote_switch_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 赎回
     */
    private fun deduct(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.deduct)
//                .tag(this)
//                .params("accountHash", ECApplication.i().getAccountBean().getLoginAcountHash())
//                .params("nodeHash",deductSignatureBean.getNodeHash())
//                .params("count",deductSignatureBean.getDeductValue())
//                .params("rate",deductSignatureBean.getFee())
//                .params("pass",pw)
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadMyVoteList));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_vote_deduct_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 转账
     */
    private fun transfer(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.transfer)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",transferSignatureBean.getValue())
//                .params("addressHash",transferSignatureBean.getAddressHash())
//                .params("serviceCharge",transferSignatureBean.getFee())
//                .params("memo",transferSignatureBean.getRemark())
//                .params("type",transferSignatureBean.getChainType())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_transfer_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 提现
     */
    private fun withdraw(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.withdraw)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",withdrawSignatureBean.getValue())
//                .params("addressHash",withdrawSignatureBean.getAddressHash())
//                .params("serviceCharge",withdrawSignatureBean.getFee())
//                .params("memo",withdrawSignatureBean.getRemark())
//                .params("type",withdrawSignatureBean.getChainType())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_withdraw_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 映射
     */
    private fun map(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.map)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",mapSignatureBean.getValue())
//                .params("serviceCharge",mapSignatureBean.getFee())
//                .params("type",mapSignatureBean.getChainType())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_map_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * dns 映射
     */
    private fun dnsMap(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.dnsMap)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",mapSignatureBean.getValue())
//                .params("serviceCharge",mapSignatureBean.getFee())
//                .params("type",mapSignatureBean.getChainType())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_map_success));
//                            getActivity().finish();
//                        } else {
//                            ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 取消映射
     */
    private fun cancelMap(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.cancelMap)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",mapSignatureBean.getValue())
//                .params("serviceCharge",mapSignatureBean.getFee())
//                .params("type",mapSignatureBean.getChainType())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_map_cancel_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 取消映射
     */
    private fun dnsCancelMap(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.dnsCancelMap)
//                .tag(this)
//                .params("pwd", pw)
//                .params("count",mapSignatureBean.getValue())
//                .params("serviceCharge",mapSignatureBean.getFee())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadAsset));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_asset_map_cancel_success));
//                            getActivity().finish();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 一键提息
     */
    private fun oneKey(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.oneClickRaiseInterest)
//                .tag(this)
//                .params("pwd", pw)
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.reloadMyVoteList));
//                            if (isAdded()) ToastUtils.showShort(getActivity(),getResources().getString(R.string.tip_vote_deduct_success));
//                            dismiss();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        if (isAdded()) ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    /**
     * 公投投票
     */
    private fun referendumVote(pw: String) {
//        OkGo.<BaseBeanNoData>post(ServiceUrl.referendumVote)
//                .tag(this)
//                .params("pwd", pw)
//                .params("id", referendumVoteSignatureBean.getId())
//                .params("type", referendumVoteSignatureBean.getVoteType())
//                .params("rate", referendumVoteSignatureBean.getFee())
//                .execute(new DialogCallback<BaseBeanNoData>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBeanNoData> response) {
//                        if (response.body().status == 200) {
//                            if (referendumVoteSignatureBean.getVoteType() == 1) {
//                                if (isAdded()) ToastUtils.showShort(getActivity(),getString(R.string.already_agree));
//                            } else {
//                                if (isAdded()) ToastUtils.showShort(getActivity(),getString(R.string.opposed));
//                            }
//                            EventBus.getDefault().post(new LocalEvent<>(LocalEvent.referendumVote,referendumVoteSignatureBean.getVoteType()));
//                            dismiss();
//                        } else {
//                            if (isAdded()) ToastUtils.showShort(getActivity(),response.body().errorMsg);
//                        }
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        dismiss();
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
    }

    override fun onAuthenticationSucceeded(purpose: Int, value: String) {
        if (purpose == KeyProperties.PURPOSE_DECRYPT) {
            if (type == SignatureType.NODE_REGISTER) {
                startRegister(value)
            } else if (type == SignatureType.VOTE) {
                voteBet(value)
            } else if (type == SignatureType.REDEEM) {
                redeem(value)
            } else if (type == SignatureType.TURN) {
                turn(value)
            } else if (type == SignatureType.DEDUCT) {
                deduct(value)
            } else if (type == SignatureType.TRANSFER) {
                transfer(value)
            } else if (type == SignatureType.WITHDRAW) {
                withdraw(value)
            } else if (type == SignatureType.MAP) {
                if (mapSignatureBean!!.chainType == WithdrawConstant.DNS) {
                    dnsMap(value)
                } else {
                    map(value)
                }
            } else if (type == SignatureType.UNMAP) {
                if (mapSignatureBean!!.chainType == WithdrawConstant.DNS) {
                    dnsCancelMap(value)
                } else {
                    cancelMap(value)
                }
            } else if (type == SignatureType.ONE_KEY) {
                oneKey(value)
            } else if (type == SignatureType.REFERENDUM) {
                referendumVote(value)
            }
        }
    }
    private var listener:RequestListener?=null
    fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(type:Int,params:String)
    }

    companion object {
        fun newInstance(type: Int, bean: Any?): SignatureDialog {
            val fragment = SignatureDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putParcelable("signatureBean", bean as Parcelable?)
            fragment.arguments = bundle
            return fragment
        }
    }
}