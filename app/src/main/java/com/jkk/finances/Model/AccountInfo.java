package com.jkk.finances.Model;

import com.jkk.finances.Utils.StampDate;
import com.jkk.finances.Utils.UUIDutil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 3591022581529556534L;

    private String uuid;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String useName;
    private Float money;
    private String time;
    private String type;
    private Integer more;
    private String str;
    private String url;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AccountInfo(String useName, Float money, String time, String type, Integer more, String str) {
        this.uuid = UUIDutil.getUUID();
        this.useName = useName;
        this.money = money;
        this.time = time;
        this.type = type;
        this.more = more;
        if (more==0){
            this.str=str;
        }else if(more==1){
            this.url=str;
        }
    }

    public AccountInfo(String useName, Float money, String time, String type, Integer more, String str, String url) {
        this.uuid = UUIDutil.getUUID();
        this.useName = useName;
        this.money = money;
        this.time = time;
        this.type = type;
        this.more = more;
        this.str = str;
        this.url = url;
    }
    public static ArrayList<AccountInfo> get(){
        ArrayList<AccountInfo> list = new ArrayList<>();
        list.add(new AccountInfo("买菜",604F, String.valueOf(new Date().getTime()/1000),"支付宝"));
        list.add(new AccountInfo("买阿萨德",-100F, String.valueOf(new Date().getTime()/1000),"微信"));
        list.add(new AccountInfo("买",6.88F, String.valueOf(new Date().getTime()/1000),"现金"));
        list.add(new AccountInfo("阿萨德啊",1057F, String.valueOf(new Date().getTime()/1000),"现金"));
        list.add(new AccountInfo("阿萨德啊",3F, String.valueOf(new Date().getTime()/1000),"微信"));
        list.add(new AccountInfo("阿萨德啊",-1009.1F, String.valueOf(new Date().getTime()/1000),"银行卡"));
        list.add(new AccountInfo("阿萨德啊",666F, String.valueOf(new Date().getTime()/1000),"微信"));
        list.add(new AccountInfo("阿萨德啊",-308.6F, String.valueOf(new Date().getTime()/1000),"支付宝"));
        return list;
    }
    public AccountInfo() { this.uuid = UUIDutil.getUUID(); }

    public AccountInfo(String useName, Float money, String time, String type) {
        this.uuid = UUIDutil.getUUID();
        this.useName = useName;
        this.money = money;
        this.time = time;
        this.type = type;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }
}
