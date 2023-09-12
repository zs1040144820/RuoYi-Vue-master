package com.ruoyi.quartz.task;

import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileCollectMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetailsMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetectMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileHandleMapper;
import com.ruoyi.quartz.util.ConversionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 存活性探测任务
 * @author: lzh
 * @date: 2022年10月28日 13:31
 */
@Component("activeDetectTask")
public class ActiveDetectTask {
    @Autowired
    private AddrgenSeedfileDetailsMapper addrgenSeedfileDetailsMapper;
    @Autowired
    private AddrgenSeedfileDetectMapper detectMapper;
    @Autowired
    private AddrgenSeedfileHandleMapper handleMapper;

    public void detectIP(String id) throws IOException {
        /*Thread detectThread = new Thread(new Runnable(){
                public void run(){

            }
        });
        detectThread.start();*/
        //int i= 0;
        //long activeNums = 0;
        //long total = 0;
        Long lid = Long.valueOf(id);
        Long i1 = handleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(lid).getDetectTimes();
        int i = i1.intValue();
        System.out.println("i="+i);
        AddrgenSeedfileHandle handle = new AddrgenSeedfileHandle();
        handle.setAddrSeedfileId(lid);
        handle.setDetectTimes(i1+1);
        handleMapper.updateAddrgenSeedfileHandle(handle);
        //得到文件主键，查出列表
        List<AddrgenSeedfileDetails> list = addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsListByFileId(lid);
        //取出每个地址details，加入格式化后的地址
        Iterator<AddrgenSeedfileDetails> iterator = list.iterator();
        Pattern pattern  =Pattern.compile("Host is up");
        Pattern host_timeout = Pattern.compile("host timeout");
        Date startTime = new Date();
        while (iterator.hasNext()) {
            AddrgenSeedfileDetails details = iterator.next();
            String addr = details.getStandardIpaddress();
            System.out.println("nmap -6 -T4" + addr);
            Process p = null;
            try {
                p = Runtime.getRuntime().exec("nmap -6 -T4 --host-timeout 30 " + addr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final InputStream stream = p.getInputStream();
            BufferedReader reader = null;
            String s = "", line = null;
            try {
                reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("GBK")));
                while ((line = reader.readLine()) != null) {
                    s = s + line;
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            Matcher matcher = pattern.matcher(s);
            Matcher matcher1 = host_timeout.matcher(s);
            boolean b = matcher.find();
            boolean b1 = matcher1.find();
            System.out.println(b);
            System.out.println(b1);
            if (b && (!b1)){
                switch (i%30){
                    case 0:
                        details.setDetect0("2");
                        break;
                    case 1:
                        details.setDetect1("2");
                        break;
                    case 2:
                        details.setDetect2("2");
                        break;
                    case 3:
                        details.setDetect3("2");
                        break;
                    case 4:
                        details.setDetect4("2");
                        break;
                    case 5:
                        details.setDetect5("2");
                        break;
                    case 6:
                        details.setDetect6("2");
                        break;
                    case 7:
                        details.setDetect7("2");
                        break;
                    case 8:
                        details.setDetect8("2");
                        break;
                    case 9:
                        details.setDetect9("2");
                        break;
                    case 10:
                        details.setDetect10("2");
                        break;
                    case 11:
                        details.setDetect11("2");
                        break;
                    case 12:
                        details.setDetect12("2");
                        break;
                    case 13:
                        details.setDetect13("2");
                        break;
                    case 14:
                        details.setDetect14("2");
                        break;
                    case 15:
                        details.setDetect15("2");
                        break;
                    case 16:
                        details.setDetect16("2");
                        break;
                    case 17:
                        details.setDetect17("2");
                        break;
                    case 18:
                        details.setDetect18("2");
                        break;
                    case 19:
                        details.setDetect19("2");
                        break;
                    case 20:
                        details.setDetect20("2");
                        break;
                    case 21:
                        details.setDetect21("2");
                        break;
                    case 22:
                        details.setDetect22("2");
                        break;
                    case 23:
                        details.setDetect23("2");
                        break;
                    case 24:
                        details.setDetect24("2");
                        break;
                    case 25:
                        details.setDetect25("2");
                        break;
                    case 26:
                        details.setDetect26("2");
                        break;
                    case 27:
                        details.setDetect27("2");
                        break;
                    case 28:
                        details.setDetect28("2");
                        break;
                    case 29:
                        details.setDetect29("2");
                        break;
                }
            }else {
                switch (i%30){
                    case 0:
                        details.setDetect0("1");
                        break;
                    case 1:
                        details.setDetect1("1");
                        break;
                    case 2:
                        details.setDetect2("1");
                        break;
                    case 3:
                        details.setDetect3("1");
                        break;
                    case 4:
                        details.setDetect4("1");
                        break;
                    case 5:
                        details.setDetect5("1");
                        break;
                    case 6:
                        details.setDetect6("1");
                        break;
                    case 7:
                        details.setDetect7("1");
                        break;
                    case 8:
                        details.setDetect8("1");
                        break;
                    case 9:
                        details.setDetect9("1");
                        break;
                    case 10:
                        details.setDetect10("1");
                        break;
                    case 11:
                        details.setDetect11("1");
                        break;
                    case 12:
                        details.setDetect12("1");
                        break;
                    case 13:
                        details.setDetect13("1");
                        break;
                    case 14:
                        details.setDetect14("1");
                        break;
                    case 15:
                        details.setDetect15("1");
                        break;
                    case 16:
                        details.setDetect16("1");
                        break;
                    case 17:
                        details.setDetect17("1");
                        break;
                    case 18:
                        details.setDetect18("1");
                        break;
                    case 19:
                        details.setDetect19("1");
                        break;
                    case 20:
                        details.setDetect20("1");
                        break;
                    case 21:
                        details.setDetect21("1");
                        break;
                    case 22:
                        details.setDetect22("1");
                        break;
                    case 23:
                        details.setDetect23("1");
                        break;
                    case 24:
                        details.setDetect24("1");
                        break;
                    case 25:
                        details.setDetect25("1");
                        break;
                    case 26:
                        details.setDetect26("1");
                        break;
                    case 27:
                        details.setDetect27("1");
                        break;
                    case 28:
                        details.setDetect28("1");
                        break;
                    case 29:
                        details.setDetect29("1");
                        break;
                }
            }
            addrgenSeedfileDetailsMapper.updateAddrgenSeedfileDetails(details);
        }
        Iterator<AddrgenSeedfileDetails> iterator2 = list.iterator();
        while (iterator2.hasNext()){
            AddrgenSeedfileDetails details = iterator2.next();
            int sta = Integer.parseInt(details.getDetect0())/2+Integer.parseInt(details.getDetect1())/2+Integer.parseInt(details.getDetect2())/2+
                    Integer.parseInt(details.getDetect3())/2+Integer.parseInt(details.getDetect4())/2+Integer.parseInt(details.getDetect5())/2+
                    Integer.parseInt(details.getDetect6())/2+Integer.parseInt(details.getDetect7())/2+Integer.parseInt(details.getDetect8())/2+
                    Integer.parseInt(details.getDetect9())/2+Integer.parseInt(details.getDetect10())/2+Integer.parseInt(details.getDetect11())/2+
                    Integer.parseInt(details.getDetect12())/2+Integer.parseInt(details.getDetect13())/2+Integer.parseInt(details.getDetect14())/2+
                    Integer.parseInt(details.getDetect15())/2+Integer.parseInt(details.getDetect16())/2+Integer.parseInt(details.getDetect17())/2+
                    Integer.parseInt(details.getDetect18())/2+Integer.parseInt(details.getDetect19())/2+Integer.parseInt(details.getDetect20())/2+
                    Integer.parseInt(details.getDetect21())/2+Integer.parseInt(details.getDetect22())/2+Integer.parseInt(details.getDetect23())/2+
                    Integer.parseInt(details.getDetect24())/2+Integer.parseInt(details.getDetect25())/2+Integer.parseInt(details.getDetect26())/2+
                    Integer.parseInt(details.getDetect27())/2+Integer.parseInt(details.getDetect28())/2+Integer.parseInt(details.getDetect29())/2;
            String stability = String.valueOf(sta);
            details.setStability(stability);
            addrgenSeedfileDetailsMapper.updateAddrgenSeedfileDetails(details);
        }

        Date endTime = new Date();
        AddrgenSeedfileDetect detect = new AddrgenSeedfileDetect();
        detect.setDetectTime(endTime);
        detect.setAddrSeedfileId(lid);
        detect.setDetectStatus("3");
        detect.setDetectConsume(ConversionTime.formatTime((endTime.getTime() - startTime.getTime())));
        detect.setAddrSeedfileName(handleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(lid).getAddrSeedfileName());
        detectMapper.updateAddrgenSeedfileDetectByFileId(detect);
    }
}
