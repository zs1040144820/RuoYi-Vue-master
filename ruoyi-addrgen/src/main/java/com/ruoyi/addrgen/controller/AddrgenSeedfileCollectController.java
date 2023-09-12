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
import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;
import com.ruoyi.addrgen.service.IAddrgenSeedfileCollectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 种子文件采集历史记录Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@RestController
@RequestMapping("/addrgen/collect")
public class AddrgenSeedfileCollectController extends BaseController
{
    @Autowired
    private IAddrgenSeedfileCollectService addrgenSeedfileCollectService;

    /**
     * 查询种子文件采集历史记录列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        startPage();
        List<AddrgenSeedfileCollect> list = addrgenSeedfileCollectService.selectAddrgenSeedfileCollectList(addrgenSeedfileCollect);
        return getDataTable(list);
    }

    /**
     * 导出种子文件采集历史记录列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:export')")
    @Log(title = "种子文件采集历史记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        List<AddrgenSeedfileCollect> list = addrgenSeedfileCollectService.selectAddrgenSeedfileCollectList(addrgenSeedfileCollect);
        ExcelUtil<AddrgenSeedfileCollect> util = new ExcelUtil<AddrgenSeedfileCollect>(AddrgenSeedfileCollect.class);
        util.exportExcel(response, list, "种子文件采集历史记录数据");
    }

    /**
     * 获取种子文件采集历史记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:query')")
    @GetMapping(value = "/{collectRecordId}")
    public AjaxResult getInfo(@PathVariable("collectRecordId") Long collectRecordId)
    {
        return AjaxResult.success(addrgenSeedfileCollectService.selectAddrgenSeedfileCollectByCollectRecordId(collectRecordId));
    }

    /**
     * 新增种子文件采集历史记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:add')")
    @Log(title = "种子文件采集历史记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        return toAjax(addrgenSeedfileCollectService.insertAddrgenSeedfileCollect(addrgenSeedfileCollect));
    }

    /**
     * 修改种子文件采集历史记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:edit')")
    @Log(title = "种子文件采集历史记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenSeedfileCollect addrgenSeedfileCollect)
    {
        return toAjax(addrgenSeedfileCollectService.updateAddrgenSeedfileCollect(addrgenSeedfileCollect));
    }

    /**
     * 删除种子文件采集历史记录
     */
    @PreAuthorize("@ss.hasPermi('addrgen:collect:remove')")
    @Log(title = "种子文件采集历史记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{collectRecordIds}")
    public AjaxResult remove(@PathVariable Long[] collectRecordIds)
    {
        return toAjax(addrgenSeedfileCollectService.deleteAddrgenSeedfileCollectByCollectRecordIds(collectRecordIds));
    }
}
