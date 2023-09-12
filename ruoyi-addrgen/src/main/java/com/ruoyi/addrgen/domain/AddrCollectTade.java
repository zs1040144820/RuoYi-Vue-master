package com.ruoyi.addrgen.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;

public class AddrCollectTade {
    private String taskname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开启时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tstime;
    private String uid;
    private String command;
    private String source;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Date getTstime() {
        return tstime;
    }

    public void setTstime(Date tstime) {
        this.tstime = tstime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "AddrCollectTade{" +
                "taskname='" + taskname + '\'' +
                ", tstime=" + tstime +
                ", uid='" + uid + '\'' +
                ", command='" + command + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
