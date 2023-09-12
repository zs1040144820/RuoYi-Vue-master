package com.ruoyi.addrgen.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 种子文件采集历史记录对象 addrgen_seedfile_collect
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public class AddrgenSeedfileCollect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采集记录id */
    private Long collectRecordId;

    private Long addrSeedfileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String addrSeedfileName;

    /** 采集日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采集日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectTime;

    /** 采集耗时 */
    @Excel(name = "采集耗时")
    private String collectConsuming;

    /** 采集到的地址数 */
    @Excel(name = "采集到的地址数")
    private Long activeIpnum;

    /** 总地址数 */
    @Excel(name = "总地址数")
    private Long totalIpnum;

    private String collectStatus;

    @Override
    public String toString() {
        return "AddrgenSeedfileCollect{" +
                "collectRecordId=" + collectRecordId +
                ", addrSeedfileId=" + addrSeedfileId +
                ", addrSeedfileName='" + addrSeedfileName + '\'' +
                ", collectTime=" + collectTime +
                ", collectConsuming='" + collectConsuming + '\'' +
                ", activeIpnum=" + activeIpnum +
                ", totalIpnum=" + totalIpnum +
                ", collectStatus='" + collectStatus + '\'' +
                '}';
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public void setCollectRecordId(Long collectRecordId)
    {
        this.collectRecordId = collectRecordId;
    }

    public Long getCollectRecordId() 
    {
        return collectRecordId;
    }
    public void setAddrSeedfileId(Long addrSeedfileId) 
    {
        this.addrSeedfileId = addrSeedfileId;
    }

    public Long getAddrSeedfileId() 
    {
        return addrSeedfileId;
    }
    public void setAddrSeedfileName(String addrSeedfileName) 
    {
        this.addrSeedfileName = addrSeedfileName;
    }

    public String getAddrSeedfileName() 
    {
        return addrSeedfileName;
    }
    public void setCollectTime(Date collectTime) 
    {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() 
    {
        return collectTime;
    }
    public void setCollectConsuming(String collectConsuming) 
    {
        this.collectConsuming = collectConsuming;
    }

    public String getCollectConsuming() 
    {
        return collectConsuming;
    }
    public void setActiveIpnum(Long activeIpnum) 
    {
        this.activeIpnum = activeIpnum;
    }

    public Long getActiveIpnum() 
    {
        return activeIpnum;
    }
    public void setTotalIpnum(Long totalIpnum) 
    {
        this.totalIpnum = totalIpnum;
    }

    public Long getTotalIpnum() 
    {
        return totalIpnum;
    }

}
