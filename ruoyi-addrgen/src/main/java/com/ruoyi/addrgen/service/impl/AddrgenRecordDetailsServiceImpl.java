package com.ruoyi.addrgen.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenRecordDetailsMapper;
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;
import com.ruoyi.addrgen.service.IAddrgenRecordDetailsService;

/**
 * 生成文件详情Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@Service
public class AddrgenRecordDetailsServiceImpl implements IAddrgenRecordDetailsService 
{
    @Autowired
    private AddrgenRecordDetailsMapper addrgenRecordDetailsMapper;

    /**
     * 查询生成文件详情
     * 
     * @param detailsId 生成文件详情主键
     * @return 生成文件详情
     */
    @Override
    public AddrgenRecordDetails selectAddrgenRecordDetailsByDetailsId(Long detailsId)
    {
        return addrgenRecordDetailsMapper.selectAddrgenRecordDetailsByDetailsId(detailsId);
    }

    /**
     * 查询生成文件详情列表
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 生成文件详情
     */
    @Override
    public List<AddrgenRecordDetails> selectAddrgenRecordDetailsList(AddrgenRecordDetails addrgenRecordDetails)
    {
        return addrgenRecordDetailsMapper.selectAddrgenRecordDetailsList(addrgenRecordDetails);
    }

    /**
     * 新增生成文件详情
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 结果
     */
    @Override
    public int insertAddrgenRecordDetails(AddrgenRecordDetails addrgenRecordDetails)
    {
        return addrgenRecordDetailsMapper.insertAddrgenRecordDetails(addrgenRecordDetails);
    }

    /**
     * 修改生成文件详情
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 结果
     */
    @Override
    public int updateAddrgenRecordDetails(AddrgenRecordDetails addrgenRecordDetails)
    {
        return addrgenRecordDetailsMapper.updateAddrgenRecordDetails(addrgenRecordDetails);
    }

    /**
     * 批量删除生成文件详情
     * 
     * @param detailsIds 需要删除的生成文件详情主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenRecordDetailsByDetailsIds(Long[] detailsIds)
    {
        return addrgenRecordDetailsMapper.deleteAddrgenRecordDetailsByDetailsIds(detailsIds);
    }

    /**
     * 删除生成文件详情信息
     * 
     * @param detailsId 生成文件详情主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenRecordDetailsByDetailsId(Long detailsId)
    {
        return addrgenRecordDetailsMapper.deleteAddrgenRecordDetailsByDetailsId(detailsId);
    }
}
