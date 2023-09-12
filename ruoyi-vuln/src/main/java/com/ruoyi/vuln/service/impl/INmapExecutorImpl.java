package com.ruoyi.vuln.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.vuln.commandobserver.CommandExecutorObserver;
import com.ruoyi.vuln.commandsubject.CommandExecutorSubject;
import com.ruoyi.vuln.domain.NmapCommand;
import com.ruoyi.vuln.domain.VulnDetectHistory;
import com.ruoyi.vuln.domain.VulnScript;
import com.ruoyi.vuln.domain.VulnSingleAddrDetails;
import com.ruoyi.vuln.domain.vo.ExecutionObjectFactory;
import com.ruoyi.vuln.domain.vo.Script;
import com.ruoyi.vuln.domain.vo.ScriptHelp;
import com.ruoyi.vuln.mapper.VulnDetectHistoryMapper;
import com.ruoyi.vuln.mapper.VulnScriptMapper;
import com.ruoyi.vuln.mapper.VulnSingleAddrDetailsMapper;
import com.ruoyi.vuln.service.INmapExecutor;
import com.ruoyi.vuln.utils.CVEGradeQuery;
import com.ruoyi.vuln.utils.ComputeIPs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @author:
 * @date: 2022年09月25日 14:24
 */
@Service
public class INmapExecutorImpl implements INmapExecutor, CommandExecutorSubject {
    private Thread commandThread;
    //private NmapCommand cmd;
    public List<String> addrFileList = new ArrayList<>();
    String projectPath = System.getProperty("user.dir");//获取属性
    String finalPath = projectPath+"\\vulnScript.xml";
    @Autowired
    private VulnDetectHistoryMapper vulnDetectHistoryMapper;
    @Autowired
    private VulnSingleAddrDetailsMapper vulnSingleAddrDetailsMapper;
    @Autowired
    private VulnDetectHistoryServiceImpl vulnDetectHistoryService;
    @Autowired
    private VulnSingleAddrDetailsServiceImpl vulnSingleAddrDetailsService;

    @Autowired
    private VulnScriptMapper vulnScriptMapper;

    @Override//重写
    public void nmapHelp() throws IOException {
        Runtime.getRuntime().exec("nmap --script-help all -oX "+ finalPath);
    }

    /*
     *  执行nmap命令
     *  @param nmapCommand nmap命令
     * */
    @Override
    public void nmapExecute(NmapCommand nmapCommand) throws InterruptedException {
        Thread t= new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Thread.currentThread().setName(nmapCommand.getUuid());

                    boolean a = false;
                    List<String> iPs = new ArrayList<>();
                    List<VulnSingleAddrDetails> singleList = new ArrayList<>();
                    if (nmapCommand.isUseFile()){
                        iPs = addrFileList;
                    }else if (nmapCommand.getDesIP() != null && nmapCommand.getDesIP().length() != 0) {
                        a = true;
                        iPs = ComputeIPs.findIPsForIpv6(nmapCommand.getSrcIP(), nmapCommand.getDesIP());
                    }else{
                        iPs.add(nmapCommand.getSrcIP());
                    }
                    Iterator<String> ipsIteratorfirst = iPs.iterator();
                    while(ipsIteratorfirst.hasNext()){
                        VulnSingleAddrDetails details = new VulnSingleAddrDetails();
                        details.setIpv6Addr(ipsIteratorfirst.next());
                        details.setServices("");
                        singleList.add(details);
                    }
                    VulnDetectHistory detectHistory = new VulnDetectHistory();
                    detectHistory.setVulnSingleAddrDetailsList(singleList);
                    detectHistory.setUuid(nmapCommand.getUuid());
                    detectHistory.setTargetRange(nmapCommand.getSrcIP()+"→"+nmapCommand.getDesIP());
                    detectHistory.setFos(nmapCommand.isOsSwitch());
                    detectHistory.setDatabasetype(nmapCommand.isDatabaseSwitch());
                    detectHistory.setPortService(true);
                    detectHistory.setStartport(nmapCommand.getStartPort());
                    detectHistory.setEndport(nmapCommand.getEndPort());
                    detectHistory.setRate("0");
                    Date dat = new Date();
                    detectHistory.setScanDatetime(dat);
                    vulnDetectHistoryService.insertVulnDetectHistory(detectHistory);
                    VulnDetectHistory detectHistory1 = vulnDetectHistoryService.selectVulnDetectHistoryByRecordsId(detectHistory.getRecordsId());

                    int totalIPs = iPs.size();
                    int cuIPS = 0;
                    Iterator<VulnSingleAddrDetails> ipsIterator = detectHistory1.getVulnSingleAddrDetailsList().iterator();
                    //while (!Thread.currentThread().isInterrupted() && ipsIterator.hasNext()){
                try {
                    final String[] outStr = {""};
                    while (ipsIterator.hasNext()){
                        cuIPS++;
                        VulnSingleAddrDetails singleAddrDetails= ipsIterator.next();
                        String currentIP = singleAddrDetails.getIpv6Addr();
                        String command = composeCommand(nmapCommand,currentIP);
                        Process p = Runtime.getRuntime().exec(command);
                        final InputStream stream = p.getInputStream();
                        final InputStream errors = p.getErrorStream();
                        int finalCuIPS = cuIPS;
                        commandThread = new Thread(new Runnable() {
                            public void run() {
                                BufferedReader reader = null;
                                BufferedReader errorReader = null;
                                String s = "";
                                String line = null;
                                try {
                                    boolean firstLine = true;
                                    reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("GBK")));
                                    s = s + "<br>\n" + "<h3>" + currentIP + "</h3>";
                                    while ((line = reader.readLine()) != null) {
                                        line = escape(line);
                                        String jump = "<br>\n";
                                        if (firstLine)
                                            jump = "";
                                        s = s + jump + line;
                                        firstLine = false;
                                    }
                                    errorReader = new BufferedReader(new InputStreamReader(errors));
                                    while ((line = errorReader.readLine()) != null) {
                                        line = escape(line);
                                        String jump = "<br>";
                                        if (firstLine)
                                            jump = "";
                                        s = s + jump + line;
                                        firstLine = false;
                                    }
                                    System.out.println("扫描结果---------：" + s);
                                    outStr[0] = outStr[0] + s;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    //VulnDetectHistory his = new VulnDetectHistory();
                                    //计算高危漏洞比例
                                    Pattern pa = Pattern.compile("IDs:\\s*CVE:CVE-[0-9]{4}-[0-9]+");
                                    int countVulnDetected = 0;
                                    Matcher matcher = pa.matcher(s);
                                    StringBuilder CVE = new StringBuilder();
                                    while (matcher.find()){
                                        String[] split = matcher.group().split(":");
                                        CVE.append(split[2]+",");
                                        countVulnDetected++;
                                    }
                                    if (CVE.length() != 0){
                                        String CVE1 = CVE.toString().substring(0, CVE.length() - 1);
                                        VulnScript script = vulnScriptMapper.selectGradeByCve(CVE1);
                                        if(script!=null){
                                            String cvss20 = script.getCvss20();
                                            String cvss30 = script.getCvss30();
                                            String cvss31 = script.getCvss31();
                                            //his.setHighriskRate(CVE1+" CVSS2.0 "+cvss20+" CVSS3.0 "+cvss30+" CVSS3.1 "+cvss31);
                                            //singleAddrDetails.setCve(CVE1+" CVSS2.0 "+cvss20+" CVSS3.0 "+cvss30+" CVSS3.1 "+cvss31);
                                            singleAddrDetails.setCve(CVE1);
                                        }else{
                                            List<String> list0=new ArrayList<>();
                                            list0=CVEGradeQuery.test(CVE1);
                                            String grade= StringUtils.join(list0,",");
                                            String a1=CVE1+" "+grade;
                                            //his.setHighriskRate(a1);
                                            singleAddrDetails.setCve(a1);
                                            VulnScript script1=new VulnScript();
                                            script1.setCvss20(list0.get(0));
                                            script1.setCve(CVE1);
                                            vulnScriptMapper.insertVulnScript(script1);

                                        }
                                    }else {
                                        //his.setHighriskRate("");
                                        singleAddrDetails.setCve("");
                                    }
                                    //OS提取
                                    Pattern pos = Pattern.compile("Running.*:.*");
                                    Matcher matcher2 = pos.matcher(s);
                                    while (matcher2.find()){
                                        String[] split = matcher2.group().split(":");
                                        if (split.length != 0){
                                            String[] os = split[1].split("<br>");
                                            System.out.println(os[0]);
                                            singleAddrDetails.setOs(os[0]);
                                        }
                                    }
                                    //提取服务
                                    Pattern pservice = Pattern.compile("\\d+/[a-z]*\\s+[a-z]*\\s+.+");//端口号/协议 端口状态 服务
                                    Matcher matcher3 = pservice.matcher(s);
                                    String portSer = "";
                                    while (matcher3.find()){
                                        String ser = matcher3.group();
                                        String[] serl = ser.split("<br>");
                                        portSer = portSer + serl[0] + ",\n";
                                    }
                                    System.out.println(portSer);
                                    //his.setPortService(portSer);
                                    singleAddrDetails.setServices(portSer);

                                    try {
                                        reader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //下面是将本次扫描记录添加到数据库
                                    singleAddrDetails.setCommand(command);
                                    Date d = new Date();
                                    //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    singleAddrDetails.setScanTime(d);
                                    singleAddrDetails.setResultDetails(s);
                                    vulnSingleAddrDetailsService.updateVulnSingleAddrDetails(singleAddrDetails);

                                    DecimalFormat df = new DecimalFormat("0");
                                    String rate = df.format((float) finalCuIPS / totalIPs * 100);
                                    nmapCommand.setOutput(outStr[0]);
                                    nmapCommand.setRate(rate);

                                    detectHistory.setRate(rate);
                                    detectHistory.setSuspendPosition(currentIP);
                                    vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);
                                }
                            }
                        });
                        commandThread.start();
                        commandThread.join();
                        /*DecimalFormat df = new DecimalFormat("0");
                        String rate = df.format((float)cuIPS / totalIPs * 100);
                        nmapCommand.setOutput(outStr[0]);
                        nmapCommand.setRate(rate);
                        detectHistory.setRate(rate);
                        vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);*/
                    }
                    Date end = new Date();
                    detectHistory.setScanedTime(end);
                    detectHistory.setRate("100");
                    vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);
                    notifyObserves(nmapCommand);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(".....中断,保存未完成的状态.....");
                    nmapCommand.setStatus("继续");
                }
            }
        });
        t.start();
        t.join();
    }
    //恢复任务
    @Override
    public void nmapReExecute(NmapCommand nmapCommand) throws InterruptedException {
        Thread t= new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Thread.currentThread().setName(nmapCommand.getUuid());

                VulnDetectHistory detectHistory = new VulnDetectHistory();
                detectHistory.setUuid(nmapCommand.getUuid());
                List<VulnDetectHistory> vulnDetectHistories = vulnDetectHistoryMapper.selectVulnDetectHistoryList(detectHistory);
                VulnSingleAddrDetails sin = new VulnSingleAddrDetails();
                sin.setRecordsId(vulnDetectHistories.get(0).getRecordsId());
                List<VulnSingleAddrDetails> vulnSingleAddrDetails1 = vulnSingleAddrDetailsMapper.selectVulnSingleAddrDetailsList(sin);
                detectHistory.setRecordsId(vulnDetectHistories.get(0).getRecordsId());

                int totalIPs = vulnSingleAddrDetails1.size();
                int cuIPS = 0;
                Iterator<VulnSingleAddrDetails> ipsIterator = vulnSingleAddrDetails1.iterator();
                try {
                    final String[] outStr = {nmapCommand.getOutput()};
                    while (ipsIterator.hasNext()){
                        cuIPS++;
                        VulnSingleAddrDetails singleAddrDetails= ipsIterator.next();
                        if (singleAddrDetails.getCommand() != null) {
                            /*DecimalFormat df = new DecimalFormat("0");
                            String rate = df.format(cuIPS / totalIPs * 100);
                            nmapCommand.setOutput(outStr[0]);
                            nmapCommand.setRate(rate);*/
                            continue;
                        }
                        String currentIP = singleAddrDetails.getIpv6Addr();
                        String command = composeCommand(nmapCommand,currentIP);
                        Process p = Runtime.getRuntime().exec(command);
                        final InputStream stream = p.getInputStream();
                        final InputStream errors = p.getErrorStream();
                        int finalCuIPS = cuIPS;
                        commandThread = new Thread(new Runnable() {
                            public void run() {
                                BufferedReader reader = null;
                                BufferedReader errorReader = null;
                                String s = "";
                                String line = null;
                                try {
                                    boolean firstLine = true;
                                    reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("GBK")));
                                    s = s + "<br>\n" + "<h3>" + currentIP + "</h3>";
                                    while ((line = reader.readLine()) != null) {
                                        line = escape(line);
                                        String jump = "<br>\n";
                                        if (firstLine)
                                            jump = "";
                                        s = s + jump + line;
                                        firstLine = false;
                                    }
                                    errorReader = new BufferedReader(new InputStreamReader(errors));
                                    while ((line = errorReader.readLine()) != null) {
                                        line = escape(line);
                                        String jump = "<br>";
                                        if (firstLine)
                                            jump = "";
                                        s = s + jump + line;
                                        firstLine = false;
                                    }
                                    System.out.println("扫描结果---------：" + s);
                                    outStr[0] = outStr[0] + s;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    //VulnDetectHistory his = new VulnDetectHistory();
                                    //计算高危漏洞比例
                                    Pattern pa = Pattern.compile("IDs:\\s*CVE:CVE-[0-9]{4}-[0-9]+");
                                    int countVulnDetected = 0;
                                    Matcher matcher = pa.matcher(s);
                                    StringBuilder CVE = new StringBuilder();
                                    while (matcher.find()){
                                        String[] split = matcher.group().split(":");
                                        CVE.append(split[2]+",");
                                        countVulnDetected++;
                                    }
                                    if (CVE.length() != 0){
                                        String CVE1 = CVE.toString().substring(0, CVE.length() - 1);
                                        VulnScript script = vulnScriptMapper.selectGradeByCve(CVE1);
                                        if(script!=null){
                                            String cvss20 = script.getCvss20();
                                            String cvss30 = script.getCvss30();
                                            String cvss31 = script.getCvss31();
                                            //his.setHighriskRate(CVE1+" CVSS2.0 "+cvss20+" CVSS3.0 "+cvss30+" CVSS3.1 "+cvss31);
                                            singleAddrDetails.setCve(CVE1+" CVSS2.0 "+cvss20+" CVSS3.0 "+cvss30+" CVSS3.1 "+cvss31);
                                        }else{
                                            List<String> list0=new ArrayList<>();
                                            list0=CVEGradeQuery.test(CVE1);
                                            String grade= StringUtils.join(list0,",");
                                            String a1=CVE1+" "+grade;
                                            //his.setHighriskRate(a1);
                                            singleAddrDetails.setCve(a1);
                                            VulnScript script1=new VulnScript();
                                            script1.setCvss20(list0.get(0));
                                            script1.setCve(CVE1);
                                            vulnScriptMapper.insertVulnScript(script1);

                                        }
                                    }else {
                                        //his.setHighriskRate("");
                                        singleAddrDetails.setCve("");
                                    }
                                    //OS提取
                                    Pattern pos = Pattern.compile("Running.*:.*");
                                    Matcher matcher2 = pos.matcher(s);
                                    while (matcher2.find()){
                                        String[] split = matcher2.group().split(":");
                                        if (split.length != 0){
                                            String[] os = split[1].split("<br>");
                                            System.out.println(os[0]);
                                            singleAddrDetails.setOs(os[0]);
                                        }
                                    }
                                    //提取服务
                                    Pattern pservice = Pattern.compile("\\d+/[a-z]*\\s+[a-z]*\\s+.+");//端口号/协议 端口状态 服务
                                    Matcher matcher3 = pservice.matcher(s);
                                    String portSer = "";
                                    while (matcher3.find()){
                                        String ser = matcher3.group();
                                        String[] serl = ser.split("<br>");
                                        portSer = portSer + serl[0] + ",\n";
                                    }
                                    System.out.println(portSer);
                                    //his.setPortService(portSer);
                                    singleAddrDetails.setServices(portSer);

                                    try {
                                        reader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //下面是将本次扫描记录添加到数据库
                                    singleAddrDetails.setCommand(command);
                                    Date d = new Date();
                                    //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    singleAddrDetails.setScanTime(d);
                                    singleAddrDetails.setResultDetails(s);
                                    vulnSingleAddrDetailsService.updateVulnSingleAddrDetails(singleAddrDetails);

                                    DecimalFormat df = new DecimalFormat("0");
                                    String rate = df.format((float) finalCuIPS / totalIPs * 100);
                                    nmapCommand.setOutput(outStr[0]);
                                    nmapCommand.setRate(rate);

                                    detectHistory.setRate(rate);
                                    detectHistory.setSuspendPosition(currentIP);
                                    vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);
                                }
                            }
                        });
                        commandThread.start();
                        commandThread.join();
                        /*DecimalFormat df = new DecimalFormat("0");
                        String rate = df.format((float)cuIPS / totalIPs * 100);
                        nmapCommand.setOutput(outStr[0]);
                        nmapCommand.setRate(rate);
                        detectHistory.setRate(rate);
                        vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);*/
                    }
                    Date end = new Date();
                    detectHistory.setScanedTime(end);
                    detectHistory.setRate("100");
                    vulnDetectHistoryMapper.updateVulnDetectHistory(detectHistory);
                    notifyObserves(nmapCommand);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(".....中断,保存未完成的状态.....");
                    nmapCommand.setStatus("继续");
                }
            }
        });
        t.start();
        t.join();
    }

    /*
     *  初始化nmap脚本
     *  @return 脚本列表
     * */
    @Override
    public List<String> nmapInitScript() {
        List<String> vulnList = new ArrayList<>();
        //XML文件由Nmap程序输出
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(finalPath));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(ExecutionObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(sb.toString());
            Object execution = unmarshaller.unmarshal(reader);
            if (execution instanceof ScriptHelp) {
                ScriptHelp sh = (ScriptHelp) execution;
                List<Script> list = sh.getScripts();
                Iterator<Script> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Script script = iterator.next();
                    if (script.getCategories().contains("vuln")) {
                        //正则表达式取出脚本名称
                        String[] sl = script.getFilename().split("scripts\\\\");
                        String[] ll = sl[1].split("\\.");
                        vulnList.add(ll[0]);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vulnList;
    }

    /*
     *  通过nmapCommand构造完整的nmap命令
     * */
    @Override
    public String composeCommand(NmapCommand nmapCommand,String currentIP) {


        List<String> commandList = new ArrayList<String>();
        commandList.add("nmap ");
        commandList.add("-6 ");//-6扫描IPv6地址，-T4：Aggressive模式假设用户具有合适及可靠的网络从而加速 扫描，
                                        // -A (激烈扫描模式选项)目前，这个选项启用了操作系统检测(-O) 和版本扫描(-sV)
                                        // -v 详细信息
        //端口扫描单独一条命令
        if (!nmapCommand.getStartPort().equals("") || !nmapCommand.getEndPort().equals("")){
            commandList.add("-p ");
            if (nmapCommand.getStartPort().equals("")){
                commandList.add(nmapCommand.getEndPort()+" ");
            }else if (nmapCommand.getEndPort().equals("")){
                commandList.add(nmapCommand.getStartPort()+" ");
            }else {
                commandList.add(nmapCommand.getStartPort()+"-"+nmapCommand.getEndPort()+" ");
            }
            commandList.add(currentIP);
            String[] s = commandList.toArray(new String[]{});
            StringBuilder commandstr = new StringBuilder();
            for (String s1 : s) {
                System.out.print(s1);
                commandstr.append(s1);
            }
            System.out.println();
            return commandstr.toString();
        }
        commandList.add("--script ");
        if (nmapCommand.isChecked()) {
            commandList.add("\"vuln\" ");
            commandList.add(currentIP);
            if (nmapCommand.isDatabaseSwitch() || nmapCommand.isOsSwitch()){
                commandList.add(" -A -T4 --osscan-guess ");
                commandList.add(currentIP);
            }
            String[] s = commandList.toArray(new String[]{});
            StringBuilder commandstr = new StringBuilder();
            for (String s1 : s) {
                System.out.print(s1);
                commandstr.append(s1);
            }
            System.out.println();
            return commandstr.toString();
        }
        commandList.add("\"");
        for (String o : nmapCommand.getScriptSelect()) {
            commandList.add(o);
            commandList.add(",");
        }
        commandList.add("\" ");
        commandList.add(currentIP);
        if (nmapCommand.isDatabaseSwitch() || nmapCommand.isOsSwitch()){
            commandList.add(" -A -T4 --osscan-guess ");
            commandList.add(currentIP);
        }
        String[] s = commandList.toArray(new String[]{});
        StringBuilder commandstr = new StringBuilder();
        for (String s1 : s) {
            commandstr.append(s1);
        }
        return commandstr.toString();
    }

    @Transactional
    @Override
    public int insertVulnDetectHistory(VulnDetectHistory vulnDetectHistory) {
        int rows = vulnDetectHistoryMapper.insertVulnDetectHistory(vulnDetectHistory);
        //insertVulnSingleAddrDetails(vulnDetectHistory);
        return rows;
    }

    @Override
    public List<String> handleFile(MultipartFile file) throws IOException {
        List<String> addrList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            addrList.add(line);
            addrList.add(" ");
        }
        addrFileList = addrList;
        return addrList;
    }

    private String escape(String str) {
        String line = str;
        line = line.replace("&", "&amp;");
        line = line.replace("\"", "&quot;");
        line = line.replace("<", "&lt;");
        line = line.replace(">", "&gt;");
        return line;
    }


    @Override
    public void addObserver(CommandExecutorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CommandExecutorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserves(NmapCommand nmapCommand) {
        for (CommandExecutorObserver observer : observers) {
            observer.update(nmapCommand);
        }
    }
}
