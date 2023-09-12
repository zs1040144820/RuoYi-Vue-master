package com.ruoyi.addrgen.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.addrgen.domain.*;
import com.ruoyi.addrgen.service.IAddrCollectService;
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
import com.ruoyi.addrgen.service.IAddrgenRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/CollectIP")
public class AddrCollectController extends BaseController {

    @Autowired
    private IAddrCollectService addrCollectService;

    @PreAuthorize("@ss.hasPermi('CollectIP:start')")
    @Log(title = "出口流量地址收集", businessType = BusinessType.COLLECT1)
    @PostMapping("/start")
    public void StartCollecting(@RequestBody AddrCollect addrcollect){
        addrCollectService.StartCollecting(addrcollect);
    }

    @PostMapping("/insertask")
    public void inserTask(@RequestBody AddrCollect addrcollect){
        addrCollectService.inserTask(addrcollect);
    }

    @PostMapping("/insertask2")
    public void inserTask2(@RequestBody AddrCollect addrcollect){
        addrCollectService.inserTask2(addrcollect);
    }

    @PreAuthorize("@ss.hasPermi('CollectIP:start2')")
    @Log(title = "已有资产地址收集", businessType = BusinessType.COLLECT2)
    @PostMapping("/start2")
    public void StartCollecting2(@RequestBody AddrCollect addrcollect){
        addrCollectService.StartCollecting2(addrcollect);
    }

    @GetMapping("/showlist")
    public TableDataInfo list(AddrCollect ac)
    {
        startPage();
        List<AddrCollect> list = addrCollectService.selectCollectList(ac);
        return getDataTable(list);
    }

    @GetMapping("/showlist1")
    public TableDataInfo list1(AddrCollect addrcollect)
    {
        System.out.println("[DB]AddrCollectController2: Searching all info1");
        List<AddrCollect> list = addrCollectService.selectCollectList1(addrcollect);
        return getDataTable(list);
    }

    @GetMapping("/showlist2")
    public TableDataInfo list2(AddrCollect addrcollect)
    {
        System.out.println("[DB]AddrCollectController2: Searching all info2");
        List<AddrCollect> list = addrCollectService.selectCollectList2(addrcollect);
        return getDataTable(list);
    }

    @GetMapping("/showtadelist")
    public TableDataInfo tadelist(AddrCollectTade act)
    {
        System.out.println("[DB]AddrCollectController: Searching tade info");
        startPage();
        List<AddrCollectTade> list = addrCollectService.selectTadeList(act);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('CollectIP:deltask')")
    @DeleteMapping("/deltask/{taskId}")
    public AjaxResult remove(@PathVariable String taskId)
    {
        return toAjax(addrCollectService.deleteTaskByUid(taskId));
    }

    @GetMapping("/showcmd/{uid}")
    public AjaxResult getCommand(@PathVariable("uid") String uid)
    {
        AjaxResult ar = AjaxResult.success(addrCollectService.getCommand(uid));
        return ar;
    }

    @GetMapping("/showcmd2/{uid}")
    public TableDataInfo getCommand2(@PathVariable("uid") String uid){
        List<AddrCollectDetail2> list = addrCollectService.getCommand2(uid);
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+list.size());
        return getDataTable(list);
    }

    @GetMapping("/showdetail/{uid}")
    public TableDataInfo getDetail(@PathVariable("uid") String uid)
    {
        List<String> acd = addrCollectService.getDetail(uid);
        return getDataTable(acd);
    }


    @GetMapping("/getcmd/{uid}")
    public AjaxResult getTempcom(@PathVariable("uid") String uid)
    {
        AjaxResult ar = AjaxResult.success(addrCollectService.getTempcom(uid));
        return ar;
    }

    @GetMapping("/showdetail2/{uid}")
    public TableDataInfo getDetail2(@PathVariable("uid") String uid)
    {
        List<String> acd = addrCollectService.getDetail2(uid);
        return getDataTable(acd);
    }

    @DeleteMapping("/deldetail/{uid}")
    public AjaxResult delDetail(@PathVariable("uid") String uid)
    {
        return toAjax(addrCollectService.deleteDetailByUid(uid));
    }

    @GetMapping("/showtask/{uid}")
    public AjaxResult showTask(@PathVariable("uid") String uid)
    {
        AjaxResult ar = AjaxResult.success(addrCollectService.showTask(uid));
        System.out.println(ar);
        return ar;
    }

    @Log(title = "种子地址文件总览", businessType = BusinessType.EXPORT)
    @PostMapping("/exportask")
    public void export(HttpServletResponse response, AddrCollect addrCollect)
    {
        List<AddrCollect> list = addrCollectService.selectCollectList(addrCollect);
        ExcelUtil<AddrCollect> util = new ExcelUtil<AddrCollect>(AddrCollect.class);
        util.exportExcel(response, list, "收集IP地址任务数据");
    }

    @GetMapping("/tmntask/{uid}")
    public void terminateTask(@PathVariable("uid") String uid)
    {
        addrCollectService.terminateTask(uid);
    }

    @GetMapping("/tmntask2/{uid}")
    public void terminateTask2(@PathVariable("uid") String uid)
    {
        addrCollectService.terminateTask2(uid);
    }

    @GetMapping("/showall2")
    public TableDataInfo showDe2()
    {
        List<AddrCollect> list = addrCollectService.selectDeList2();
        return getDataTable(list);
    }

    @GetMapping("/showips")
    public TableDataInfo showIps()
    {
        List<String> list = addrCollectService.selectIps();
        return getDataTable(list);
    }

    @GetMapping("/showips2")
    public TableDataInfo showIps2()
    {
        List<String> list = addrCollectService.selectIps2();
        return getDataTable(list);
    }
}
