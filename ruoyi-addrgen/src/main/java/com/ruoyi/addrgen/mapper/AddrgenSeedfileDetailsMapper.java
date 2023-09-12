package com.ruoyi.addrgen.mapper;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;

/**
 * 种子文件详情Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public interface AddrgenSeedfileDetailsMapper 
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

    public int batchUpdateAddrgenSeedfileDetails(List<AddrgenSeedfileDetails> addrgenSeedfileDetailsList);
    /**
     * 删除种子文件详情
     * 
     * @param addrseedId 种子文件详情主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrseedId(Long addrseedId);

    /**
     * 批量删除种子文件详情
     * 
     * @param addrseedIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrseedIds(Long[] addrseedIds);
    /*
     * 根据文件的主键名，查出文件中的地址详细信息集合
     * */

    public List<AddrgenSeedfileDetails> selectAddrgenSeedfileDetailsListByFileId(Long addrSeedfileId);
    /*
    根据标准IPv6地址查
    * */
    public List<AddrgenSeedfileDetails> selectAddrgenSeedfileDetailsListByStardIP(String stardIP);
}
