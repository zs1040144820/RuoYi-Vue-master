package com.ruoyi.vuln.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.vuln.commandobserver.CommandExecutorObserver;
import com.ruoyi.vuln.commandsubject.CommandExecutorSubject;
import com.ruoyi.vuln.domain.NmapCommand;
import com.ruoyi.vuln.domain.Output;
import com.ruoyi.vuln.service.INmapExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 漏洞扫描历史记录Controller
 * 
 * @author ruoyi
 * @date 2022-09-25
 */
@RestController
@RequestMapping("/vuln/scan")
public class VulnScanController extends BaseController implements CommandExecutorObserver
{
    private List<NmapCommand> ongoingCommands  = new ArrayList<NmapCommand>();
    private List<NmapCommand> finishedCommands = new ArrayList<NmapCommand>();
    @Autowired
    private INmapExecutor nmapExecutor;
    @Autowired
    private CommandExecutorSubject subject;

    @PostConstruct
    public void initNmapHelp() throws IOException {
        subject.addObserver(this);
        nmapExecutor.nmapHelp();
    }

    /**
     * 初始化脚本列表
     */
    @PreAuthorize("@ss.hasPermi('vuln:scan:initscript')")
    @GetMapping("/initscript")
    public AjaxResult initScript()
    {
        //subject.addObserver(this);
        return AjaxResult.success(nmapExecutor.nmapInitScript());
    }

    @GetMapping("/getOngoingC")
    public AjaxResult getOngoingC()
    {
        List<NmapCommand> commandLs = new ArrayList<NmapCommand>();
        commandLs.addAll(ongoingCommands);
        commandLs.addAll(finishedCommands);
        return AjaxResult.success(commandLs);
    }
    @GetMapping("/getOngoingCom")
    public AjaxResult getOngoingCom()
    {
        return AjaxResult.success(ongoingCommands.size());
    }
    /**
     * 漏洞扫描
     */
    @PreAuthorize("@ss.hasPermi('vuln:scan:scanvuln')")
    @Log(title = "漏洞扫描", businessType = BusinessType.SCANVULN)
    @PostMapping("/scanvuln")
    public AjaxResult scanVuln(@RequestBody NmapCommand nmapCommand) throws InterruptedException {
        nmapCommand.setRate("0");
        nmapCommand.setStatus("暂停");
        this.ongoingCommands.add(0, nmapCommand);
        nmapExecutor.nmapExecute(nmapCommand);
        System.out.println(finishedCommands.get(0).getUuid()+finishedCommands.get(0).getOutput());
        return AjaxResult.success(new Output(finishedCommands.get(0).getUuid(),finishedCommands.get(0).getOutput()));
    }

    /*上传文件*/
    @PreAuthorize("@ss.hasPermi('vuln:scan:importAddr')")
    @Log(title = "上传文件", businessType = BusinessType.INSERT)
    @PostMapping("/importAddr")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        nmapExecutor.handleFile(file);
        return AjaxResult.success("成功");
    }

    @Override
    public void update(NmapCommand nmapCommand) {
        this.ongoingCommands.remove(nmapCommand);
        this.finishedCommands.add(0,nmapCommand);
    }

    @GetMapping("/stopThread/{uuid}")
    @Log(title = "停止漏洞扫描", businessType = BusinessType.STOPVULN)
    public AjaxResult stopThread(@PathVariable String uuid) throws InterruptedException {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        System.out.println("现有线程个数：" + noThreads);
        for (int i = 0; i < noThreads; i++) {
            String threadName = lstThreads[i].getName();
            // 中断指定的线程
            if (threadName.equals(uuid)) {
                System.out.println("中断线程：" + lstThreads[i].getId());
                Thread.sleep(500);
                lstThreads[i].interrupt();
            }
        }
        return AjaxResult.success("1");
    }

    @DeleteMapping("/del/{uuid}")
    @Log(title = "删除任务", businessType = BusinessType.DELETE)
    public AjaxResult deleteCommand(@PathVariable String uuid){
        try{
            Iterator<NmapCommand> iterator1 = this.finishedCommands.iterator();
            while (iterator1.hasNext()){
                NmapCommand next = iterator1.next();
                if (next.getUuid().equals(uuid)){
                    this.finishedCommands.remove(next);
                }
            }
            Iterator<NmapCommand> iterator = this.ongoingCommands.iterator();
            while (iterator.hasNext()){
                NmapCommand next = iterator.next();
                if (next.getUuid().equals(uuid)){
                    this.ongoingCommands.remove(next);
                }
            }
        }catch (Exception e){

        }
        return AjaxResult.success("OK");
    }
    @PostMapping("/resumeTask")
    @Log(title = "恢复任务", businessType = BusinessType.REEXEC)
    public AjaxResult resumeTask(@RequestBody NmapCommand nmapCommand) throws InterruptedException {
        Iterator<NmapCommand> iterator = this.ongoingCommands.iterator();
        while (iterator.hasNext()){
            NmapCommand next = iterator.next();
            if (next.getUuid().equals(nmapCommand.getUuid())){
                int i = this.ongoingCommands.indexOf(next);
                nmapCommand.setOutput(next.getOutput());
                nmapCommand.setRate(next.getRate());
                nmapCommand.setStatus("暂停");
                this.ongoingCommands.remove(next);//移除原任务，开始新任务
                this.ongoingCommands.add(i, nmapCommand);
                break;
            }
        }
        nmapExecutor.nmapReExecute(nmapCommand);
        return AjaxResult.success("OK");
    }
}
