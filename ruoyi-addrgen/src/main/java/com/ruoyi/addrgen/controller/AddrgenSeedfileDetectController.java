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
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetect;
import com.ruoyi.addrgen.service.IAddrgenSeedfileDetectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 种子文件存活性探测历史Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@RestController
@RequestMapping("/addrgen/detect")
public class AddrgenSeedfileDetectController extends BaseController
{
    @Autowired
    private IAddrgenSeedfileDetectService addrgenSeedfileDetectService;

    /**
     * 查询种子文件存活性探测历史列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        startPage();
        List<AddrgenSeedfileDetect> list = addrgenSeedfileDetectService.selectAddrgenSeedfileDetectList(addrgenSeedfileDetect);
        return getDataTable(list);
    }

    /**
     * 导出种子文件存活性探测历史列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:export')")
    @Log(title = "种子文件存活性探测历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        List<AddrgenSeedfileDetect> list = addrgenSeedfileDetectService.selectAddrgenSeedfileDetectList(addrgenSeedfileDetect);
        ExcelUtil<AddrgenSeedfileDetect> util = new ExcelUtil<AddrgenSeedfileDetect>(AddrgenSeedfileDetect.class);
        util.exportExcel(response, list, "种子文件存活性探测历史数据");
    }

    /**
     * 获取种子文件存活性探测历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:query')")
    @GetMapping(value = "/{detectRecordId}")
    public AjaxResult getInfo(@PathVariable("detectRecordId") Long detectRecordId)
    {
        return AjaxResult.success(addrgenSeedfileDetectService.selectAddrgenSeedfileDetectByDetectRecordId(detectRecordId));
    }

    /**
     * 新增种子文件存活性探测历史
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:add')")
    @Log(title = "种子文件存活性探测历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        return toAjax(addrgenSeedfileDetectService.insertAddrgenSeedfileDetect(addrgenSeedfileDetect));
    }

    /**
     * 修改种子文件存活性探测历史
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:edit')")
    @Log(title = "种子文件存活性探测历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenSeedfileDetect addrgenSeedfileDetect)
    {
        return toAjax(addrgenSeedfileDetectService.updateAddrgenSeedfileDetect(addrgenSeedfileDetect));
    }

    /**
     * 删除种子文件存活性探测历史
     */
    @PreAuthorize("@ss.hasPermi('addrgen:detect:remove')")
    @Log(title = "种子文件存活性探测历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detectRecordIds}")
    public AjaxResult remove(@PathVariable Long[] detectRecordIds)
    {
        return toAjax(addrgenSeedfileDetectService.deleteAddrgenSeedfileDetectByDetectRecordIds(detectRecordIds));
    }
}
