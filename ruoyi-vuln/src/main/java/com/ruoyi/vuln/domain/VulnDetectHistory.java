package com.ruoyi.vuln.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 漏洞扫描历史记录对象 vuln_detect_history
 * 
 * @author ruoyi
 * @date 2022-09-25
 */
public class VulnDetectHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public VulnDetectHistory(){}
    /** 探测历史主键id */
    private Long recordsId;

    /** nmap命令 */
    @Excel(name = "nmap命令")
    private String command;

    /** 靶机地址（范围） */
    @Excel(name = "靶机地址", readConverterExp = "范=围")
    private String targetRange;

    /** 扫描时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "扫描时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date scanDatetime;

    /** 高危漏洞比例 */
    @Excel(name = "高危漏洞比例")
    private String highriskRate;

    /** 高危漏洞比例 */
    @Excel(name = "扫描结果")
    private String results;

    private boolean fos;
    private boolean portService;
    private boolean databasetype;
    private String startport;
    private String endport;
    private String uuid;
    private boolean usefile;
    private String addrfilename;
    private String rate = "0";
    private String suspendPosition;
    private Date scanedTime;

    /** 单条地址扫描结果信息 */
    private List<VulnSingleAddrDetails> vulnSingleAddrDetailsList;

    public Long getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(Long recordsId) {
        this.recordsId = recordsId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTargetRange() {
        return targetRange;
    }

    public void setTargetRange(String targetRange) {
        this.targetRange = targetRange;
    }

    public Date getScanDatetime() {
        return scanDatetime;
    }

    public void setScanDatetime(Date scanDatetime) {
        this.scanDatetime = scanDatetime;
    }

    public String getHighriskRate() {
        return highriskRate;
    }

    public void setHighriskRate(String highriskRate) {
        this.highriskRate = highriskRate;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public boolean isFos() {
        return fos;
    }

    public void setFos(boolean fos) {
        this.fos = fos;
    }

    public boolean isPortService() {
        return portService;
    }

    public void setPortService(boolean portService) {
        this.portService = portService;
    }

    public boolean isDatabasetype() {
        return databasetype;
    }

    public void setDatabasetype(boolean databasetype) {
        this.databasetype = databasetype;
    }

    public String getStartport() {
        return startport;
    }

    public void setStartport(String startport) {
        this.startport = startport;
    }

    public String getEndport() {
        return endport;
    }

    public void setEndport(String endport) {
        this.endport = endport;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isUsefile() {
        return usefile;
    }

    public void setUsefile(boolean usefile) {
        this.usefile = usefile;
    }

    public String getAddrfilename() {
        return addrfilename;
    }

    public void setAddrfilename(String addrfilename) {
        this.addrfilename = addrfilename;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSuspendPosition() {
        return suspendPosition;
    }

    public void setSuspendPosition(String suspendPosition) {
        this.suspendPosition = suspendPosition;
    }

    public Date getScanedTime() {
        return scanedTime;
    }

    public void setScanedTime(Date scanedTime) {
        this.scanedTime = scanedTime;
    }

    public List<VulnSingleAddrDetails> getVulnSingleAddrDetailsList()
    {
        return vulnSingleAddrDetailsList;
    }

    public void setVulnSingleAddrDetailsList(List<VulnSingleAddrDetails> vulnSingleAddrDetailsList)
    {
        this.vulnSingleAddrDetailsList = vulnSingleAddrDetailsList;
    }

    @Override
    public String toString() {
        return "VulnDetectHistory{" +
                "recordsId=" + recordsId +
                ", command='" + command + '\'' +
                ", targetRange='" + targetRange + '\'' +
                ", scanDatetime=" + scanDatetime +
                ", highriskRate='" + highriskRate + '\'' +
                ", results='" + results + '\'' +
                ", os='" + fos + '\'' +
                ", portService='" + portService + '\'' +
                ", vulnSingleAddrDetailsList=" + vulnSingleAddrDetailsList +
                '}';
    }
}
