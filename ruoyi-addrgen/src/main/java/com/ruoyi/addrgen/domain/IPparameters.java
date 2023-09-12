package com.ruoyi.addrgen.domain;

import java.util.Arrays;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2022年11月03日 18:26
 */
public class IPparameters {
    private String[] ASN;
    private String[] BGP;
    private String[] IID;

    public String[] getASN() {
        return ASN;
    }

    public void setASN(String[] ASN) {
        this.ASN = ASN;
    }

    public String[] getBGP() {
        return BGP;
    }

    public void setBGP(String[] BGP) {
        this.BGP = BGP;
    }

    public String[] getIID() {
        return IID;
    }

    public void setIID(String[] IID) {
        this.IID = IID;
    }

    @Override
    public String toString() {
        return "IPparameters{" +
                "ASN=" + Arrays.toString(ASN) +
                ", BGP='" + BGP + '\'' +
                ", IID=" + Arrays.toString(IID) +
                '}';
    }
}
