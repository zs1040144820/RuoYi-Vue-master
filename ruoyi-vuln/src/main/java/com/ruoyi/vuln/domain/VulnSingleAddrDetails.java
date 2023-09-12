package com.ruoyi.vuln.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单条地址扫描结果对象 vuln_single_addr_details
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-05
 */
public class VulnSingleAddrDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public VulnSingleAddrDetails(){}
    /** ipv6地址主键id */
    private Long ipaddrId;

    /** 探测历史主键id */
    @Excel(name = "探测历史主键id")
    private Long recordsId;

    /** ipv6地址 */
    @Excel(name = "ipv6地址")
    private String ipv6Addr;

    /** 指纹信息 */
    @Excel(name = "指纹信息")
    private String resultDetails;

    /** nmap命令 */
    @Excel(name = "nmap命令")
    private String command;

    /** 扫描时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "扫描时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scanTime;

    /** cve-id */
    @Excel(name = "cve-id")
    private String cve;

    /** cnv-did */
    @Excel(name = "cnv-did")
    private String cnnd;

    /** 操作系统 */
    @Excel(name = "操作系统")
    private String os;

    /** 服务 */
    @Excel(name = "服务")
    private String services;

    public void setIpaddrId(Long ipaddrId) 
    {
        this.ipaddrId = ipaddrId;
    }

    public Long getIpaddrId() 
    {
        return ipaddrId;
    }
    public void setRecordsId(Long recordsId) 
    {
        this.recordsId = recordsId;
    }

    public Long getRecordsId() 
    {
        return recordsId;
    }
    public void setIpv6Addr(String ipv6Addr) 
    {
        this.ipv6Addr = ipv6Addr;
    }

    public String getIpv6Addr() 
    {
        return ipv6Addr;
    }
    public void setResultDetails(String resultDetails) 
    {
        this.resultDetails = resultDetails;
    }

    public String getResultDetails() 
    {
        return resultDetails;
    }
    public void setCommand(String command) 
    {
        this.command = command;
    }

    public String getCommand() 
    {
        return command;
    }
    public void setScanTime(Date scanTime) 
    {
        this.scanTime = scanTime;
    }

    public Date getScanTime() 
    {
        return scanTime;
    }
    public void setCve(String cve) 
    {
        this.cve = cve;
    }

    public String getCve() 
    {
        return cve;
    }
    public void setCnnd(String cnnd) 
    {
        this.cnnd = cnnd;
    }

    public String getCnnd() 
    {
        return cnnd;
    }
    public void setOs(String os) 
    {
        this.os = os;
    }

    public String getOs() 
    {
        return os;
    }
    public void setServices(String services) 
    {
        this.services = services;
    }

    public String getServices() 
    {
        return services;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ipaddrId", getIpaddrId())
            .append("recordsId", getRecordsId())
            .append("ipv6Addr", getIpv6Addr())
            .append("resultDetails", getResultDetails())
            .append("command", getCommand())
            .append("scanTime", getScanTime())
            .append("cve", getCve())
            .append("cnnd", getCnnd())
            .append("os", getOs())
            .append("services", getServices())
            .toString();
    }
}
