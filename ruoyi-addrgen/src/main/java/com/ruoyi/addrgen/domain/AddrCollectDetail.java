package com.ruoyi.addrgen.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AddrCollectDetail {
    private String TaskID;//主键
    private String IpAddr;//文件名

    public AddrCollectDetail(String TaskID, String IpAddr) {
        this.TaskID = TaskID;
        this.IpAddr = IpAddr;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getIpAddr() {
        return IpAddr;
    }

    public void setIpAddr(String ipAddr) {
        IpAddr = ipAddr;
    }

    @Override
    public String toString() {
        return "AddrCollectDetail{" +
                "TaskID='" + TaskID + '\'' +
                ", IpAddr='" + IpAddr + '\'' +
                '}';
    }
}
