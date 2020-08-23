package com.kopernik.ui.login

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
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
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ChoseAreaCodeDialog
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import com.kopernik.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.confirmBtn
import kotlinx.android.synthetic.main.activity_login.etInput
import kotlinx.android.synthetic.main.activity_login.icClear
import kotlinx.android.synthetic.main.activity_login.icEye
import kotlinx.android.synthetic.main.activity_login.ivVerifySucess
import kotlinx.android.synthetic.main.activity_login.line
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
import kotlinx.android.synthetic.main.activity_set_up_password.*
import java.util.ArrayList

class LoginActivity : NewBaseActivity<LoginViewModel, ViewDataBinding>() {
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
            LaunchConfig.startForgetPasswordActivity(this)
        }
        //进注册页面
        register.setOnClickListener {
            LaunchConfig.startRegisterActivity(this)
        }
        //协议
        userProtocol.setOnClickListener {
          LaunchConfig.startUserProtocolActivity(this)
        }
        tvPhoneHead.setOnClickListener {
            var doialog = ChoseAreaCodeDialog.newInstance(1)
            doialog!!.setOnRequestListener(object : ChoseAreaCodeDialog.RequestListener {
                override fun onRequest(type: Int, params: String) {

                }
            })
            doialog!!.show(supportFragmentManager, "doialog")
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
            tvPhoneRegisterLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            tvEmailRegister.setTextColor(resources.getColor(R.color.white))
            tvEmailRegisterLine.setBackgroundColor(resources.getColor(R.color.white))
            etInput.hint=resources.getString(R.string.re_phone)
            etInput.inputType= InputType.TYPE_CLASS_PHONE
            tvPhoneHead.visibility= View.VISIBLE
            line.visibility=View.VISIBLE
            etInput.setText("")
        }
        //邮箱登录
        llEmailRegister.setOnClickListener {
            registerType=2
            tvPhoneRegister.setTextColor(resources.getColor(R.color.white))
            tvPhoneRegisterLine.setBackgroundColor(resources.getColor(R.color.white))
            tvEmailRegister.setTextColor(resources.getColor(R.color.color_ffcf32))
            tvEmailRegisterLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            etInput.hint=resources.getString(R.string.re_email)
            etInput.inputType= InputType.TYPE_CLASS_TEXT
            etInput.setText("")
            tvPhoneHead.visibility= View.GONE
            line.visibility=View.GONE

        }
        //眼睛
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
                if (etInput.text.toString().trim().isNullOrEmpty() && !etInput.text.toString()
                        .trim().matches(
                            Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
            }else{//邮箱获取验证码
                if (etInput.text.toString().trim().isNullOrEmpty() && !etInput.text.toString()
                        .trim().matches(
                            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }

            }

            if (!(etInput!!.text.toString().trim().length in 8..16&& StringUtils.isContainAll(etInput!!.text.toString().trim()))){
                ToastUtils.showShort(getActivity(), getString(R.string.intput_password_error))
                return@setOnClickListener
            }
                viewModel.run {
                    login(registerType.toString(),etInput.text.toString().trim(),etPassword.text.toString().trim()).observe(this@LoginActivity, Observer {
                        if (it.status == 200) {
                            UserConfig.singleton?.accountBean=it.data.user
                            LaunchConfig.startMainAc(this@LoginActivity)
                            finish()
                        } else {
                            ToastUtils.showShort(this@LoginActivity, it.errorMsg)
                        }
                    })
                }
        }


    }


    fun showDialog(){
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_chose_area_code, null)
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
        adapter.setOnItemChildClickListener { adapter, view, position ->

        }
        recycleView?.adapter=adapter

        // 创建PopupWindow对象，指定宽度和高度
        val window =
            PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.width = recycleView.getWidth()
        // 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // 设置可以获取焦点
        window.isFocusable = true
        // 设置可以触摸弹出框以外的区域
        window.isOutsideTouchable = true
        // 更新popupwindow的状态
        window.update()
        // 以下拉的方式显示，并且可以设置显示的位置
        //window.showAsDropDown(llHeader, 0, 20);
        window.showAtLocation(llHeader, Gravity.LEFT or Gravity.BOTTOM, 0, 50) //这里的50是因为我底部按钮的高度是50

    }
}
