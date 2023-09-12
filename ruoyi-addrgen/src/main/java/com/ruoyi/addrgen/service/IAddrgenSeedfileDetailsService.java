package com.ruoyi.addrgen.service;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetailsUp;

/**
 * 种子文件详情Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public interface IAddrgenSeedfileDetailsService 
{
    /**
     * 查询种子文件详情
     * 
     * @param addrseedId 种子文件详情主键
     * @return 种子文件详情
     */
    public AddrgenSeedfileDetails selectAddrgenSeedfileDetailsByAddrseedId(Long addrseedId);

    /**
     * 查询种子文件详情列表
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 种子文件详情集合
     */
    public List<AddrgenSeedfileDetails> selectAddrgenSeedfileDetailsList(AddrgenSeedfileDetails addrgenSeedfileDetails);

    /**
     * 新增种子文件详情
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 结果
     */
    public int insertAddrgenSeedfileDetails(AddrgenSeedfileDetails addrgenSeedfileDetails);

    /**
     * 修改种子文件详情
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 结果
     */
    public int updateAddrgenSeedfileDetails(AddrgenSeedfileDetails addrgenSeedfileDetails);

    /**
     * 批量删除种子文件详情
     * 
     * @param addrseedIds 需要删除的种子文件详情主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrseedIds(Long[] addrseedIds);

    /**
     * 删除种子文件详情信息
     * 
     * @param addrseedId 种子文件详情主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrseedId(Long addrseedId);

    public void updateCategory(AddrgenSeedfileDetailsUp ardu);
}
