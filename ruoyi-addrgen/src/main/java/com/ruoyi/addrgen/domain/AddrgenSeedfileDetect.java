package com.ruoyi.addrgen.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 种子文件存活性探测历史对象 addrgen_seedfile_detect
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public class AddrgenSeedfileDetect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 探测记录主键 */
    private Long detectRecordId;

    private Long addrSeedfileId;

    @Excel(name = "文件名")
    private String addrSeedfileName;

    /** 探测时长 */
    @Excel(name = "探测时长")
    private String detectConsume;

    /** 探测日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "探测日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectTime;

    private String detectStatus;

    public String getDetectStatus() {
        return detectStatus;
    }

    public void setDetectStatus(String detectStatus) {
        this.detectStatus = detectStatus;
    }

    public void setDetectRecordId(Long detectRecordId)
    {
        this.detectRecordId = detectRecordId;
    }

    public Long getDetectRecordId() 
    {
        return detectRecordId;
    }
    public void setAddrSeedfileId(Long addrSeedfileId) 
    {
        this.addrSeedfileId = addrSeedfileId;
    }

    public Long getAddrSeedfileId() 
    {
        return addrSeedfileId;
    }
    public void setDetectConsume(String detectConsume) 
    {
        this.detectConsume = detectConsume;
    }

    public String getDetectConsume() 
    {
        return detectConsume;
    }
    public void setDetectTime(Date detectTime) 
    {
        this.detectTime = detectTime;
    }

    public Date getDetectTime() 
    {
        return detectTime;
    }

    public String getAddrSeedfileName() {
        return addrSeedfileName;
    }

    public void setAddrSeedfileName(String addrSeedfileName) {
        this.addrSeedfileName = addrSeedfileName;
    }

    @Override
    public String toString() {
        return "AddrgenSeedfileDetect{" +
                "detectRecordId=" + detectRecordId +
                ", addrSeedfileId=" + addrSeedfileId +
                ", addrSeedfileName='" + addrSeedfileName + '\'' +
                ", detectConsume='" + detectConsume + '\'' +
                ", detectTime=" + detectTime +
                ", detectStatus='" + detectStatus + '\'' +
                '}';
    }
}
