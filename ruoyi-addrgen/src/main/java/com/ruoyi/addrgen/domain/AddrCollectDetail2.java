package com.ruoyi.addrgen.domain;

public class AddrCollectDetail2 {

    private String uid;
    private String detail;
    private String command;
    private String isalive;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getIsalive() {
        return isalive;
    }

    public void setIsalive(String isalive) {
        this.isalive = isalive;
    }

    @Override
    public String toString() {
        return "AddrCollectDetail2{" +
                "uid='" + uid + '\'' +
                ", detail='" + detail + '\'' +
                ", command='" + command + '\'' +
                ", isalive='" + isalive + '\'' +
                '}';
    }
}
