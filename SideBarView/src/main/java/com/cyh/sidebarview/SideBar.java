package com.cyh.sidebarview;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SideBar extends View{
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    /** sidebar的字符列表 **/
    private CharSequence[] letters;

    private int choose = -1;// 选中
    private Paint paint = new Paint();



    private TextView mText;
    
    
    private Context mContext;



    private TextDialog mTextDialog;

    /** sidebar的字符颜色 **/
    private int mSideTextColor;
    /** sidebar的字符选中时的颜色 **/
    private int mSideTextSelectColor;
    /** sidebar的字符大小 **/
    private float mSideTextSize;
    /** sidebar的背景颜色 **/
    private Drawable mSideBackground;
    /** 选中弹窗字符颜色 **/
    private int mDialogTextColor;
    /** 选中弹窗字符大小 **/
    private float mDialogTextSize;
    /** 选中弹窗字符背景颜色 **/
    private Drawable mDialogTextBackground;
    /** 选中弹窗字符背景宽度 **/
    private int mDialogTextBackgroundWidth;
    /** 选中弹窗字符背景高度 **/
    private int mDialogTextBackgroundHeight;


    public void setTextView(TextView mText) {
        this.mText = mText;
    }
    
    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        initData(attrs);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initData(attrs);
    }

    public SideBar(Context context) {
        super(context);
        mContext=context;
        initData(null);
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / letters.length;// 获取每一个字母的高度

        for (int i = 0; i < letters.length; i++) {
            paint.setColor(mSideTextColor);
            paint.setTextSize(mSideTextSize);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            // 选中的状态
            if (i == choose) {
                paint.setColor(mSideTextSelectColor);
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText((String) letters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText((String) letters[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }


    public CharSequence[] getLetters() {
        return letters;
    }

   

    /**
     * 初始化数据
     * @param attrs
     */
    private void initData(@Nullable AttributeSet attrs) {
        // 加载默认资源
        final Resources res = getResources();
        final CharSequence[] defaultStringArray = res.getTextArray(R.array.side_bar_def_list);
        final int defaultSideTextColor = res.getColor(R.color.default_side_text_color);
        final int defaultSideTextSelectColor = res.getColor(R.color.default_side_text_select_color);
        final float defaultSideTextSize = res.getDimension(R.dimen.default_side_text_size);
        final Drawable defaultSideBackground = res.getDrawable(R.drawable.default_side_background);
        final int defaultDialogTextColor = res.getColor(R.color.default_dialog_text_color);
        final float defaultDialogTextSize = res.getDimension(R.dimen.default_dialog_text_size);
        final Drawable defaultDialogTextBackground = res.getDrawable(R.drawable.default_dialog_text_background);
        final int defaultDialogTextBackgroundWidth = res.getDimensionPixelSize(R.dimen.default_dialog_text_background_width);
        final int defaultDialogTextBackgroundHeight = res.getDimensionPixelSize(R.dimen.default_dialog_text_background_height);
        // 读取配置信息
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SideBar);
        letters =  a.getTextArray(R.styleable.SideBar_sideTextArray);
        
        if (null == letters ||letters.length <= 0){
            letters =  defaultStringArray;
        }
        mSideTextColor = a.getColor(R.styleable.SideBar_sideTextColor, defaultSideTextColor);
        mSideTextSelectColor = a.getColor(R.styleable.SideBar_sideTextSelectColor, defaultSideTextSelectColor);
        mSideTextSize = a.getDimension(R.styleable.SideBar_sideTextSize, defaultSideTextSize);
        mSideBackground = a.getDrawable(R.styleable.SideBar_sideBackground);
        if (null == mSideBackground){
            mSideBackground = defaultSideBackground;
        }
        mDialogTextColor = a.getColor(R.styleable.SideBar_dialogTextColor, defaultDialogTextColor);
        mDialogTextSize = a.getDimension(R.styleable.SideBar_dialogTextSize, defaultDialogTextSize);
        mDialogTextBackground = a.getDrawable(R.styleable.SideBar_dialogTextBackground);
        if (null == mDialogTextBackground){
            mDialogTextBackground = defaultDialogTextBackground;
        }
        mDialogTextBackgroundWidth = a.getDimensionPixelSize(R.styleable.SideBar_dialogTextBackgroundWidth, defaultDialogTextBackgroundWidth);
        mDialogTextBackgroundHeight = a.getDimensionPixelSize(R.styleable.SideBar_dialogTextBackgroundHeight, defaultDialogTextBackgroundHeight);
        // 释放内存，回收资源
        a.recycle();
        mTextDialog = new TextDialog(mContext, mDialogTextBackgroundWidth, mDialogTextBackgroundHeight,
                mDialogTextColor, mDialogTextSize, mDialogTextBackground);
    }




    @SuppressLint("NewApi")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letters.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
//			choose = -1;//
                invalidate();
                if (mText != null) {
                    mText.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackground(mSideBackground);
                setAlpha((float) 1.0);
                if (oldChoose != c) {
                    if (c >= 0 && c < letters.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged((String) letters[c]);
                        }
                        if (mText != null) {
                            mText.setText(letters[c]);
                            mText.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setChooseStr(String s){
        int i = 0;
        for (CharSequence b: letters){
            if (b.toString().equalsIgnoreCase(s)){
                setChoose(i);
                break;
            }
            i++;
        }
    }

    private void setChoose(int c){
        if (this.choose != c) {
            if (c >= 0 && c < letters.length) {
                if (mText != null) {
                    mText.setText(letters[c]);
                    mText.setVisibility(View.VISIBLE);
                }
                choose = c;
                invalidate();
            }
        }
        this.choose = c;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}

