package com.ruoyi.vuln.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.vuln.mapper.VulnSingleAddrDetailsMapper;
import com.ruoyi.vuln.domain.VulnSingleAddrDetails;
import com.ruoyi.vuln.service.IVulnSingleAddrDetailsService;

/**
 * 单条地址扫描结果Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-05
 */
@Service
public class VulnSingleAddrDetailsServiceImpl implements IVulnSingleAddrDetailsService 
{
    @Autowired
    private VulnSingleAddrDetailsMapper vulnSingleAddrDetailsMapper;

    /**
     * 查询单条地址扫描结果
     * 
     * @param ipaddrId 单条地址扫描结果主键
     * @return 单条地址扫描结果
     */
    @Override
    public VulnSingleAddrDetails selectVulnSingleAddrDetailsByIpaddrId(Long ipaddrId)
    {
        return vulnSingleAddrDetailsMapper.selectVulnSingleAddrDetailsByIpaddrId(ipaddrId);
    }

    /**
     * 查询单条地址扫描结果列表
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 单条地址扫描结果
     */
    @Override
    public List<VulnSingleAddrDetails> selectVulnSingleAddrDetailsList(VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        return vulnSingleAddrDetailsMapper.selectVulnSingleAddrDetailsList(vulnSingleAddrDetails);
    }

    /**
     * 新增单条地址扫描结果
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 结果
     */
    @Override
    public int insertVulnSingleAddrDetails(VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        return vulnSingleAddrDetailsMapper.insertVulnSingleAddrDetails(vulnSingleAddrDetails);
    }

    /**
     * 修改单条地址扫描结果
     * 
     * @param vulnSingleAddrDetails 单条地址扫描结果
     * @return 结果
     */
    @Override
    public int updateVulnSingleAddrDetails(VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        return vulnSingleAddrDetailsMapper.updateVulnSingleAddrDetails(vulnSingleAddrDetails);
    }

    /**
     * 批量删除单条地址扫描结果
     * 
     * @param ipaddrIds 需要删除的单条地址扫描结果主键
     * @return 结果
     */
    @Override
    public int deleteVulnSingleAddrDetailsByIpaddrIds(Long[] ipaddrIds)
    {
        return vulnSingleAddrDetailsMapper.deleteVulnSingleAddrDetailsByIpaddrIds(ipaddrIds);
    }

    /**
     * 删除单条地址扫描结果信息
     * 
     * @param ipaddrId 单条地址扫描结果主键
     * @return 结果
     */
    @Override
    public int deleteVulnSingleAddrDetailsByIpaddrId(Long ipaddrId)
    {
        return vulnSingleAddrDetailsMapper.deleteVulnSingleAddrDetailsByIpaddrId(ipaddrId);
    }
}
