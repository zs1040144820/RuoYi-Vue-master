package com.ruoyi.vuln.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.vuln.domain.VulnSingleAddrDetails;
import com.ruoyi.vuln.service.IVulnSingleAddrDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单条地址扫描结果Controller
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-05
 */
@RestController
@RequestMapping("/vuln/singleDetails")
public class VulnSingleAddrDetailsController extends BaseController
{
    @Autowired
    private IVulnSingleAddrDetailsService vulnSingleAddrDetailsService;

    /**
     * 查询单条地址扫描结果列表
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:list')")
    @GetMapping("/list")
    public TableDataInfo list(VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        startPage();
        List<VulnSingleAddrDetails> list = vulnSingleAddrDetailsService.selectVulnSingleAddrDetailsList(vulnSingleAddrDetails);
        return getDataTable(list);
    }

    /**
     * 导出单条地址扫描结果列表
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:export')")
    @Log(title = "单条地址扫描结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        List<VulnSingleAddrDetails> list = vulnSingleAddrDetailsService.selectVulnSingleAddrDetailsList(vulnSingleAddrDetails);
        ExcelUtil<VulnSingleAddrDetails> util = new ExcelUtil<VulnSingleAddrDetails>(VulnSingleAddrDetails.class);
        util.exportExcel(response, list, "单条地址扫描结果数据");
    }

    /**
     * 获取单条地址扫描结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:query')")
    @GetMapping(value = "/{ipaddrId}")
    public AjaxResult getInfo(@PathVariable("ipaddrId") Long ipaddrId)
    {
        return AjaxResult.success(vulnSingleAddrDetailsService.selectVulnSingleAddrDetailsByIpaddrId(ipaddrId));
    }

    /**
     * 新增单条地址扫描结果
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:add')")
    @Log(title = "单条地址扫描结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        return toAjax(vulnSingleAddrDetailsService.insertVulnSingleAddrDetails(vulnSingleAddrDetails));
    }

    /**
     * 修改单条地址扫描结果
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:edit')")
    @Log(title = "单条地址扫描结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VulnSingleAddrDetails vulnSingleAddrDetails)
    {
        return toAjax(vulnSingleAddrDetailsService.updateVulnSingleAddrDetails(vulnSingleAddrDetails));
    }

    /**
     * 删除单条地址扫描结果
     */
    @PreAuthorize("@ss.hasPermi('vuln:singleDetails:remove')")
    @Log(title = "单条地址扫描结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ipaddrIds}")
    public AjaxResult remove(@PathVariable Long[] ipaddrIds)
    {
        return toAjax(vulnSingleAddrDetailsService.deleteVulnSingleAddrDetailsByIpaddrIds(ipaddrIds));
    }
}
