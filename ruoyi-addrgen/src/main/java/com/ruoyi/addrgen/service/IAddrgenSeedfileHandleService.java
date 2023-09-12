package com.ruoyi.addrgen.service;

import java.io.IOException;
import java.util.List;

import com.ruoyi.addrgen.domain.AddrGen;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;
import org.springframework.web.multipart.MultipartFile;

/**
 * 种子地址文件总览Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
public interface IAddrgenSeedfileHandleService 
{
    /**
     * 查询种子地址文件总览
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 种子地址文件总览
     */
    public AddrgenSeedfileHandle selectAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId);

    /**
     * 查询种子地址文件总览列表
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 种子地址文件总览集合
     */
    public List<AddrgenSeedfileHandle> selectAddrgenSeedfileHandleList(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 新增种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    public int insertAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 修改种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    public int updateAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 修改种子地址文件总览但是不会先删除
     *
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    public int updateAddrgenSeedfileHandle2(AddrgenSeedfileHandle addrgenSeedfileHandle);

    /**
     * 批量删除种子地址文件总览
     * 
     * @param addrSeedfileIds 需要删除的种子地址文件总览主键集合
     * @return 结果
     */
    public int deleteAddrgenSeedfileHandleByAddrSeedfileIds(Long[] addrSeedfileIds);

    /**
     * 删除种子地址文件总览信息
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 结果
     */
    public int deleteAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId);

    public boolean handleFile(MultipartFile file,String name,String size) throws IOException;

    public int formatAddr(AddrgenSeedfileHandle addrgenSeedfileHandle) throws InterruptedException, IOException;

    public void getFileByID(AddrGen addrGen);
}
