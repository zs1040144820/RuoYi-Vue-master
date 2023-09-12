package com.ruoyi.addrgen.utils;

import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.IPparameters;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetParameters {
    //获取python文件，执行cmd命令，返回python执行结果
    public static String getFile(String filename) throws IOException, InterruptedException {
        String param = filename;
        String[] command = new String[] {"python","./searchFor.py",param};
        final Process process = Runtime.getRuntime().exec(command);
        printErrorMessage(process.getErrorStream());
        Reader reader = new InputStreamReader(process.getInputStream());
        BufferedReader bf = new BufferedReader(reader);
        String line = null;
        try {
            //while((line=bf.readLine())!=null) {
                //System.out.println(line); // 返回值
            //}
            line = bf.readLine();
            reader.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    //打印错误信息
    private static void printErrorMessage(final InputStream input) {
        new Thread(new Runnable() {
            public void run() {
                Reader reader = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while((line=bf.readLine())!=null) {
                        System.out.println(line); // 返回值
                    }
                    reader.close();
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //获取所有需要的参数
    public static void getPara(String[] Root, String[] ASN, String[] BGP, String[] IID) throws IOException, InterruptedException {
        ConsoleProgressBarDemo cpbd = new ConsoleProgressBarDemo(50, '#');
        System.out.println("[!]Start searching for ASN,BGP,IID information!");
        for(int i=0;i<Root.length;i++){
            String msg = getFile(Root[i]); // 给python传参
            String[] msgarr = msg.split(";;");
            ASN[i] = msgarr[1];
            BGP[i] = msgarr[2];
            String[] temp = Root[i].split(":", 5);
            IID[i] = temp[4];
            if(i== Root.length-1)
                cpbd.show(100);
            else
                cpbd.show((i+1)* (100/Root.length));
            Thread.sleep(100);
        }
        System.out.println("[!]Getting information accomplished!");
    }

    //main func
    public static IPparameters getIPDetails(List<AddrgenSeedfileDetails> addrlist){
        //1.初始化存放Root和IP地址五个维度的值的数组
        ArrayList<String> seedSetList = new ArrayList<>();
        Iterator<AddrgenSeedfileDetails> iterator = addrlist.iterator();
        while (iterator.hasNext()) {
            String standardIpaddress = iterator.next().getStandardIpaddress();
            System.out.println(standardIpaddress);
            if (standardIpaddress != null){
                //String s = standardIpaddress.replace(":", "");
                seedSetList.add(standardIpaddress);
            }
        }
        System.out.println("[!]Successfully read seed address file!");
        String[] Root = (String[]) seedSetList.toArray(new String[0]);
        String[] ASN = new String[Root.length];
        String[] BGP = new String[Root.length];
        String[] IID = new String[Root.length];
        try {
            getPara(Root, ASN, BGP, IID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("//              IPv6 Addresses             //  ASN  //   BGP Prefix   //    Interface ID    //");
        for(int i=0;i< Root.length;i++){
            System.out.println("// "+Root[i]+" // "+ASN[i]+" // "+BGP[i]+" // "+IID[i]+" // ");
        }
        IPparameters iPparameters = new IPparameters();
        iPparameters.setASN(ASN);
        iPparameters.setBGP(BGP);
        iPparameters.setIID(IID);
        return iPparameters;
    }
}