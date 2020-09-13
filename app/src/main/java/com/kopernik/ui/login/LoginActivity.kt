package com.kopernik.ui.login

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aleyn.mvvm.base.NoViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ChoseAreaCodeDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.DeviceIDUtil
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import com.kopernik.ui.login.viewmodel.LoginViewModel
import dev.utils.app.NetWorkUtils
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_asset_transform.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.confirmBtn
import kotlinx.android.synthetic.main.activity_login.etInput
import kotlinx.android.synthetic.main.activity_login.icClear
import kotlinx.android.synthetic.main.activity_login.icEye
import kotlinx.android.synthetic.main.activity_login.ivPhoneHead
import kotlinx.android.synthetic.main.activity_login.ivVerifySucess
import kotlinx.android.synthetic.main.activity_login.llEmailRegister
import kotlinx.android.synthetic.main.activity_login.llHeader
import kotlinx.android.synthetic.main.activity_login.llPhoneRegister
import kotlinx.android.synthetic.main.activity_login.sbProgress
import kotlinx.android.synthetic.main.activity_login.tvEmailRegister
import kotlinx.android.synthetic.main.activity_login.tvEmailRegisterLine
import kotlinx.android.synthetic.main.activity_login.tvPhoneHead
import kotlinx.android.synthetic.main.activity_login.tvPhoneRegister
import kotlinx.android.synthetic.main.activity_login.tvPhoneRegisterLine
import kotlinx.android.synthetic.main.activity_login.tvSeekBar
import kotlinx.android.synthetic.main.activity_login.tvVerifySu
import java.util.ArrayList

class LoginActivity : NewFullScreenBaseActivity<LoginViewModel, ViewDataBinding>() {
    private var registerType=1//默认手机注册
    private var slideVerify=false
    private var openEye=false
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        ImmersionBar.with(this).reset().init()
        return super.onCreateView(name, context, attrs)
    }
    override fun layoutId()=R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        //忘记密码
        forgetPsw.setOnClickListener {
            LaunchConfig.startForgetPasswordActivity(this,registerType,1)
        }
        //进注册页面
        register.setOnClickListener {
            LaunchConfig.startRegisterActivity(this)
        }
        //协议
        userProtocol.setOnClickListener {
          LaunchConfig.startUserProtocolActivity(this)
        }
        sbProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.progress==100){
                    seekBar.thumb=resources.getDrawable(R.color.transparent,null)
                    tvSeekBar.visibility=View.GONE
                    tvVerifySu.visibility=View.VISIBLE
                    ivVerifySucess.visibility=View.VISIBLE
                    seekBar.isEnabled=false
                    slideVerify=true
                }else {
                    seekBar?.progress=0
                }
            }

        })
        //显示区号选择
        llHeader.setOnClickListener {
            showDialog()
        }
    }

    override fun initData() {
        icEye.setOnClickListener {
            if (!openEye){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etInput.transformationMethod = HideReturnsTransformationMethod.getInstance();
                icEye.setBackgroundResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etInput.transformationMethod = PasswordTransformationMethod.getInstance();
                icEye.setBackgroundResource(R.mipmap.ic_close_eye)
            }
        }
        //手机登录
        llPhoneRegister.setOnClickListener {
            registerType=1
            tvPhoneRegister.setTextColor(resources.getColor(R.color.color_ffcf32))
            tvPhoneRegisterLine.visibility=View.VISIBLE
            tvEmailRegister.setTextColor(resources.getColor(R.color.white))
            tvEmailRegisterLine.visibility=View.GONE
            etInput.hint=resources.getString(R.string.login_input_phone_hint)
            etInput.inputType= InputType.TYPE_CLASS_PHONE
            llHeader.visibility= View.VISIBLE
            etInput.setText("")
        }
        //邮箱登录
        llEmailRegister.setOnClickListener {
            registerType=2
            tvPhoneRegister.setTextColor(resources.getColor(R.color.white))
            tvPhoneRegisterLine.visibility=View.GONE
            tvEmailRegister.setTextColor(resources.getColor(R.color.color_ffcf32))
            tvEmailRegisterLine.visibility=View.VISIBLE
            etInput.hint=resources.getString(R.string.login_input_email_hint)
            etInput.inputType= InputType.TYPE_CLASS_TEXT
            etInput.setText("")
            llHeader.visibility= View.GONE

        }
        //眼睛
        icEye.setOnClickListener {
            if (!openEye){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                openEye = true
                etPassword.setSelection(etPassword.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                openEye = false
                etPassword.setSelection(etPassword.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_close_eye)
            }
        }
        //清除
        icClear.setOnClickListener {
            etInput.setText("")
        }

        //下一步按钮
        confirmBtn.setOnClickListener {
            if (!slideVerify){
                ToastUtils.showShort(this, resources.getString(R.string.verify_slide_right))
                return@setOnClickListener
            }
            //手机登录
            if (registerType==1) {
                //手机号不为空
                if (etInput.text.toString().trim().isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                return@setOnClickListener
                }
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
            }else{//邮箱获取验证码
                //邮箱不为空
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }

            }
            if (!(etPassword!!.text.toString().trim().length in 8..16&& StringUtils.isContainAll(etPassword!!.text.toString().trim()))){
                ToastUtils.showShort(getActivity(), getString(R.string.intput_password_error))
                return@setOnClickListener
            }
                viewModel.run {
                    Log.e("loginIp",NetWorkUtils.getIPAddress(true)+ DeviceIDUtil.getDeviceId(applicationContext))
                    var ipAddress=MD5Utils.md5(NetWorkUtils.getIPAddress(true)+ DeviceIDUtil.getDeviceId(applicationContext))

                    login(registerType.toString(),etInput.text.toString().trim(),MD5Utils.md5(MD5Utils.md5(etPassword.text.toString().trim())),ipAddress).observe(this@LoginActivity, Observer {
                        if (it.status == 200) {
                            UserConfig.singleton?.accountBean=it.data.user
                            LaunchConfig.startMainAc(this@LoginActivity)
                            finish()
                        } else {
                            ErrorCode.showErrorMsg(this@LoginActivity, it.status)
                        }
                    })
                }
        }


    }


    fun showDialog(){
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_chose_area_code, null)
        // 创建PopupWindow对象，指定宽度和高度
        val popupWindow =
            PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        var recycleView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recycleView?.layoutManager= LinearLayoutManager(this)
        val list: MutableList<LoginCountryBean> = ArrayList()
        list.add(LoginCountryBean(R.mipmap.ic_china,"+86"))
        list.add(LoginCountryBean(R.mipmap.ic_korea,"+82"))
        list.add(LoginCountryBean(R.mipmap.ic_england,"+44"))
        list.add(LoginCountryBean(R.mipmap.ic_america,"+1"))
        list.add(LoginCountryBean(R.mipmap.ic_australia,"+61"))
        list.add(LoginCountryBean(R.mipmap.ic_singapore,"+65"))
        var adapter= ChoseAreaAdapter(list)
        adapter.setOnItemClickListener { adapter, view, position ->
            tvPhoneHead.text=(adapter.data[position] as LoginCountryBean).header
            ivPhoneHead.setImageResource((adapter.data[position] as LoginCountryBean).resId)
            popupWindow.dismiss()
        }
        recycleView?.adapter=adapter


//        window.width = recycleView.getWidth()
        // 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // 设置可以获取焦点
        popupWindow.isFocusable = true
        // 设置可以触摸弹出框以外的区域
        popupWindow.isOutsideTouchable = true
        // 更新popupwindow的状态
        popupWindow.update()
        // 以下拉的方式显示，并且可以设置显示的位置
        popupWindow.showAsDropDown(llHeader, 0, 20)
    }
}
