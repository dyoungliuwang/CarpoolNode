package com.dyoung.carpool.node.greendao.model;

/**
 * Created by admin on 2016/11/18.
 */
public enum NodeType {
    WX("微信"),QQ("QQ"),PHONE("手机号");
    private String name;

    NodeType(String name){
        this.name=name;
    }
   public String getName(){
        return this.name;
    }
}
