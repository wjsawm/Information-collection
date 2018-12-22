package com.example.anull.wxpxxcj181222;

import org.litepal.crud.LitePalSupport;

//物品类型
//危险品类型
public class Data4 extends LitePalSupport {
    private int id;
    private String type;

    public Data4(String type) {
        setType(type);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
