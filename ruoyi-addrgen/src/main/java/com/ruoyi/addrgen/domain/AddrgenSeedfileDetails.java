package com.ruoyi.addrgen.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 种子文件详情对象 addrgen_seedfile_details
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
public class AddrgenSeedfileDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 每条IP地址的主键id */
    private Long addrseedId;

    private Long addrSeedfileId;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String addrseedName;

    /** 资产分类 */
    @Excel(name = "资产分类")
    private String addrseedCategory;

    /** IPv6地址 */
    @Excel(name = "IPv6地址")
    private String ipaddress;

    /** 采集时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectTime;

    /** 标准IPv6地址 */
    @Excel(name = "标准IPv6地址")
    private String standardIpaddress;

    /** ASN */
    @Excel(name = "ASN")
    private String asn;

    /** BGP前缀 */
    @Excel(name = "BGP前缀")
    private String bgpPrefix;

    /** InterfaceID */
    @Excel(name = "InterfaceID")
    private String interfaceId;

    /** 响应类型 */
    @Excel(name = "响应类型", readConverterExp = "1=存在端口开放并且响应ICMPv6,2=存在端口开放但不响应ICMPv6,3=仅响应ICMPv6,4=没有任何响应ICMPv6")
    private String responseType;

    /** 稳定性 */
    @Excel(name = "稳定性")
    private String stability;

    /** 采集结果 */
    @Excel(name = "采集结果", readConverterExp = "1=活跃,0=不活跃")
    private String collectis;

    private String detect0;

    private String detect1;

    private String detect2;

    private String detect3;

    private String detect4;

    private String detect5;

    private String detect6;

    private String detect7;

    private String detect8;

    private String detect9;

    private String detect10;

    private String detect11;

    private String detect12;

    private String detect13;

    private String detect14;

    private String detect15;

    private String detect16;

    private String detect17;

    private String detect18;

    private String detect19;

    private String detect20;

    private String detect21;

    private String detect22;

    private String detect23;

    private String detect24;

    private String detect25;

    private String detect26;

    private String detect27;

    private String detect28;

    private String detect29;

    public void setAddrseedId(Long addrseedId) 
    {
        this.addrseedId = addrseedId;
    }

    public Long getAddrseedId() 
    {
        return addrseedId;
    }
    public void setAddrSeedfileId(Long addrSeedfileId) 
    {
        this.addrSeedfileId = addrSeedfileId;
    }

    public Long getAddrSeedfileId() 
    {
        return addrSeedfileId;
    }
    public void setAddrseedName(String addrseedName) 
    {
        this.addrseedName = addrseedName;
    }

    public String getAddrseedName() 
    {
        return addrseedName;
    }
    public void setAddrseedCategory(String addrseedCategory) 
    {
        this.addrseedCategory = addrseedCategory;
    }

    public String getAddrseedCategory() 
    {
        return addrseedCategory;
    }
    public void setIpaddress(String ipaddress) 
    {
        this.ipaddress = ipaddress;
    }

    public String getIpaddress() 
    {
        return ipaddress;
    }
    public void setCollectTime(Date collectTime) 
    {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() 
    {
        return collectTime;
    }
    public void setStandardIpaddress(String standardIpaddress) 
    {
        this.standardIpaddress = standardIpaddress;
    }

    public String getStandardIpaddress() 
    {
        return standardIpaddress;
    }
    public void setAsn(String asn) 
    {
        this.asn = asn;
    }

    public String getAsn() 
    {
        return asn;
    }
    public void setBgpPrefix(String bgpPrefix) 
    {
        this.bgpPrefix = bgpPrefix;
    }

    public String getBgpPrefix() 
    {
        return bgpPrefix;
    }
    public void setInterfaceId(String interfaceId) 
    {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceId() 
    {
        return interfaceId;
    }
    public void setResponseType(String responseType) 
    {
        this.responseType = responseType;
    }

    public String getResponseType() 
    {
        return responseType;
    }
    public void setStability(String stability) 
    {
        this.stability = stability;
    }

    public String getStability() 
    {
        return stability;
    }
    public void setDetect0(String detect0) 
    {
        this.detect0 = detect0;
    }

    public String getDetect0() 
    {
        return detect0;
    }
    public void setDetect1(String detect1) 
    {
        this.detect1 = detect1;
    }

    public String getDetect1() 
    {
        return detect1;
    }
    public void setDetect2(String detect2) 
    {
        this.detect2 = detect2;
    }

    public String getDetect2() 
    {
        return detect2;
    }
    public void setDetect3(String detect3) 
    {
        this.detect3 = detect3;
    }

    public String getDetect3() 
    {
        return detect3;
    }
    public void setDetect4(String detect4) 
    {
        this.detect4 = detect4;
    }

    public String getDetect4() 
    {
        return detect4;
    }
    public void setDetect5(String detect5) 
    {
        this.detect5 = detect5;
    }

    public String getDetect5() 
    {
        return detect5;
    }
    public void setDetect6(String detect6) 
    {
        this.detect6 = detect6;
    }

    public String getDetect6() 
    {
        return detect6;
    }
    public void setDetect7(String detect7) 
    {
        this.detect7 = detect7;
    }

    public String getDetect7() 
    {
        return detect7;
    }
    public void setDetect8(String detect8) 
    {
        this.detect8 = detect8;
    }

    public String getDetect8() 
    {
        return detect8;
    }
    public void setDetect9(String detect9) 
    {
        this.detect9 = detect9;
    }

    public String getDetect9() 
    {
        return detect9;
    }
    public void setDetect10(String detect10) 
    {
        this.detect10 = detect10;
    }

    public String getDetect10() 
    {
        return detect10;
    }
    public void setDetect11(String detect11) 
    {
        this.detect11 = detect11;
    }

    public String getDetect11() 
    {
        return detect11;
    }
    public void setDetect12(String detect12) 
    {
        this.detect12 = detect12;
    }

    public String getDetect12() 
    {
        return detect12;
    }
    public void setDetect13(String detect13) 
    {
        this.detect13 = detect13;
    }

    public String getDetect13() 
    {
        return detect13;
    }
    public void setDetect14(String detect14) 
    {
        this.detect14 = detect14;
    }

    public String getDetect14() 
    {
        return detect14;
    }
    public void setDetect15(String detect15) 
    {
        this.detect15 = detect15;
    }

    public String getDetect15() 
    {
        return detect15;
    }
    public void setDetect16(String detect16) 
    {
        this.detect16 = detect16;
    }

    public String getDetect16() 
    {
        return detect16;
    }
    public void setDetect17(String detect17) 
    {
        this.detect17 = detect17;
    }

    public String getDetect17() 
    {
        return detect17;
    }
    public void setDetect18(String detect18) 
    {
        this.detect18 = detect18;
    }

    public String getDetect18() 
    {
        return detect18;
    }
    public void setDetect19(String detect19) 
    {
        this.detect19 = detect19;
    }

    public String getDetect19() 
    {
        return detect19;
    }
    public void setDetect20(String detect20) 
    {
        this.detect20 = detect20;
    }

    public String getDetect20() 
    {
        return detect20;
    }
    public void setDetect21(String detect21) 
    {
        this.detect21 = detect21;
    }

    public String getDetect21() 
    {
        return detect21;
    }
    public void setDetect22(String detect22) 
    {
        this.detect22 = detect22;
    }

    public String getDetect22() 
    {
        return detect22;
    }
    public void setDetect23(String detect23) 
    {
        this.detect23 = detect23;
    }

    public String getDetect23() 
    {
        return detect23;
    }
    public void setDetect24(String detect24) 
    {
        this.detect24 = detect24;
    }

    public String getDetect24() 
    {
        return detect24;
    }
    public void setDetect25(String detect25) 
    {
        this.detect25 = detect25;
    }

    public String getDetect25() 
    {
        return detect25;
    }
    public void setDetect26(String detect26) 
    {
        this.detect26 = detect26;
    }

    public String getDetect26() 
    {
        return detect26;
    }
    public void setDetect27(String detect27) 
    {
        this.detect27 = detect27;
    }

    public String getDetect27() 
    {
        return detect27;
    }
    public void setDetect28(String detect28) 
    {
        this.detect28 = detect28;
    }

    public String getDetect28() 
    {
        return detect28;
    }
    public void setDetect29(String detect29) 
    {
        this.detect29 = detect29;
    }

    public String getDetect29() 
    {
        return detect29;
    }

    @Override
    public String toString() {
        return "AddrgenSeedfileDetails{" +
                "addrseedId=" + addrseedId +
                ", addrSeedfileId=" + addrSeedfileId +
                ", addrseedName='" + addrseedName + '\'' +
                ", addrseedCategory='" + addrseedCategory + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", collectTime=" + collectTime +
                ", standardIpaddress='" + standardIpaddress + '\'' +
                ", asn='" + asn + '\'' +
                ", bgpPrefix='" + bgpPrefix + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", responseType='" + responseType + '\'' +
                ", stability='" + stability + '\'' +
                ", collectis='" + collectis + '\'' +
                ", detect0='" + detect0 + '\'' +
                ", detect1='" + detect1 + '\'' +
                ", detect2='" + detect2 + '\'' +
                ", detect3='" + detect3 + '\'' +
                ", detect4='" + detect4 + '\'' +
                ", detect5='" + detect5 + '\'' +
                ", detect6='" + detect6 + '\'' +
                ", detect7='" + detect7 + '\'' +
                ", detect8='" + detect8 + '\'' +
                ", detect9='" + detect9 + '\'' +
                ", detect10='" + detect10 + '\'' +
                ", detect11='" + detect11 + '\'' +
                ", detect12='" + detect12 + '\'' +
                ", detect13='" + detect13 + '\'' +
                ", detect14='" + detect14 + '\'' +
                ", detect15='" + detect15 + '\'' +
                ", detect16='" + detect16 + '\'' +
                ", detect17='" + detect17 + '\'' +
                ", detect18='" + detect18 + '\'' +
                ", detect19='" + detect19 + '\'' +
                ", detect20='" + detect20 + '\'' +
                ", detect21='" + detect21 + '\'' +
                ", detect22='" + detect22 + '\'' +
                ", detect23='" + detect23 + '\'' +
                ", detect24='" + detect24 + '\'' +
                ", detect25='" + detect25 + '\'' +
                ", detect26='" + detect26 + '\'' +
                ", detect27='" + detect27 + '\'' +
                ", detect28='" + detect28 + '\'' +
                ", detect29='" + detect29 + '\'' +
                '}';
    }

    public String getCollectis() {
        return collectis;
    }

    public void setCollectis(String collectis) {
        this.collectis = collectis;
    }

}
