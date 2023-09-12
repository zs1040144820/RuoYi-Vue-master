package com.ruoyi.vuln.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CVEGradeQuery {

    public static List test(String CVE) {
        List list=new ArrayList<>();

        String requestUrl = "https://nvd.nist.gov/vuln/search/results?form_type=Basic&results_type=overview&query="+CVE+"&search_type=all&isCpeNameSearch=false";
        HttpClient client = new HttpClient();
        HttpClientParams clientParams = client.getParams();
        clientParams.setContentCharset("UTF-8");
        GetMethod method = new GetMethod(requestUrl);
        String response = null;
        int code = 0;
        try {
    code = client.executeMethod(method);
    response = method.getResponseBodyAsString();
    if (code == HttpStatus.SC_OK) {
        Document document = Jsoup.parse(response);
        Element postItems1 = document.getElementById("cvss2-link");//cvss2.0
        Element postItems3 = document.getElementById("cvss3-link");//cvss3.0  cvss3.1
        list.add(postItems1.text());
        if (postItems3 != null){
            list.add(postItems3.text());
        }

        System.out.println(list);

    } else {
        System.out.println("返回状态不是200,可能需要登录或者授权，亦或者重定向了！");
    }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
