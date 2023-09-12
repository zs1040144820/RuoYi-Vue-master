package com.ruoyi.addrgen.service.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ruoyi.addrgen.domain.*;
import com.ruoyi.addrgen.mapper.AddrgenRecordMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileDetailsMapper;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileFormatMapper;
import com.ruoyi.addrgen.utils.*;
import inet.ipaddr.IPAddressString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.addrgen.mapper.AddrgenSeedfileHandleMapper;
import com.ruoyi.addrgen.service.IAddrgenSeedfileHandleService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 种子地址文件总览Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2022-10-16
 */
@Service
public class AddrgenSeedfileHandleServiceImpl implements IAddrgenSeedfileHandleService 
{
    @Autowired
    private AddrgenSeedfileHandleMapper addrgenSeedfileHandleMapper;
    @Autowired
    private AddrgenSeedfileDetailsMapper addrgenSeedfileDetailsMapper;
    @Autowired
    private AddrgenSeedfileFormatMapper addrgenSeedfileFormatMapper;
    @Autowired
    private AddrgenRecordMapper addrgenRecordMapper;
    @Autowired
    private AddrgenRecordServiceImpl addrgenRecordService;

    public List<String> addrFileList = new ArrayList<>();
    private Thread commandThread;


    /**
     * 查询种子地址文件总览
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 种子地址文件总览
     */
    @Override
    public AddrgenSeedfileHandle selectAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId)
    {
        return addrgenSeedfileHandleMapper.selectAddrgenSeedfileHandleByAddrSeedfileId(addrSeedfileId);
    }

    /**
     * 查询种子地址文件总览列表
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 种子地址文件总览
     */
    @Override
    public List<AddrgenSeedfileHandle> selectAddrgenSeedfileHandleList(AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        return addrgenSeedfileHandleMapper.selectAddrgenSeedfileHandleList(addrgenSeedfileHandle);
    }

    /**
     * 新增种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        int rows = addrgenSeedfileHandleMapper.insertAddrgenSeedfileHandle(addrgenSeedfileHandle);
        insertAddrgenSeedfileDetails(addrgenSeedfileHandle);
        return rows;
    }

    /**
     * 修改种子地址文件总览
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览
     * @return 结果
     */
    @Transactional
    @Override
    public int updateAddrgenSeedfileHandle(AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        addrgenSeedfileHandleMapper.deleteAddrgenSeedfileDetailsByAddrSeedfileId(addrgenSeedfileHandle.getAddrSeedfileId());
        insertAddrgenSeedfileDetails(addrgenSeedfileHandle);
        return addrgenSeedfileHandleMapper.updateAddrgenSeedfileHandle(addrgenSeedfileHandle);
    }

    //修改但是不会先删除
    @Override
    public int updateAddrgenSeedfileHandle2(AddrgenSeedfileHandle addrgenSeedfileHandle) {
        return addrgenSeedfileHandleMapper.updateAddrgenSeedfileHandle(addrgenSeedfileHandle);
    }

    /**
     * 批量删除种子地址文件总览
     * 
     * @param addrSeedfileIds 需要删除的种子地址文件总览主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAddrgenSeedfileHandleByAddrSeedfileIds(Long[] addrSeedfileIds)
    {
        addrgenSeedfileHandleMapper.deleteAddrgenSeedfileDetailsByAddrSeedfileIds(addrSeedfileIds);
        return addrgenSeedfileHandleMapper.deleteAddrgenSeedfileHandleByAddrSeedfileIds(addrSeedfileIds);
    }

    /**
     * 删除种子地址文件总览信息
     * 
     * @param addrSeedfileId 种子地址文件总览主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAddrgenSeedfileHandleByAddrSeedfileId(Long addrSeedfileId)
    {
        addrgenSeedfileHandleMapper.deleteAddrgenSeedfileDetailsByAddrSeedfileId(addrSeedfileId);
        return addrgenSeedfileHandleMapper.deleteAddrgenSeedfileHandleByAddrSeedfileId(addrSeedfileId);
    }

    /**
     * 新增种子文件详情信息
     * 
     * @param addrgenSeedfileHandle 种子地址文件总览对象
     */
    public void insertAddrgenSeedfileDetails(AddrgenSeedfileHandle addrgenSeedfileHandle)
    {
        List<AddrgenSeedfileDetails> addrgenSeedfileDetailsList = addrgenSeedfileHandle.getAddrgenSeedfileDetailsList();
        Long addrSeedfileId = addrgenSeedfileHandle.getAddrSeedfileId();
        if (StringUtils.isNotNull(addrgenSeedfileDetailsList))
        {
            List<AddrgenSeedfileDetails> list = new ArrayList<AddrgenSeedfileDetails>();
            for (AddrgenSeedfileDetails addrgenSeedfileDetails : addrgenSeedfileDetailsList)
            {
                addrgenSeedfileDetails.setAddrSeedfileId(addrSeedfileId);
                list.add(addrgenSeedfileDetails);
            }
            if (list.size() > 0)
            {
                addrgenSeedfileHandleMapper.batchAddrgenSeedfileDetails(list);
            }
        }
    }

    @Override
    public boolean handleFile(MultipartFile file,String name,String size) throws IOException {
        //首先，先把获得的文件信息插入到数据库中
        String size1 = UnitConversion.getSize(size);
        AddrgenSeedfileHandle addrgenSeedfileHandle = new AddrgenSeedfileHandle();
        addrgenSeedfileHandle.setAddrSeedfileName(name);
        addrgenSeedfileHandle.setAddrSeedfileSize(size1);
        Date date = new Date();
        addrgenSeedfileHandle.setAddrSeedfileUploadtime(date);
        addrgenSeedfileHandle.setFormaticon("el-icon-document-checked");
        //先插入记录
        int rows = insertAddrgenSeedfileHandle(addrgenSeedfileHandle);
        List<AddrgenSeedfileDetails> detalisList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")){
                continue;
            }
            AddrgenSeedfileDetails addrgenSeedfileDetails = new AddrgenSeedfileDetails();
            addrgenSeedfileDetails.setIpaddress(line);
            addrgenSeedfileDetails.setDetect0("0");
            addrgenSeedfileDetails.setDetect1("0");
            addrgenSeedfileDetails.setDetect2("0");
            addrgenSeedfileDetails.setDetect3("0");
            addrgenSeedfileDetails.setDetect4("0");
            addrgenSeedfileDetails.setDetect5("0");
            addrgenSeedfileDetails.setDetect6("0");
            addrgenSeedfileDetails.setDetect7("0");
            addrgenSeedfileDetails.setDetect8("0");
            addrgenSeedfileDetails.setDetect9("0");
            addrgenSeedfileDetails.setDetect10("0");
            addrgenSeedfileDetails.setDetect11("0");
            addrgenSeedfileDetails.setDetect12("0");
            addrgenSeedfileDetails.setDetect13("0");
            addrgenSeedfileDetails.setDetect14("0");
            addrgenSeedfileDetails.setDetect15("0");
            addrgenSeedfileDetails.setDetect16("0");
            addrgenSeedfileDetails.setDetect17("0");
            addrgenSeedfileDetails.setDetect18("0");
            addrgenSeedfileDetails.setDetect19("0");
            addrgenSeedfileDetails.setDetect20("0");
            addrgenSeedfileDetails.setDetect21("0");
            addrgenSeedfileDetails.setDetect22("0");
            addrgenSeedfileDetails.setDetect23("0");
            addrgenSeedfileDetails.setDetect24("0");
            addrgenSeedfileDetails.setDetect25("0");
            addrgenSeedfileDetails.setDetect26("0");
            addrgenSeedfileDetails.setDetect27("0");
            addrgenSeedfileDetails.setDetect28("0");
            addrgenSeedfileDetails.setDetect29("0");
            addrgenSeedfileDetails.setStability("0");
            detalisList.add(addrgenSeedfileDetails);
            if (detalisList.size() == 500){
                addrgenSeedfileHandle.setAddrgenSeedfileDetailsList(detalisList);
                insertAddrgenSeedfileDetails(addrgenSeedfileHandle);
                detalisList.clear();
            }
        }
        //最后不满500个也要加进去
        addrgenSeedfileHandle.setAddrgenSeedfileDetailsList(detalisList);
        insertAddrgenSeedfileDetails(addrgenSeedfileHandle);

        return rows > 0 ? true : false;
    }

    @Override
    public int formatAddr(AddrgenSeedfileHandle addrgenSeedfileHandle) throws InterruptedException, IOException {
        /*commandThread = new Thread(new Runnable(){
            @Override
            public void run() {

        }
        );*/
        addrgenSeedfileHandle.setFormaticon("el-icon-loading");
        updateAddrgenSeedfileHandle2(addrgenSeedfileHandle);
        //得到文件主键，查出列表
        List<AddrgenSeedfileDetails> list = addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsListByFileId(addrgenSeedfileHandle.getAddrSeedfileId());
        List<AddrgenSeedfileDetails> batchDetailsList = new ArrayList<>();
        //取出每个地址details，加入格式化后的地址
        Iterator<AddrgenSeedfileDetails> iterator = list.iterator();
        while (iterator.hasNext()){
            AddrgenSeedfileDetails details = iterator.next();
            details.setStandardIpaddress(new IPAddressString(details.getIpaddress()).getAddress().toFullString());
            batchDetailsList.add(details);
            if (batchDetailsList.size() == 500){
                //将500个格式化后地址存入数据库
                addrgenSeedfileDetailsMapper.batchUpdateAddrgenSeedfileDetails(batchDetailsList);
                //格式化后探测ASN、BGP前缀等
                IPparameters ipDetails = GetParameters.getIPDetails(batchDetailsList);
                for(int i = 0;i < 500; i++){
                    System.out.println(ipDetails.getASN()[i]);
                    System.out.println(ipDetails.getBGP()[i]);
                    System.out.println(ipDetails.getIID()[i]);
                    batchDetailsList.get(i).setAsn(ipDetails.getASN()[i]);
                    batchDetailsList.get(i).setBgpPrefix(ipDetails.getBGP()[i]);
                    batchDetailsList.get(i).setInterfaceId(ipDetails.getIID()[i]);
                }
                addrgenSeedfileDetailsMapper.batchUpdateAddrgenSeedfileDetails(batchDetailsList);
                //探测Activity
                detectActivity(batchDetailsList);
                batchDetailsList.clear();
            }
        }
        //将不足500个格式化后地址存入数据库
        addrgenSeedfileDetailsMapper.batchUpdateAddrgenSeedfileDetails(batchDetailsList);
        //格式化后探测ASN、BGP前缀等
        IPparameters ipDetails = GetParameters.getIPDetails(batchDetailsList);
        for(int i = 0;i < batchDetailsList.size(); i++){
            System.out.println(ipDetails.getASN()[i]);
            System.out.println(ipDetails.getBGP()[i]);
            System.out.println(ipDetails.getIID()[i]);
            batchDetailsList.get(i).setAsn(ipDetails.getASN()[i]);
            batchDetailsList.get(i).setBgpPrefix(ipDetails.getBGP()[i]);
            batchDetailsList.get(i).setInterfaceId(ipDetails.getIID()[i]);
        }
        addrgenSeedfileDetailsMapper.batchUpdateAddrgenSeedfileDetails(batchDetailsList);
        //探测Activity
        detectActivity(batchDetailsList);

        //插入一条该文件的格式化历史记录
        AddrgenSeedfileFormat addrgenSeedfileFormat = new AddrgenSeedfileFormat();
        addrgenSeedfileFormat.setAddrSeedfileId(addrgenSeedfileHandle.getAddrSeedfileId());
        addrgenSeedfileFormat.setInputFilename(addrgenSeedfileHandle.getAddrSeedfileName());
        addrgenSeedfileFormat.setOutputFilename("formated-"+addrgenSeedfileHandle.getAddrSeedfileName());
        Date d = new Date();
        addrgenSeedfileFormat.setFormatTime(d);
        addrgenSeedfileFormatMapper.insertAddrgenSeedfileFormat(addrgenSeedfileFormat);
        addrgenSeedfileHandle.setFormaticon("el-icon-document-checked");
        updateAddrgenSeedfileHandle2(addrgenSeedfileHandle);
        return 1;
    }

    @Override
    public void getFileByID(AddrGen addrGen) {
        List<AddrgenSeedfileDetails> addrlist = addrgenSeedfileDetailsMapper.selectAddrgenSeedfileDetailsListByFileId(Long.valueOf(addrGen.getFileID()));
        if (addrGen.getIsmul().equals("1")){//开启多层级分类
            int count = -1;
            AddrgenRecord addrgenRecord = new AddrgenRecord();
            MLCResults mlcResults = MLC.MTLC(addrlist, addrGen.isActivity(), addrGen.isStability(),0.2,2);
            for (int i = 0; i < mlcResults.getStaToLOG().length;i++){
                System.out.println("---------------第"+i+"次");
                int len = mlcResults.getStaToLOG()[i];
                List<String> mlcaddrList = new ArrayList<>();
                for (int j = 0;j < len;j++){
                    count ++;
                    System.out.println("集合内地址----");
                    System.out.println(mlcResults.getAddrList()[count]);
                    System.out.println("集合内地址----完");
                    mlcaddrList.add(mlcResults.getAddrList()[count]);
                }
                if (mlcaddrList.size()==1) continue;
                addrgenRecord = BSPRGen.bsprGen(mlcaddrList, addrGen);
            }
            addrgenRecordService.insertAddrgenRecord(addrgenRecord);
            System.out.println("执行..............插入记录");
            Iterator<AddrgenRecordDetails> iterator = addrgenRecord.getAddrgenRecordDetailsList().iterator();
            List<AddrgenRecordDetails> recordDetailsList = new ArrayList<>();
            while (iterator.hasNext()){
                AddrgenRecordDetails recordDetails = new AddrgenRecordDetails();
                recordDetails.setIpaddr(iterator.next().getIpaddr());
                recordDetailsList.add(recordDetails);
                if (recordDetailsList.size() == 500){
                    addrgenRecord.setAddrgenRecordDetailsList(recordDetailsList);
                    addrgenRecordService.insertAddrgenRecordDetails(addrgenRecord);
                    recordDetailsList.clear();
                }
            }
            addrgenRecord.setAddrgenRecordDetailsList(recordDetailsList);
            addrgenRecordService.insertAddrgenRecordDetails(addrgenRecord);
            recordDetailsList.clear();
        }else {//未开启多层级分类
            System.out.println("进入..................................");
            List<String> addrListstr = new ArrayList<>();
            Iterator<AddrgenSeedfileDetails> iterator1 = addrlist.iterator();
            while (iterator1.hasNext()){
                addrListstr.add(iterator1.next().getStandardIpaddress());
            }
            AddrgenRecord addrgenRecord = BSPRGen.bsprGen(addrListstr, addrGen);
            addrgenRecordService.insertAddrgenRecord(addrgenRecord);
            System.out.println("执行..............插入记录");
            Iterator<AddrgenRecordDetails> iterator = addrgenRecord.getAddrgenRecordDetailsList().iterator();
            List<AddrgenRecordDetails> recordDetailsList = new ArrayList<>();
            while (iterator.hasNext()){
                AddrgenRecordDetails recordDetails = new AddrgenRecordDetails();
                recordDetails.setIpaddr(iterator.next().getIpaddr());
                recordDetailsList.add(recordDetails);
                if (recordDetailsList.size() == 500){
                    addrgenRecord.setAddrgenRecordDetailsList(recordDetailsList);
                    addrgenRecordService.insertAddrgenRecordDetails(addrgenRecord);
                    recordDetailsList.clear();
                }
            }
            addrgenRecord.setAddrgenRecordDetailsList(recordDetailsList);
            addrgenRecordService.insertAddrgenRecordDetails(addrgenRecord);
            recordDetailsList.clear();
        }
    }

    public void detectActivity(List<AddrgenSeedfileDetails> list) throws IOException {
        /*
        * 地址响应类型activity的4种类型
        * 1.存在端口开放并且响应ICMPv6
        * 2.存在端口开放但不响应ICMPv6
        * 3.仅响应ICMPv6
        * 4.没有任何响应ICMPv6
        *
        * */
        Iterator<AddrgenSeedfileDetails> iterator = list.iterator();
        Pattern pattern_up = Pattern.compile("Host is up");
        Pattern pattern_down = Pattern.compile("Host seems down");
        Pattern pattern_open = Pattern.compile("open");
        Pattern pattern_cloesd = Pattern.compile("closed");
        List<AddrgenSeedfileDetails> resultList = new ArrayList<>();
        while (iterator.hasNext()) {
            AddrgenSeedfileDetails details = iterator.next();
            String addr = details.getStandardIpaddress();
            Process p = Runtime.getRuntime().exec("nmap -6 -sP " + addr);
            Process p2 = Runtime.getRuntime().exec("nmap -6 -F " + addr);
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
            final InputStream stream2 = p2.getInputStream();
            BufferedReader reader2 = null;
            String s2 = "", line2 = null;
            try {
                reader2 = new BufferedReader(new InputStreamReader(stream2, Charset.forName("GBK")));
                while ((line2 = reader2.readLine()) != null) {
                    s2 = s2 + line2;
                    System.out.println(line2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Matcher matcher_up = pattern_up.matcher(s);
            Matcher matcher_down = pattern_down.matcher(s);
            Matcher matcher_open = pattern_open.matcher(s2);
            Matcher matcher_closed = pattern_cloesd.matcher(s2);
            boolean b_up = matcher_up.find();
            boolean b_down = matcher_down.find();
            boolean b_open = matcher_open.find();
            boolean b_closed = matcher_closed.find();
            if (b_up && (b_open || b_closed)){
                details.setResponseType("1");//类型1
                System.out.println(1);
            }else if ((b_open || b_closed) && b_down){
                details.setResponseType("2");//类型2
                System.out.println(2);
            }else if (!(b_open || b_closed) && b_up){
                details.setResponseType("3");//类型3
                System.out.println(3);
            }else if (!(b_open || b_closed) && b_down){
                details.setResponseType("4");//类型4
                System.out.println(4);
            }
            resultList.add(details);
        }
        addrgenSeedfileDetailsMapper.batchUpdateAddrgenSeedfileDetails(resultList);
    }
}
