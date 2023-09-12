package com.ruoyi.addrgen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Nmap探测工具，30s超时时间
 */
public class NmapDetect {
    public static boolean detect(String addr) throws IOException {
        Pattern pattern  = Pattern.compile("Host is up");
        Pattern host_timeout = Pattern.compile("host timeout");
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
        Matcher matcher = pattern.matcher(s);
        Matcher matcher1 = host_timeout.matcher(s);
        boolean b = matcher.find();
        boolean b1 = matcher1.find();
        if (b && (!b1)){
            return true;//活跃
        }else {
            return false;//不活跃
        }
    }
}
