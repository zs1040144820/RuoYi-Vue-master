package com.ruoyi.addrgen.domain;

import java.util.Arrays;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2022年11月11日 15:34
 */
public class MLCResults {
    private String [] AddrList;
    private Integer[] StaToLOG;

    public String[] getAddrList() {
        return AddrList;
    }

    public void setAddrList(String[] addrList) {
        AddrList = addrList;
    }

    public Integer[] getStaToLOG() {
        return StaToLOG;
    }

    public void setStaToLOG(Integer[] staToLOG) {
        StaToLOG = staToLOG;
    }

    @Override
    public String toString() {
        return "MLCResults{" +
                "AddrList=" + Arrays.toString(AddrList) +
                ", StaToLOG=" + Arrays.toString(StaToLOG) +
                '}';
    }
}
