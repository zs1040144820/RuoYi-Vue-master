package com.ruoyi.vuln.service;

import java.util.List;
import com.ruoyi.vuln.domain.VulnSingleAddrDetails;

/**
 * 单条地址扫描结果Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-05
 */
public interface IVulnSingleAddrDetailsService 
{
    /**
     * 查询单条地址扫描结果
     * 
     * @param ipaddrId 单条地址扫描结果主键
     * @return 单条地址扫描结果
     */
    public VulnSingleAddrDetails selectVulnSingleAddrDetailsByIpaddrId(Long ipaddrId);

    /**
     * 查询单条地址扫描结果列表
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 单条地址扫描结果集合
     */
    public List<VulnSingleAddrDetails> selectVulnSingleAddrDetailsList(VulnSingleAddrDetails vulnSingleAddrDetails);

    /**
     * 新增单条地址扫描结果
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 结果
     */
    public int insertVulnSingleAddrDetails(VulnSingleAddrDetails vulnSingleAddrDetails);

    /**
     * 修改单条地址扫描结果
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 结果
     */
    public int updateVulnSingleAddrDetails(VulnSingleAddrDetails vulnSingleAddrDetails);

    /**
     * 批量删除单条地址扫描结果
     * 
     * @param ipaddrIds 需要删除的单条地址扫描结果主键集合
     * @return 结果
     */
    public int deleteVulnSingleAddrDetailsByIpaddrIds(Long[] ipaddrIds);

    /**
     * 删除单条地址扫描结果信息
     * 
     * @param ipaddrId 单条地址扫描结果主键
     * @return 结果
     */
    public int deleteVulnSingleAddrDetailsByIpaddrId(Long ipaddrId);
}
