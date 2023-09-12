package com.ruoyi.addrgen.utils;

import java.text.DecimalFormat;

/**
 * @Description: TODO
 * @author: lzh
 * @date: 2022年10月18日 11:19
 */
public class UnitConversion {
    public static String getSize(String o){
        long i = Integer.valueOf(o);
        String result = "";
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        /*实现保留小数点两位*/
        DecimalFormat df = new DecimalFormat("#.00");

        if (i >= gb){
            result =  df.format((float) i / gb) + "GB";
        }else if(i >= mb){
            result =  df.format((float) i / mb) + "MB";
        }else if(i >= kb){
            result = String.format("%.2f", (float) i / kb) + "KB";
        }else {
            result =  i + "B";
        }
        return result;
    }
}
