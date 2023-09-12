package com.ruoyi.addrgen.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 种子地址文件总览对象 addrgen_seedfile_handle
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
public class AddrgenSeedfileHandle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 种子地址文件id */
    private Long addrSeedfileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String addrSeedfileName;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addrSeedfileUploadtime;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String addrSeedfileSize;

    private String formaticon;

    private String collect;

    private String detect;

    private Long detectTimes;

    /** 种子文件详情信息 */
    private List<AddrgenSeedfileDetails> addrgenSeedfileDetailsList;

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
    public void setAddrSeedfileUploadtime(Date addrSeedfileUploadtime) 
    {
        this.addrSeedfileUploadtime = addrSeedfileUploadtime;
    }

    public Date getAddrSeedfileUploadtime() 
    {
        return addrSeedfileUploadtime;
    }
    public void setAddrSeedfileSize(String addrSeedfileSize) 
    {
        this.addrSeedfileSize = addrSeedfileSize;
    }

    public String getAddrSeedfileSize() 
    {
        return addrSeedfileSize;
    }

    public List<AddrgenSeedfileDetails> getAddrgenSeedfileDetailsList()
    {
        return addrgenSeedfileDetailsList;
    }

    public void setAddrgenSeedfileDetailsList(List<AddrgenSeedfileDetails> addrgenSeedfileDetailsList)
    {
        this.addrgenSeedfileDetailsList = addrgenSeedfileDetailsList;
    }

    public String getFormaticon() {
        return formaticon;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getDetect() {
        return detect;
    }

    public void setDetect(String detect) {
        this.detect = detect;
    }

    public void setFormaticon(String formaticon) {
        this.formaticon = formaticon;
    }

    public Long getDetectTimes() {
        return detectTimes;
    }

    public void setDetectTimes(Long detectTimes) {
        this.detectTimes = detectTimes;
    }

    @Override
    public String toString() {
        return "AddrgenSeedfileHandle{" +
                "addrSeedfileId=" + addrSeedfileId +
                ", addrSeedfileName='" + addrSeedfileName + '\'' +
                ", addrSeedfileUploadtime=" + addrSeedfileUploadtime +
                ", addrSeedfileSize='" + addrSeedfileSize + '\'' +
                ", formaticon='" + formaticon + '\'' +
                ", collect='" + collect + '\'' +
                ", detect='" + detect + '\'' +
                ", detectTimes=" + detectTimes +
                ", addrgenSeedfileDetailsList=" + addrgenSeedfileDetailsList +
                '}';
    }
}
