package com.kopernik.ui.login


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.dialog.ChoseAreaCodeDialog
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import com.kopernik.ui.login.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.confirmBtn
import kotlinx.android.synthetic.main.activity_register.etInput
import kotlinx.android.synthetic.main.activity_register.icClear
import kotlinx.android.synthetic.main.activity_register.ivPhoneHead
import kotlinx.android.synthetic.main.activity_register.ivVerifySucess
import kotlinx.android.synthetic.main.activity_register.llEmailRegister
import kotlinx.android.synthetic.main.activity_register.llHeader
import kotlinx.android.synthetic.main.activity_register.llPhoneRegister
import kotlinx.android.synthetic.main.activity_register.sbProgress
import kotlinx.android.synthetic.main.activity_register.tvEmailRegister
import kotlinx.android.synthetic.main.activity_register.tvEmailRegisterLine
import kotlinx.android.synthetic.main.activity_register.tvPhoneHead
import kotlinx.android.synthetic.main.activity_register.tvPhoneRegister
import kotlinx.android.synthetic.main.activity_register.tvPhoneRegisterLine
import kotlinx.android.synthetic.main.activity_register.tvSeekBar
import kotlinx.android.synthetic.main.activity_register.tvVerifySu
import java.util.ArrayList


class RegisterActivity : NewFullScreenBaseActivity<RegisterViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.activity_register
    private var registerType=1//默认手机注册
    private var slideVerify=false
    private var phone=""
    private var email=""
    override fun initView(savedInstanceState: Bundle?) {
        setTitleAndRightonClick(getString(R.string.re_quick_register),getString(R.string.re_login_title),object :OnRightClickItem{
            override fun onClick() {
                finish()
            }
        })
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
        //协议
        userProtocol.setOnClickListener {
            LaunchConfig.startUserProtocolActivity(this)
        }
        llHeader.setOnClickListener {
            showDialog()
        }
        //获取验证码
        tvCode.setOnClickListener {

            //手机登录
            if (registerType==1) {
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                        Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
                viewModel.run {
                    sendCode(etInput.text.toString().trim()).observeForever {
                        if (it.status == 200) {
                            timer.start()
                        } else {
                            ToastUtils.showShort(this@RegisterActivity, it.errorMsg)
                        }
                    }
                }
            }else{//邮箱获取验证码
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }

                viewModel.run {
                    sendEmailCode(etInput.text.toString().trim()).observeForever {
                        if (it.status == 200) {
                            timer.start()
                        } else {
                            ToastUtils.showShort(this@RegisterActivity, it.errorMsg)
                        }
                    }
                }
            }
        }
    }



override fun initData() {
    //手机登录
    llPhoneRegister.setOnClickListener {
        registerType=1
        tvPhoneRegister.setTextColor(resources.getColor(R.color.color_ffcf32))
        tvPhoneRegisterLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
        tvEmailRegister.setTextColor(resources.getColor(R.color.white))
        tvEmailRegisterLine.setBackgroundColor(resources.getColor(R.color.white))
        etInput.hint=resources.getString(R.string.login_input_phone_hint)
        etInput.inputType=InputType.TYPE_CLASS_PHONE
        llHeader.visibility= View.VISIBLE
        etInput.setText("")
    }
    //邮箱登录
    llEmailRegister.setOnClickListener {
        registerType=2
        tvPhoneRegister.setTextColor(resources.getColor(R.color.white))
        tvPhoneRegisterLine.setBackgroundColor(resources.getColor(R.color.white))
        tvEmailRegister.setTextColor(resources.getColor(R.color.color_ffcf32))
        tvEmailRegisterLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
        etInput.hint=resources.getString(R.string.login_input_email_hint)
        etInput.inputType=InputType.TYPE_CLASS_TEXT
        etInput.setText("")
        llHeader.visibility= View.GONE
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

     if (registerType==1){
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
         //验证码
         if (verifyCode.text.toString().trim().isNullOrEmpty()){
             ToastUtils.showShort(this, resources.getString(R.string.re_input_code))
             return@setOnClickListener}
        //邀请码
        if (etInviteCode.text.toString().trim().isNullOrEmpty()){
            ToastUtils.showShort(this, resources.getString(R.string.verify_invite_code_error))
            return@setOnClickListener
        }
        viewModel.run {
         checkPhone(etInput.text.toString().trim(),verifyCode.text.toString().trim()).observe(this@RegisterActivity, Observer {
             if (it.status == 200) {
                 LaunchConfig.startRegisterSetUpPasswordActivity(this@RegisterActivity,
                 1,
                     etInput.text.toString().trim()
                 ,
                     etInviteCode.text.toString().trim()
                 )
             } else {
                 ToastUtils.showShort(this@RegisterActivity, it.errorMsg)
             }
         })
        }
     }else{
         //邮箱不为空
         if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
         if (!etInput.text.toString()
                 .trim().matches(
                     Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
         ) {
             ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
             return@setOnClickListener
         }
         //验证码
         if (verifyCode.text.toString().trim().isNullOrEmpty()){
             ToastUtils.showShort(this, resources.getString(R.string.re_input_code))
             return@setOnClickListener}
         //邀请码
         if (etInviteCode.text.toString().trim().isNullOrEmpty()){
             ToastUtils.showShort(this, resources.getString(R.string.verify_invite_code_error))
             return@setOnClickListener
         }
         viewModel.run {
             checkEMail(etInput.text.toString().trim(),verifyCode.text.toString().trim()).observe(this@RegisterActivity, Observer {
                 if (it.status == 200) {
                     LaunchConfig.startRegisterSetUpPasswordActivity(this@RegisterActivity,
                         2,
                         etInput.text.toString().trim()
                         ,
                         etInviteCode.text.toString().trim()
                     )
                 } else {
                     ToastUtils.showShort(this@RegisterActivity, it.errorMsg)
                 }
             })
         }
     }
    }
}

//控制下一步按钮是否点亮
private fun resetOkBtn() {
//    nextBtn.isEnabled = inputs.size == 12
}

    //计时器定时
    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tvCode.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
            tvCode.setTextColor(ContextCompat.getColor(baseContext, R.color.color_5D5386))
            tvCode.isClickable = false
        }

        override fun onFinish() {
            tvCode.text = "重新获取"
            tvCode.setTextColor(ContextCompat.getColor(baseContext, R.color.color_F4C41B))
            tvCode.isClickable = true
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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