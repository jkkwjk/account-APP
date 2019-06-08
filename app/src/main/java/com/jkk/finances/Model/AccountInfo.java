package com.jkk.finances.Model;

import com.jkk.finances.Utils.StampDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 3591022581529556534L;

    private String useName;
    private BigDecimal money; //精确到3位 显示2位
    private String time;
    private String type;
    private int more;
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

    public AccountInfo(String useName, BigDecimal money, String time, String type, int more, String str) {
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

    public AccountInfo(String useName, BigDecimal money, String time, String type, int more, String str, String url) {
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
        list.add(new AccountInfo("买菜",new BigDecimal("2.1"), String.valueOf(new Date().getTime()/1000),"支付宝"));
        list.add(new AccountInfo("买阿萨德",new BigDecimal("2.45"), String.valueOf(new Date().getTime()/1000),"支付"));
        list.add(new AccountInfo("买",new BigDecimal("2.411"), String.valueOf(new Date().getTime()/1000),"支宝"));
        list.add(new AccountInfo("阿萨德啊",new BigDecimal("3"), String.valueOf(new Date().getTime()/1000),"付宝"));
        return list;
    }
    public AccountInfo() { }

    public AccountInfo(String useName, BigDecimal money, String time, String type) {
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
