package com.ruoyi.common.enums;

/**
 * 业务操作类型
 * 
 * @author ruoyi
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空数据
     */
    CLEAN,

    /**
     * 漏洞扫描
     */
    SCANVULN,

    /**
     * 停止漏洞扫描
     */
    STOPVULN,

    /**
     * 恢复任务
     */
    REEXEC,

    /**
     * 地址格式化
     */
    FORMATADDR,

    /**
     * 地址扩展
     */
    GENADDR,

    /**
     * 口令破解
     */
    PWDCRACK,

    /**
     * 存活性探测
     */
    ALIVEDETECT,

    /**
     * 别名区训练
     */
    ALIASDETECT,
    /**
     * 出口流量收集
     */
    COLLECT1,
    /**
     * 已有资产收集
     */
    COLLECT2

}
