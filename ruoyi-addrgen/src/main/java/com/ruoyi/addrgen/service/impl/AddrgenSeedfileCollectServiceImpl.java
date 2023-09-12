package com.ruoyi.addrgen.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileCollectMapper;
import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;
import com.ruoyi.addrgen.service.IAddrgenSeedfileCollectService;

/**
 * 种子文件采集历史记录Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@Service
public class AddrgenSeedfileCollectServiceImpl implements IAddrgenSeedfileCollectService 
{
    @Autowired
    private AddrgenSeedfileCollectMapper addrgenSeedfileCollectMapper;

    /**
     * 查询种子文件采集历史记录
     * 
     * @param collectRecordId 种子文件采集历史记录主键
     * @return 种子文件采集历史记录
     */
    @Override
    public AddrgenSeedfileCollect selectAddrgenSeedfileCollectByCollectRecordId(Long collectRecordId)
    {

        return addrgenSeedfileCollectMapper.selectAddrgenSeedfileCollectByCollectRecordId(collectRecordId);
    }

    /**
     * 查询种子文件采集历史记录列表
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 种子文件采集历史记录
     */
    @Override
    public List<AddrgenSeedfileCollect> selectAddrgenSeedfileCollectList(AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        return addrgenSeedfileCollectMapper.selectAddrgenSeedfileCollectList(addrgenSeedfileCollect);
    }

    /**
     * 新增种子文件采集历史记录
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 结果
     */
    @Override
    public int insertAddrgenSeedfileCollect(AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        return addrgenSeedfileCollectMapper.insertAddrgenSeedfileCollect(addrgenSeedfileCollect);
    }

    /**
     * 修改种子文件采集历史记录
     * 
     * @param addrgenSeedfileCollect 种子文件采集历史记录
     * @return 结果
     */
    @Override
    public int updateAddrgenSeedfileCollect(AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        return addrgenSeedfileCollectMapper.updateAddrgenSeedfileCollect(addrgenSeedfileCollect);
    }
    @Override
    public int updateAddrgenSeedfileCollectByfileID(AddrgenSeedfileCollect addrgenSeedfileCollect){
        return addrgenSeedfileCollectMapper.updateAddrgenSeedfileCollectByfileID(addrgenSeedfileCollect);
    }
    /**
     * 批量删除种子文件采集历史记录
     * 
     * @param collectRecordIds 需要删除的种子文件采集历史记录主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileCollectByCollectRecordIds(Long[] collectRecordIds)
    {
        return addrgenSeedfileCollectMapper.deleteAddrgenSeedfileCollectByCollectRecordIds(collectRecordIds);
    }

    /**
     * 删除种子文件采集历史记录信息
     * 
     * @param collectRecordId 种子文件采集历史记录主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileCollectByCollectRecordId(Long collectRecordId)
    {
        return addrgenSeedfileCollectMapper.deleteAddrgenSeedfileCollectByCollectRecordId(collectRecordId);
    }
}
