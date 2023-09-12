package com.ruoyi.vuln.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.vuln.commandobserver.CnnvdExecutorObserver;
import com.ruoyi.vuln.commandsubject.CnnvdExecutorSubject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.vuln.domain.VulnCnnvd;
import com.ruoyi.vuln.service.IVulnCnnvdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 漏洞特征Controller
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/vuln/cnnvd")
public class VulnCnnvdController extends BaseController implements CnnvdExecutorObserver
{
    private String rate;
    @Autowired
    private IVulnCnnvdService vulnCnnvdService;

    @Autowired
    private CnnvdExecutorSubject subject;

    @PostConstruct
    public void init() throws IOException {
        subject.addObserver(this);
    }
    /**
     * 查询漏洞特征列表
     */
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:list')")
    @GetMapping("/list")
    public TableDataInfo list(VulnCnnvd vulnCnnvd)
    {
        startPage();
        List<VulnCnnvd> list = vulnCnnvdService.selectVulnCnnvdList(vulnCnnvd);
        int count=list.size();
        return getDataTable(list);
    }


    /**
     * 导出漏洞特征列表
     */
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:export')")
    @Log(title = "漏洞特征", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VulnCnnvd vulnCnnvd)
    {
        List<VulnCnnvd> list = vulnCnnvdService.selectVulnCnnvdList(vulnCnnvd);
        ExcelUtil<VulnCnnvd> util = new ExcelUtil<VulnCnnvd>(VulnCnnvd.class);
        util.exportExcel(response, list, "漏洞特征数据");
    }

    /**
     * 获取漏洞特征详细信息
     */
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:query')")
    @GetMapping(value = "/{cnnvdId}")
    public AjaxResult getInfo(@PathVariable("cnnvdId") Long cnnvdId)
    {
        return AjaxResult.success(vulnCnnvdService.selectVulnCnnvdByCnnvdId(cnnvdId));
    }

    /**
     * 新增漏洞特征
     */

    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:add')")
    @Log(title = "漏洞特征", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VulnCnnvd vulnCnnvd)
    {
        return toAjax(vulnCnnvdService.insertVulnCnnvd(vulnCnnvd));
    }

    /**
     * 修改漏洞特征
     */
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:edit')")
    @Log(title = "漏洞特征", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VulnCnnvd vulnCnnvd)
    {
        return toAjax(vulnCnnvdService.updateVulnCnnvd(vulnCnnvd));
    }

    /**
     * 删除漏洞特征
     */
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:remove')")
    @Log(title = "漏洞特征", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cnnvdIds}")
    public AjaxResult remove(@PathVariable Long[] cnnvdIds)
    {
        return toAjax(vulnCnnvdService.deleteVulnCnnvdByCnnvdIds(cnnvdIds));
    }

    /*上传文件*/
    @PreAuthorize("@ss.hasPermi('vuln:cnnvd:importFile')")
    @Log(title = "导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importFile")
    public AjaxResult importFile(MultipartFile file, @RequestParam String addrSeedfileName, @RequestParam String addrSeedfileSize) throws Exception
    {


        String s = vulnCnnvdService.dealVulnCnnvd(file, addrSeedfileName, addrSeedfileSize);

        return AjaxResult.success(s);
    }
    @GetMapping("/backRate")
    public AjaxResult backRate(){
        return AjaxResult.success(rate);
    }

    @Override
    public void update(String rate) {
        this.rate=rate;
    }
}

