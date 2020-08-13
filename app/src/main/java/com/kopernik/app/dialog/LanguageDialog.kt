package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity

import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import java.util.*

class LanguageDialog : DialogFragment() {
    private var recyclerView: RecyclerView? = null
    private var cancelTv: TextView? = null
    private val datas: MutableList<String> =
        ArrayList()
    private var currentLanguage: String? = null
    private var listener: LanguageChooseListener? = null
    fun setListener(listener: LanguageChooseListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_tx_type)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        currentLanguage = arguments!!.getString("currentLanguage")
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        recyclerView = dialog.findViewById(R.id.recycler_view)
        cancelTv = dialog.findViewById(R.id.cancel_tv)
        cancelTv?.setOnClickListener { dismiss() }
        datas.add(activity!!.resources.getString(R.string.chinese))
        datas.add(activity!!.resources.getString(R.string.english))
        datas.add(activity!!.resources.getString(R.string.korean))
        val adapter =
            Adapter(R.layout.item_tx_type, datas)
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.title_tv) {
                if (listener != null) {
                    listener!!.languageChooseListener(datas[position])
                    dialog.dismiss()
                }
            }
        }
    }

    internal inner class Adapter(
        layoutResId: Int,
        data: List<String?>?
    ) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.setText(R.id.title_tv, item)
            if (currentLanguage == item) {
                helper.setTextColor(
                    R.id.title_tv,
                    ContextCompat.getColor(
                        mContext,
                        R.color.tx_type_dialog_choose_text
                    )
                )
            } else {
                helper.setTextColor(
                    R.id.title_tv,
                    ContextCompat.getColor(mContext, R.color.tx_type_dialog_text)
                )
            }
            helper.addOnClickListener(R.id.title_tv)
        }
    }

    interface LanguageChooseListener {
        fun languageChooseListener(languageName: String?)
    }

    companion object {
        fun newInstance(currentLanguage: String?): LanguageDialog {
            val fragment = LanguageDialog()
            val bundle = Bundle()
            bundle.putString("currentLanguage", currentLanguage)
            fragment.arguments = bundle
            return fragment
        }
    }
}