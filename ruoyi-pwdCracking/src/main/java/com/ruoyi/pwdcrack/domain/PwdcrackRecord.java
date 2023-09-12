package com.ruoyi.pwdcrack.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 口令破解对象 pwdcrack_record
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-20
 */
public class PwdcrackRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long crackId;

    /** 目标地址 */
    @Excel(name = "目标地址")
    private String desIp;

    /** 协议/数据库 */
    @Excel(name = "协议/数据库")
    private String cProcotol;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码字典 */
    @Excel(name = "密码字典")
    private String pwdDic;

    /** 结果 */
    @Excel(name = "结果")
    private String result;

    /** 获取的密码 */
    @Excel(name = "获取的密码")
    private String pwdResult;

    private Date cTime;

    public void setCrackId(Long crackId) 
    {
        this.crackId = crackId;
    }

    public Long getCrackId() 
    {
        return crackId;
    }
    public void setDesIp(String desIp) 
    {
        this.desIp = desIp;
    }

    public String getDesIp() 
    {
        return desIp;
    }
    public void setcProcotol(String cProcotol) 
    {
        this.cProcotol = cProcotol;
    }

    public String getcProcotol() 
    {
        return cProcotol;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setPwdDic(String pwdDic) 
    {
        this.pwdDic = pwdDic;
    }

    public String getPwdDic() 
    {
        return pwdDic;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setPwdResult(String pwdResult) 
    {
        this.pwdResult = pwdResult;
    }

    public String getPwdResult() 
    {
        return pwdResult;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("crackId", getCrackId())
            .append("desIp", getDesIp())
            .append("cProcotol", getcProcotol())
            .append("username", getUsername())
            .append("pwdDic", getPwdDic())
            .append("result", getResult())
            .append("pwdResult", getPwdResult())
            .toString();
    }
}
