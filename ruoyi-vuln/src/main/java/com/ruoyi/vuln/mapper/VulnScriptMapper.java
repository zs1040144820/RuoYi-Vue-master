package com.ruoyi.vuln.mapper;

import com.ruoyi.vuln.domain.VulnScript;

import java.util.List;

/**
 * 脚本Mapper接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-21
 */
public interface VulnScriptMapper 
{
    /**
     * 查询脚本
     * 
     * @param scriptId 脚本主键
     * @return 脚本
     */
    public VulnScript selectVulnScriptByScriptId(Long scriptId);


    /**
     * 查询脚本列表
     * 
     * @param vulnScript 脚本
     * @return 脚本集合
     */
    public List<VulnScript> selectVulnScriptList(VulnScript vulnScript);

    /**
     * 新增脚本
     * 
     * @param vulnScript 脚本
     * @return 结果
     */
    public int insertVulnScript(VulnScript vulnScript);

    /**
     * 修改脚本
     * 
     * @param vulnScript 脚本
     * @return 结果
     */
    public int updateVulnScript(VulnScript vulnScript);

    /**
     * 删除脚本
     * 
     * @param scriptId 脚本主键
     * @return 结果
     */
    public int deleteVulnScriptByScriptId(Long scriptId);

    /**
     * 批量删除脚本
     * 
     * @param scriptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVulnScriptByScriptIds(Long[] scriptIds);

    public VulnScript selectGradeByCve(String cve);
}
