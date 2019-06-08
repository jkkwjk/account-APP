package com.jkk.finances.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 3591022581529556534L;

    private String useName;
    private BigDecimal money; //精确到3位 显示2位
    private String time;
    private Integer type;
    private int more;
    private String urlOrstr;

    public AccountInfo() {
    }

    public AccountInfo(String useName, BigDecimal money, String time, Integer type, int more, String urlOrstr) {
        this.useName = useName;
        this.money = money;
        this.time = time;
        this.type = type;
        this.more = more;
        this.urlOrstr = urlOrstr;
    }

    public AccountInfo(String useName, BigDecimal money, String time, Integer type) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public String getUrlOrstr() {
        return urlOrstr;
    }

    public void setUrlOrstr(String urlOrstr) {
        this.urlOrstr = urlOrstr;
    }
}
