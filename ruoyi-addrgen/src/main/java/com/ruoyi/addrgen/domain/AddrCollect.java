package com.ruoyi.addrgen.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class AddrCollect extends BaseEntity {
    private Long collectTaskID;//主键
    @Excel(name = "任务名")
    private String filename;//文件名
    private String timetype;//输入的收集时长类型-时分秒
    private String collectime;//收集时长
    private String uniqueID;//唯一标识符
    private String source;//地址来源
    private int rate;//进度条

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开启时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startime;//开启时间
    @Excel(name = "任务状态")
    private String tasksituation;//任务状态
    private String commandresponse;//控制台回显情况

    public Long getCollectTaskID() {
        return collectTaskID;
    }

    public void setCollectTaskID(Long collectTaskID) {
        this.collectTaskID = collectTaskID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public String getCollectime() {
        return collectime;
    }

    public void setCollectime(String collectime) {
        this.collectime = collectime;
    }

    public Date getStartime() {
        return startime;
    }

    public void setStartime(Date startime) {
        this.startime = startime;
    }

    public String getTasksituation() {
        return tasksituation;
    }

    public void setTasksituation(String tasksituation) {
        this.tasksituation = tasksituation;
    }

    public String getCommandresponse() {
        return commandresponse;
    }

    public void setCommandresponse(String commandresponse) {
        this.commandresponse = commandresponse;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "AddrCollect{" +
                "collectTaskID=" + collectTaskID +
                ", filename='" + filename + '\'' +
                ", timetype='" + timetype + '\'' +
                ", collectime='" + collectime + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", source='" + source + '\'' +
                ", rate=" + rate +
                ", startime=" + startime +
                ", tasksituation='" + tasksituation + '\'' +
                ", commandresponse='" + commandresponse + '\'' +
                '}';
    }
}
