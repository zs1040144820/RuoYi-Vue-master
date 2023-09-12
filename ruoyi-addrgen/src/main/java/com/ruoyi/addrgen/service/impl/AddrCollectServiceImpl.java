package com.ruoyi.addrgen.service.impl;

import com.ruoyi.addrgen.domain.AddrCollect;
import com.ruoyi.addrgen.domain.AddrCollectDetail;
import com.ruoyi.addrgen.domain.AddrCollectDetail2;
import com.ruoyi.addrgen.domain.AddrCollectTade;
import com.ruoyi.addrgen.mapper.AddrCollectDetail2Mapper;
import com.ruoyi.addrgen.mapper.AddrCollectDetailMapper;
import com.ruoyi.addrgen.mapper.AddrCollectMapper;
import com.ruoyi.addrgen.mapper.AddrCollectTadeMapper;
import com.ruoyi.addrgen.service.IAddrCollectService;
import com.ruoyi.addrgen.utils.TaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.ruoyi.addrgen.utils.IPv6Range.extractParenthesis;
import static com.ruoyi.addrgen.utils.IPv6Range.getIPv6Range;

@Service
public class AddrCollectServiceImpl implements IAddrCollectService {
    //初始化一个容量为3的静态队列，用于存放任务的uid
    private static TaskQueue taskqueue = new TaskQueue(1);
    private static TaskQueue taskqueue2 = new TaskQueue(2);
    @Autowired
    private AddrCollectMapper addrCollectMapper;

    @Autowired
    private AddrCollectTadeMapper actm;

    @Autowired
    private AddrCollectDetailMapper addrCollectDetailMapper;

    @Autowired
    private AddrCollectDetail2Mapper acd2m;

    //对抓包所得IP地址去除端口号
    public static String removePort(String str){
        String[] arr = str.split("\\.");
        if(arr.length==0)
            return str;
        return arr[0];
    }

    //去除最后一位冒号，避免造成歧义
    public static String removeLastColon(String str) {
        if (str.endsWith(":")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    //对控制台返回的结果进行过滤，仅保留IPv6地址，去除端口号并进行去重，将剩余的地址返回
    public static ArrayList<String> getIPAddress(String str){
        String[] arr = str.replaceAll("<br>", " ").split("\\s+"); //对输入的抓包信息去除换行符，使用空格切分字符串
        ArrayList<String> duplicate = new ArrayList<String>();
        ArrayList<String> realIPAddress = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {  //在上一步所得数组中找到所有的IP地址和端口号，存入Set
            if (arr[i].contains(">")) {
                String source = arr[i - 1];
                String destination = arr[i + 1];
                //System.out.println("Source：" + source);
                duplicate.add(removePort(source));
                //System.out.println("Destination：" + destination);
                duplicate.add(removeLastColon((removePort(destination))));
            }
        }
        HashSet<String> set = new HashSet<String>(duplicate);  //使用Set进行去重，去重结果存入realIPAddress
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String ipaddress = iterator.next();
            realIPAddress.add(ipaddress);
            //System.out.println(ipaddress);
        }
        return realIPAddress;
    }

    //调用Windows控制台启动tcpdump并获取返回的结果
    public StringBuffer runTCPDump(int settime,AddrCollect addrcollect,String uid){
        String command = "wsl --distribution kali-linux timeout "+settime+" tcpdump -n ip6";
        System.out.println("[Sys]Generating Tcpdump command:"+command);
        StringBuffer result = new StringBuffer();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);  //设置要执行的命令
            processBuilder.directory(null); // 默认为当前工作目录
            long startTime = System.currentTimeMillis(); // 记录开始时间
            Process process = processBuilder.start();  // 启动进程

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateTime(startTime, System.currentTimeMillis(), settime ,uid); // 将开始时间和当前时间作为参数传递给 changeTime 方法
                }
            }, 3000, 3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));  //获取命令执行的输出结果
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line+"<br>");
                //System.out.println(line);
                if(!keepGoing2(uid)){
                    process.destroy();
                    return result;
                }
                updateCom(result.toString(),addrcollect);
            }
            process.waitFor();  // 等待进程执行完毕
            timer.cancel(); // 取消计时器
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    //更新运行进度
    private void updateTime(long startTime, long currentTime, int settime, String uid) {
        AddrCollect temp = selectByUid(uid);
        if(temp.getTasksituation().equals("被终止") || temp.getTasksituation().equals("已完成")){return;}
            long elapsedTime = currentTime - startTime; // 计算已经过去的时间
            int rate = (int) ((((double)elapsedTime / (double)settime))/10);
            AddrCollect ac = new AddrCollect();
            ac.setRate(rate);
            ac.setUniqueID(uid);
            addrCollectMapper.updateTaskList(ac);
    }

    //更新tasklist中的控制台输出，便于前台实时获取
    public void updateCom(String tempcmd,AddrCollect addrcollect){
        addrcollect.setCommandresponse(tempcmd);
        addrCollectMapper.updateCom(addrcollect);
    }

    //用户将地址存入list
    public static String wirteToList(ArrayList<String> ipaddress,ArrayList<AddrCollectDetail> acd,String uid){
        for (String item : ipaddress) {
            acd.add(new AddrCollectDetail(uid,item));
        }
        return uid;
    }

    //收集时长转化为秒
    public static int toSec(int time,String type){
        if(type.equals("hour"))
            return time*3600;
        else if(type.equals("min"))
            return time*60;
        else if(type.equals("sec"))
            return time;
        return 0;
    }

    /** TaskController注册方法，返回任务状况列表 */
    @Override
    public List<AddrCollect> selectCollectList(AddrCollect addrcollect) {
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList()--Start reading from database");
        List<AddrCollect> aclist = addrCollectMapper.selectCollectList(addrcollect);
        return aclist;
    }

    /** TaskController注册方法，返回任务状况列表1 */
    @Override
    public List<AddrCollect> selectCollectList1(AddrCollect addrcollect) {
        addrcollect.setSource("出口流量和交换机");
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList1()--Start reading from database");
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList1()--Showing tasklist + "+addrCollectMapper.selectCollectList(addrcollect).toString());
        return addrCollectMapper.selectCollectList(addrcollect);
    }

    /** TaskController注册方法，返回任务状况列表2 */
    @Override
    public List<AddrCollect> selectCollectList2(AddrCollect addrcollect) {
        addrcollect.setSource("已有资产");
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList2()--Start reading from database");
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList2()--Showing tasklist + "+addrCollectMapper.selectCollectList(addrcollect).toString());
        return addrCollectMapper.selectCollectList(addrcollect);
    }

    /** TaskController注册方法，返回所有已收集到的地址 */
    @Override
    public List<String> selectIps() {
        List<String> str = new ArrayList<>();
        List<AddrCollectDetail2> acd2 = acd2m.selectIps();
        for(int i=0;i<acd2.size();i++){
            str.add(acd2.get(i).getDetail());
        }
        return str;
    }
    @Override
    public List<String> selectIps2() {
        List<String> str = new ArrayList<>();
        List<AddrCollectDetail> acd = addrCollectDetailMapper.selectIps2();
        for(int i=0;i<acd.size();i++){
            if(acd.get(i).getIpAddr().equals("本次任务未收集到IP地址")){
                continue;
            }
            str.add(acd.get(i).getIpAddr());
        }
        return str;
    }

    /** TaskController注册方法，将当前任务细节插入数据库 */
    @Override
    public void inserTask(AddrCollect addrcollect) {
        System.out.println("[Sys]inserTask()--Start writing task to DB");
        System.out.println("[Sys]inserTask()--Incoming object :"+addrcollect.toString());
        addrcollect.setRate(0);
        writeTask(addrcollect);
    }

    /** TaskController注册方法，插入任务（已有资产收集） */
    @Override
    public void inserTask2(AddrCollect addrcollect) {
        System.out.println("[Sys]inserTask2()--Start writing task to DB");
        System.out.println("[Sys]inserTask2()--Incoming object :"+addrcollect.toString());
        addrcollect.setRate(0);
        writeTask2(addrcollect);
        System.out.println("[Sys]inserTask2()--write task info done");
    }

    /** TaskController注册方法，删除一条记录 */
    @Transactional
    @Override
    public int deleteTaskByUid(String taskId) {
        System.out.println("[DB]deleteTaskByUid--Start deleting task,uid="+taskId);
        if(!taskqueue.isEmpty()){
            if(taskqueue.getFront().equals(taskId)){
                taskqueue.dequeue();
            }
        }
        addrCollectMapper.deleteTaskByUid(taskId);
        if(hasInfoT(taskId))
            actm.deleteTadeByUid(taskId);
        if(hasInfo2(taskId))
            acd2m.deleteDetail2ByUid(taskId);
        if(hasInfo1(taskId))
            addrCollectDetailMapper.deleteDetailByUid(taskId);
        return 1;
    }

    //判别detai1中是否存在该任务信息
    public boolean hasInfo1(String taskId){
        List<AddrCollectDetail> acdl = addrCollectDetailMapper.selectDetail(taskId);
        if(acdl.size() != 0)
            return true;
        else
            return false;
    }

    //判别detai2中是否存在该任务信息
    public boolean hasInfo2(String taskId){
        AddrCollectDetail2 acd2 = new AddrCollectDetail2();
        acd2.setUid(taskId);
        List<AddrCollectDetail2> acdl2 = acd2m.selectDetail2(acd2);
        if(acdl2.size() != 0)
            return true;
        else
            return false;
    }

    //判断tade中是否存在该任务信息
    public boolean hasInfoT(String taskId){
        AddrCollectTade act = actm.selectTade(taskId);
        if(act == null)
            return false;
        else
            return true;
    }

    /** TaskController注册方法，返回tade列表 */
    @Override
    public List<AddrCollectTade> selectTadeList(AddrCollectTade act) {
        System.out.println("[DB]AddrCollectServiceImpl: selectCollectList()--Start reading tadelist from database");
        return actm.selectTadeList(act);
    }

    /** TaskController注册方法，显示任务的控制台输出 */
    @Override
    public String getCommand(String uid) {
        System.out.println("[DB]getCommand()--Receive UID:"+uid);
        AddrCollect ac = selectByUid(uid);
        System.out.println("[DB]getCommand()--CMD Response:"+ac.getCommandresponse());
        return ac.getCommandresponse();
    }
    @Override
    public List<AddrCollectDetail2> getCommand2(String uid) {
        AddrCollectDetail2 acd2 = new AddrCollectDetail2();
        acd2.setUid(uid);
        return acd2m.selectDetail2(acd2);
    }

    /** TaskController注册方法，显示任务收集到的IP地址 */
    @Override
    public List<String> getDetail(String uid) {
        List<String> ips = new ArrayList<>();
        System.out.println("[DB]getDetail()--Receive UID:"+uid);
        List<AddrCollectDetail> acd = addrCollectDetailMapper.selectDetail(uid);
        for(int i=0;i<acd.size();i++){
            ips.add(acd.get(i).getIpAddr());
        }
        return ips;
    }
    @Override
    public List<String> getDetail2(String uid) {
        AddrCollectDetail2 acd2f = new AddrCollectDetail2();
        List<String> ips = new ArrayList<>();
        System.out.println("[DB]getDetail2()--Receive UID:"+uid);
        acd2f.setUid(uid);
        List<AddrCollectDetail2> acd2 = acd2m.selectDetail2(acd2f);
        for(int i=0;i<acd2.size();i++){
            if(acd2.get(i).getIsalive().equals("alive")){
                ips.add(acd2.get(i).getDetail());
            }
        }
        if(ips.size()==0){
            ips.add("本次任务未收集到存活的IP地址");
        }
        return ips;
    }

    /** TaskController注册方法，任务运行中显示控制台回显 */
    @Override
    public String getTempcom(String uid) {
        AddrCollect ac = selectByUid(uid);
        return ac.getCommandresponse();
    }

    /** TaskController注册方法，终止正在运行的任务 */
    @Override
    public void terminateTask(String uid) {
        if(!taskqueue.isEmpty()){
            if(taskqueue.getFront().equals(uid)){
                taskqueue.dequeue();
            }
        }
        AddrCollect ac = selectByUid(uid);
        ac.setTasksituation("被终止");
        addrCollectMapper.updateTaskList(ac);
    }
    @Override
    public void terminateTask2(String uid) {
        AddrCollect ac = selectByUid(uid);
        ac.setTasksituation("被终止");
        addrCollectMapper.updateTaskList(ac);
    }

    /** TaskController注册方法，返回所有detail2 */
    @Override
    public List<AddrCollect> selectDeList2() {
        return acd2m.selectAll();
    }

    /** TaskController注册方法，删除任务的Tade和收集到的IP地址信息 */
    @Transactional
    @Override
    public int deleteDetailByUid(String uid) {
        System.out.println("[DB]deleteDetailByUid--Start deleting tade and Ipaddr,uid="+uid);
        addrCollectMapper.deleteTaskByUid(uid);
        addrCollectDetailMapper.deleteDetailByUid(uid);
        return actm.deleteTadeByUid(uid);
    }

    /** TaskController注册方法，返回任务的执行状态 */
    @Override
    public String showTask(String uid) {
        AddrCollect ac = selectByUid(uid);
        return ac.getTasksituation();
    }

    /** TaskController注册方法，开始收集（已有资产收集）*/
    @Override
    public void StartCollecting2(AddrCollect addrcollect) {
        System.out.println("[Sys]StartCollecting2()--Start to collect IP addr using nmap!");
        String uid = taskqueue2.dequeue();
        List<String> alist = null;
        try {
            alist = getIPv6Range(addrcollect.getTimetype(),addrcollect.getCollectime());//从地址区间取出每一条地址
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if(keepGoing2(uid)){
            for(int i=0;i<alist.size();i++){
                double rate=0.0;
                double son = i+1;
                double mom = alist.size();
                if(keepGoing2(uid)){
                    //生成命令并进行探测，返回控制台输出
                    String result = detectAddr(alist.get(i)).toString();
                    if(keepGoing2(uid)){
                        //将探测结果储存到数据库，判断存活性
                        saveAddr(alist.get(i),result,uid);
                        rate = (son/mom)*100;
                        System.out.println(son+";;;;;"+mom+";;;;;"+(int) rate+"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        updateRate2(uid,(int) rate);
                    }
                }
            }
            //更新任务状态
            AddrCollectTade act = new AddrCollectTade();
            act.setSource(addrcollect.getSource());
            act.setCommand("已有资产控制台另存");
            act.setTstime(addrcollect.getStartime());
            act.setTaskname(addrcollect.getFilename());
            if(keepGoing2(uid)){
                writeTade(act,uid);
                updateTask2(uid,addrcollect);
            }
            //System.out.println("[Sys]dequeue"+taskqueue2.dequeue());
        }
    }

    //已有資產更新運行進度
    public void updateRate2(String uid,int rate){
        AddrCollect ac = addrCollectMapper.selectByID(uid);
        //改成當前rate
        ac.setRate(rate);
        addrCollectMapper.updateTaskList(ac);
    }

    //当前任务是否需要继续进行
    public boolean keepGoing2(String uid){
        AddrCollect ac = addrCollectMapper.selectByID(uid);
        if(ac == null)
            return false;
        if(ac.getTasksituation().equals("被终止"))
            return false;
        else
            return true;
    }

    //更新task信息
    public String updateTask2(String random,AddrCollect addrcollect){
        System.out.println("[DB]AddrCollectServiceImpl: updateTask()--Start updating tasklist,uid="+random);
        addrcollect.setUniqueID(random);
        addrcollect.setTasksituation("运行完毕");
        addrcollect.setRate(100);
        addrcollect.setCommandresponse("已有资产控制台另存");
        //[DB]writeToDataBase-Temp write task info to table(addr_collect_tasklist)
        addrCollectMapper.updateTaskList(addrcollect);
        System.out.println("[DB]AddrCollectServiceImpl: updateTask()--Start updating tasklist,uid="+random);
        return random;
    }

    //记录存活和未存活的地址
    public void saveAddr(String addr,String result,String uid){
        AddrCollectDetail2 acd2 = new AddrCollectDetail2();
        acd2.setUid(uid);
        acd2.setDetail(addr);
        acd2.setCommand(result);
        if(isAlive(result)){
            acd2.setIsalive("alive");
        }else{
            acd2.setIsalive("dead");
        }
        acd2m.insertDetail2(acd2);
    }

    /** TaskController注册方法，开始从出口流量和交换机收集IP地址 */
    @Override
    public void StartCollecting(AddrCollect addrcollect) {
        AddrCollectTade act = new AddrCollectTade();
        String uid = taskqueue.getFront();
        ArrayList<AddrCollectDetail> acd = new ArrayList<>();
        System.out.println("[Sys]Start collecting IP address");
        System.out.println("[Sys]StartCollecting()--Incoming object :"+addrcollect.toString());
        StringBuffer results = runTCPDump(toSec(Integer.valueOf(addrcollect.getCollectime()), addrcollect.getTimetype()),addrcollect,uid); //设定运行时间，运行tcpdump并获取运行结果
        if(keepGoing2(uid)){
            if(!taskqueue.isEmpty()){
                if(taskqueue.getFront().equals(uid))
                    taskqueue.dequeue();
            }
            ArrayList<String> ipaddress = getIPAddress(results.toString()); //从tcpdump的运行结果中获得所有IP地址，存入ipaddress待用
            System.out.println("[Sys]StartCollecting()--Queue is empty:"+taskqueue.isEmpty());
            wirteToList(ipaddress,acd,uid);
            if(acd.size() == 0){
                acd.add(new AddrCollectDetail(uid,"本次任务未收集到IP地址"));
                other(addrcollect, act, uid, acd, results, ipaddress);
                //return results.toString();
            }
            else{
                other(addrcollect, act, uid, acd, results, ipaddress);
                //return results.toString();
            }
        }
        else {
            if(!taskqueue.isEmpty()){
                System.out.println("[Sys]StartCollecting()--Progress terminating");
                if(taskqueue.getFront().equals(uid))
                    taskqueue.dequeue();
                System.out.println("[Sys]StartCollecting()--Progress terminated");
                System.out.println("[Sys]StartCollecting()--taskqueue empty?--"+taskqueue.isEmpty());
            }
        }
    }

    //更新出口流量和交換機監聽任務運行進度
    public void updateRate(String uid,int rate){
        AddrCollect ac = addrCollectMapper.selectByID(uid);
        if(ac.getTasksituation().equals("已完成"))
            return;
        //改成當前rate
        ac.setRate(rate);
        addrCollectMapper.updateTaskList(ac);
    }

    //工具方法
    public void other(AddrCollect addrcollect, AddrCollectTade act, String uid, ArrayList<AddrCollectDetail> acd, StringBuffer results, ArrayList<String> ipaddress) {
        if(keepGoing2(uid)){
            writeAddr(acd);
            updateTask(uid,addrcollect,results.toString());
            writeTade(act,uid);
            System.out.println(results);
            for(int i=0;i<ipaddress.size();i++){
                System.out.println(ipaddress.get(i));
            }
            System.out.println();
            System.out.println("[Sys]StartCollecting()--IP all extracted and saved");
        }
    }

    //获取当前系统时间，并格式化
    public static Date getCurrentTime() {
        LocalDate date = LocalDate.now();
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //用户自定义任务细节，写入数据库
    public void writeTask(AddrCollect addrcollect){
        if( !taskqueue.isFull()){
            String random = randomString();
            Date date = getCurrentTime();
            addrcollect.setStartime(date);
            addrcollect.setTasksituation("运行中");
            addrcollect.setCommandresponse("等待控制台更新...");
            addrcollect.setUniqueID(random);
            addrcollect.setSource("出口流量和交换机");
            //[DB]writeToDataBase-Temp write task info to table(addr_collect_tasklist)
            if(!taskqueue.isFull()){
                addrCollectMapper.insertIntoTaskList(addrcollect);
                System.out.println("[Sys]writeTask()--TaskQueue is empty? "+taskqueue.isEmpty());
                taskqueue.enqueue(random);
            }
        }
    }

    //用户自定义任务细节，写入数据库(已有资产)
    public void writeTask2(AddrCollect addrcollect){
        System.out.println("[Sys]writeTask()--TaskQueue is full? "+taskqueue2.isFull());
        if( !taskqueue2.isFull()){
            String IPRange = addrcollect.getTimetype()+"-"+addrcollect.getCollectime();
            String random = randomString();
            Date date = getCurrentTime();
            addrcollect.setStartime(date);
            addrcollect.setTasksituation("运行中");
            addrcollect.setCommandresponse("请等待任务运行完毕");
            addrcollect.setUniqueID(random);
            addrcollect.setSource("已有资产");
            //[DB]writeToDataBase-Temp write task info to table(addr_collect_tasklist)
            if( !taskqueue2.isFull() ){
                addrCollectMapper.insertIntoTaskList(addrcollect);
                System.out.println("[Sys]writeTask2()--TaskQueue2 is empty? "+taskqueue2.isEmpty());
                System.out.println("[Sys]enqueue"+random);
                taskqueue2.enqueue(random);
            }
        }
    }

    //Tade数据写入数据库
    public void writeTade(AddrCollectTade act,String uid){
        AddrCollect ac = selectByUid(uid);
        act.setTaskname(ac.getFilename());
        act.setTstime(ac.getStartime());
        act.setUid(uid);
        act.setCommand(ac.getCommandresponse());
        act.setSource(ac.getSource());
        actm.insertIntoTade(act);
    }

    //根据uid从数据库中查找到对应的记录
    public AddrCollect selectByUid(String uid){
        AddrCollect ac = addrCollectMapper.selectByID(uid);
        return ac;
    }

    //用户将收集到的地址细节写入数据库
    public void writeAddr(ArrayList<AddrCollectDetail> acd){
        addrCollectDetailMapper.batchInsertDetail(acd);
    }

    //生成一个16位的随机数作为任务唯一标识
    public static String randomString() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[12];
        random.nextBytes(bytes); // 生成12个随机字节
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes); // 将字节数组转换为URL安全的Base64编码的字符串
        return randomString.substring(0, 16); // 截取字符串的前16个字符作为随机字符串
    }

    //用户自定义文件名，地址写入数据库
    public String updateTask(String random,AddrCollect addrcollect, String command){
        System.out.println("[DB]AddrCollectServiceImpl: updateTask()--Start updating tasklist,uid="+random);
        addrcollect.setUniqueID(random);
        addrcollect.setTasksituation("运行完毕");
        addrcollect.setRate(100);
        if(command==null || command.equals(""))
            addrcollect.setCommandresponse("本次任务未收集到IP地址!");
        else
            addrcollect.setCommandresponse(command);
        //[DB]writeToDataBase-Temp write task info to table(addr_collect_tasklist)
        addrCollectMapper.updateTaskList(addrcollect);
        System.out.println("[DB]AddrCollectServiceImpl: updateTask()--Start updating tasklist,uid="+random);
        return random;
    }

    //判断nmap扫描的地址是否存活
    public static boolean isAlive(String result){
        String[] str = extractParenthesis(result);
        for(int i=0;i<str.length;i++){
            if(str[i].equals("(1 host up)"))
                return true;
        }
        return false;
    }

    //使用nmap探测地址区间
    public StringBuffer detectAddr(String addr){
        String command = "nmap -6 "+addr; //-Pn
        System.out.println("[Sys]Generating Nmap command:"+command);
        StringBuffer result = new StringBuffer();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);  //设置要执行的命令
            processBuilder.directory(null); // 默认为当前工作目录
            Process process = processBuilder.start();  // 启动进程
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));  //获取命令执行的输出结果
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line+"\n");
                //System.out.println(line);
            }
            process.waitFor();  // 等待进程执行完毕
            process.destroy();  // 终止进程
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}