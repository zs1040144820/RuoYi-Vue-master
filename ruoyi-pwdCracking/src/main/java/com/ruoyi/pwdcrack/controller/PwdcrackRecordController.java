package com.ruoyi.pwdcrack.controller;

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
import com.ruoyi.pwdcrack.domain.PwdcrackRecord;
import com.ruoyi.pwdcrack.service.IPwdcrackRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 口令破解Controller
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-20
 */
@RestController
@RequestMapping("/pwdcrack/record")
public class PwdcrackRecordController extends BaseController
{
    @Autowired
    private IPwdcrackRecordService pwdcrackRecordService;

    /**
     * 查询口令破解列表
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(PwdcrackRecord pwdcrackRecord)
    {
        startPage();
        List<PwdcrackRecord> list = pwdcrackRecordService.selectPwdcrackRecordList(pwdcrackRecord);
        return getDataTable(list);
    }

    /**
     * 导出口令破解列表
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:export')")
    @Log(title = "口令破解", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PwdcrackRecord pwdcrackRecord)
    {
        List<PwdcrackRecord> list = pwdcrackRecordService.selectPwdcrackRecordList(pwdcrackRecord);
        ExcelUtil<PwdcrackRecord> util = new ExcelUtil<PwdcrackRecord>(PwdcrackRecord.class);
        util.exportExcel(response, list, "口令破解数据");
    }

    /**
     * 获取口令破解详细信息
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:query')")
    @GetMapping(value = "/{crackId}")
    public AjaxResult getInfo(@PathVariable("crackId") Long crackId)
    {
        return AjaxResult.success(pwdcrackRecordService.selectPwdcrackRecordByCrackId(crackId));
    }

    /**
     * 新增口令破解
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:add')")
    @Log(title = "口令破解", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PwdcrackRecord pwdcrackRecord)
    {
        return toAjax(pwdcrackRecordService.insertPwdcrackRecord(pwdcrackRecord));
    }

    /**
     * 修改口令破解
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:edit')")
    @Log(title = "口令破解", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PwdcrackRecord pwdcrackRecord)
    {
        return toAjax(pwdcrackRecordService.updatePwdcrackRecord(pwdcrackRecord));
    }

    /**
     * 删除口令破解
     */
    @PreAuthorize("@ss.hasPermi('pwdcrack:record:remove')")
    @Log(title = "口令破解", businessType = BusinessType.DELETE)
	@DeleteMapping("/{crackIds}")
    public AjaxResult remove(@PathVariable Long[] crackIds)
    {
        return toAjax(pwdcrackRecordService.deletePwdcrackRecordByCrackIds(crackIds));
    }
}
