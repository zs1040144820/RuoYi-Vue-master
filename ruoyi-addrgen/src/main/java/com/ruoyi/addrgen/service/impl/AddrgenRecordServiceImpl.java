package com.ruoyi.addrgen.service.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.*;

import com.ruoyi.addrgen.domain.AddrgenRecordDetect;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.mapper.AddrgenRecordDetailsMapper;
import com.ruoyi.addrgen.mapper.AddrgenRecordDetectMapper;
import com.ruoyi.addrgen.utils.ConversionTime;
import com.ruoyi.addrgen.utils.NmapDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;
import com.ruoyi.addrgen.mapper.AddrgenRecordMapper;
import com.ruoyi.addrgen.domain.AddrgenRecord;
import com.ruoyi.addrgen.service.IAddrgenRecordService;

/**
 * 生成记录Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-11-04
 */
@Service
public class AddrgenRecordServiceImpl implements IAddrgenRecordService 
{
    @Autowired
    private AddrgenRecordMapper addrgenRecordMapper;
    @Autowired
    private AddrgenRecordDetailsMapper detailsMapper;
    @Autowired
    private AddrgenRecordDetectMapper detectMapper;

    /**
     * 查询生成记录
     * 
     * @param recordId 生成记录主键
     * @return 生成记录
     */
    @Override
    public AddrgenRecord selectAddrgenRecordByRecordId(Long recordId)
    {
        return addrgenRecordMapper.selectAddrgenRecordByRecordId(recordId);
    }

    /**
     * 查询生成记录列表
     * 
     * @param addrgenRecord 生成记录
     * @return 生成记录
     */
    @Override
    public List<AddrgenRecord> selectAddrgenRecordList(AddrgenRecord addrgenRecord)
    {
        return addrgenRecordMapper.selectAddrgenRecordList(addrgenRecord);
    }

    /**
     * 新增生成记录
     * 
     * @param addrgenRecord 生成记录
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAddrgenRecord(AddrgenRecord addrgenRecord)
    {
        int rows = addrgenRecordMapper.insertAddrgenRecord(addrgenRecord);
        //insertAddrgenRecordDetails(addrgenRecord);
        return rows;
    }

    /**
     * 修改生成记录
     * 
     * @param addrgenRecord 生成记录
     * @return 结果
     */
    @Transactional
    @Override
    public int updateAddrgenRecord(AddrgenRecord addrgenRecord)
    {
        addrgenRecordMapper.deleteAddrgenRecordDetailsByRecordId(addrgenRecord.getRecordId());
        insertAddrgenRecordDetails(addrgenRecord);
        return addrgenRecordMapper.updateAddrgenRecord(addrgenRecord);
    }

    /**
     * 批量删除生成记录
     * 
     * @param recordIds 需要删除的生成记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAddrgenRecordByRecordIds(Long[] recordIds)
    {
        addrgenRecordMapper.deleteAddrgenRecordDetailsByRecordIds(recordIds);
        return addrgenRecordMapper.deleteAddrgenRecordByRecordIds(recordIds);
    }

    /**
     * 删除生成记录信息
     * 
     * @param recordId 生成记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAddrgenRecordByRecordId(Long recordId)
    {
        addrgenRecordMapper.deleteAddrgenRecordDetailsByRecordId(recordId);
        return addrgenRecordMapper.deleteAddrgenRecordByRecordId(recordId);
    }

    @Override
    public void detectIP(Long recordId) throws IOException {
        List<AddrgenRecordDetails> detailsList = detailsMapper.selectdetailsByRecordId(recordId);
        Iterator<AddrgenRecordDetails> iterator = detailsList.iterator();
        Pattern pattern  =Pattern.compile("Host is up");
        Pattern host_timeout = Pattern.compile("host timeout");
        Long total = new Long(0);
        Long hitnum = new Long(0);
        Date startTime = new Date();
        while (iterator.hasNext()) {
            total++;
            AddrgenRecordDetails details = iterator.next();
            String addr = details.getIpaddr();
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
                hitnum++;
                details.setIsactive("1");//活跃
            }else {
                details.setIsactive("0");//不活跃
            }
            detailsMapper.updateAddrgenRecordDetails(details);
        }
        AddrgenRecordDetect addrgenRecordDetect = new AddrgenRecordDetect();
        Date endTime = new Date();
        addrgenRecordDetect.setConsume(ConversionTime.formatTime((endTime.getTime() - startTime.getTime())));
        addrgenRecordDetect.setRecordId(recordId);
        String inputFilename = addrgenRecordMapper.selectAddrgenRecordByRecordId(recordId).getInputFilename();
        addrgenRecordDetect.setInputFile(inputFilename);
        addrgenRecordDetect.setDetectTime(startTime);
        addrgenRecordDetect.setHitNum(hitnum);
        addrgenRecordDetect.setTotal(total);
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);//最多两位百分小数，如25.23%
        Double hit1 = new Double(hitnum);
        Double total1 = new Double(total);
        String hitrate = fmt.format(hit1/total1);
        addrgenRecordDetect.setHitRate(hitrate);
        detectMapper.insertAddrgenRecordDetect(addrgenRecordDetect);
    }

    @Override
    public String aliasDetect(Long recordId) throws IOException {
        List<AddrgenRecordDetails> detailsList = detailsMapper.selectdetailsByRecordId(recordId);
        Iterator<AddrgenRecordDetails> iterator = detailsList.iterator();
        //1.初始化存放Root的数组
        ArrayList<String> seedSetList = new ArrayList<>();
        while (iterator.hasNext()){
            seedSetList.add(iterator.next().getIpaddr());
        }
        String[] Root = (String[]) seedSetList.toArray(new String[0]);
        //2.提取所有网络前缀
        ArrayList<String> Prefixes = new ArrayList<>();
        extractPrefix(Root,Prefixes);
        System.out.println("[!]All Prefixes included:");
        for(int i=0;i<Prefixes.size();i++){
            System.out.println("   "+Prefixes.get(i));
        }
        //3.对每个网络前缀随机拼接出3条地址，预备输入nmap进行探测
        System.out.println("[!]Start generating random addresses;");
        ArrayList<String> RandomAddr= generateRandomAddr(Prefixes);
        for(int i=0;i<RandomAddr.size();i++){
            System.out.println("   "+RandomAddr.get(i));
        }
        //4.一次取三条地址进行探测，探测到活跃即记录下前缀，存入arraylist
        System.out.println("[!]Start detect active addresses:");
        ArrayList<String> ActivePrefixes = new ArrayList<>(); //别名区前缀存入该动态数组
        for(int i=0;i<RandomAddr.size();i+=3){
            System.out.println(RandomAddr.get(i));
            System.out.println(RandomAddr.get(i+1));
            System.out.println(RandomAddr.get(i+2));
            System.out.println("\n");
            boolean b1 = NmapDetect.detect(RandomAddr.get(i));
            boolean b2 = NmapDetect.detect(RandomAddr.get(i+1));
            boolean b3 = NmapDetect.detect(RandomAddr.get(i+2));
            System.out.println(b1);
            System.out.println(b2);
            System.out.println(b3);
            if(b1||b2||b3){
                String[] arr = RandomAddr.get(i).split(":");
                ActivePrefixes.add(arr[0]+":"+arr[1]+":"+arr[2]+":"+arr[3]+":"+arr[4]+":"+arr[5]+":");
            }
        }
        System.out.println("[!]Print Alias prefixes:");
        String resultAlias = "别名区（用\"/\"分隔）：";
        for(int i=0;i< ActivePrefixes.size();i++){
            System.out.println(ActivePrefixes.get(i));
            resultAlias = resultAlias+"/"+ActivePrefixes.get(i);
        }
        AddrgenRecord addrgenRecord = new AddrgenRecord();
        addrgenRecord.setRecordId(recordId);
        addrgenRecord.setAlias(resultAlias);
        System.out.println("resultAlias"+resultAlias);
        addrgenRecordMapper.updateAddrgenRecord(addrgenRecord);
        return null;
    }

    /**
     * 新增生成文件详情信息
     * 
     * @param addrgenRecord 生成记录对象
     */
    public void insertAddrgenRecordDetails(AddrgenRecord addrgenRecord)
    {
        List<AddrgenRecordDetails> addrgenRecordDetailsList = addrgenRecord.getAddrgenRecordDetailsList();
        Long recordId = addrgenRecord.getRecordId();
        if (StringUtils.isNotNull(addrgenRecordDetailsList))
        {
            List<AddrgenRecordDetails> list = new ArrayList<AddrgenRecordDetails>();
            for (AddrgenRecordDetails addrgenRecordDetails : addrgenRecordDetailsList)
            {
                addrgenRecordDetails.setRecordId(recordId);
                list.add(addrgenRecordDetails);
            }
            if (list.size() > 0)
            {
                addrgenRecordMapper.batchAddrgenRecordDetails(list);
            }
        }
    }

    //别名区训练需要用到的函数-------------------------
    //十进制转化为十六进制
    public static String intToHexString(int n) {
        if(n==10) return "A";
        if(n==11) return "B";
        if(n==12) return "C";
        if(n==13) return "D";
        if(n==14) return "E";
        if(n==15) return "F";
        return String.valueOf(n);
    }

    //提取所有网络前缀
    public static void extractPrefix(String[] Root,ArrayList<String> Prefixes){
        for(int i=0;i<Root.length;i++){
            String[] arr = Root[i].split(":");
            if(Prefixes.contains(arr[0]+":"+arr[1]+":"+arr[2]+":"+arr[3]+":"+arr[4]+":"+arr[5]+":")){
                continue;
            } else {
                Prefixes.add(arr[0]+":"+arr[1]+":"+arr[2]+":"+arr[3]+":"+arr[4]+":"+arr[5]+":");
            }
        }
    }

    //生成随机待测地址
    public static ArrayList<String> generateRandomAddr(ArrayList<String> Prefixes){
        ArrayList<String> RandomAddr = new ArrayList<>();
        for(int i=0;i<Prefixes.size();i++){
            Random random=new Random();
            StringBuilder str=new StringBuilder();  //变长字符串str
            for(int k=0;k<3;k++){
                for(int j=0;j<8;j++){   //随机生成数字，并添加到字符串
                    if(j==4) str.append(":");
                    str.append(intToHexString(random.nextInt(16)));
                }
                RandomAddr.add(Prefixes.get(i)+str.toString());
                str= new StringBuilder("");
            }
        }
        return RandomAddr;
    }
    //别名区训练需要用到的函数-------------------------
}
