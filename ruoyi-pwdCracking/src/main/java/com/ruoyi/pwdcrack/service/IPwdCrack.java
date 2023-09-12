package com.ruoyi.pwdcrack.service;

import com.ruoyi.pwdcrack.domain.CrackCommand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPwdCrack {
    /*
    破解命令执行器
     */
    public void crackExecute(CrackCommand crackCommand) throws IOException, InterruptedException;

    /*
    * 破解命令构造器
    * */
    public String composeCommand(CrackCommand crackCommand);

    /*
    * 处理文件
    * */
    public void handleFile(MultipartFile file) throws IOException;

    /*
    * 获取自定义字典列表
    * */
    public List<String> getWordLists();
}
