package com.ruoyi.addrgen.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 探测历史对象 addrgen_record_detect
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public class AddrgenRecordDetect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long detectId;

    private Long recordId;

    /** 探测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "探测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectTime;

    /** 命中率 */
    @Excel(name = "命中率")
    private String hitRate;

    /** 命中地址数 */
    @Excel(name = "命中地址数")
    private Long hitNum;

    /** 生成地址总数 */
    @Excel(name = "生成地址总数")
    private Long total;

    /** 输入文件名 */
    @Excel(name = "输入文件名")
    private String inputFile;

    /** 耗时 */
    @Excel(name = "耗时")
    private String consume;

    public void setDetectId(Long detectId) 
    {
        this.detectId = detectId;
    }

    public Long getDetectId() 
    {
        return detectId;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setDetectTime(Date detectTime) 
    {
        this.detectTime = detectTime;
    }

    public Date getDetectTime() 
    {
        return detectTime;
    }
    public void setHitRate(String hitRate) 
    {
        this.hitRate = hitRate;
    }

    public String getHitRate() 
    {
        return hitRate;
    }
    public void setHitNum(Long hitNum) 
    {
        this.hitNum = hitNum;
    }

    public Long getHitNum() 
    {
        return hitNum;
    }
    public void setTotal(Long total) 
    {
        this.total = total;
    }

    public Long getTotal() 
    {
        return total;
    }
    public void setInputFile(String inputFile) 
    {
        this.inputFile = inputFile;
    }

    public String getInputFile() 
    {
        return inputFile;
    }
    public void setConsume(String consume) 
    {
        this.consume = consume;
    }

    public String getConsume() 
    {
        return consume;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detectId", getDetectId())
            .append("recordId", getRecordId())
            .append("detectTime", getDetectTime())
            .append("hitRate", getHitRate())
            .append("hitNum", getHitNum())
            .append("total", getTotal())
            .append("inputFile", getInputFile())
            .append("consume", getConsume())
            .toString();
    }
}
