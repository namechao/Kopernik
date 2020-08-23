package com.kopernik.app.widget.library.provider;

import android.graphics.Color;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kopernik.R;
import com.kopernik.app.widget.library.IViewProvider;
import com.kopernik.ui.login.bean.LoginCountryBean;


public class DefaultItemViewProvider implements IViewProvider<LoginCountryBean> {
    @Override
    public int resLayout() {
        return R.layout.scroll_picker_default_item_layout;
    }

    @Override
    public void onBindView(@NonNull View view, @Nullable LoginCountryBean bean) {
        TextView tv = view.findViewById(R.id.tv_content);
        TextView iv = view.findViewById(R.id.iv_content);
        iv.setBackgroundResource(bean.getResId());
        tv.setText(bean.getHeader());
        view.setTag(bean.getHeader());
        tv.setTextSize(18);
    }

    @Override
    public void updateView(@NonNull View itemView, boolean isSelected) {
        TextView tv = itemView.findViewById(R.id.tv_content);
        tv.setTextSize(isSelected ? 18 : 14);
        tv.setTextColor(Color.parseColor(isSelected ? "#ffffff" : "#ED5275"));
    }
}
