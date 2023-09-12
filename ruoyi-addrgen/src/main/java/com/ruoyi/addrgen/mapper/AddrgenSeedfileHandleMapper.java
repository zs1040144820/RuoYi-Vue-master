package com.ruoyi.addrgen.mapper;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;

/**
 * 种子地址文件总览Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
public interface AddrgenSeedfileHandleMapper 
{
    /**
     * 查询种子地址文件总览
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 种子地址文件总览
     */
    public AddrgenSeedfileHandle selectAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId);

    /**
     * 查询种子地址文件总览列表
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 种子地址文件总览集合
     */
    public List<AddrgenSeedfileHandle> selectAddrgenSeedfileHandleList(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 新增种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    public int insertAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 修改种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    public int updateAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 删除种子地址文件总览
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId);

    /**
     * 批量删除种子地址文件总览
     * 
     * @param addrSeedfileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileHandleByAddrSeedfileIds(Long[] addrSeedfileIds);

    /**
     * 批量删除种子文件详情
     * 
     * @param addrSeedfileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrSeedfileIds(Long[] addrSeedfileIds);
    
    /**
     * 批量新增种子文件详情
     * 
     * @param addrgenSeedfileDetailsList 种子文件详情列表
     * @return 结果
     */
    public int batchAddrgenSeedfileDetails(List<AddrgenSeedfileDetails> addrgenSeedfileDetailsList);
    

    /**
     * 通过种子地址文件总览主键删除种子文件详情信息
     * 
     * @param addrSeedfileId 种子地址文件总览ID
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetailsByAddrSeedfileId(Long addrSeedfileId);

}
