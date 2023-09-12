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
import com.ruoyi.addrgen.domain.AddrgenSeedfileFormat;
import com.ruoyi.addrgen.service.IAddrgenSeedfileFormatService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 种子文件格式化记录Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@RestController
@RequestMapping("/addrgen/format")
public class AddrgenSeedfileFormatController extends BaseController
{
    @Autowired
    private IAddrgenSeedfileFormatService addrgenSeedfileFormatService;

    /**
     * 查询种子文件格式化记录列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        startPage();
        List<AddrgenSeedfileFormat> list = addrgenSeedfileFormatService.selectAddrgenSeedfileFormatList(addrgenSeedfileFormat);
        return getDataTable(list);
    }

    /**
     * 导出种子文件格式化记录列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:export')")
    @Log(title = "种子文件格式化记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        List<AddrgenSeedfileFormat> list = addrgenSeedfileFormatService.selectAddrgenSeedfileFormatList(addrgenSeedfileFormat);
        ExcelUtil<AddrgenSeedfileFormat> util = new ExcelUtil<AddrgenSeedfileFormat>(AddrgenSeedfileFormat.class);
        util.exportExcel(response, list, "种子文件格式化记录数据");
    }

    /**
     * 获取种子文件格式化记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:query')")
    @GetMapping(value = "/{formatRecordId}")
    public AjaxResult getInfo(@PathVariable("formatRecordId") Long formatRecordId)
    {
        return AjaxResult.success(addrgenSeedfileFormatService.selectAddrgenSeedfileFormatByFormatRecordId(formatRecordId));
    }

    /**
     * 新增种子文件格式化记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:add')")
    @Log(title = "种子文件格式化记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        return toAjax(addrgenSeedfileFormatService.insertAddrgenSeedfileFormat(addrgenSeedfileFormat));
    }

    /**
     * 修改种子文件格式化记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:edit')")
    @Log(title = "种子文件格式化记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenSeedfileFormat addrgenSeedfileFormat)
    {
        return toAjax(addrgenSeedfileFormatService.updateAddrgenSeedfileFormat(addrgenSeedfileFormat));
    }

    /**
     * 删除种子文件格式化记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:format:remove')")
    @Log(title = "种子文件格式化记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{formatRecordIds}")
    public AjaxResult remove(@PathVariable Long[] formatRecordIds)
    {
        return toAjax(addrgenSeedfileFormatService.deleteAddrgenSeedfileFormatByFormatRecordIds(formatRecordIds));
    }
}
