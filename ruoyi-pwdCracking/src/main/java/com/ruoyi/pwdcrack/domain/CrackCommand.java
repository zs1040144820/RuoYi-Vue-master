package com.ruoyi.pwdcrack.domain;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2023年04月20日 10:18
 */
public class CrackCommand {
    private String ipAddress;
    private String uname;
    private String port;
    private String protoc;
    private String dic;
    private String myDic;
    private String crackResult;
    private boolean flag;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtoc() {
        return protoc;
    }

    public void setProtoc(String protoc) {
        this.protoc = protoc;
    }

    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public String getMyDic() {
        return myDic;
    }

    public void setMyDic(String myDic) {
        this.myDic = myDic;
    }

    public String getCrackResult() {
        return crackResult;
    }

    public void setCrackResult(String crackResult) {
        this.crackResult = crackResult;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CrackCommand{" +
                "ipAddress='" + ipAddress + '\'' +
                ", uname='" + uname + '\'' +
                ", port='" + port + '\'' +
                ", protoc='" + protoc + '\'' +
                ", dic='" + dic + '\'' +
                ", myDic='" + myDic + '\'' +
                ", crackResult='" + crackResult + '\'' +
                ", flag=" + flag +
                '}';
    }
}
