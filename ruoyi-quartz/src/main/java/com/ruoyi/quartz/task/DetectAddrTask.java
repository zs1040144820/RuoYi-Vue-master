package com.ruoyi.quartz.task;

import com.ruoyi.addrgen.domain.AddrgenSeedfileCollect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.AddrgenSeedfileHandle;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileCollectMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetailsMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileHandleMapper;
import com.ruoyi.quartz.util.ConversionTime;
import inet.ipaddr.IPAddressString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

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
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("detectAddrTask")
public class DetectAddrTask
{
    @Autowired
    private AddrgenSeedfileDetailsMapper addrgenSeedfileDetailsMapper;
    @Autowired
    private AddrgenSeedfileCollectMapper collectMapper;
    @Autowired
    private AddrgenSeedfileHandleMapper handleMapper;

    public void detectIP(String id) throws IOException {
        long activeNums = 0;
        long total = 0;
        Long lid = Long.valueOf(id);
        //得到文件主键，查出列表
        List<AddrgenSeedfileDetails> list = addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsListByFileId(lid);
        //取出每个地址details，加入格式化后的地址
        Iterator<AddrgenSeedfileDetails> iterator = list.iterator();
        Pattern pattern  =Pattern.compile("Host is up");
        Pattern host_timeout = Pattern.compile("host timeout");
        Date startTime = new Date();
        while (iterator.hasNext()) {
            total++;
            AddrgenSeedfileDetails details = iterator.next();
            String addr = details.getStandardIpaddress();
            System.out.println("nmap -6 -T4" + addr);
            Process p = Runtime.getRuntime().exec("nmap -6 -T4 --host-timeout 30 " + addr);
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
                activeNums++;
                details.setCollectis("1");//活跃
            }else {
                details.setCollectis("0");//不活跃
            }
            Date detectTime = new Date();
            details.setCollectTime(detectTime);
            addrgenSeedfileDetailsMapper.updateAddrgenSeedfileDetails(details);
        }
        Date endTime = new Date();
        AddrgenSeedfileCollect collect = new AddrgenSeedfileCollect();
        collect.setActiveIpnum(activeNums);
        collect.setTotalIpnum(total);
        collect.setAddrSeedfileId(lid);
        collect.setCollectStatus("1");
        collect.setAddrSeedfileName(handleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(lid).getAddrSeedfileName());
        collect.setCollectConsuming(ConversionTime.formatTime((endTime.getTime() - startTime.getTime())));
        collect.setCollectTime(endTime);
        collectMapper.updateAddrgenSeedfileCollectByfileID(collect);
    }
}
