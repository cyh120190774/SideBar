package com.cyh.sidebar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CusRecyclerView extends RecyclerView {
    private OnScrollChangeListener onScrollChangeListener;



    public CusRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CusRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (onScrollChangeListener!=null){
            if (getLayoutManager() instanceof LinearLayoutManager){
                onScrollChangeListener.scrollChange(((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition());
            }else if (getLayoutManager() instanceof GridLayoutManager){
                onScrollChangeListener.scrollChange(((GridLayoutManager)getLayoutManager()).findFirstVisibleItemPosition());
            }
        }
    }


    /**
     * 注意：如果有headView ,headView也会被当做一个，会使pos+1
     * @param onScrollChangeListener
     */
    public void addOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener){
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener{
        void scrollChange(int pos);
    }
}
