package com.cyh.sidebar.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyh.sidebar.R;
import com.cyh.sidebar.bean.AreaPhoneBean;

import java.util.ArrayList;
import java.util.List;


public class PhoneAreaAdapter extends BaseQuickAdapter<AreaPhoneBean, BaseViewHolder> {

    private ArrayList<AreaPhoneBean> mBeans;


    public PhoneAreaAdapter(List<AreaPhoneBean> data) {
        super(R.layout.item_phone_area, data);
        mBeans = (ArrayList<AreaPhoneBean>) data;
    }


    @Override
    protected void convert(BaseViewHolder helper, AreaPhoneBean item) {

        TextView txt_tag = helper.getView(R.id.txt_tag);

        TextView txt_name = helper.getView(R.id.txt_name);

        TextView txt_num = helper.getView(R.id.txt_num);


        txt_name.setText(item.getName());

        txt_num.setText(item.getCode());


        String fisrtSpell = item.getFisrtSpell().toUpperCase();

        if (helper.getAdapterPosition() == 0) {
            txt_tag.setVisibility(View.VISIBLE);
            txt_tag.setText(fisrtSpell);

        } else {

            String lastFisrtSpell = mBeans.get(helper.getAdapterPosition()-1).getFisrtSpell().toUpperCase();

            if (fisrtSpell.equals(lastFisrtSpell)) {
                txt_tag.setVisibility(View.GONE);
            } else {
                txt_tag.setVisibility(View.VISIBLE);
                txt_tag.setText(fisrtSpell);

            }


        }
    }
}
