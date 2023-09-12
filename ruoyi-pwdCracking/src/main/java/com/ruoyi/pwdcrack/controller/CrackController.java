package com.ruoyi.pwdcrack.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.pwdcrack.crackobserver.CrackObserver;
import com.ruoyi.pwdcrack.cracksubject.CrackSubject;
import com.ruoyi.pwdcrack.domain.CrackCommand;
import com.ruoyi.pwdcrack.service.IPwdCrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2023年04月20日 10:21
 */
@RestController
@RequestMapping("/crack")
public class CrackController implements CrackObserver {
    @Autowired
    private IPwdCrack pwdCrack;
    @Autowired
    private CrackSubject subject;

    private List<CrackCommand> cmd  = new ArrayList<CrackCommand>();


    @PostConstruct
    public void init() throws IOException {
        CrackCommand crackCommand = new CrackCommand();
        cmd.add(crackCommand);
        subject.addObserver(this);
    }

    @PreAuthorize("@ss.hasPermi('crack:startCrack')")
    @PostMapping("/startCrack")
    @Log(title = "口令破解", businessType = BusinessType.PWDCRACK)
    public AjaxResult startCrack (@RequestBody CrackCommand crackCommand) throws IOException, InterruptedException {
        System.out.println(crackCommand.getIpAddress());
        System.out.println(crackCommand.getPort());
        System.out.println(crackCommand.getDic());
        System.out.println(crackCommand.getMyDic());
        System.out.println(crackCommand.getProtoc());
        System.out.println(crackCommand.getUname());
        this.cmd.get(0).setCrackResult("");
        this.cmd.get(0).setFlag(false);
        crackCommand.setFlag(false);
        pwdCrack.crackExecute(crackCommand);
        return AjaxResult.success();
    }
    @PreAuthorize("@ss.hasPermi('crack:importData')")
    @Log(title = "上传口令文件", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        pwdCrack.handleFile(file);
        return AjaxResult.success("成功");
    }
    @GetMapping("/getWordList")
    public AjaxResult getWordList()
    {
        return AjaxResult.success(pwdCrack.getWordLists());
    }
    @GetMapping("/getResults")
    public AjaxResult getResults(){
        return AjaxResult.success(this.cmd.get(0));
    }

    @Override
    public void update(CrackCommand cmd) {
        this.cmd.get(0).setCrackResult(cmd.getCrackResult());
        this.cmd.get(0).setFlag(cmd.isFlag());
    }
}
