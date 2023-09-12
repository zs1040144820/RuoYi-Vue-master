package com.ruoyi.vuln.domain.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Entry {

    private String name;
    private String vulnId;
    private String published;
    private String modified;
    private String source;
    private String severity;
    private String vulnType;

    private String vulnDescript;
    private String vulnSolution;
    private OtherId otherId;

    @XmlElement(name="other-id")
    public OtherId getOtherId() {
        return otherId;
    }

    public void setOtherId(OtherId otherId) {
        this.otherId = otherId;
    }
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "vuln-id")
    public String getVulnId() {
        return vulnId;
    }

    public void setVulnId(String vulnId) {
        this.vulnId = vulnId;
    }
    @XmlElement(name = "published")
    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }
    @XmlElement(name = "modified")
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
    @XmlElement(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @XmlElement(name = "severity")
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
    @XmlElement(name = "vuln-type")
    public String getVulnType() {
        return vulnType;
    }

    public void setVulnType(String vulnType) {
        this.vulnType = vulnType;
    }
    @XmlElement(name = "vuln-descript")
    public String getVulnDescript() {
        return vulnDescript;
    }

    public void setVulnDescript(String vulnDescript) {
        this.vulnDescript = vulnDescript;
    }
    @XmlElement(name = "vuln-solution")
    public String getVulnSolution() {
        return vulnSolution;
    }

    public void setVulnSolution(String vulnSolution) {
        this.vulnSolution = vulnSolution;
    }

    @Override
    public String toString() {
        return "entry{" +
                "name='" + name + '\'' +
                ", vulnId='" + vulnId + '\'' +
                ", published='" + published + '\'' +
                ", modified='" + modified + '\'' +
                ", source='" + source + '\'' +
                ", severity='" + severity + '\'' +
                ", vulnType='" + vulnType + '\'' +
                ", vulnDescript='" + vulnDescript + '\'' +
                ", vulnSolution='" + vulnSolution + '\'' +
                '}';
    }
}
