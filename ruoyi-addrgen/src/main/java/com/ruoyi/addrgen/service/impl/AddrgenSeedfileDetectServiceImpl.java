package com.ruoyi.addrgen.service.impl;

import java.util.List;

import com.ruoyi.addrgen.service.IAddrgenSeedfileDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetectMapper;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetect;

/**
 * 种子文件存活性探测历史Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@Service
public class AddrgenSeedfileDetectServiceImpl implements IAddrgenSeedfileDetectService
{
    @Autowired
    private AddrgenSeedfileDetectMapper addrgenSeedfileDetectMapper;

    /**
     * 查询种子文件存活性探测历史
     * 
     * @param detectRecordId 种子文件存活性探测历史主键
     * @return 种子文件存活性探测历史
     */
    @Override
    public AddrgenSeedfileDetect selectAddrgenSeedfileDetectByDetectRecordId(Long detectRecordId)
    {
        return addrgenSeedfileDetectMapper.selectAddrgenSeedfileDetectByDetectRecordId(detectRecordId);
    }

    /**
     * 查询种子文件存活性探测历史列表
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 种子文件存活性探测历史
     */
    @Override
    public List<AddrgenSeedfileDetect> selectAddrgenSeedfileDetectList(AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        return addrgenSeedfileDetectMapper.selectAddrgenSeedfileDetectList(addrgenSeedfileDetect);
    }

    /**
     * 新增种子文件存活性探测历史
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 结果
     */
    @Override
    public int insertAddrgenSeedfileDetect(AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        return addrgenSeedfileDetectMapper.insertAddrgenSeedfileDetect(addrgenSeedfileDetect);
    }

    /**
     * 修改种子文件存活性探测历史
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 结果
     */
    @Override
    public int updateAddrgenSeedfileDetect(AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        return addrgenSeedfileDetectMapper.updateAddrgenSeedfileDetect(addrgenSeedfileDetect);
    }
    @Override
    public int updateAddrgenSeedfileDetectByFileId(AddrgenSeedfileDetect addrgenSeedfileDetect){
        return addrgenSeedfileDetectMapper.updateAddrgenSeedfileDetectByFileId(addrgenSeedfileDetect);
    }

    /**
     * 批量删除种子文件存活性探测历史
     * 
     * @param detectRecordIds 需要删除的种子文件存活性探测历史主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileDetectByDetectRecordIds(Long[] detectRecordIds)
    {
        return addrgenSeedfileDetectMapper.deleteAddrgenSeedfileDetectByDetectRecordIds(detectRecordIds);
    }

    /**
     * 删除种子文件存活性探测历史信息
     * 
     * @param detectRecordId 种子文件存活性探测历史主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileDetectByDetectRecordId(Long detectRecordId)
    {
        return addrgenSeedfileDetectMapper.deleteAddrgenSeedfileDetectByDetectRecordId(detectRecordId);
    }
}
