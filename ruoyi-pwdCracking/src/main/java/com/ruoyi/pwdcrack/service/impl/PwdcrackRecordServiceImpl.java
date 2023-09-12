package com.ruoyi.pwdcrack.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pwdcrack.mapper.PwdcrackRecordMapper;
import com.ruoyi.pwdcrack.domain.PwdcrackRecord;
import com.ruoyi.pwdcrack.service.IPwdcrackRecordService;

/**
 * 口令破解Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-20
 */
@Service
public class PwdcrackRecordServiceImpl implements IPwdcrackRecordService 
{
    @Autowired
    private PwdcrackRecordMapper pwdcrackRecordMapper;

    /**
     * 查询口令破解
     * 
     * @param crackId 口令破解主键
     * @return 口令破解
     */
    @Override
    public PwdcrackRecord selectPwdcrackRecordByCrackId(Long crackId)
    {
        return pwdcrackRecordMapper.selectPwdcrackRecordByCrackId(crackId);
    }

    /**
     * 查询口令破解列表
     * 
     * @param pwdcrackRecord 口令破解
     * @return 口令破解
     */
    @Override
    public List<PwdcrackRecord> selectPwdcrackRecordList(PwdcrackRecord pwdcrackRecord)
    {
        return pwdcrackRecordMapper.selectPwdcrackRecordList(pwdcrackRecord);
    }

    /**
     * 新增口令破解
     * 
     * @param pwdcrackRecord 口令破解
     * @return 结果
     */
    @Override
    public int insertPwdcrackRecord(PwdcrackRecord pwdcrackRecord)
    {
        return pwdcrackRecordMapper.insertPwdcrackRecord(pwdcrackRecord);
    }

    /**
     * 修改口令破解
     * 
     * @param pwdcrackRecord 口令破解
     * @return 结果
     */
    @Override
    public int updatePwdcrackRecord(PwdcrackRecord pwdcrackRecord)
    {
        return pwdcrackRecordMapper.updatePwdcrackRecord(pwdcrackRecord);
    }

    /**
     * 批量删除口令破解
     * 
     * @param crackIds 需要删除的口令破解主键
     * @return 结果
     */
    @Override
    public int deletePwdcrackRecordByCrackIds(Long[] crackIds)
    {
        return pwdcrackRecordMapper.deletePwdcrackRecordByCrackIds(crackIds);
    }

    /**
     * 删除口令破解信息
     * 
     * @param crackId 口令破解主键
     * @return 结果
     */
    @Override
    public int deletePwdcrackRecordByCrackId(Long crackId)
    {
        return pwdcrackRecordMapper.deletePwdcrackRecordByCrackId(crackId);
    }
}
