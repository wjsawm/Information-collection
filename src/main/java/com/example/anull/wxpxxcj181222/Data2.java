package com.example.anull.wxpxxcj181222;

import org.litepal.crud.LitePalSupport;

//二级分组
//人员名单
public class Data2 extends LitePalSupport {
    private int id;
    //private Data1 data1;
    private int data1_id;


    private String name;

    public Data2(/*Data1 data1,*/int data1_id, String name) {
        //setData1(data1);
        setData1_id(data1_id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData1_id() {
        return data1_id;
    }

    public void setData1_id(int data1_id) {
        this.data1_id = data1_id;
    }

//    public Data1 getData1() {
//        return data1;
//    }
//
//    public void setData1(Data1 data1) {
//        this.data1 = data1;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
