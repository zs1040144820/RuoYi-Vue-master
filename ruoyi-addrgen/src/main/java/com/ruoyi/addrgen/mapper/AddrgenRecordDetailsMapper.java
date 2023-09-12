package com.ruoyi.addrgen.mapper;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;

/**
 * 生成文件详情Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public interface AddrgenRecordDetailsMapper 
{
    /**
     * 查询生成文件详情
     * 
     * @param detailsId 生成文件详情主键
     * @return 生成文件详情
     */
    public AddrgenRecordDetails selectAddrgenRecordDetailsByDetailsId(Long detailsId);

    /**
     * 查询生成文件详情列表
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 生成文件详情集合
     */
    public List<AddrgenRecordDetails> selectAddrgenRecordDetailsList(AddrgenRecordDetails addrgenRecordDetails);

    /**
     * 新增生成文件详情
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 结果
     */
    public int insertAddrgenRecordDetails(AddrgenRecordDetails addrgenRecordDetails);

    /**
     * 修改生成文件详情
     * 
     * @param addrgenRecordDetails 生成文件详情
     * @return 结果
     */
    public int updateAddrgenRecordDetails(AddrgenRecordDetails addrgenRecordDetails);

    /**
     * 删除生成文件详情
     * 
     * @param detailsId 生成文件详情主键
     * @return 结果
     */
    public int deleteAddrgenRecordDetailsByDetailsId(Long detailsId);

    /**
     * 批量删除生成文件详情
     * 
     * @param detailsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenRecordDetailsByDetailsIds(Long[] detailsIds);

    public List<AddrgenRecordDetails> selectdetailsByRecordId(Long recordId);
}
