package com.ruoyi.vuln.service.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.vuln.commandobserver.CnnvdExecutorObserver;
import com.ruoyi.vuln.commandobserver.CommandExecutorObserver;
import com.ruoyi.vuln.commandsubject.CnnvdExecutorSubject;
import com.ruoyi.vuln.domain.vo.Cnnvd;
import com.ruoyi.vuln.domain.vo.Entry;
import com.ruoyi.vuln.domain.vo.ExecutionObjectFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.vuln.mapper.VulnCnnvdMapper;
import com.ruoyi.vuln.domain.VulnCnnvd;
import com.ruoyi.vuln.service.IVulnCnnvdService;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 漏洞特征Service业务层处理
 * 
 * @author IPv6ScanGroup
 * @date 2023-04-12
 */
@Service
public class VulnCnnvdServiceImpl implements IVulnCnnvdService, CnnvdExecutorSubject
{
    @Autowired
    private VulnCnnvdMapper vulnCnnvdMapper;

    /**
     * 查询漏洞特征
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 漏洞特征
     */
    @Override
    public VulnCnnvd selectVulnCnnvdByCnnvdId(Long cnnvdId)
    {
        return vulnCnnvdMapper.selectVulnCnnvdByCnnvdId(cnnvdId);
    }

    /**
     * 查询漏洞特征列表
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 漏洞特征
     */
    @Override
    public List<VulnCnnvd> selectVulnCnnvdList(VulnCnnvd vulnCnnvd)
    {
        return vulnCnnvdMapper.selectVulnCnnvdList(vulnCnnvd);
    }

    /**
     * 新增漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    @Override
    public int insertVulnCnnvd(VulnCnnvd vulnCnnvd)
    {
        return vulnCnnvdMapper.insertVulnCnnvd(vulnCnnvd);
    }

    /**
     * 修改漏洞特征
     * 
     * @param vulnCnnvd 漏洞特征
     * @return 结果
     */
    @Override
    public int updateVulnCnnvd(VulnCnnvd vulnCnnvd)
    {
        return vulnCnnvdMapper.updateVulnCnnvd(vulnCnnvd);
    }

    /**
     * 批量删除漏洞特征
     * 
     * @param cnnvdIds 需要删除的漏洞特征主键
     * @return 结果
     */
    @Override
    public int deleteVulnCnnvdByCnnvdIds(Long[] cnnvdIds)
    {
        return vulnCnnvdMapper.deleteVulnCnnvdByCnnvdIds(cnnvdIds);
    }

    /**
     * 删除漏洞特征信息
     * 
     * @param cnnvdId 漏洞特征主键
     * @return 结果
     */
    @Override
    public int deleteVulnCnnvdByCnnvdId(Long cnnvdId)
    {
        return vulnCnnvdMapper.deleteVulnCnnvdByCnnvdId(cnnvdId);
    }

    @Override
    public String dealVulnCnnvd(MultipartFile file, String name, String size) throws IOException, JAXBException, ParseException {
        VulnCnnvd vulnCnnvd1 = new VulnCnnvd();
        String datezz = null;
        int chr = name.charAt(0);
        int chr1 = name.charAt(1);
        if ((chr >= 48 && chr <= 57) && (chr1 >= 48 && chr1 <= 57)) {
            datezz = name.substring(0, 4);
        }
        String month = name.substring(0, 2);
        if (month.equals("当日")) {
            LocalDate date2 = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date2.format(formatter);
            datezz = formattedDate;
        }
        if (month.equals("当月")) {
            LocalDate date2 = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            String formattedDate = date2.format(formatter);
            datezz = formattedDate;
        }

        if (month.equals("1月") || month.equals("2月") || month.equals("3月") || month.equals("4月") || month.equals("5月") || month.equals("6月")
                || month.equals("7月") || month.equals("8月") || month.equals("9月")) {
            LocalDate date2 = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String formattedDate = date2.format(formatter);
            String m = formattedDate + "-" + "0" + name.substring(0, 1);
            datezz = m;
        }
        String month1 = name.substring(0, 3);
        if (month1.equals("10月") || month1.equals("11月") || month1.equals("12月")) {
            LocalDate date2 = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String formattedDate = date2.format(formatter);
            String m = formattedDate + "-" + name.substring(0, 2);
            datezz = m;
        }
        vulnCnnvd1.setFileYear(datezz);
        List<VulnCnnvd> vulnCnnvds = vulnCnnvdMapper.selectVulnCnnvdList(vulnCnnvd1);
        if (!vulnCnnvds.isEmpty())
            return "该时间内的脚本已经插入";

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = file.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        int j=0;
        Pattern p = Pattern.compile("<entry>.*?</entry>");
        Matcher m = p.matcher(sb.toString());
        Matcher m1=p.matcher(sb.toString());
        while (m1.find()){
            j++;
        }

        int i = 0;
        while (m.find()){
            try{
                String ss = m.group();
                Document document = Jsoup.parse(ss);

                // 获取根元素
                Element rootElement = document.children().first();
                //System.out.println("根元素名称：" + rootElement.tagName());

                // 获取所有entry元素
                Elements entryElements = rootElement.getElementsByTag("entry");
                //System.out.println("共有 " + entryElements.size() + " 个entry元素：");

                // 遍历所有entry元素
                for (Element entryElement : entryElements) {
                    if (entryElement == null){
                        continue;
                    }
                    // 获取entry元素下的子元素
                    Element nameElement = entryElement.getElementsByTag("name").first();
                    Element vulnIdElement = entryElement.getElementsByTag("vuln-id").first();
                    Element publishedElement = entryElement.getElementsByTag("published").first();
                    Element modifiedElement = entryElement.getElementsByTag("modified").first();
                    Element sourceElement = entryElement.getElementsByTag("source").first();
                    Element severityElement = entryElement.getElementsByTag("severity").first();
                    Element vulnTypeElement = entryElement.getElementsByTag("vuln-type").first();
                    Element vulnDescriptElement = entryElement.getElementsByTag("vuln-descript").first();
                    Element cveIdElement = entryElement.getElementsByTag("cve-id").first();
                    Element vulnSolutionElement = entryElement.getElementsByTag("vuln-solution").first();

                    VulnCnnvd vulnCnnvd = new VulnCnnvd();
                    vulnCnnvd.setName(nameElement.text());
                    vulnCnnvd.setVulnId(vulnIdElement.text());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(publishedElement.text());
                    vulnCnnvd.setPublished(date);
                    Date date1 = sdf.parse(modifiedElement.text());
                    vulnCnnvd.setModified(date1);
                    vulnCnnvd.setSource(sourceElement.text());
                    vulnCnnvd.setSeverity(severityElement.text());
                    vulnCnnvd.setVulnType(vulnTypeElement.text());
                    vulnCnnvd.setVulnDescript(vulnDescriptElement.text());
                    vulnCnnvd.setCveId(cveIdElement.text());
                    vulnCnnvd.setVulnSolution(vulnSolutionElement.text());
                    vulnCnnvd.setFileYear(datezz);
                    vulnCnnvdMapper.insertVulnCnnvd(vulnCnnvd);
                }
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
            System.out.println(i++);
            DecimalFormat df = new DecimalFormat("0");
            String rate = df.format((float)i / j * 100);
            System.out.println("jsguwdu"+rate);
            notifyObserves(rate);
        }
        notifyObserves("100");

        return "插入成功";
    }

    @Override
    public void addObserver(CnnvdExecutorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CnnvdExecutorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserves(String rate) {
        for (CnnvdExecutorObserver observer : observers) {
            observer.update(rate);
        }
    }
}
