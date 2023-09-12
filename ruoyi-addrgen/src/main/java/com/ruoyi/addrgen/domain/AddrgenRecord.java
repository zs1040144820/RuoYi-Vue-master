package com.ruoyi.addrgen.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生成记录对象 addrgen_record
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public class AddrgenRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录主键 */
    private Long recordId;

    /** 输入文件名 */
    @Excel(name = "输入文件名")
    private String inputFilename;

    /** 输出文件名 */
    @Excel(name = "输出文件名")
    private String outputFilename;

    /** 生成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date genTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 是否启用多层级分类 */
    @Excel(name = "是否启用多层级分类")
    private String ismul;

    /** 生成总地址数 */
    @Excel(name = "生成总地址数")
    private Long totalNums;

    /** 别名区 */
    @Excel(name = "别名区")
    private String alias;

    /** 生成文件详情信息 */
    private List<AddrgenRecordDetails> addrgenRecordDetailsList;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setInputFilename(String inputFilename) 
    {
        this.inputFilename = inputFilename;
    }

    public String getInputFilename() 
    {
        return inputFilename;
    }
    public void setOutputFilename(String outputFilename) 
    {
        this.outputFilename = outputFilename;
    }

    public String getOutputFilename() 
    {
        return outputFilename;
    }
    public void setGenTime(Date genTime) 
    {
        this.genTime = genTime;
    }

    public Date getGenTime() 
    {
        return genTime;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setIsmul(String ismul) 
    {
        this.ismul = ismul;
    }

    public String getIsmul() 
    {
        return ismul;
    }
    public void setTotalNums(Long totalNums) 
    {
        this.totalNums = totalNums;
    }

    public Long getTotalNums() 
    {
        return totalNums;
    }

    public List<AddrgenRecordDetails> getAddrgenRecordDetailsList()
    {
        return addrgenRecordDetailsList;
    }

    public void setAddrgenRecordDetailsList(List<AddrgenRecordDetails> addrgenRecordDetailsList)
    {
        this.addrgenRecordDetailsList = addrgenRecordDetailsList;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "AddrgenRecord{" +
                "recordId=" + recordId +
                ", inputFilename='" + inputFilename + '\'' +
                ", outputFilename='" + outputFilename + '\'' +
                ", genTime=" + genTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", ismul='" + ismul + '\'' +
                ", totalNums=" + totalNums +
                ", alias='" + alias + '\'' +
                ", addrgenRecordDetailsList=" + addrgenRecordDetailsList +
                '}';
    }
}
