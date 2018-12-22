package com.example.anull.wxpxxcj181222;

import org.litepal.LitePal;

import java.util.List;

public class Buttonfuzhi {
    public static String[] buttons1() {

        List<Data1> date1s = LitePal.findAll(Data1.class);
        String[] returns = new String[date1s.size()];
        for (int i = 0; i < date1s.size(); i++) {
            returns[i] = date1s.get(i).toString();
        }
        return returns;
    }

    public static String[] buttons2(int Data1Id) {
        List<Data2> data2s = LitePal.where("data1_id=?", String.valueOf(Data1Id)).find(Data2.class);
        String[] returns = new String[data2s.size()];
        for (int i = 0; i < data2s.size(); i++) {
            returns[i] = data2s.get(i).toString();
        }
        return returns;
    }

    public static String[] buttons3() {

        List<Data4> date3s = LitePal.findAll(Data4.class);
        String[] returns = new String[date3s.size()];
        for (int i = 0; i < date3s.size(); i++) {
            returns[i] = date3s.get(i).toString();
        }
        //String[]a=new String[]{"a","b"};
        return returns;
    }

    public static String[] buttons4() {

        List<Data5> date1s = LitePal.findAll(Data5.class);
        String[] returns = new String[date1s.size()];
        for (int i = 0; i < date1s.size(); i++) {
            returns[i] = date1s.get(i).toString();
        }
        return returns;
    }
}
