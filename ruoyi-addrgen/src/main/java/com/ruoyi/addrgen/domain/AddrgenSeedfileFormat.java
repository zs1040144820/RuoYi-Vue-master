package com.ruoyi.addrgen.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 种子文件格式化记录对象 addrgen_seedfile_format
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public class AddrgenSeedfileFormat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 格式化记录id */
    private Long formatRecordId;

    private Long addrSeedfileId;

    /** 输入文件 */
    @Excel(name = "输入文件")
    private String inputFilename;

    /** 输出文件 */
    @Excel(name = "输出文件")
    private String outputFilename;

    /** 格式化日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "格式化日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date formatTime;

    public void setFormatRecordId(Long formatRecordId) 
    {
        this.formatRecordId = formatRecordId;
    }

    public Long getFormatRecordId() 
    {
        return formatRecordId;
    }
    public void setAddrSeedfileId(Long addrSeedfileId) 
    {
        this.addrSeedfileId = addrSeedfileId;
    }

    public Long getAddrSeedfileId() 
    {
        return addrSeedfileId;
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
    public void setFormatTime(Date formatTime) 
    {
        this.formatTime = formatTime;
    }

    public Date getFormatTime() 
    {
        return formatTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("formatRecordId", getFormatRecordId())
            .append("addrSeedfileId", getAddrSeedfileId())
            .append("inputFilename", getInputFilename())
            .append("outputFilename", getOutputFilename())
            .append("formatTime", getFormatTime())
            .toString();
    }
}
