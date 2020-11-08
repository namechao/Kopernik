package com.kopernik.app.config

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.kopernik.ui.login.RegisterSetUpPasswordActivity
import com.kopernik.ui.MainActivity
import com.kopernik.ui.Ecology.*
import com.kopernik.ui.Ecology.entity.CheckRegisterInfo
import com.kopernik.ui.login.*
import com.kopernik.ui.asset.*
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.mine.BuyMningMachineActivity
import com.kopernik.ui.mine.PurchaseMiningMachineryActivity
import com.kopernik.ui.mine.SynthetiseUTCActivity
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.MineBean
import com.kopernik.ui.my.*
import com.kopernik.ui.setting.*

object LaunchConfig {
    //回到主页面
    fun startMainAc(context: Context) {
        val intent =
            Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
          context.startActivity(intent)
    }
    //进选择账户
    fun startLoginActivity(context: Context) {
        val intent =
            Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    fun startForgetPasswordNextActivity(context: Activity ,registerType: Int,changePasswordType:Int,account:String,verifyCode: String) {
        val intent =
            Intent(context, ForgetPasswordNextActivity::class.java)
        intent.putExtra("registerType",registerType)
        intent.putExtra("account",account)
        intent.putExtra("changePasswordType",changePasswordType)
        intent.putExtra("verifyCode",verifyCode)
        context.startActivityForResult(intent,10)
    }

    fun startTradePasswordNextActivity(context: Activity,registerType:String,changeTradePasswordType:Int,verifyCode: String) {
        val intent =
            Intent(context, ForgetTradePasswordNextActivity::class.java)
            intent.putExtra("registerType",registerType)
            intent.putExtra("changeTradePasswordType",changeTradePasswordType)
            intent.putExtra("verifyCode",verifyCode)
        context.startActivityForResult(intent,13)
    }
   fun startGoogleVerifyFirstStepActivity(context: Context) {
        val intent =
            Intent(context, GoogleVerifyFirstStepActivity::class.java)
        context.startActivity(intent)
    }
    fun startGoogleVerifyActivity(context: Context) {
        val intent =
            Intent(context, GoogleVerifyActivity::class.java)
        context.startActivity(intent)
    }
   fun startGoogleVerifySecondStepActivity(context: Context) {
        val intent =
            Intent(context, GoogleVerifySecondStepActivity::class.java)
        context.startActivity(intent)
    }
   fun startInviteFriendsActivity(context: Context) {
        val intent =
            Intent(context, InviteFriendsActivity::class.java)
        context.startActivity(intent)
    }
    fun startNoticeActivity(context: Context,id: String?) {
        val intent =
            Intent(context, NoticeActivity::class.java)
        intent.putExtra("id",id)
        context.startActivity(intent)
    }
   fun startGoogleVerifiedActivity(context: Context) {
        val intent =
            Intent(context, GoogleVerifiedActivity::class.java)
        context.startActivity(intent)
    }

    fun startTradePasswordActivity(context: Context,registerType:Int,changeTradePasswordType:Int) {
        val intent =
            Intent(context, ForgetTradePasswordActivity::class.java)
        intent.putExtra("changeTradePasswordType",changeTradePasswordType)
        intent.putExtra("registerType",registerType)
        context.startActivity(intent)
    }

    fun startUserProtocolActivity(
        context: Context
    ) {
        val intent =
            Intent(context, UserProtocolActivity::class.java)
        context.startActivity(intent)
    }

    fun startRegisterSetUpPasswordActivity(
        context: Activity,type:Int,acount:String,invitationCode:String,verifyCode:String
    ) {
        val intent = Intent(context, RegisterSetUpPasswordActivity::class.java)
        intent.putExtra("type",type)
        intent.putExtra("acount",acount)
        intent.putExtra("invitationCode",invitationCode)
        intent.putExtra("verifyCode",verifyCode)
        context.startActivityForResult(intent,12)
    }

    fun startForgetPasswordActivity(
        context: Context,
        registerType:Int,changePasswordType:Int
    ) {
        val intent =
            Intent(context, ForgetPasswordActivity::class.java)
           intent.putExtra("registerType",registerType)
           intent.putExtra("changePasswordType",changePasswordType)

        context.startActivity(intent)
    }

    fun startRegisterActivity(context: Context) {
        val intent =
            Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

    fun startNodeRegisterAc(
        context: Context,
        info: CheckRegisterInfo?
    ) {
        val intent =
            Intent(context, NodeRegisterActivity::class.java)
        intent.putExtra("info", info)
        context.startActivity(intent)
    }

    fun startChooseNodeLogoAc(
        context: Context,
        nodeHash: String?
    ) {
        val intent =
            Intent(context, ChooseNodeLogoActivity::class.java)
        intent.putExtra("nodeHash", nodeHash)
        context.startActivity(intent)
    }

    fun startVoteBetAc(
        context: Context,
        type: Int,
        nodeHash: String?
    ) {
        val intent =
            Intent(context, VoteBetActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("nodeHash", nodeHash)
        context.startActivity(intent)
    }

    fun startMyVoteHistoryAc(context: Context) {
        val intent =
            Intent(context, MyVoteHistoryActivity::class.java)
        context.startActivity(intent)
    }

    fun startVoteOperateAc(
        context: Context,
        type: Int,
        nodeHash: String?,
        interest: String?,
        nodeAddress: String?
    ) {
        val intent =
            Intent(context, VoteOperateActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("nodeHash", nodeHash)
        if (interest != null) intent.putExtra("interest", interest)
        intent.putExtra("nodeAddress", nodeAddress)
        context.startActivity(intent)
    }

    fun startRedeemTimeLineAc(
        context: Context,
        type: Int,
        nodeHash: String?
    ) {
        val intent =
            Intent(context, RedeemTimeLineActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("nodeHash", nodeHash)
        context.startActivity(intent)
    }

    fun startAssetDetailsAc(context: Context, type: Int,assetTitle:String) {
        val intent =
            Intent(context, UYTAssetActivity::class.java)
        intent.putExtra("chainType", type)
        intent.putExtra("assetTitle", assetTitle)
        context.startActivity(intent)
    }


    fun startTransferAc(context: Context,allConfigEntity: AllConfigEntity) {
        val intent =
            Intent(context, TransferActivity::class.java)
        intent.putExtra("allConfigEntity",allConfigEntity)
        context.startActivity(intent)
    }

    fun startDepositCoinActivity(
        context: Context
    ) {
        val intent =
            Intent(context, DepositCoinActivity::class.java)
        context.startActivity(intent)
    }
    fun startDepositMoneyActivity(
        context: Context
    ) {
        val intent =
            Intent(context, DepositMoneyActivity::class.java)
        context.startActivity(intent)
    }

    fun startWithdrawCoinAc(context: Context,allConfigEntity: AllConfigEntity) {
        val intent =
            Intent(context, WithdrawCoinActivity::class.java)
        intent.putExtra("allConfigEntity",allConfigEntity)
        context.startActivity(intent)
    }

//    fun ToBeExtractedListAc(
//        context: Context,
//        type: Int,
//        chainName: String,
//        availableAmount: String
//    ) {
//        val intent =
//            Intent(context, ToBeExtractedListActivity::class.java)
//        intent.putExtra("chainName", chainName)
//        intent.putExtra("chainType", type)
//        intent.putExtra("availableAmount", availableAmount)
//        context.startActivity(intent)
//    }

    fun AssetTransformAc(context: Context, type: Int, name: String, availableAmount: String) {
        val intent = Intent(context, AssetTransformActivty::class.java)
        intent.putExtra("chainType", type)
        intent.putExtra("chainName", name)
        intent.putExtra("availableAmount", availableAmount)
        context.startActivity(intent)
    }

    fun startMapActivity(context: Context, mapType: Int, type: Int) {
        val intent =
            Intent(context, MapActivity::class.java)
        intent.putExtra("mapType", mapType)
        intent.putExtra("type", type)
        context.startActivity(intent)
    }

    fun startInviteAddressAc(context: Context) {
        val intent =
            Intent(context, InviteAddressActivity::class.java)
        context.startActivity(intent)
    }

    fun startMapTimeLineAc(
        context: Context,
        chainName: String?
    ) {
        val intent =
            Intent(context, MapTimeLineActivity::class.java)
        intent.putExtra("chainName", chainName)
        context.startActivity(intent)
    }

    fun startQRCodeAc(context: Context, content: String?) {
        val intent =
            Intent(context, QRCodeActivity::class.java)
        intent.putExtra("content", content)
        context.startActivity(intent)
    }

    fun startSecurityAc(context: Context) {
        val intent =
            Intent(context, SecurityActivity::class.java)
        context.startActivity(intent)
    }

    fun startModifyNickAc(context: Context) {
        val intent =
            Intent(context, ModifyNickActivity::class.java)
        context.startActivity(intent)
    }

    fun startModifyPasswordAc(context: Context) {
        val intent =
            Intent(context, ModifyPassActivity::class.java)
        context.startActivity(intent)
    }

    fun startExportMnemonicAc(context: Context) {
        val intent =
            Intent(context, ExportMnemonicActivity::class.java)
        context.startActivity(intent)
    }

    fun startExportMnemonicSuccessAc(
        context: Context
    ) {
        val intent = Intent(context, ExportMnemonicSuccessActivity::class.java)
        context.startActivity(intent)
    }

    fun startContactAc(context: Context) {
        val intent =
            Intent(context, ContactActivity::class.java)
        context.startActivity(intent)
    }

    fun startContactForResult(ac: Activity, requestCode: Int) {
        val intent = Intent(ac, ContactActivity::class.java)
        intent.putExtra("forResult", true)
        ac.startActivityForResult(intent, requestCode)
    }

    /**
     * 新增  contactId address remark 为 null
     */
    fun startAddContactAc(
        context: Context,
        type: Int,
        contactId: Int,
        address: String?,
        remark: String?
    ) {
        val intent =
            Intent(context, AddContactActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("contactId", contactId)
        intent.putExtra("address", address)
        intent.putExtra("remark", remark)
        context.startActivity(intent)
    }

    fun startSettingActivityAc(context: Context) {
        val intent =
            Intent(context, SettingActivity::class.java)
        context.startActivity(intent)
    }
    fun startRealNameAuthenticationActivity(context: Context) {
        val intent =
            Intent(context, RealNameAuthenticationActivity::class.java)
        context.startActivity(intent)
    }

    fun startNodeAboutActivityAc(context: Context) {
        val intent =
            Intent(context, NodeAboutActivity::class.java)
        context.startActivity(intent)
    }

    fun startAboutUsActivityAc(context: Context) {
        val intent =
            Intent(context, AboutUsActivity::class.java)
        context.startActivity(intent)
    }
    fun startUTDMRevenueRecordActivity(context: Context) {
        val intent =
            Intent(context, UTDMRevenueRecordActivity::class.java)
        context.startActivity(intent)
    }
    fun startBuyMningMachineActivity(context: Activity, mineType:Int?,minePrice:String?,minebean: MineBean?) {
        val intent =
            Intent(context, BuyMningMachineActivity::class.java)
        intent.putExtra("mineType",mineType)
        intent.putExtra("minePrice",minePrice)
        intent.putExtra("minebean",minebean)
        context.startActivityForResult(intent,PurchaseMiningMachineryActivity.REQUEST_BUY_MINE)
    }
    fun startInviteFriendsSecondActivity(context: Context,uid:String) {
        val intent =
            Intent(context, InviteFriendsSecondActivity::class.java)
        intent.putExtra("uid",uid)
        context.startActivity(intent)
    }
    fun startSynthetiseUTCActivity(context: Context) {
        val intent =
            Intent(context, SynthetiseUTCActivity::class.java)
        context.startActivity(intent)
    }
    fun startPurchaseMiningMachineryActivity(context: Context) {
        val intent =
            Intent(context, PurchaseMiningMachineryActivity::class.java)
        context.startActivity(intent)
    }
    fun startUTCAssetActivity(context: Context,asset:String?) {
        val intent =
            Intent(context, UTCAssetActivity::class.java)
        intent.putExtra("asset",asset)
        context.startActivity(intent)
    }
    fun startUTKAssetActivity(context: Context,asset:String?) {
        val intent =
            Intent(context, UTKAssetActivity::class.java)
        intent.putExtra("asset",asset)
        context.startActivity(intent)
    }
    fun startUDMTAssetActivity(context: Context,asset:String?) {
        val intent =
            Intent(context, UTDMAssetActivity::class.java)
        intent.putExtra("asset",asset)
        context.startActivity(intent)
    }
    fun startUYTAssetActivity(context: Context,asset:String?,type:String) {
        val intent =
            Intent(context, UYTAssetActivity::class.java)
        intent.putExtra("asset",asset)
        intent.putExtra("type",type)
        context.startActivity(intent)
    }
    fun startUYTTESTAssetActivity(context: Context,asset:String?,type:String) {
        val intent =
            Intent(context, UYTTESTAssetActivity::class.java)
        intent.putExtra("asset",asset)
        intent.putExtra("type",type)
        context.startActivity(intent)
    }
    fun startWebViewAc(
        context: Context,
        url: String?,
        title: String?
    ) {
        val intent =
            Intent(context, WebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", title)
        context.startActivity(intent)
    }

    fun startVersionAc(context: Context) {
        val intent =
            Intent(context, VersionActivity::class.java)
        context.startActivity(intent)
    }

    fun startVerifyPwAcForResult(ac: Activity, requestCode: Int) {
        val intent =
            Intent(ac, VerifyPwActivity::class.java)
        ac.startActivityForResult(intent, requestCode)
    }



    fun startReferendumListAc(context: Context) {
        val intent =
            Intent(context, ReferendumListActivity::class.java)
        context.startActivity(intent)
    }

    fun startReferendumDetailsAc(context: Context, id: Int) {
        val intent =
            Intent(context, ReferendumDetailsActivity::class.java)
        intent.putExtra("id", id)
        context.startActivity(intent)
    }

    fun startCrossChainTxAc(context: Context, type: Int) {
        val intent = Intent(context, CrossChainTxActivity::class.java)
        intent.putExtra("type", type)
        context.startActivity(intent)
    }

    fun startNodeLeaderBoardAc(context: Context) {
        val intent =
            Intent(context, NodeLeaderBoardActivity::class.java)
        context.startActivity(intent)
    }

    fun startSkinAc(context: Context) {
        val intent =
            Intent(context, SkinActivity::class.java)
        context.startActivity(intent)
    }
}