package com.ruoyi.vuln.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.xml.bind.annotation.XmlTransient;

/**
 * 漏洞特征对象 vuln_cnnvd
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-12
 */
public class VulnCnnvd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 漏洞名称 */
    @Excel(name = "漏洞名称")
    private String name;

    /** vuln-id */
    @Excel(name = "vuln-id")
    private String vulnId;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date published;

    /** 调整时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调整时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modified;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 严重等级 */
    @Excel(name = "严重等级")
    private String severity;

    /** 漏洞类型 */
    @Excel(name = "漏洞类型")
    private String vulnType;

    /** 漏洞描述 */
    @Excel(name = "漏洞描述")
    private String vulnDescript;

    /** cve-id */
    @Excel(name = "cve-id")
    private String cveId;

    /** 漏洞解决方法 */
    @Excel(name = "漏洞解决方法")
    private String vulnSolution;

    /** 所属文件年份 */
    @Excel(name = "所属文件年份")
    private String fileYear;

    /** 主键id */
    private Long cnnvdId;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setVulnId(String vulnId) 
    {
        this.vulnId = vulnId;
    }

    public String getVulnId() 
    {
        return vulnId;
    }
    public void setPublished(Date published) 
    {
        this.published = published;
    }

    public Date getPublished() 
    {
        return published;
    }
    public void setModified(Date modified) 
    {
        this.modified = modified;
    }

    public Date getModified() 
    {
        return modified;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setSeverity(String severity) 
    {
        this.severity = severity;
    }

    public String getSeverity() 
    {
        return severity;
    }
    public void setVulnType(String vulnType) 
    {
        this.vulnType = vulnType;
    }

    public String getVulnType() 
    {
        return vulnType;
    }
    public void setVulnDescript(String vulnDescript)
    {
        this.vulnDescript = vulnDescript;
    }

    public String getVulnDescript() 
    {
        return vulnDescript;
    }
    public void setCveId(String cveId) 
    {
        this.cveId = cveId;
    }

    public String getCveId() 
    {
        return cveId;
    }
    public void setVulnSolution(String vulnSolution)
    {
        this.vulnSolution = vulnSolution;
    }

    public String getVulnSolution()
    {
        return vulnSolution;
    }
    public void setFileYear(String fileYear)
    {
        this.fileYear = fileYear;
    }

    public String getFileYear()
    {
        return fileYear;
    }

    public Long getCnnvdId() 
    {
        return cnnvdId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("vulnId", getVulnId())
            .append("published", getPublished())
            .append("modified", getModified())
            .append("source", getSource())
            .append("severity", getSeverity())
            .append("vulnType", getVulnType())
            .append("vulnDescript", getVulnDescript())
            .append("cveId", getCveId())
            .append("vulnSolution", getVulnSolution())
            .append("fileYear", getFileYear())
            .append("cnnvdId", getCnnvdId())
            .toString();
    }
}
