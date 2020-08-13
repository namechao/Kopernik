package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
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
import com.kopernik.common.AssetOptConstant
import java.util.*

class TxTypeDialog : DialogFragment() {
    private var recyclerView: RecyclerView? = null
    private var cancelTv: TextView? = null
    private val datas: MutableList<String> =
        ArrayList()
    private var p = 0
    private var listener: TypeChooseListener? = null
    private var chooseType: HashMap<String, String>? = null
    fun setListener(listener: TypeChooseListener?) {
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
        //        lp.height =  getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.attributes = lp
        p = arguments!!.getInt("position", 0)
        chooseType = HashMap()
        chooseType!!["0"] = AssetOptConstant.ALL
        //        chooseType.put("1", AssetOptConstant.RECHARGE);
//        chooseType.put("2", AssetOptConstant.CASH);
        chooseType!!["1"] = AssetOptConstant.ROLL
        chooseType!!["2"] = AssetOptConstant.MAPING
        chooseType!!["3"] = AssetOptConstant.UNMAPPING
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        recyclerView = dialog.findViewById(R.id.recycler_view)
        cancelTv = dialog.findViewById(R.id.cancel_tv)
        cancelTv?.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        datas.add(activity!!.resources.getString(R.string.all))
        //        datas.add(getActivity().getResources().getString(R.string.title_asset_recharge));
//        datas.add(getActivity().getResources().getString(R.string.title_asset_withdraw));
        datas.add(activity!!.resources.getString(R.string.title_asset_transfer))
        datas.add(activity!!.resources.getString(R.string.title_asset_map))
        datas.add(activity!!.resources.getString(R.string.title_asset_map_cancel))
        val adapter =
            Adapter(R.layout.item_tx_type, datas)
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setAdapter(adapter)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.title_tv) {
                if (listener != null) {
                    listener!!.typeChooseListener(
                        position,
                        chooseType!![position.toString() + ""],
                        datas[position]
                    )
                    dialog.dismiss()
                }
            }
        }
    }

    internal inner class Adapter(
        layoutResId: Int,
        data: List<String?>?
    ) : BaseQuickAdapter<String?, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: String?) {
            helper.setText(R.id.title_tv, item)
            if (p == helper.adapterPosition) {
                helper.setTextColor(
                    R.id.title_tv,
                    ContextCompat.getColor(
                        context!!,
                        R.color.tx_type_dialog_choose_text
                    )
                )
            } else {
                helper.setTextColor(
                    R.id.title_tv,
                    ContextCompat.getColor(
                        context!!,
                        R.color.tx_type_dialog_text
                    )
                )
            }
            helper.addOnClickListener(R.id.title_tv)
        }
    }

    interface TypeChooseListener {
        fun typeChooseListener(
            position: Int,
            type: String?,
            typeName: String?
        )
    }

    companion object {
        fun newInstance(position: Int): TxTypeDialog {
            val fragment = TxTypeDialog()
            val bundle = Bundle()
            bundle.putInt("position", position)
            fragment.arguments = bundle
            return fragment
        }
    }
}