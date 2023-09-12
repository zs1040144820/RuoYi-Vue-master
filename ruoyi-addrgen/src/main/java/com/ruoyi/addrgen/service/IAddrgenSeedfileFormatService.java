package com.ruoyi.addrgen.service;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileFormat;

/**
 * 种子文件格式化记录Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public interface IAddrgenSeedfileFormatService 
{
    /**
     * 查询种子文件格式化记录
     * 
     * @param formatRecordId 种子文件格式化记录主键
     * @return 种子文件格式化记录
     */
    public AddrgenSeedfileFormat selectAddrgenSeedfileFormatByFormatRecordId(Long formatRecordId);

    /**
     * 查询种子文件格式化记录列表
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 种子文件格式化记录集合
     */
    public List<AddrgenSeedfileFormat> selectAddrgenSeedfileFormatList(AddrgenSeedfileFormat addrgenSeedfileFormat);

    /**
     * 新增种子文件格式化记录
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 结果
     */
    public int insertAddrgenSeedfileFormat(AddrgenSeedfileFormat addrgenSeedfileFormat);

    /**
     * 修改种子文件格式化记录
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 结果
     */
    public int updateAddrgenSeedfileFormat(AddrgenSeedfileFormat addrgenSeedfileFormat);

    /**
     * 批量删除种子文件格式化记录
     * 
     * @param formatRecordIds 需要删除的种子文件格式化记录主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileFormatByFormatRecordIds(Long[] formatRecordIds);

    /**
     * 删除种子文件格式化记录信息
     * 
     * @param formatRecordId 种子文件格式化记录主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileFormatByFormatRecordId(Long formatRecordId);
}
