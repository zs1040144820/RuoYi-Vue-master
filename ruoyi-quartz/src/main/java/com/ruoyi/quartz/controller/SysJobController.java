package com.ruoyi.quartz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetect;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileCollectMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetectMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileHandleMapper;
import com.ruoyi.quartz.util.ConversionTime;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.quartz.util.CronUtils;
import com.ruoyi.quartz.util.ScheduleUtils;

/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController
{
    @Autowired
    private ISysJobService jobService;
    @Autowired
    private AddrgenSeedfileCollectMapper collectMapper;
    @Autowired
    private AddrgenSeedfileHandleMapper handleMapper;
    @Autowired
    private AddrgenSeedfileDetectMapper detectMapper;

    /**
     * 查询定时任务列表
     */
    /*@PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysJob sysJob)
    {
        startPage();
        List<SysJob> list = jobService.selectJobList(sysJob);
        return getDataTable(list);
    }*/
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = "/list/{id}")
    public AjaxResult list(@PathVariable("id") String id)
    {
        //startPage();
        System.out.println("haoma......."+id);
        List<SysJob> list = jobService.selectJobByinvokeTarget(id);
        System.out.println(list.size());
        return AjaxResult.success(list);
    }

    /**
     * 导出定时任务列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:export')")
    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysJob sysJob)
    {
        List<SysJob> list = jobService.selectJobList(sysJob);
        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
        util.exportExcel(response, list, "定时任务");
    }

    /**
     * 获取定时任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:query')")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId)
    {
        return AjaxResult.success(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:add')")
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysJob job) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return error("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return error("新增任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        }
        else if (!ScheduleUtils.whiteList(job.getInvokeTarget()))
        {
            return error("新增任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setCreateBy(getUsername());
        return toAjax(jobService.insertJob(job));
    }

    /**
     * 修改定时任务
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysJob job) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        }
        else if (!ScheduleUtils.whiteList(job.getInvokeTarget()))
        {
            return error("修改任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setUpdateBy(getUsername());
        return toAjax(jobService.updateJob(job));
    }

    /**
     * 定时任务状态修改
     */
    //@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException, TaskException {
        List<SysJob> jobs = jobService.selectByInvokeTarget(job.getInvokeTarget());
        String[] cron = job.getCronExpression().split(":");
        int year = Integer.parseInt(cron[0]);
        int month = Integer.parseInt(cron[1]);
        int day = Integer.parseInt(cron[2]);
        String hour = cron[3];
        String min = cron[4];
        String second = cron[5];
        String cron1 = second + " "+ min +" "+ hour + " " + day + " " + month + " " + "?";
        SysJob job1 = new SysJob(job);
        job1.setCronExpression(cron1);
        if (jobs.size() <= 0){
            //插入采集记录
            AddrgenSeedfileCollect collect = new AddrgenSeedfileCollect();
            Long lid = Long.valueOf(job.getSfileID());
            collect.setAddrSeedfileId(lid);
            collect.setCollectStatus("0");
            collectMapper.insertAddrgenSeedfileCollect(collect);
            //插入采集任务
            jobService.insertJob(job1);
            //jobService.insertJob(job2);
            return toAjax(jobService.changeStatus(job1));
        }else {
            AddrgenSeedfileCollect collect = new AddrgenSeedfileCollect();
            Long lid = Long.valueOf(job.getSfileID());
            collect.setAddrSeedfileId(lid);
            collect.setCollectStatus("0");
            collectMapper.updateAddrgenSeedfileCollectByfileID(collect);
            jobs.get(0).setStatus(job.getStatus());
            jobs.get(0).setCronExpression(cron1);
            jobService.updateJob(jobs.get(0));
            //jobs.get(1).setStatus(job.getStatus());
            //jobs.get(1).setCronExpression(cron2);
            //jobService.updateJob(jobs.get(1));
            return toAjax(jobService.changeStatus(jobs.get(0)));
        }
    }
    /**
     * 定时任务状态修改
     */
    //@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatusforDetect")
    public AjaxResult changeStatusforDetect(@RequestBody SysJob job) throws SchedulerException, TaskException {
        List<SysJob> jobs = jobService.selectByInvokeTarget(job.getInvokeTarget());
        String[] cron = job.getCronExpression().split(":");
        int year = Integer.parseInt(cron[0]);
        int month = Integer.parseInt(cron[1]);
        int day = Integer.parseInt(cron[2]);
        String hour = cron[3];
        String min = cron[4];
        String second = cron[5];
        int collectDays = Integer.parseInt(job.getCollectDays());
        int days = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            days = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
        } else if (month == 2) {
            if (year % 4 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }
        int d = days - day + 1;//本月执行天数
        if (d > collectDays)
            days = day+collectDays-1;
        String cron1 = second + " "+ min +" "+ hour + " " + day + "-" + days + " " + month + " " + "? *";
        int dd = collectDays - d;//下个月执行天数
        int mon = 0;
        if (month < 12) {//如果是12月下个月就是1月
            mon = month + 1;
        } else {
            mon = 1;
        }
        String cron2 = "";
        if (d < collectDays) {
            cron2 = second + " "+ min +" "+ hour + " 1" + "-" + dd + " " + mon + " " + "? *";
        }
        System.out.println(cron1);
        System.out.println(cron2);
        System.out.println(jobs.size());
        SysJob job1 = new SysJob(job);
        SysJob job2 = new SysJob(job);
        job1.setCronExpression(cron1);
        job2.setCronExpression(cron2);
        if (jobs.size() <= 0){
            //插入探测记录
            AddrgenSeedfileDetect detect = new AddrgenSeedfileDetect();
            Long lid = Long.valueOf(job.getSfileID());
            detect.setAddrSeedfileId(lid);
            detect.setDetectStatus("0");
            detect.setAddrSeedfileName(handleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(lid).getAddrSeedfileName());
            detectMapper.insertAddrgenSeedfileDetect(detect);
            jobService.insertJob(job1);
            if (cron2 != ""){
                jobService.insertJob(job2);
                return toAjax(jobService.changeStatus(job1)+jobService.changeStatus(job2));
            }
            return toAjax(jobService.changeStatus(job1));
        }else {
            AddrgenSeedfileDetect detect = new AddrgenSeedfileDetect();
            Long lid = Long.valueOf(job.getSfileID());
            detect.setAddrSeedfileId(lid);
            detect.setDetectStatus("0");
            detect.setAddrSeedfileName(handleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(lid).getAddrSeedfileName());
            detectMapper.updateAddrgenSeedfileDetectByFileId(detect);

            jobs.get(0).setStatus(job.getStatus());
            jobService.updateJob(jobs.get(0));
            if (jobs.size() != 1){
                jobs.get(1).setStatus(job.getStatus());
                jobService.updateJob(jobs.get(1));
                return toAjax(jobService.changeStatus(jobs.get(0)) + jobService.changeStatus(jobs.get(1)));
            }
            return toAjax(jobService.changeStatus(jobs.get(0)));
        }
    }

    /**
     * 定时任务立即执行一次
     */
    //@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/run")
    public AjaxResult run(@RequestBody SysJob job) throws SchedulerException, TaskException {
        boolean result = jobService.run(job);
        return result ? success() : error("任务不存在或已过期！");
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException
    {
        jobService.deleteJobByIds(jobIds);
        return success();
    }
}
