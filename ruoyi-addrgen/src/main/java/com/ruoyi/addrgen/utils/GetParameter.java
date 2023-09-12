package com.ruoyi.addrgen.utils;

import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.IPparameters;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetParameter {
    //运行cmd命令
    public static void runCmd(String tempName) {
        Runtime run = Runtime.getRuntime();
        String command = "nc whois.cymru.com 43 < " + tempName + " > list.txt";
        try {
            Process process = run.exec("cmd /c start nc && " + command, null, new File("E:\\IPV6扫描项目\\RuoYi-Vue-master"));
            process.waitFor();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //获取ASN,BGP前缀和IID
    public static void getPara(String[] Root, String[] ASN, String[] BGP, String[] IID) {
        try {
            File directory = new File("");
            File temp = File.createTempFile("myTempFile", ".txt", new File(directory.getAbsolutePath()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            System.out.println("[!]Start writing information to temp file!");
            bw.write("begin" + "\n");
            bw.write("verbose" + "\n");
            for (int i = 0; i < Root.length; i++) {
                bw.write(Root[i] + "\n");
            }
            bw.write("end");
            System.out.println("[!]Generate temp file :" + temp.getName() + " accomplished!");
            bw.close();
            File file = new File(directory.getAbsolutePath() + "//list.txt");
            runCmd(temp.getName());
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(input);// 按行读取字符串
            String str;
            do {
                TimeUnit.SECONDS.sleep(3);//秒
            } while ((str = bf.readLine()) == null);
            while ((str = bf.readLine()) != null) {
                str = str.replaceAll(" ", "");
                str = str.replaceAll("\n", "");
                char[] over = str.toCharArray();
                int sentinel = 0;
                StringBuilder sb = new StringBuilder();
                ArrayList<String> arrl1 = new ArrayList<String>();
                for (int i = sentinel; i < over.length; i++) {
                    if (over[i] == '|') {
                        arrl1.add(sb.toString());
                        sb.setLength(0);
                        sentinel++;
                        continue;
                    } else {
                        sb.append(over[i]);
                        sentinel++;
                    }
                }
                for (int l = 0; l < Root.length; l++) {
                    if (arrl1.get(1).equals(Root[l])) {
                        ASN[l] = arrl1.get(0);
                        BGP[l] = arrl1.get(2);
                    }
                }
            }
            //boolean deleted = file.delete();
            bf.close();
            input.close();
            temp.deleteOnExit();
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Root.length; i++) {
            String[] temp = Root[i].split(":", 5);
            IID[i] = temp[4];
        }
    }

    //main func
    public static IPparameters getIPDetails(List<AddrgenSeedfileDetails> addrlist){
        //1.初始化存放Root和IP地址五个维度的值的数组
        ArrayList<String> seedSetList = new ArrayList<>();
        //File file = new File("./src/seed.txt");
        //InputStreamReader input = new InputStreamReader(new FileInputStream(file));
        //BufferedReader bf = new BufferedReader(input);
        Iterator<AddrgenSeedfileDetails> iterator = addrlist.iterator();
        // 按行读取字符串
        //String str;
        while (iterator.hasNext()) {
            String standardIpaddress = iterator.next().getStandardIpaddress();
            System.out.println(standardIpaddress);
            if (standardIpaddress != null){
                //String s = standardIpaddress.replace(":", "");
                seedSetList.add(standardIpaddress);
            }
        }
        //bf.close();
        //input.close();
        System.out.println("[!]Successfully read seed address file!");
        String[] Root = (String[]) seedSetList.toArray(new String[0]);
        String[] ASN = new String[Root.length];
        String[] BGP = new String[Root.length];
        String[] IID = new String[Root.length];
        getPara(Root, ASN, BGP, IID);
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

//              IPv6 Addresses             //  ASN  //   BGP Prefix   //    Interface ID    //
// 2001:1210:0100:0001:0000:0000:0000:0017 // 2549 // 2001:1210::/32 // 0000:0000:0000:0017 //
// 2001:1210:0100:0001:0000:0000:0000:0218 // 2549 // 2001:1210::/32 // 0000:0000:0000:0218 //
// 2001:1210:0105:0034:0000:0403:00a8:0001 // 2549 // 2001:1210::/32 // 0000:0403:00a8:0001 //
// 2001:1210:0105:0034:0000:0606:00a8:0031 // 2549 // 2001:1210::/32 // 0000:0606:00a8:0031 //
// 2001:1210:0106:0001:0000:0000:0000:0000 // 2549 // 2001:1210::/32 // 0000:0000:0000:0000 //
// 2001:1210:0106:0001:0000:0000:0000:0003 // 2549 // 2001:1210::/32 // 0000:0000:0000:0003 //
// 2001:1210:3400:0152:0000:0000:0000:0000 // 2549 // 2001:1210::/32 // 0000:0000:0000:0000 //
// 2001:1210:3400:0152:0000:0000:0000:0014 // 2549 // 2001:1210::/32 // 0000:0000:0000:0014 //
// 2001:1210:3400:0152:0000:0000:0000:0202 // 2549 // 2001:1210::/32 // 0000:0000:0000:0202 //
// 2001:1208:ffff:ffff:ffff:ffff:ffff:000e // 3905 // 2001:1208:8000::/33 // ffff:ffff:ffff:000e //