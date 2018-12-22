package com.example.anull.wxpxxcj181222;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

//一级分组
//班组
public class Data1 extends LitePalSupport {
    private int id;
    private String team;

    //private List<Data2> data2s = new ArrayList<Data2>();
    public Data1(String team) {
        setTeam(team);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return team;
    }
    //    public List<Data2> getData2s() {
//        return data2s;
//    }
//
//    public void setData2s(List<Data2> data2s) {
//        this.data2s = data2s;
//    }
}
