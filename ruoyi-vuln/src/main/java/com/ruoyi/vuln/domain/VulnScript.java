package com.ruoyi.vuln.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 脚本对象 vuln_script
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-21
 */
public class VulnScript extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long scriptId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String scriptName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String cve;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String cvss20;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String cvss30;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String cvss31;

    public void setScriptId(Long scriptId) 
    {
        this.scriptId = scriptId;
    }

    public Long getScriptId() 
    {
        return scriptId;
    }
    public void setScriptName(String scriptName) 
    {
        this.scriptName = scriptName;
    }

    public String getScriptName() 
    {
        return scriptName;
    }
    public void setCve(String cve) 
    {
        this.cve = cve;
    }

    public String getCve() 
    {
        return cve;
    }
    public void setCvss20(String cvss20) 
    {
        this.cvss20 = cvss20;
    }

    public String getCvss20() 
    {
        return cvss20;
    }
    public void setCvss30(String cvss30) 
    {
        this.cvss30 = cvss30;
    }

    public String getCvss30() 
    {
        return cvss30;
    }
    public void setCvss31(String cvss31) 
    {
        this.cvss31 = cvss31;
    }

    public String getCvss31() 
    {
        return cvss31;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scriptId", getScriptId())
            .append("scriptName", getScriptName())
            .append("cve", getCve())
            .append("cvss20", getCvss20())
            .append("cvss30", getCvss30())
            .append("cvss31", getCvss31())
            .toString();
    }
}
