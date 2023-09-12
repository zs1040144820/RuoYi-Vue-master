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
import com.ruoyi.addrgen.domain.AddrgenRecordDetect;
import com.ruoyi.addrgen.service.IAddrgenRecordDetectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 探测历史Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@RestController
@RequestMapping("/genedIP/detect")
public class AddrgenRecordDetectController extends BaseController
{
    @Autowired
    private IAddrgenRecordDetectService addrgenRecordDetectService;

    /**
     * 查询探测历史列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenRecordDetect addrgenRecordDetect)
    {
        startPage();
        List<AddrgenRecordDetect> list = addrgenRecordDetectService.selectAddrgenRecordDetectList(addrgenRecordDetect);
        return getDataTable(list);
    }

    /**
     * 导出探测历史列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:export')")
    @Log(title = "探测历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenRecordDetect addrgenRecordDetect)
    {
        List<AddrgenRecordDetect> list = addrgenRecordDetectService.selectAddrgenRecordDetectList(addrgenRecordDetect);
        ExcelUtil<AddrgenRecordDetect> util = new ExcelUtil<AddrgenRecordDetect>(AddrgenRecordDetect.class);
        util.exportExcel(response, list, "探测历史数据");
    }

    /**
     * 获取探测历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:query')")
    @GetMapping(value = "/{detectId}")
    public AjaxResult getInfo(@PathVariable("detectId") Long detectId)
    {
        return AjaxResult.success(addrgenRecordDetectService.selectAddrgenRecordDetectByDetectId(detectId));
    }

    /**
     * 新增探测历史
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:add')")
    @Log(title = "探测历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenRecordDetect addrgenRecordDetect)
    {
        return toAjax(addrgenRecordDetectService.insertAddrgenRecordDetect(addrgenRecordDetect));
    }

    /**
     * 修改探测历史
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:edit')")
    @Log(title = "探测历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenRecordDetect addrgenRecordDetect)
    {
        return toAjax(addrgenRecordDetectService.updateAddrgenRecordDetect(addrgenRecordDetect));
    }

    /**
     * 删除探测历史
     */
    @PreAuthorize("@ss.hasPermi('genedIP:detect:remove')")
    @Log(title = "探测历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detectIds}")
    public AjaxResult remove(@PathVariable Long[] detectIds)
    {
        return toAjax(addrgenRecordDetectService.deleteAddrgenRecordDetectByDetectIds(detectIds));
    }
}
