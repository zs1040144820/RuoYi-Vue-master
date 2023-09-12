package com.ruoyi.addrgen.mapper;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenRecord;
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;

/**
 * 生成记录Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public interface AddrgenRecordMapper 
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
     * 删除生成记录
     * 
     * @param recordId 生成记录主键
     * @return 结果
     */
    public int deleteAddrgenRecordByRecordId(Long recordId);

    /**
     * 批量删除生成记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenRecordByRecordIds(Long[] recordIds);

    /**
     * 批量删除生成文件详情
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenRecordDetailsByRecordIds(Long[] recordIds);
    
    /**
     * 批量新增生成文件详情
     * 
     * @param addrgenRecordDetailsList 生成文件详情列表
     * @return 结果
     */
    public int batchAddrgenRecordDetails(List<AddrgenRecordDetails> addrgenRecordDetailsList);
    

    /**
     * 通过生成记录主键删除生成文件详情信息
     * 
     * @param recordId 生成记录ID
     * @return 结果
     */
    public int deleteAddrgenRecordDetailsByRecordId(Long recordId);
}
