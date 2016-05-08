package com.lxb.jyb.bean;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public class BDMT4userinfo {

    /**
     * account : 900176848
     * password : gr7xvnq
     * broker : GMI
     * server : GMI-Demo01
     */

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 交易平台
     */
    private String broker;
    /**
     * 服务器地址
     */
    private String server;

    public BDMT4userinfo(String account, String password, String broker, String server) {
        this.account = account;
        this.password = password;
        this.broker = broker;
        this.server = server;
    }

    public BDMT4userinfo() {

    }

    public StringBuffer toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"account\"").append(":\"").append(account)
                .append("\",");
        buffer.append("\"password\"").append(":\"").append(password).append("\",");
        buffer.append("\"broker\"").append(":\"").append(broker).append("\",");
        buffer.append("\"server\"").append(":\"").append(server)
                .append("\"");
        buffer.append("}");
        return buffer;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getBroker() {
        return broker;
    }

    public String getServer() {
        return server;
    }
}
