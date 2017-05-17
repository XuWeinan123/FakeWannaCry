package com.xwn.fakewannacry;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ActionBar actionBar;
    private TextView timeTimer;
    private ImageView alipay;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        actionBar = getSupportActionBar();
        actionBar.hide();
        timeTimer = (TextView) findViewById(R.id.time_timer);
        alipay = (ImageView) findViewById(R.id.alipay);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        Typeface type = Typeface.createFromAsset(getAssets(), "DS-DIGI.TTF");
        timeTimer.setTypeface(type);
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "12345678910"));  // 将内容set到剪贴板
                if (clipboardManager.hasPrimaryClip()){
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();  // 获取内容
                }
                Toast.makeText(Main2Activity.this,"账号复制成功",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        //Toast.makeText(Main2Activity.this,isServiceWork(Main2Activity.this,"com.xwn.fakewannacry.XuebaService")+"",Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isServiceWork(Main2Activity.this,"com.xwn.fakewannacry.XuebaService")){
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}