package com.cyh.sidebar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.cyh.sidebar.adapter.PhoneAreaAdapter;
import com.cyh.sidebar.bean.AreaPhoneBean;
import com.cyh.sidebar.databinding.ActivityMainBinding;
import com.cyh.sidebar.utils.PinyinUtils;
import com.cyh.sidebar.utils.ReadAssetsJsonUtil;
import com.cyh.sidebarview.SideBar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AreaPhoneBean> mBeans = new ArrayList<>();

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 防止输入法压缩布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        initView();

        initData();

    }

    private void initData() {
//        mBeans.clear();
        JSONArray array = ReadAssetsJsonUtil.getJSONArray("area_phone_code.json", this);
        if (null == array) array = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            AreaPhoneBean bean = new AreaPhoneBean();
            bean.name = array.optJSONObject(i).optString("zh");
            bean.fisrtSpell = PinyinUtils.getFirstSpell(bean.name.substring(0,1));
            bean.name_py = PinyinUtils.getPinYin(bean.name);
            bean.code = array.optJSONObject(i).optString("code");
            bean.locale = array.optJSONObject(i).optString("locale");
            bean.en_name = array.optJSONObject(i).optString("en");
            mBeans.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mBeans, new AreaPhoneBean.ComparatorPY());
        // 数据更新
//        adapter.notifyDataSetChanged();
    }

    private void initView() {
        PhoneAreaAdapter adapter = new PhoneAreaAdapter(mBeans);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        binding.rvArea.setLayoutManager(manager);

        binding.rvArea.setAdapter(adapter);

        binding.sbIndex.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (adapter.getData().size() < 2) {
                    return;
                }
                if (s.equals(binding.sbIndex.getLetters()[0])) {
                    manager.scrollToPositionWithOffset(0, 0);
                } else if (s.equals(binding.sbIndex.getLetters()[binding.sbIndex.getLetters().length - 1])) {
                    manager.scrollToPositionWithOffset(
                            adapter.getData().size() - 1,
                            0
                    );
                } else {
                    for (int i = 0; i < mBeans.size(); i++) {
                        if (mBeans.get(i).fisrtSpell.compareToIgnoreCase(s) == 0) {
                            manager.scrollToPositionWithOffset(i, 0);
                            break;
                        }
                    }
                }


            }
        });

        binding.rvArea.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    String str =  ((PhoneAreaAdapter)binding.rvArea.getAdapter()).getData().get(pos).getFisrtSpell();
                    binding.sbIndex.setChooseStr(str);
                }
            }
        });

    }
}