package com.ruoyi.pwdcrack.service.impl;

import com.ruoyi.pwdcrack.crackobserver.CrackObserver;
import com.ruoyi.pwdcrack.cracksubject.CrackSubject;
import com.ruoyi.pwdcrack.domain.CrackCommand;
import com.ruoyi.pwdcrack.domain.PwdcrackRecord;
import com.ruoyi.pwdcrack.mapper.PwdcrackRecordMapper;
import com.ruoyi.pwdcrack.service.IPwdCrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2023年04月20日 10:53
 */
@Service
public class PwdCrackImpl implements IPwdCrack, CrackSubject {

    @Autowired
    private PwdcrackRecordMapper pwdcrackRecordMapper;

    @Override
    public void crackExecute(CrackCommand crackCommand) throws IOException, InterruptedException {
        String command = composeCommand(crackCommand);
        PwdcrackRecord pwdcrackRecord = new PwdcrackRecord();
        Date date = new Date();
        pwdcrackRecord.setcTime(date);
        Process p = Runtime.getRuntime().exec(new String[]{"cmd", "/c",command});
        final InputStream stream = p.getInputStream();
        final InputStream errors = p.getErrorStream();
        StringBuilder s = new StringBuilder();
        s.append(" ");
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                String line = null;
                BufferedReader errorReader =  new BufferedReader(new InputStreamReader(errors, Charset.forName("GBK")));
                try {
                    boolean firstLine = true;
                    while ((line = errorReader.readLine()) != null) {
                        String jump = "<br>\n";
                        if (firstLine)
                            jump = "";
                        s.append(jump);
                        s.append(line);
                        crackCommand.setCrackResult(s.toString());
                        crackCommand.setFlag(false);
                        notifyObserves(crackCommand);
                        firstLine = false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                String line = null;
                BufferedReader reader =  new BufferedReader(new InputStreamReader(stream, Charset.forName("GBK")));;
                try {
                    boolean firstLine = true;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("Hydra")){
                            continue;
                        }
                        //String line1 = line.replaceAll("(?i)Hydra", "");
                        String jump = "<br>\n";
                        if (firstLine)
                            jump = "";
                        s.append(jump);
                        s.append(line);
                        crackCommand.setCrackResult(s.toString());
                        crackCommand.setFlag(false);
                        notifyObserves(crackCommand);
                        firstLine = false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        t1.start();
        t2.join();
        t1.join();
        crackCommand.setCrackResult(s.toString());
        crackCommand.setFlag(true);
        notifyObserves(crackCommand);
        pwdcrackRecord.setcProcotol(crackCommand.getProtoc());
        pwdcrackRecord.setDesIp(crackCommand.getIpAddress());
        if (crackCommand.getMyDic() != null && !crackCommand.getMyDic().equals("")){
            pwdcrackRecord.setPwdDic(crackCommand.getMyDic());
        }else {
            pwdcrackRecord.setPwdDic(crackCommand.getDic());
        }
        pwdcrackRecord.setUsername(crackCommand.getUname());
        pwdcrackRecord.setResult(s.toString());
        Pattern pattern = Pattern.compile("password:\\s*(\\w+)");
        Matcher matcher = pattern.matcher(s.toString());
        if (matcher.find()) {
            String password = matcher.group(1);
            pwdcrackRecord.setPwdResult(password);
        }
        pwdcrackRecordMapper.insertPwdcrackRecord(pwdcrackRecord);
    }

    @Override
    public String composeCommand(CrackCommand crackCommand) {
        List<String> commandList = new ArrayList<String>();
        commandList.add("wsl hydra -l ");
        commandList.add(crackCommand.getUname());
        commandList.add(" -P ");
        if (crackCommand.getMyDic() != null && !crackCommand.getMyDic().equals("")){
            commandList.add("./wordlists/"+crackCommand.getMyDic()+" -e ns ");
        }else {
            commandList.add("/usr/share/wordlists/dirb/"+crackCommand.getDic()+" -e ns ");
        }
        if(crackCommand.getPort() != null && !crackCommand.getPort().equals("")){
            commandList.add("-s "+crackCommand.getPort()+" ");
        }
        commandList.add(crackCommand.getIpAddress());
        commandList.add(" "+crackCommand.getProtoc());
        String[] s = commandList.toArray(new String[]{});
        StringBuilder commandstr = new StringBuilder();
        for (String s1 : s) {
            commandstr.append(s1);
        }
        System.out.println(commandstr.toString());
        return commandstr.toString();
    }

    @Override
    public void handleFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File("./wordlists",fileName);
        OutputStream out = null;
        try{
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<String> getWordLists() {
        File file = new File("./wordlists/");
        //用数组把文件夹下的文件存起来
        File[] files = file.listFiles();
        List<String> wordLists = new ArrayList<>();
        for (File file2 : files) {
            wordLists.add(file2.getName());
        }
        return wordLists;
    }
    public void notifyob(String s){

    }

    @Override
    public void addObserver(CrackObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CrackObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserves(CrackCommand cmd) {
        for (CrackObserver observer : observers) {
            observer.update(cmd);
        }
    }
}
