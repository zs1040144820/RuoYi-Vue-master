package com.ruoyi.addrgen.service;

import java.io.IOException;
import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenRecord;

/**
 * 生成记录Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public interface IAddrgenRecordService 
{
    /**
     * 查询生成记录
     * 
     * @param recordId 生成记录主键
     * @return 生成记录
     */
    public AddrgenRecord selectAddrgenRecordByRecordId(Long recordId);

    /**
     * 查询生成记录列表
     * 
     * @param addrgenRecord 生成记录
     * @return 生成记录集合
     */
    public List<AddrgenRecord> selectAddrgenRecordList(AddrgenRecord addrgenRecord);

    /**
     * 新增生成记录
     * 
     * @param addrgenRecord 生成记录
     * @return 结果
     */
    public int insertAddrgenRecord(AddrgenRecord addrgenRecord);

    /**
     * 修改生成记录
     * 
     * @param addrgenRecord 生成记录
     * @return 结果
     */
    public int updateAddrgenRecord(AddrgenRecord addrgenRecord);

    /**
     * 批量删除生成记录
     * 
     * @param recordIds 需要删除的生成记录主键集合
     * @return 结果
     */
    public int deleteAddrgenRecordByRecordIds(Long[] recordIds);

    /**
     * 删除生成记录信息
     * 
     * @param recordId 生成记录主键
     * @return 结果
     */
    public int deleteAddrgenRecordByRecordId(Long recordId);

    /*
    * 探测存活性
    * */
    public void detectIP(Long recordId) throws IOException;

    /*
    * 别名区训练
    * */
    public String aliasDetect(Long recordId) throws IOException;
}
