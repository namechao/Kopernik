package com.kopernik.app.config

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.kopernik.ui.login.RegisterSetUpPasswordActivity
import com.kopernik.ui.MainActivity
import com.kopernik.ui.Ecology.*
import com.kopernik.ui.Ecology.entity.CheckRegisterInfo
import com.kopernik.ui.login.*
import com.kopernik.ui.login.bean.Mnemonic
import com.kopernik.ui.asset.*
import com.kopernik.ui.setting.*

object LaunchConfig {
    //回到主页面
    fun startMainAc(context: Context) {
        val intent =
            Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
    //进选择账户
    fun startChooseAccountAc(context: Context) {
        val intent =
            Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    fun startForgetPasswordNextActivity(context: Context) {
        val intent =
            Intent(context, ForgetPasswordNextActivity::class.java)
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
        context: Context
    ) {
        val intent = Intent(context, RegisterSetUpPasswordActivity::class.java)
        context.startActivity(intent)
    }

    fun startForgetPasswordActivity(
        context: Context
    ) {
        val intent =
            Intent(context, ForgetPasswordActivity::class.java)
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

    fun startMyVoteAc(context: Context) {
        val intent =
            Intent(context, MyVoteActivity::class.java)
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
            Intent(context, AssetDetailsActivity::class.java)
        intent.putExtra("chainType", type)
        intent.putExtra("assetTitle", assetTitle)
        context.startActivity(intent)
    }
    fun startOtherAssetDetailsAc(context: Context, type: Int, assetTitle: String) {
        val intent =
            Intent(context, OtherAssetDetailsActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("assetTitle", assetTitle)
        context.startActivity(intent)
    }

    fun startTransferAc(context: Context, type: Int, chainName: String) {
        val intent =
            Intent(context, TransferActivity::class.java)
        intent.putExtra("chainType", type)
        intent.putExtra("chainName", chainName)
        context.startActivity(intent)
    }

    fun startDepositCoinActivity(
        context: Context,
        type: Int,
        address: String?
    ) {
        val intent =
            Intent(context, DepositCoinActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("address", address)
        context.startActivity(intent)
    }
    fun startDepositMoneyActivity(
        context: Context,
        type: Int,
        address: String?
    ) {
        val intent =
            Intent(context, DepositMoneyActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("address", address)
        context.startActivity(intent)
    }

    fun startWithdrawCoinAc(context: Context, id: Int, type: Int, availableAmount: String) {
        val intent =
            Intent(context, WithdrawCoinActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("id", id)
        intent.putExtra("availableAmount", availableAmount)
        context.startActivity(intent)
    }

    fun ToBeExtractedListAc(
        context: Context,
        type: Int,
        chainName: String,
        availableAmount: String
    ) {
        val intent =
            Intent(context, ToBeExtractedListActivity::class.java)
        intent.putExtra("chainName", chainName)
        intent.putExtra("chainType", type)
        intent.putExtra("availableAmount", availableAmount)
        context.startActivity(intent)
    }

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

    fun startBaseSettingActivityAc(context: Context) {
        val intent =
            Intent(context, BaseSettingActivity::class.java)
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

    fun startSwitchAccountAc(context: Context) {
        val intent =
            Intent(context, SwitchAccountActivity::class.java)
        context.startActivity(intent)
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