package com.ruoyi.vuln.domain.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cnnvd")
public class Cnnvd {
    private List<Entry> entrys;

    @XmlElement(name="entry")
    public List<Entry> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<Entry> entrys) {
        this.entrys = entrys;
    }



    @Override
    public String toString() {
        return "cnnvd{" +
                "entrys=" + entrys +
                '}';
    }
}
