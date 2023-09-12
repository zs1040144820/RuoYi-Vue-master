package com.ruoyi.vuln.domain.vo;

import javax.xml.bind.annotation.XmlElement;

public class OtherId {
    private String cveId;

    @XmlElement(name="cve-id")
    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    @Override
    public String toString() {
        return "OtherId{" +
                "cveId='" + cveId + '\'' +
                '}';
    }
}
