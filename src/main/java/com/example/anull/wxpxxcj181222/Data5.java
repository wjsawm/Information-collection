package com.example.anull.wxpxxcj181222;

import org.litepal.crud.LitePalSupport;

//物品去向
//危险品去向
public class Data5 extends LitePalSupport {
    private int id;
    private String where;

    public Data5(String where) {
        setWhere(where);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public String toString() {
        return where;
    }
}
