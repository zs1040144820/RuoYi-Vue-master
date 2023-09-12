package com.ruoyi.addrgen.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.addrgen.domain.AddrgenSeedfileDetailsUp;
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
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.service.IAddrgenSeedfileDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 种子文件详情Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-17
 */
@RestController
@RequestMapping("/addrgen/details")
public class AddrgenSeedfileDetailsController extends BaseController
{
    @Autowired
    private IAddrgenSeedfileDetailsService addrgenSeedfileDetailsService;

    /**
     * 查询种子文件详情列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        startPage();
        List<AddrgenSeedfileDetails> list = addrgenSeedfileDetailsService.selectAddrgenSeedfileDetailsList(addrgenSeedfileDetails);
        return getDataTable(list);
    }

    /**
     * 导出种子文件详情列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:export')")
    @Log(title = "种子文件详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        List<AddrgenSeedfileDetails> list = addrgenSeedfileDetailsService.selectAddrgenSeedfileDetailsList(addrgenSeedfileDetails);
        ExcelUtil<AddrgenSeedfileDetails> util = new ExcelUtil<AddrgenSeedfileDetails>(AddrgenSeedfileDetails.class);
        util.exportExcel(response, list, "种子文件详情数据");
    }

    /**
     * 获取种子文件详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:query')")
    @GetMapping(value = "/{addrseedId}")
    public AjaxResult getInfo(@PathVariable("addrseedId") Long addrseedId)
    {
        return AjaxResult.success(addrgenSeedfileDetailsService.selectAddrgenSeedfileDetailsByAddrseedId(addrseedId));
    }

    /**
     * 新增种子文件详情
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:add')")
    @Log(title = "种子文件详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        return toAjax(addrgenSeedfileDetailsService.insertAddrgenSeedfileDetails(addrgenSeedfileDetails));
    }

    /**
     * 修改种子文件详情
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:edit')")
    @Log(title = "种子文件详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenSeedfileDetails addrgenSeedfileDetails)
    {
        return toAjax(addrgenSeedfileDetailsService.updateAddrgenSeedfileDetails(addrgenSeedfileDetails));
    }

    /**
     * 删除种子文件详情
     */
    @PreAuthorize("@ss.hasPermi('addrgen:details:remove')")
    @Log(title = "种子文件详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addrseedIds}")
    public AjaxResult remove(@PathVariable Long[] addrseedIds)
    {
        return toAjax(addrgenSeedfileDetailsService.deleteAddrgenSeedfileDetailsByAddrseedIds(addrseedIds));
    }

    //@PreAuthorize("@ss.hasPermi('addrgen:details:remove')")
    @PutMapping("/updateca")
    public void updateCategory(@RequestBody AddrgenSeedfileDetailsUp ardu){
        System.out.println("***********************  "+ardu.getSeedId()+"  *********************************  "+ardu.getCategory());
        addrgenSeedfileDetailsService.updateCategory(ardu);
    }
}
