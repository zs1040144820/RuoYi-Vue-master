package com.ruoyi.addrgen.service.impl;

import java.util.List;

import com.ruoyi.addrgen.service.IAddrgenSeedfileFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileFormatMapper;
import com.ruoyi.addrgen.domain.AddrgenSeedfileFormat;

/**
 * 种子文件格式化记录Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@Service
public class AddrgenSeedfileFormatServiceImpl implements IAddrgenSeedfileFormatService
{
    @Autowired
    private AddrgenSeedfileFormatMapper addrgenSeedfileFormatMapper;

    /**
     * 查询种子文件格式化记录
     * 
     * @param formatRecordId 种子文件格式化记录主键
     * @return 种子文件格式化记录
     */
    @Override
    public AddrgenSeedfileFormat selectAddrgenSeedfileFormatByFormatRecordId(Long formatRecordId)
    {
        return addrgenSeedfileFormatMapper.selectAddrgenSeedfileFormatByFormatRecordId(formatRecordId);
    }

    /**
     * 查询种子文件格式化记录列表
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 种子文件格式化记录
     */
    @Override
    public List<AddrgenSeedfileFormat> selectAddrgenSeedfileFormatList(AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        return addrgenSeedfileFormatMapper.selectAddrgenSeedfileFormatList(addrgenSeedfileFormat);
    }

    /**
     * 新增种子文件格式化记录
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 结果
     */
    @Override
    public int insertAddrgenSeedfileFormat(AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        return addrgenSeedfileFormatMapper.insertAddrgenSeedfileFormat(addrgenSeedfileFormat);
    }

    /**
     * 修改种子文件格式化记录
     * 
     * @param addrgenSeedfileFormat 种子文件格式化记录
     * @return 结果
     */
    @Override
    public int updateAddrgenSeedfileFormat(AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        return addrgenSeedfileFormatMapper.updateAddrgenSeedfileFormat(addrgenSeedfileFormat);
    }

    /**
     * 批量删除种子文件格式化记录
     * 
     * @param formatRecordIds 需要删除的种子文件格式化记录主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileFormatByFormatRecordIds(Long[] formatRecordIds)
    {
        return addrgenSeedfileFormatMapper.deleteAddrgenSeedfileFormatByFormatRecordIds(formatRecordIds);
    }

    /**
     * 删除种子文件格式化记录信息
     * 
     * @param formatRecordId 种子文件格式化记录主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileFormatByFormatRecordId(Long formatRecordId)
    {
        return addrgenSeedfileFormatMapper.deleteAddrgenSeedfileFormatByFormatRecordId(formatRecordId);
    }
}
