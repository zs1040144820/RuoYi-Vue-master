package com.ruoyi.addrgen.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.addrgen.domain.AddrGen;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;
import com.ruoyi.addrgen.service.IAddrgenSeedfileHandleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 种子地址文件总览Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/addrgen/handleseedfile")
public class AddrgenSeedfileHandleController extends BaseController
{
    @Autowired
    private IAddrgenSeedfileHandleService addrgenSeedfileHandleService;

    /**
     * 查询种子地址文件总览列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        startPage();
        List<AddrgenSeedfileHandle> list = addrgenSeedfileHandleService.selectAddrgenSeedfileHandleList(addrgenSeedfileHandle);
        return getDataTable(list);
    }

    /**
     * 导出种子地址文件总览列表
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:export')")
    @Log(title = "种子地址文件总览", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        List<AddrgenSeedfileHandle> list = addrgenSeedfileHandleService.selectAddrgenSeedfileHandleList(addrgenSeedfileHandle);
        ExcelUtil<AddrgenSeedfileHandle> util = new ExcelUtil<AddrgenSeedfileHandle>(AddrgenSeedfileHandle.class);
        util.exportExcel(response, list, "种子地址文件总览数据");
    }

    /**
     * 获取种子地址文件总览详细信息
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:query')")
    @GetMapping(value = "/{addrSeedfileId}")
    public AjaxResult getInfo(@PathVariable("addrSeedfileId") Long addrSeedfileId)
    {
        return AjaxResult.success(addrgenSeedfileHandleService.selectAddrgenSeedfileHandleByAddrSeedfileId(addrSeedfileId));
    }

    /**
     * 新增种子地址文件总览
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:add')")
    @Log(title = "种子地址文件总览", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        return toAjax(addrgenSeedfileHandleService.insertAddrgenSeedfileHandle(addrgenSeedfileHandle));
    }

    /**
     * 修改种子地址文件总览
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:edit')")
    @Log(title = "种子地址文件总览", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        return toAjax(addrgenSeedfileHandleService.updateAddrgenSeedfileHandle(addrgenSeedfileHandle));
    }

    /**
     * 修改种子地址文件总览但不会先删除
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:edit2')")
    @Log(title = "种子地址文件总览", businessType = BusinessType.UPDATE)
    @PutMapping("/up")
    public AjaxResult edit2(@RequestBody AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        return toAjax(addrgenSeedfileHandleService.updateAddrgenSeedfileHandle2(addrgenSeedfileHandle));
    }

    /**
     * 删除种子地址文件总览
     */
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:remove')")
    @Log(title = "种子地址文件总览", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addrSeedfileIds}")
    public AjaxResult remove(@PathVariable Long[] addrSeedfileIds)
    {
        return toAjax(addrgenSeedfileHandleService.deleteAddrgenSeedfileHandleByAddrSeedfileIds(addrSeedfileIds));
    }

    /*上传文件*/
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:importSeedFile')")
    @Log(title = "上传文件", businessType = BusinessType.IMPORT)
    @PostMapping("/importSeedFile")
    public AjaxResult importData(MultipartFile file, @RequestParam String addrSeedfileName, @RequestParam String addrSeedfileSize) throws Exception
    {
        System.out.println(addrSeedfileName);
        System.out.println(addrSeedfileSize);
        if (addrgenSeedfileHandleService.handleFile(file,addrSeedfileName,addrSeedfileSize)){
            return AjaxResult.success("上传文件成功");
        }else {
            return AjaxResult.success("上传失败或文件为空");
        }
    }
    //地址格式化
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:formatAddr')")
    @Log(title = "格式化IP地址", businessType = BusinessType.FORMATADDR)
    @PostMapping("/formatAddr")
    public AjaxResult formatAddr(@RequestBody AddrgenSeedfileHandle addrgenSeedfileHandle) throws InterruptedException, IOException {
        System.out.println(addrgenSeedfileHandle.getAddrSeedfileId());
        int i = addrgenSeedfileHandleService.formatAddr(addrgenSeedfileHandle);
        return AjaxResult.success(i);
    }

    //地址扩展
    @PreAuthorize("@ss.hasPermi('addrgen:handleseedfile:genAddr')")
    @Log(title = "IPv6地址扩展", businessType = BusinessType.GENADDR)
    @PostMapping("/genAddr")
    public AjaxResult genAddr(@RequestBody AddrGen addrGen) throws InterruptedException {
        System.out.println(Long.valueOf(addrGen.getFileID()));
        System.out.println(addrGen.getIsmul());
        System.out.println(addrGen.isActivity());
        System.out.println(addrGen.isStability());
        System.out.println(addrGen.getGenNum());
        addrgenSeedfileHandleService.getFileByID(addrGen);
        return AjaxResult.success("");
    }
}
