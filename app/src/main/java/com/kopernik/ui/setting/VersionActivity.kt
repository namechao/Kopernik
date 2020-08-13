package com.kopernik.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.allenliu.versionchecklib.core.http.HttpHeaders
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener
import com.google.gson.Gson
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.setting.entity.UpdateBean2
import kotlinx.android.synthetic.main.activity_version.*
import com.kopernik.data.api.Api

class VersionActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_version

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.app_update))
        versionTv.text = BuildConfig.VERSION_NAME

        updateSpt.setOnClickListener { v -> checkUpdate2() }
    }

    override fun initData() {
        checkUpdate()
    }

    private fun checkUpdate() {
//        OkGo.< BaseBean < UpdateBean > > get<BaseBean<UpdateBean>>(ServiceUrl.checkUpdate)
//            .tag(this)
//            .execute(object : JsonCallback<BaseBean<UpdateBean?>?>() {
//                fun onSuccess(response: Response<BaseBean<UpdateBean?>?>) {
//                    if (response.body().data.getDeploy()
//                            .getVersionCode() > BuildConfig.VERSION_CODE
//                    ) {
//                        updateSpt.setRightString(getString(R.string.found_new_version_2))
//                            .setRightTextColor(
//                                SkinCompatResources.getColor(
//                                    getActivity(),
//                                    R.color.time_line_year
//                                )
//                            )
//                    } else {
//                        ToastUtils.showShort(
//                            getActivity(),
//                            getString(R.string.current_is_new_version)
//                        )
//                    }
//                }
//
//                fun onShowErrorMsg(code: Int) {
//                    ErrorMsg.showErrorMsg(getActivity(), code)
//                }
//            })
    }

    private fun checkUpdate2() {
        val httpHeaders = HttpHeaders()
        httpHeaders.put("apptype", "android")
        AllenVersionChecker
            .getInstance()
            .requestVersion()
            .setHttpHeaders(httpHeaders)
            .setRequestUrl(Api.checkUpdate)
            .request(object : RequestVersionListener {
                @Nullable
               override fun onRequestVersionSuccess(result: String?): UIData? {
                    val updateBean: UpdateBean2 =
                        Gson().fromJson<UpdateBean2>(result, UpdateBean2::class.java)
                    val deployBean: UpdateBean2.DataBean.DeployBean? =
                        updateBean.data?.deploy
                    if (deployBean?.versionCode!! > BuildConfig.VERSION_CODE) {
                        return UIData
                            .create()
                            .setDownloadUrl(deployBean.url)
                            .setTitle(deployBean.versionName)
                            .setContent(deployBean.versionDesc)
                    } else {
                        ToastUtils.showShort(
                            getActivity(),
                            getString(R.string.current_is_new_version)
                        )
                    }
                    return null
                }

              override  fun onRequestVersionFailure(message: String?) {
                    ToastUtils.showShort(getActivity(), message)
                }
            })
            .setCustomVersionDialogListener(createUpdateDialog())
            .setShowNotification(true)
            .setShowDownloadingDialog(false)
            .setForceRedownload(true) //本地有安装包缓存也会重新下载apk
            .executeMission(getActivity())
    }

    private fun createUpdateDialog(): CustomVersionDialogListener? {
        return CustomVersionDialogListener { context, versionBundle ->
            val baseDialog = Dialog(context, R.style.UpdateDialog)
                baseDialog.setContentView(R.layout.dialog_update)
            baseDialog.setCanceledOnTouchOutside(false)
            val version =
                baseDialog.findViewById<TextView>(R.id.tv_version)
            val textView =
                baseDialog.findViewById<TextView>(R.id.tv_msg)
            val content: String = versionBundle.content.replace("\\n", " \n")
            textView.text = content
            version.text = versionBundle.title
            baseDialog
        }
    }
}