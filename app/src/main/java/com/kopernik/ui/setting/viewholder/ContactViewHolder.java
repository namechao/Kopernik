package com.kopernik.ui.setting.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kopernik.R;


public class ContactViewHolder extends BaseViewHolder {

    private TextView addressTv;
    private ImageView copyIv;
    private SuperTextView nameSpt,chainSpt;
    private SuperButton modifySpb,delSpb;

    public ContactViewHolder(View itemView) {
        super(itemView);
    }

    public SuperTextView getNameSpt() {
        if (nameSpt == null) {
            nameSpt = itemView.findViewById(R.id.contact_name_spt);
        }
        return nameSpt;
    }

    public SuperTextView getChainSpt() {
        if (chainSpt == null) {
            chainSpt = itemView.findViewById(R.id.chain_spt);
        }
        return chainSpt;
    }

    public TextView getAddressTv() {
        if (addressTv == null) {
            addressTv = itemView.findViewById(R.id.contact_address_tv);
        }
        return addressTv;
    }

    public SuperButton getModifySpb() {
        if (modifySpb == null) {
            modifySpb = itemView.findViewById(R.id.modify_spb);
        }
        return modifySpb;
    }

    public SuperButton getDelSpb() {
        if (delSpb == null) {
            delSpb = itemView.findViewById(R.id.del_spb);
        }
        return delSpb;
    }

    public ImageView getCopyIv() {
        if (copyIv == null) {
            copyIv = itemView.findViewById(R.id.copy_iv);
        }
        return copyIv;
    }
}