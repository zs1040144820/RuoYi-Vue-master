package com.ruoyi.addrgen.utils;

import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.domain.MLCResults;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
* 剩余问题：
* ①获取到IP地址集的五个维度的信息并传入；
* ②将每个叶子结点分别存储，分别输入
*
* 可能的问题：
* ①如果叶子结点中只有一个地址，是否要将其输入BSPR-Gen？
* */
public class MLC1 {
    //运行cmd命令
    /*public static void runCmd(String tempName) {
        Runtime run = Runtime.getRuntime();
        String command = "nc whois.cymru.com 43 < " + tempName + " > list.txt";
        try {
            Process process = run.exec("cmd /c start nc && " + command, null, new File("E:\\IPv6扫描\\函数\\MLC1-Windows"));
            process.waitFor();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    //获取ASN和BGP前缀
    /*public static void getPara(String[] Root, String[] ASN, String[] BGP) {
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
            System.out.println("[!]Generate temp file *" + temp.getName() + "* accomplished!");
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
    }*/

    //求log工具方法
    public static double log(double n, double base) {
        return Math.log(n) / Math.log(base);
    }

    //计算LOG_Q并加入数组
    public static void count(String args, String[] LOGQ, int k) {
        //1.计算Q
        System.out.println("\n[!]Algorithm runs " + k + " time(s)");
        StringBuffer recordType = new StringBuffer();// 用于记录字符种类
        int[] recordNumber = new int[args.length() + 1];// 用于记录字符个数
        ArrayList<Integer> realNumber = new ArrayList<>();//真正的字符个数，用于找出最大的值
        for (int i = 0; i < args.length(); i++) {
            int count = 0;// 用于临时计数
            String medium = args.charAt(i) + "";// 将args.charAt(i)的值赋给medium
            if (!recordType.toString().contains(medium)) {// 判断recordType中没有medium中的字符
                recordType.append(medium);// 将medium中的字符添加到recordType中
                count++;
                for (int j = i + 1; j < args.length(); j++) {// 用于计算medium中的字符在字符串中的个数
                    if (medium.equals(args.charAt(j) + "")) {// 如果args.charAt(j)中的字符与medium中的字符相同时
                        count++;
                    }
                }
                recordNumber[recordType.length()] = count;// 将计数工具的值赋给recordNumber数组
            }
        }
        System.out.println("[String " + args + " has " + recordType.length() + " valueTypes ]");
        for (int i = 0; i < recordType.length(); i++) {
            System.out.println(" * Character " + recordType.charAt(i) + " appeared " + recordNumber[i + 1] + " times ");
            realNumber.add(recordNumber[i + 1]);
        }
        Object obj = Collections.max(realNumber);
        System.out.println("[Each character appeared maximum " + obj + " times ]");
        Double Q = Double.valueOf(obj.toString()) / recordType.length();
        System.out.println("[Parameter Q equals: " + obj + "/" + recordType.length() + "=" + Q + " ]");
        //2.计算LOG_Q
        Double LOG_Q = log(Q, 16.0) + 1;
        String result = String.format("%.2f", LOG_Q);//利用字符串格式化的方式实现四舍五入,保留2位小数,1代表小数点后面的位数, 不足补0。f代表数据是浮点类型。保留2位小数就是“%.2f”，依此累推。
        //System.out.println(result);
        System.out.println("[Parameter LOG_Q equals: " + LOG_Q + " ]");
        LOGQ[k] = result.toString();
    }

    //处理字符串并返回计算完的LOG_Q数组
    public static String[] countLog(String[] Root) {
        String[] LOG_Q = new String[Root.length];
        String[] fakeRoot = new String[Root.length];//防止覆盖原数组
        //1.处理输入的Root数组-去除非IID部分 && 去除冒号
        for (int i = 0; i < Root.length; i++) {
            String[] temp = Root[i].split(":", 5);
            fakeRoot[i] = temp[4].replaceAll(":", "");
        }
        System.out.println("[Processed Root: ]");
        for (int i = 0; i < fakeRoot.length; i++) {
            System.out.println(" * " + fakeRoot[i]);
        }
        //2.计算Q并加入数组
        for (int i = 0; i < fakeRoot.length; i++) {
            count(fakeRoot[i], LOG_Q, i);
        }
        return LOG_Q;
    }

    //去重
    public static String[] arrayDeduplication(String[] originArray) {
        /*Set<String> set = new HashSet();
        for (int i = 0; i < originArray.length; i++) {
            set.add(originArray[i]);
        }
        String[] result = set.toArray(new String[set.size()]);
        return result;*/
        List list = new ArrayList();
        //遍历数组往集合里存元素  
        for (int i = 0; i < originArray.length; i++) {
            //如果集合里面没有相同的元素才往里存  
            if (!list.contains(originArray[i])) {
                list.add(originArray[i]);
            }
        }
        //toArray()方法会返回一个包含集合所有元素的Object类型数组  
        Object[] newArr = list.toArray();
        String[] resultarr = new String[newArr.length];
        System.arraycopy(newArr, 0, resultarr, 0, newArr.length);
        return resultarr;
    }

    //返回对应地址的参数
    public static String findParameter(String addr, String[] Root, String[] BGP) {
        int i;
        for (i = 0; i < Root.length; i++) {
            if (addr == Root[i])
                break;
        }
        return BGP[i];
    }

    //对2~5层进行划分
    public static void classification(ArrayList<Integer> RootToASN, String[] Level_ASN, String[] unique_BGP, String[] Root, String[] BGP, String[] Level_BGP, ArrayList<Integer> ASNToBGP) {
        int sentinel = 0;
        int n = 0;
        for (int i = 0; i < RootToASN.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = sentinel; j < (sentinel + RootToASN.get(i)); j++) {
                temp.add(Level_ASN[j]);
            }
            sentinel = sentinel + RootToASN.get(i);
            /*System.out.print("{");
            for(int k=0;k< temp.size();k++){
                System.out.print(temp.get(k)+"; ");
            }
            System.out.print("}\n");*/
            for (int l = 0; l < unique_BGP.length; l++) {
                int count = 0;
                for (int m = 0; m < temp.size(); m++) {
                    if (findParameter(temp.get(m), Root, BGP).equals(unique_BGP[l])) {
                        Level_BGP[n] = temp.get(m);
                        n++;
                        count++;
                    }
                }
                if (count != 0)
                    ASNToBGP.add(count);
            }
        }

    }

    //根据传入参数实现多层级分类
    public static MLCResults MTLC(List<AddrgenSeedfileDetails> addrlist, boolean switcha, boolean switchs) {
        //1.初始化存放Root和IP地址五个维度的值的数组
        ArrayList<String> seedSetList = new ArrayList<>();
        Iterator<AddrgenSeedfileDetails> iterator = addrlist.iterator();
        List<String> ASNlist = new ArrayList<>();
        List<String> BGPlist = new ArrayList<>();
        List<String> Activitylist = new ArrayList<>();
        List<String> Stabilitylist = new ArrayList<>();
        while (iterator.hasNext()) {
            AddrgenSeedfileDetails next = iterator.next();
            String standardIpaddress = next.getStandardIpaddress();
            System.out.println(standardIpaddress);
            if (standardIpaddress != null){
                //String s = standardIpaddress.replace(":", "");
                seedSetList.add(standardIpaddress);
                ASNlist.add(next.getAsn());
                BGPlist.add(next.getBgpPrefix());
                Activitylist.add(next.getResponseType());
                Stabilitylist.add(next.getStability());
            }
        }
        /*try {
            File file = new File("./src/seed.txt");
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(input);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                seedSetList.add(str);
            }
            bf.close();
            input.close();
        } catch (IOException e) {
            System.out.println("[!]Failed to read txt file!");
            e.printStackTrace();
        }
        System.out.println("[!]Successfully read seed address file!");*/
        //String[] Root = {"addr0","addr1","addr2","addr3","addr4","addr5","addr6","addr7","addr8","addr9","addrA","addrB","addrC","addrD","addrE","addrF"};
        String[] Root = (String[]) seedSetList.toArray(new String[0]);
        String[] ASN = ASNlist.toArray(new String[0]);
        String[] BGP = BGPlist.toArray(new String[0]);
        /*String[] act = {"3001", "3001", "3004", "3004", "3005", "3006", "3008", "3009", "3002", "3002", "3003", "3009", "3010", "3006", "3007", "3008",
                "3001", "3001", "3004", "3004", "3005", "3006", "3008", "3009", "3002", "3002", "3003", "3009", "3010", "3006", "3007", "3008",
                "3001", "3001", "3004", "3004", "3005", "3006", "3008", "3009", "3002", "3002", "3003", "3009", "3010", "3006", "3007", "3008",
                "3001", "3001"};*/
        String[] act = Activitylist.toArray(new String[0]);
        /*String[] sta = {"4001", "4001", "4004", "4004", "4005", "4006", "4008", "4009", "4002", "4002", "4003", "4009", "4010", "4006", "4007", "4008",
                "4001", "4001", "4004", "4004", "4005", "4006", "4008", "4009", "4002", "4002", "4003", "4009", "4010", "4006", "4007", "4008",
                "4001", "4001", "4004", "4004", "4005", "4006", "4008", "4009", "4002", "4002", "4003", "4009", "4010", "4006", "4007", "4008",
                "4001", "4001"};*/
        String[] sta = Stabilitylist.toArray(new String[0]);
        //getPara(Root, ASN, BGP);
        //String[] LOGQ = {"5001" ,"5001" ,"5005" ,"5006" ,"5007" ,"5008" ,"5010" ,"5011" ,"5002" ,"5003" ,"5004" ,"5012" ,"5013" ,"5008" ,"5009" ,"5010"};
        String[] LOGQ = countLog(Root);
        //2.初始化五层数组
        String[] Level_ASN = new String[Root.length];
        String[] Level_BGP = new String[Root.length];
        String[] Level_Act = new String[Root.length];
        String[] Level_Sta = new String[Root.length];
        String[] Level_Log = new String[Root.length];
        //3.ASN层，第一层
        ArrayList<Integer> RootToASN = new ArrayList<>();
        String[] unique_ASN = arrayDeduplication(ASN);
        int serial = 0;
        for (int j = 0; j < unique_ASN.length; j++) {
            int count = 0;
            for (int i = 0; i < ASN.length; i++) {
                if (ASN[i].equals(unique_ASN[j])) {
                    Level_ASN[serial] = Root[i];
                    serial++;
                    count++;
                }
            }
            RootToASN.add(count);
        }
        System.out.print(" * The situation of ASN level:{");
        for (int i = 0; i < Level_ASN.length; i++) {
            System.out.print(Level_ASN[i] + "; ");
        }
        System.out.print("} \n");
        System.out.print(" * Number to all the branch  :{");
        for (int i = 0; i < RootToASN.size(); i++) {
            System.out.print(RootToASN.get(i) + "; ");
        }
        System.out.print("} \n");
        //4.BGP层，第二层
        ArrayList<Integer> ASNToBGP = new ArrayList<>();
        String[] unique_BGP = arrayDeduplication(BGP);
        /*System.out.print("The BGPs:{");
        for(int i=0;i<unique_BGP.length;i++){
            System.out.print(unique_BGP[i]+"; ");
        }
        System.out.print("}\n");*/
        classification(RootToASN, Level_ASN, unique_BGP, Root, BGP, Level_BGP, ASNToBGP);
        System.out.print(" ** The situation of BGP level:{");
        for (int i = 0; i < Level_BGP.length; i++) {
            System.out.print(Level_BGP[i] + "; ");
        }
        System.out.print("} \n");
        System.out.print(" ** Number to all the branch  :{");
        for (int i = 0; i < ASNToBGP.size(); i++) {
            System.out.print(ASNToBGP.get(i) + "; ");
        }
        System.out.print("} \n");
        //5.Activity层，第三层，BGP-Activity
        ArrayList<Integer> BGPToAct = new ArrayList<>();
        if(switcha) {
            String[] unique_Act = arrayDeduplication(act);
            classification(ASNToBGP, Level_BGP, unique_Act, Root, act, Level_Act, BGPToAct);
            System.out.print(" *** The situation of Activity level:{");
            for (int i = 0; i < Level_Act.length; i++) {
                System.out.print(Level_Act[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" *** Number to all the branch       :{");
            for (int i = 0; i < BGPToAct.size(); i++) {
                System.out.print(BGPToAct.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        //6.Stability层，第三层，Activity-Stability
        ArrayList<Integer> ActToSta = new ArrayList<>();
        String[] unique_Sta = arrayDeduplication(sta);
        if(switcha==true && switchs==true){
            classification(BGPToAct, Level_Act, unique_Sta, Root, sta, Level_Sta, ActToSta);
            System.out.print(" **** The situation of Stability level:{");
            for (int i = 0; i < Level_Sta.length; i++) {
                System.out.print(Level_Sta[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" **** Number to all the branch        :{");
            for (int i = 0; i < ActToSta.size(); i++) {
                System.out.print(ActToSta.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        if(switcha==false && switchs==true){
            classification(ASNToBGP, Level_BGP, unique_Sta, Root, sta, Level_Sta, ActToSta);
            System.out.print(" **** The situation of Stability level:{");
            for (int i = 0; i < Level_Sta.length; i++) {
                System.out.print(Level_Sta[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" **** Number to all the branch        :{");
            for (int i = 0; i < ActToSta.size(); i++) {
                System.out.print(ActToSta.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        //7.LOG_Q层，第五层
        ArrayList<Integer> StaToLOG = new ArrayList<>();
        String[] unique_LOG = arrayDeduplication(LOGQ);
        if(switcha==true && switchs==true){
            classification(ActToSta, Level_Sta, unique_LOG, Root, LOGQ, Level_Log, StaToLOG);
            System.out.print(" ***** The situation of LOG_Q level:{");
            for (int i = 0; i < Level_Log.length; i++) {
                System.out.print(Level_Log[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" ***** Number to all the branch    :{");
            for (int i = 0; i < StaToLOG.size(); i++) {
                System.out.print(StaToLOG.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        if(switcha==false && switchs==true){
            classification(ActToSta, Level_Sta, unique_LOG, Root, LOGQ, Level_Log, StaToLOG);
            System.out.print(" ***** The situation of LOG_Q level:{");
            for (int i = 0; i < Level_Log.length; i++) {
                System.out.print(Level_Log[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" ***** Number to all the branch    :{");
            for (int i = 0; i < StaToLOG.size(); i++) {
                System.out.print(StaToLOG.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        if(switcha==true && switchs==false){
            classification(BGPToAct, Level_Act, unique_LOG, Root, LOGQ, Level_Log, StaToLOG);
            System.out.print(" ***** The situation of LOG_Q level:{");
            for (int i = 0; i < Level_Log.length; i++) {
                System.out.print(Level_Log[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" ***** Number to all the branch    :{");
            for (int i = 0; i < StaToLOG.size(); i++) {
                System.out.print(StaToLOG.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        if(switcha==false && switchs==false){
            classification(ASNToBGP, Level_BGP, unique_LOG, Root, LOGQ, Level_Log, StaToLOG);
            System.out.print(" ***** The situation of LOG_Q level:{");
            for (int i = 0; i < Level_Log.length; i++) {
                System.out.print(Level_Log[i] + "; ");
            }
            System.out.print("} \n");
            System.out.print(" ***** Number to all the branch    :{");
            for (int i = 0; i < StaToLOG.size(); i++) {
                System.out.print(StaToLOG.get(i) + "; ");
            }
            System.out.print("} \n");
        }
        MLCResults mlcResults = new MLCResults();
        mlcResults.setAddrList(Level_Log);
        mlcResults.setStaToLOG(StaToLOG.toArray(new Integer[0]));
        return mlcResults;
    }
}