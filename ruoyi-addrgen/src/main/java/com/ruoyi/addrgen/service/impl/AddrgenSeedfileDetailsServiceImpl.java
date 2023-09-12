package com.ruoyi.addrgen.service.impl;

import java.util.List;

import com.ruoyi.addrgen.domain.AddrgenSeedfileDetailsUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetailsMapper;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.service.IAddrgenSeedfileDetailsService;

/**
 * 种子文件详情Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@Service
public class AddrgenSeedfileDetailsServiceImpl implements IAddrgenSeedfileDetailsService 
{
    @Autowired
    private AddrgenSeedfileDetailsMapper addrgenSeedfileDetailsMapper;

    /**
     * 查询种子文件详情
     * 
     * @param addrseedId 种子文件详情主键
     * @return 种子文件详情
     */
    @Override
    public AddrgenSeedfileDetails selectAddrgenSeedfileDetailsByAddrseedId(Long addrseedId)
    {
        return addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsByAddrseedId(addrseedId);
    }

    /**
     * 查询种子文件详情列表
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 种子文件详情
     */
    @Override
    public List<AddrgenSeedfileDetails> selectAddrgenSeedfileDetailsList(AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        return addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsList(addrgenSeedfileDetails);
    }

    /**
     * 新增种子文件详情
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 结果
     */
    @Override
    public int insertAddrgenSeedfileDetails(AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        return addrgenSeedfileDetailsMapper.insertAddrgenSeedfileDetails(addrgenSeedfileDetails);
    }

    /**
     * 修改种子文件详情
     * 
     * @param addrgenSeedfileDetails 种子文件详情
     * @return 结果
     */
    @Override
    public int updateAddrgenSeedfileDetails(AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        return addrgenSeedfileDetailsMapper.updateAddrgenSeedfileDetails(addrgenSeedfileDetails);
    }

    /**
     * 批量删除种子文件详情
     * 
     * @param addrseedIds 需要删除的种子文件详情主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileDetailsByAddrseedIds(Long[] addrseedIds)
    {
        return addrgenSeedfileDetailsMapper.deleteAddrgenSeedfileDetailsByAddrseedIds(addrseedIds);
    }

    /**
     * 删除种子文件详情信息
     * 
     * @param addrseedId 种子文件详情主键
     * @return 结果
     */
    @Override
    public int deleteAddrgenSeedfileDetailsByAddrseedId(Long addrseedId)
    {
        return addrgenSeedfileDetailsMapper.deleteAddrgenSeedfileDetailsByAddrseedId(addrseedId);
    }

    @Override
    public void updateCategory(AddrgenSeedfileDetailsUp ardu) {
        AddrgenSeedfileDetails asd = addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsByAddrseedId(ardu.getSeedId());
        asd.setAddrseedCategory(ardu.getCategory());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&  "+ardu.getSeedId()+"  &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  "+ardu.getCategory());
        addrgenSeedfileDetailsMapper.updateAddrgenSeedfileDetails(asd);
    }
}
