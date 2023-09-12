package com.ruoyi.pwdcrack.service;

import java.util.List;
import com.ruoyi.pwdcrack.domain.PwdcrackRecord;

/**
 * 口令破解Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-20
 */
public interface IPwdcrackRecordService 
{
    /**
     * 查询口令破解
     * 
     * @param crackId 口令破解主键
     * @return 口令破解
     */
    public PwdcrackRecord selectPwdcrackRecordByCrackId(Long crackId);

    /**
     * 查询口令破解列表
     * 
     * @param pwdcrackRecord 口令破解
     * @return 口令破解集合
     */
    public List<PwdcrackRecord> selectPwdcrackRecordList(PwdcrackRecord pwdcrackRecord);

    /**
     * 新增口令破解
     * 
     * @param pwdcrackRecord 口令破解
     * @return 结果
     */
    public int insertPwdcrackRecord(PwdcrackRecord pwdcrackRecord);

    /**
     * 修改口令破解
     * 
     * @param pwdcrackRecord 口令破解
     * @return 结果
     */
    public int updatePwdcrackRecord(PwdcrackRecord pwdcrackRecord);

    /**
     * 批量删除口令破解
     * 
     * @param crackIds 需要删除的口令破解主键集合
     * @return 结果
     */
    public int deletePwdcrackRecordByCrackIds(Long[] crackIds);

    /**
     * 删除口令破解信息
     * 
     * @param crackId 口令破解主键
     * @return 结果
     */
    public int deletePwdcrackRecordByCrackId(Long crackId);
}
