package com.example.anull.wxpxxcj181222;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;

public class Banzubianji extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_banzubianji);
//        TextView textView1 = (TextView) findViewById(R.id.banzuview1);
//        TextView textView2 = (TextView) findViewById(R.id.banzuview2);
//        TextView textView3 = (TextView) findViewById(R.id.banzuview3);
//        TextView textView4 = (TextView) findViewById(R.id.banzuview4);
//        TextView textView5 = (TextView) findViewById(R.id.banzuview5);
//        TextView textView6 = (TextView) findViewById(R.id.banzuview6);
//        TextView textView7 = (TextView) findViewById(R.id.banzuview7);
//        TextView textView8 = (TextView) findViewById(R.id.banzuview8);
//        TextView textView9 = (TextView) findViewById(R.id.banzuview9);


        List<Data1> date1s = LitePal.findAll(Data1.class);


        LinearLayout ll = (LinearLayout) findViewById(R.id.lay);
        //把数据显示至屏幕
        for (Data1 data1 : date1s) {
            //1.集合中每有一条元素，就new一个textView
            TextView tv = new TextView(this);
            Button button = new Button(this);
            //2.把信息设置为文本框的内容
            tv.setText(data1.getTeam());

            //tv.setText("开发中");
            tv.setTextSize(15);
            //3.把textView设置为线性布局的子节点
            ll.addView(tv);
            ll.addView(button);

        }
//        ---------------------
//                作者：idiandi
//        来源：CSDN
//        原文：https://blog.csdn.net/idiandi/article/details/51197506
//        版权声明：本文为博主原创文章，转载请附上博文链接！
//        for (int i = 0; i < date1s.size(); i++) {
//            TextView textView = (TextView) findViewById(R.id.banzuview1);
//            //System.out.println(date1s.get(i));
//        }
        //Data1 data1 = LitePal.findFirst(Data1.class);


        //textView.setText(data1.getTeam());
//        for(Data1 date:date1s){
//            textView1.setText(date.getTeam());
////            textView2.setText(date.getTeam());
////            textView3.setText(date.getTeam());
////            textView4.setText(date.getTeam());
////            textView5.setText(date.getTeam());
////            textView6.setText(date.getTeam());
////            textView7.setText(date.getTeam());textView8.setText(date.getTeam());textView9.setText(date.getTeam());
//
//        }

    }
}
