package com.cyh.sidebarview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextDialog {

    private Dialog select_dialog;
    private RelativeLayout RL;
    private TextView TXT;

    public void dismiss(){
        if (select_dialog != null && select_dialog.isShowing()){
            select_dialog.dismiss();
        }
    }

    public void show(String str) {
        if (TXT != null) TXT.setText(str);
        if (select_dialog != null && !select_dialog.isShowing()){
            select_dialog.show();
        }
    }

    public void show(String str,long delay) {
        if (TXT != null) TXT.setText(str);
        if (select_dialog != null && !select_dialog.isShowing()){
            select_dialog.show();
        }
    }

    public TextDialog(Context context, int weight, int hight, int textColor, float textSize, Drawable bg){
        select_dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.tx_dialog, null);
        RL = (RelativeLayout) view.findViewById(R.id.rl);
        TXT = (TextView) view.findViewById(R.id.txt);

        RL.setBackground(bg);
        TXT.setTextColor(textColor);
        TXT.setTextSize(textSize);

        select_dialog.getWindow().setGravity(Gravity.BOTTOM);
        select_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = select_dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = weight;   //设置宽度充满屏幕
        lp.height = hight;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        select_dialog.setContentView(view);
    }
}
