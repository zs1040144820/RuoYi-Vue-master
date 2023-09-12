package com.ruoyi.vuln.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import com.ruoyi.vuln.domain.VulnCnnvd;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;

/**
 * 漏洞特征Service接口
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-12
 */
public interface IVulnCnnvdService 
{

    /**
     * 查询漏洞特征
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 漏洞特征
     */
    public VulnCnnvd selectVulnCnnvdByCnnvdId(Long cnnvdId);

    /**
     * 查询漏洞特征列表
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 漏洞特征集合
     */
    public List<VulnCnnvd> selectVulnCnnvdList(VulnCnnvd vulnCnnvd);

    /**
     * 新增漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    public int insertVulnCnnvd(VulnCnnvd vulnCnnvd);

    /**
     * 修改漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    public int updateVulnCnnvd(VulnCnnvd vulnCnnvd);

    /**
     * 批量删除漏洞特征
     * 
     * @param cnnvdIds 需要删除的漏洞特征主键集合
     * @return 结果
     */
    public int deleteVulnCnnvdByCnnvdIds(Long[] cnnvdIds);

    /**
     * 删除漏洞特征信息
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 结果
     */
    public int deleteVulnCnnvdByCnnvdId(Long cnnvdId);

    /*处理文件*/
    public String dealVulnCnnvd(MultipartFile file, String name, String size) throws IOException, JAXBException, ParseException;
}
