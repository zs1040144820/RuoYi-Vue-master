package com.ruoyi.addrgen.service;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;

/**
 * 种子文件采集历史记录Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public interface IAddrgenSeedfileCollectService 
{
    /**
     * 查询种子文件采集历史记录
     * 
     * @param collectRecordId 种子文件采集历史记录主键
     * @return 种子文件采集历史记录
     */
    public AddrgenSeedfileCollect selectAddrgenSeedfileCollectByCollectRecordId(Long collectRecordId);

    /**
     * 查询种子文件采集历史记录列表
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 种子文件采集历史记录集合
     */
    public List<AddrgenSeedfileCollect> selectAddrgenSeedfileCollectList(AddrgenSeedfileCollect addrgenSeedfileCollect);

    /**
     * 新增种子文件采集历史记录
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 结果
     */
    public int insertAddrgenSeedfileCollect(AddrgenSeedfileCollect addrgenSeedfileCollect);

    /**
     * 修改种子文件采集历史记录
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 结果
     */
    public int updateAddrgenSeedfileCollect(AddrgenSeedfileCollect addrgenSeedfileCollect);

    public int updateAddrgenSeedfileCollectByfileID(AddrgenSeedfileCollect addrgenSeedfileCollect);
    /**
     * 批量删除种子文件采集历史记录
     * 
     * @param collectRecordIds 需要删除的种子文件采集历史记录主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileCollectByCollectRecordIds(Long[] collectRecordIds);

    /**
     * 删除种子文件采集历史记录信息
     * 
     * @param collectRecordId 种子文件采集历史记录主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileCollectByCollectRecordId(Long collectRecordId);
}
