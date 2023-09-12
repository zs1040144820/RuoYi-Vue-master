package com.ruoyi.addrgen.service;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetect;

/**
 * 种子文件存活性探测历史Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
public interface IAddrgenSeedfileDetectService 
{
    /**
     * 查询种子文件存活性探测历史
     * 
     * @param detectRecordId 种子文件存活性探测历史主键
     * @return 种子文件存活性探测历史
     */
    public AddrgenSeedfileDetect selectAddrgenSeedfileDetectByDetectRecordId(Long detectRecordId);

    /**
     * 查询种子文件存活性探测历史列表
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 种子文件存活性探测历史集合
     */
    public List<AddrgenSeedfileDetect> selectAddrgenSeedfileDetectList(AddrgenSeedfileDetect addrgenSeedfileDetect);

    /**
     * 新增种子文件存活性探测历史
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 结果
     */
    public int insertAddrgenSeedfileDetect(AddrgenSeedfileDetect addrgenSeedfileDetect);

    /**
     * 修改种子文件存活性探测历史
     * 
     * @param addrgenSeedfileDetect 种子文件存活性探测历史
     * @return 结果
     */
    public int updateAddrgenSeedfileDetect(AddrgenSeedfileDetect addrgenSeedfileDetect);
    public int updateAddrgenSeedfileDetectByFileId(AddrgenSeedfileDetect addrgenSeedfileDetect);
    /**
     * 批量删除种子文件存活性探测历史
     * 
     * @param detectRecordIds 需要删除的种子文件存活性探测历史主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetectByDetectRecordIds(Long[] detectRecordIds);

    /**
     * 删除种子文件存活性探测历史信息
     * 
     * @param detectRecordId 种子文件存活性探测历史主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileDetectByDetectRecordId(Long detectRecordId);
}
