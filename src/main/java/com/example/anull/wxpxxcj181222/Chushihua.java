package com.example.anull.wxpxxcj181222;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePal.saveAll;

public class Chushihua {
    public void RenyuanChushihua() {
        List<Data1> data1s = new ArrayList<>();
        data1s.add(new Data1("一班"));
        data1s.add(new Data1("二班"));
        data1s.add(new Data1("三班"));

        LitePal.saveAll(data1s);

        List<Data2> data2s = new ArrayList<>();

        data2s.add(new Data2(1, "王  利"));
        data2s.add(new Data2(1, "王  刚"));
        data2s.add(new Data2(1, "闫文学"));
        data2s.add(new Data2(1, "贾天宇"));
        data2s.add(new Data2(1, "王希军"));
        data2s.add(new Data2(1, "张吉江"));
        data2s.add(new Data2(1, "陈龙"));
        data2s.add(new Data2(1, "周俊"));
        data2s.add(new Data2(1, "徐文波"));
        data2s.add(new Data2(1, "刘刚"));
        data2s.add(new Data2(1, "张保权"));
        data2s.add(new Data2(1, "周文彪"));
        data2s.add(new Data2(1, "宁有志"));
        data2s.add(new Data2(1, "张立和"));
        data2s.add(new Data2(1, "姜伟东"));
        data2s.add(new Data2(1, "吉言"));
        data2s.add(new Data2(1, "孙洪伟"));
        data2s.add(new Data2(1, "吴荣科"));
        data2s.add(new Data2(1, "万伟玲"));
        data2s.add(new Data2(1, "庄秀娟"));
        data2s.add(new Data2(1, "刘桂珍"));
        data2s.add(new Data2(1, "关艳娟"));
        data2s.add(new Data2(1, "吕秋瑜"));
        data2s.add(new Data2(1, "叶伟"));
        data2s.add(new Data2(1, "刘淑杰"));
        data2s.add(new Data2(1, "静华"));
        data2s.add(new Data2(1, "齐海娟"));
        data2s.add(new Data2(1, "刘伟红"));
        data2s.add(new Data2(1, "常新雷"));
        data2s.add(new Data2(1, "李伊娜"));

        data2s.add(new Data2(2, "于 泽"));
        data2s.add(new Data2(2, "邰 睿"));
        data2s.add(new Data2(2, "卜春明"));
        data2s.add(new Data2(2, "孔令群"));
        data2s.add(new Data2(2, "卞朝国"));
        data2s.add(new Data2(2, "刘显伟"));
        data2s.add(new Data2(2, "徐 斌"));
        data2s.add(new Data2(2, "刘子安"));
        data2s.add(new Data2(2, "冯广正"));
        data2s.add(new Data2(2, "白路"));
        data2s.add(new Data2(2, "邵会武"));
        data2s.add(new Data2(2, "孙伟时"));
        data2s.add(new Data2(2, "郝德志"));
        data2s.add(new Data2(2, "贾文玉"));
        data2s.add(new Data2(2, "方子良"));
        data2s.add(new Data2(2, "桑贺"));
        data2s.add(new Data2(2, "王岩"));
        data2s.add(new Data2(2, "曹明"));
        data2s.add(new Data2(2, "王秋华"));
        data2s.add(new Data2(2, "鲁永和"));
        data2s.add(new Data2(2, "于金林"));
        data2s.add(new Data2(2, "拱国杰"));
        data2s.add(new Data2(2, "邵渼琦"));
        data2s.add(new Data2(2, "栾海鸥"));
        data2s.add(new Data2(2, "霍霞"));
        data2s.add(new Data2(2, "徐红"));
        data2s.add(new Data2(2, "张佳楠"));
        data2s.add(new Data2(2, "马桂华"));
        data2s.add(new Data2(2, "王薇"));
        data2s.add(new Data2(2, "刘晶"));

        data2s.add(new Data2(3, "方林哲"));
        data2s.add(new Data2(3, "万盛钢"));
        data2s.add(new Data2(3, "关宏斌"));
        data2s.add(new Data2(3, "王云鹏"));
        data2s.add(new Data2(3, "李长忠"));
        data2s.add(new Data2(3, "孙天宝"));
        data2s.add(new Data2(3, "周永祥"));
        data2s.add(new Data2(3, "孟繁岐"));
        data2s.add(new Data2(3, "王德志"));
        data2s.add(new Data2(3, "徐成"));
        data2s.add(new Data2(3, "马里"));
        data2s.add(new Data2(3, "韩跃东"));
        data2s.add(new Data2(3, "孙国富"));
        data2s.add(new Data2(3, "宋希明"));
        data2s.add(new Data2(3, "杨宗君"));
        data2s.add(new Data2(3, "郑德顺"));
        data2s.add(new Data2(3, "高秋"));
        data2s.add(new Data2(3, "张星喆"));
        data2s.add(new Data2(3, "贾磊"));
        data2s.add(new Data2(3, "王明刚"));
        data2s.add(new Data2(3, "刘丽萍"));
        data2s.add(new Data2(3, "王艳"));
        data2s.add(new Data2(3, "孙赵晶"));
        data2s.add(new Data2(3, "刘凤霞"));
        data2s.add(new Data2(3, "王淑梅"));
        data2s.add(new Data2(3, "王桂玲"));
        data2s.add(new Data2(3, "王雪娇"));
        data2s.add(new Data2(3, "杨凤赞"));
        data2s.add(new Data2(3, "刘影"));

        LitePal.saveAll(data2s);


//        1	王  利 于 泽		方林哲
//        2	王  刚 邰 睿		万盛钢
//        3	闫文学		卜春明		关宏斌
//        4	贾天宇		孔令群		王云鹏
//        5	王希军		卞朝国		李长忠
//        6	张吉江		刘显伟		孙天宝
//        7	陈龙		徐 斌		周永祥
//        8	周俊		刘子安		孟繁岐
//        9	徐文波		冯广正		王德志
//        10刘刚		白路		徐成
//        11张保权		邵会武		马里
//        12周文彪		孙伟时		韩跃东
//        13宁有志		郝德志		孙国富
//        14张立和		贾文玉		宋希明
//        15姜伟东		方子良		杨宗君
//        16吉言		桑贺		郑德顺
//        17孙洪伟		王岩		高秋
//        18吴荣科		曹明		张星喆
//        19万伟玲		王秋华		贾磊
//        20庄秀娟		鲁永和		王明刚
//        21刘桂珍		于金林		刘丽萍
//        22关艳娟		拱国杰		王艳
//        23吕秋瑜		邵渼琦		孙赵晶
//        24叶伟		栾海鸥		刘凤霞
//        25刘淑杰		霍霞		王淑梅
//        26静华		徐红		王桂玲
//        27齐海娟		张佳楠		王雪娇
//        28刘伟红		马桂华		杨凤赞
//        29常新雷		王薇		刘影
//        30李伊娜		刘晶

        //https://blog.csdn.net/guolin_blog/article/details/39345833

        List<Data4> data4s = new ArrayList<>();
        data4s.add(new Data4("限制携带"));
        data4s.add(new Data4("禁止携带"));
        //data4s.add(new Data4("管制刀具"));
        LitePal.saveAll(data4s);

        List<Data5> data5s = new ArrayList<>();
        data5s.add(new Data5("安检收缴"));
        data5s.add(new Data5("旅客带回"));
        data5s.add(new Data5("旅客自弃"));
        //data5s.add(new Data5("旅客销毁"));
        LitePal.saveAll(data5s);


    }
}
