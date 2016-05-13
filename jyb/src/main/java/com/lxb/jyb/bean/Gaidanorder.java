package com.lxb.jyb.bean;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class Gaidanorder {

    public Gaidanorder() {
        super();
    }

    /**
     * account : {}
     * broker : {}
     * ticket : {}
     * price : {}
     * sl : {}
     * tp : {}
     * expiretime : {}
     */

    private String account;
    private String broker;
    private String ticket;
    private String price;
    private String sl;
    private String tp;
    private String expiretime;



    public void setAccount(String account) {
        this.account = account;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
    }

    public String getAccount() {
        return account;
    }

    public String getBroker() {
        return broker;
    }

    public String getTicket() {
        return ticket;
    }

    public String getPrice() {
        return price;
    }

    public String getSl() {
        return sl;
    }

    public String getTp() {
        return tp;
    }

    public String getExpiretime() {
        return expiretime;
    }


}
