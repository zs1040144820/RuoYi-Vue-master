package com.ruoyi.addrgen.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenRecordDetectMapper;
import com.ruoyi.addrgen.domain.AddrgenRecordDetect;
import com.ruoyi.addrgen.service.IAddrgenRecordDetectService;

/**
 * 探测历史Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@Service
public class AddrgenRecordDetectServiceImpl implements IAddrgenRecordDetectService 
{
    @Autowired
    private AddrgenRecordDetectMapper addrgenRecordDetectMapper;

    /**
     * 查询探测历史
     * 
     * @param detectId 探测历史主键
     * @return 探测历史
     */
    @Override
    public AddrgenRecordDetect selectAddrgenRecordDetectByDetectId(Long detectId)
    {
        return addrgenRecordDetectMapper.selectAddrgenRecordDetectByDetectId(detectId);
    }

    /**
     * 查询探测历史列表
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 探测历史
     */
    @Override
    public List<AddrgenRecordDetect> selectAddrgenRecordDetectList(AddrgenRecordDetect addrgenRecordDetect)
    {
        return addrgenRecordDetectMapper.selectAddrgenRecordDetectList(addrgenRecordDetect);
    }

    /**
     * 新增探测历史
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 结果
     */
    @Override
    public int insertAddrgenRecordDetect(AddrgenRecordDetect addrgenRecordDetect)
    {
        return addrgenRecordDetectMapper.insertAddrgenRecordDetect(addrgenRecordDetect);
    }

    /**
     * 修改探测历史
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 结果
     */
    @Override
    public int updateAddrgenRecordDetect(AddrgenRecordDetect addrgenRecordDetect)
    {
        return addrgenRecordDetectMapper.updateAddrgenRecordDetect(addrgenRecordDetect);
    }

    /**
     * 批量删除探测历史
     * 
     * @param detectIds 需要删除的探测历史主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenRecordDetectByDetectIds(Long[] detectIds)
    {
        return addrgenRecordDetectMapper.deleteAddrgenRecordDetectByDetectIds(detectIds);
    }

    /**
     * 删除探测历史信息
     * 
     * @param detectId 探测历史主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenRecordDetectByDetectId(Long detectId)
    {
        return addrgenRecordDetectMapper.deleteAddrgenRecordDetectByDetectId(detectId);
    }
}
