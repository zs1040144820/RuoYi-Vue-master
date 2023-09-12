package com.ruoyi.addrgen.mapper;

import java.util.List;
import com.ruoyi.addrgen.domain.AddrgenRecordDetect;

/**
 * 探测历史Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
public interface AddrgenRecordDetectMapper 
{
    /**
     * 查询探测历史
     * 
     * @param detectId 探测历史主键
     * @return 探测历史
     */
    public AddrgenRecordDetect selectAddrgenRecordDetectByDetectId(Long detectId);

    /**
     * 查询探测历史列表
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 探测历史集合
     */
    public List<AddrgenRecordDetect> selectAddrgenRecordDetectList(AddrgenRecordDetect addrgenRecordDetect);

    /**
     * 新增探测历史
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 结果
     */
    public int insertAddrgenRecordDetect(AddrgenRecordDetect addrgenRecordDetect);

    /**
     * 修改探测历史
     * 
     * @param addrgenRecordDetect 探测历史
     * @return 结果
     */
    public int updateAddrgenRecordDetect(AddrgenRecordDetect addrgenRecordDetect);

    /**
     * 删除探测历史
     * 
     * @param detectId 探测历史主键
     * @return 结果
     */
    public int deleteAddrgenRecordDetectByDetectId(Long detectId);

    /**
     * 批量删除探测历史
     * 
     * @param detectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddrgenRecordDetectByDetectIds(Long[] detectIds);
}
