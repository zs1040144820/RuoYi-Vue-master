package com.ruoyi.vuln.mapper;

import java.util.List;
import com.ruoyi.vuln.domain.VulnCnnvd;

/**
 * 漏洞特征Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-12
 */
public interface VulnCnnvdMapper 
{
    /**
     * 查询漏洞特征
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 漏洞特征
     */
    public VulnCnnvd selectVulnCnnvdByCnnvdId(Long cnnvdId);

    /**
     * 查询漏洞特征列表
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 漏洞特征集合
     */
    public List<VulnCnnvd> selectVulnCnnvdList(VulnCnnvd vulnCnnvd);

    /**
     * 新增漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    public int insertVulnCnnvd(VulnCnnvd vulnCnnvd);

    /**
     * 修改漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    public int updateVulnCnnvd(VulnCnnvd vulnCnnvd);

    /**
     * 删除漏洞特征
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 结果
     */
    public int deleteVulnCnnvdByCnnvdId(Long cnnvdId);

    /**
     * 批量删除漏洞特征
     * 
     * @param cnnvdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVulnCnnvdByCnnvdIds(Long[] cnnvdIds);
}
