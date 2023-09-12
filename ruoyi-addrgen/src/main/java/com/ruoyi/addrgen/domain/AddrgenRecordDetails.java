package com.ruoyi.addrgen.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生成文件详情对象 addrgen_record_details
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public class AddrgenRecordDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long detailsId;

    /** 生成的IPv6地址 */
    @Excel(name = "生成的IPv6地址")
    private String ipaddr;

    /** 是否活跃 */
    @Excel(name = "是否活跃", readConverterExp = "1=活跃,0=不活跃")
    private String isactive;

    private Long recordId;

    public void setDetailsId(Long detailsId) 
    {
        this.detailsId = detailsId;
    }

    public Long getDetailsId() 
    {
        return detailsId;
    }
    public void setIpaddr(String ipaddr) 
    {
        this.ipaddr = ipaddr;
    }

    public String getIpaddr() 
    {
        return ipaddr;
    }
    public void setIsactive(String isactive) 
    {
        this.isactive = isactive;
    }

    public String getIsactive() 
    {
        return isactive;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailsId", getDetailsId())
            .append("ipaddr", getIpaddr())
            .append("isactive", getIsactive())
            .append("recordId", getRecordId())
            .toString();
    }
}
