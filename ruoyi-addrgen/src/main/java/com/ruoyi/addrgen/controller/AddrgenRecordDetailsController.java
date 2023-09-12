package com.ruoyi.addrgen.controller;

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
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;
import com.ruoyi.addrgen.service.IAddrgenRecordDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生成文件详情Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@RestController
@RequestMapping("/genedIP/details")
public class AddrgenRecordDetailsController extends BaseController
{
    @Autowired
    private IAddrgenRecordDetailsService addrgenRecordDetailsService;

    /**
     * 查询生成文件详情列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenRecordDetails addrgenRecordDetails)
    {
        startPage();
        List<AddrgenRecordDetails> list = addrgenRecordDetailsService.selectAddrgenRecordDetailsList(addrgenRecordDetails);
        return getDataTable(list);
    }

    /**
     * 导出生成文件详情列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:export')")
    @Log(title = "生成文件详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenRecordDetails addrgenRecordDetails)
    {
        List<AddrgenRecordDetails> list = addrgenRecordDetailsService.selectAddrgenRecordDetailsList(addrgenRecordDetails);
        ExcelUtil<AddrgenRecordDetails> util = new ExcelUtil<AddrgenRecordDetails>(AddrgenRecordDetails.class);
        util.exportExcel(response, list, "生成文件详情数据");
    }

    /**
     * 获取生成文件详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:query')")
    @GetMapping(value = "/{detailsId}")
    public AjaxResult getInfo(@PathVariable("detailsId") Long detailsId)
    {
        return AjaxResult.success(addrgenRecordDetailsService.selectAddrgenRecordDetailsByDetailsId(detailsId));
    }

    /**
     * 新增生成文件详情
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:add')")
    @Log(title = "生成文件详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenRecordDetails addrgenRecordDetails)
    {
        return toAjax(addrgenRecordDetailsService.insertAddrgenRecordDetails(addrgenRecordDetails));
    }

    /**
     * 修改生成文件详情
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:edit')")
    @Log(title = "生成文件详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenRecordDetails addrgenRecordDetails)
    {
        return toAjax(addrgenRecordDetailsService.updateAddrgenRecordDetails(addrgenRecordDetails));
    }

    /**
     * 删除生成文件详情
     */
    @PreAuthorize("@ss.hasPermi('genedIP:details:remove')")
    @Log(title = "生成文件详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailsIds}")
    public AjaxResult remove(@PathVariable Long[] detailsIds)
    {
        return toAjax(addrgenRecordDetailsService.deleteAddrgenRecordDetailsByDetailsIds(detailsIds));
    }
}
