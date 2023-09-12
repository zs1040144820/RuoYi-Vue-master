package com.ruoyi.addrgen.controller;

import java.io.IOException;
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
import com.ruoyi.addrgen.domain.AddrgenRecord;
import com.ruoyi.addrgen.service.IAddrgenRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生成记录Controller
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@RestController
@RequestMapping("/genedIP/record")
public class AddrgenRecordController extends BaseController
{
    @Autowired
    private IAddrgenRecordService addrgenRecordService;

    /**
     * 查询生成记录列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddrgenRecord addrgenRecord)
    {
        startPage();
        List<AddrgenRecord> list = addrgenRecordService.selectAddrgenRecordList(addrgenRecord);
        return getDataTable(list);
    }

    /**
     * 导出生成记录列表
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:export')")
    @Log(title = "生成记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddrgenRecord addrgenRecord)
    {
        List<AddrgenRecord> list = addrgenRecordService.selectAddrgenRecordList(addrgenRecord);
        ExcelUtil<AddrgenRecord> util = new ExcelUtil<AddrgenRecord>(AddrgenRecord.class);
        util.exportExcel(response, list, "生成记录数据");
    }

    /**
     * 获取生成记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(addrgenRecordService.selectAddrgenRecordByRecordId(recordId));
    }

    /**
     * 新增生成记录
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:add')")
    @Log(title = "生成记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddrgenRecord addrgenRecord)
    {
        return toAjax(addrgenRecordService.insertAddrgenRecord(addrgenRecord));
    }

    /**
     * 修改生成记录
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:edit')")
    @Log(title = "生成记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddrgenRecord addrgenRecord)
    {
        return toAjax(addrgenRecordService.updateAddrgenRecord(addrgenRecord));
    }

    /**
     * 删除生成记录
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:remove')")
    @Log(title = "生成记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(addrgenRecordService.deleteAddrgenRecordByRecordIds(recordIds));
    }

    /**
     * 存活性探测
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:detectisActive')")
    @Log(title = "生成地址探测", businessType = BusinessType.ALIVEDETECT)
    @GetMapping("/detect/{recordId}")
    public AjaxResult detectisActive(@PathVariable Long recordId) throws IOException {
        System.out.println(recordId);
        addrgenRecordService.detectIP(recordId);
        return AjaxResult.success("111");
    }
    /**
     * 别名区训练
     */
    @PreAuthorize("@ss.hasPermi('genedIP:record:aliasDetect')")
    @Log(title = "别名区训练", businessType = BusinessType.ALIASDETECT)
    @GetMapping("/aliasDetect/{recordId}")
    public AjaxResult aliasDetect(@PathVariable Long recordId) throws IOException {
        System.out.println(recordId);
        addrgenRecordService.aliasDetect(recordId);
        return AjaxResult.success("111");
    }
}
