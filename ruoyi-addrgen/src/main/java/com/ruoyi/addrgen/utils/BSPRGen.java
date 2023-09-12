package com.ruoyi.addrgen.utils;

import com.ruoyi.addrgen.domain.AddrGen;
import com.ruoyi.addrgen.domain.AddrgenRecord;
import com.ruoyi.addrgen.domain.AddrgenRecordDetails;
import com.ruoyi.addrgen.domain.AddrgenSeedfileDetails;
import com.ruoyi.addrgen.service.impl.AddrgenRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.math.BigInteger;
import java.sql.Array;
import java.util.*;

/*Multi-BSPR-Gen构成：
 * 1.多层级分类Multi-Level-Classification
 * 2.BSPR-Gen
 *   2.1 生成合适的模式表示Balanced Space Pattern Representation
 *   2.2 根据BSPR确定的模式表示生成结果地址集
 *
 * 可能的问题：对比大于小于时，等于的问题，如何分配最科学？
 * 存在的问题：数据量太大时全部等效于Wildcard
 */
public class BSPRGen {
    @Autowired
    private AddrgenRecordServiceImpl addrgenRecordService;

    //判断元素是否已在数组中
    public static boolean notInArray(ArrayList<Integer> arr, int value){
        for(int i=0;i<arr.size();i++){
            if(value==arr.get(i)) return false;
        }
        return true;
    }

    //判断元素是否已在数组中
    public static boolean notInBigArray(ArrayList<BigInteger> arr, BigInteger value){
        for(int i=0;i<arr.size();i++){
            if(value.compareTo(arr.get(i))==0) return false;
        }
        return true;
    }

    //找出一个元素在该半字节位中出现了几次
    public static int findAppearance(int value, int[] arr){
        int n=0;
        for(int i=0;i<arr.length;i++){
            if(value==arr[i]) n++;
        }
        return n;
    }

    //求log工具方法
    public static double log(double n, double base) {
        return Math.log(n) / Math.log(base);
    }

    //求出每个半字节位的极差Range、香农熵Shannon、取值种类数ValueTypes
    public static ArrayList<Integer> countSeed(ArrayList<String> seedSetList,int[] Range,int[] ValueTypes,double[] Shannon){
        int[] ni = new int[seedSetList.size()];//ni用于存储当前半字节位的所有内容
        /*ni[0]=seedSetList.get(0).charAt(0);
         * ni[1]=seedSetList.get(1).charAt(0);*/
        ArrayList<Integer> values = new ArrayList<>();//当前半字节位的所有取值种类
        for(int j=0;j<32;j++){
            int maxni = 0;
            int minni = 0;
            int appearance = 0;
            double shannontemp = 0.0;
            ArrayList<Integer> vt = new ArrayList<>();//当前半字节位的所有取值种类
            values.add(-1);
            //System.out.print("all "+seedSetList.size()+" seeds's n["+(j+1)+"]:{");
            for(int i=0;i<ni.length;i++){
                //正确读取种子地址集中第j+1个半字节位的所有取值，存入ni
                ni[i] = Character.getNumericValue(seedSetList.get(i).charAt(j));
                //System.out.print(ni[i]+";");
                if(i==0){
                    maxni=ni[0];
                    minni=ni[0];
                    vt.add(ni[0]);
                    values.add(ni[0]);
                }
                if (maxni < ni[i]) {
                    maxni = ni[i];
                }
                if (minni > ni[i]) {
                    minni = ni[i];
                }
                if (notInArray(vt,ni[i])){
                    vt.add(ni[i]);
                    values.add(ni[i]);
                }
            }
            //System.out.print("}\n");
            Range[j] = maxni-minni;
            /*打印输出当前半字节位的最大值、最小值和极差
            System.out.println("calculate Range of n"+(j+1)+":max="+maxni+";min="+minni+";Range="+Range[j]);*/
            ValueTypes[j] = vt.size();
            /*打印输出当前半字节位的取值种类vt数组
            System.out.print("ValueTypes array printed as:{");
            for(int i=0;i<vt.size();i++){
                System.out.print(vt.get(i)+";");
            }
            System.out.print("}\n");*/
            for(int i=0;i<vt.size();i++){
                double temp=0.0;
                appearance=findAppearance(vt.get(i),ni);
                temp = Double.valueOf(appearance)/Double.valueOf(ni.length);
                shannontemp+=temp*log(temp,16);
                //System.out.println("For nybble "+(j+1)+" ,value:"+vt.get(i)+"; appearance:"+appearance+"; shannontemp="+shannontemp);//所有半字节位求香农熵详细信息
            }
            if(shannontemp != 0.0) Shannon[j] = (-shannontemp);
            else Shannon[j] = shannontemp;
            /*打印输出当前半字节位的香农熵*/
        }
        return values;
    }

    //打印输出极差Range数组\取值种类数ValueTypes数组\香农熵Shannon数组
    public static void printCountSeed(int[] Range,int[] ValueTypes,double[] Shannon){
        System.out.print(" * Range array printed as:{");
        for(int i=0;i<Range.length;i++){
            System.out.print(Range[i]+"; ");
        }
        System.out.print("}\n");
        System.out.print(" * Shannon array printed as:{");
        for(int i=0;i<ValueTypes.length;i++){
            System.out.print(Shannon[i]+"; ");
        }
        System.out.print("}\n");
        System.out.print(" * ValueTypes array printed as:{");
        for(int i=0;i<ValueTypes.length;i++){
            System.out.print(ValueTypes[i]+"; ");
        }
        System.out.print("}\n");
    }

    //改牛顿切线法进行大数开方
    public static String BigIntegerExtract(BigInteger num,int n){
        //1.求结果的位数i，最大支持9999*n位的数
        int size = 0;
        for(int i=1;i<=9999;i++){
            if((i*n-(n-1))<=num.toString().length() && num.toString().length()<=(i*n)){
                size = i;//求出结果有多少位
                break;
            }
        }
        //System.out.println("size="+size);
        //2.确定每一位的数值
        ArrayList<String> result = new ArrayList<>();
        for(int i=size;i>=1;i--){
            if(i == size){//最高位从1开始
                for(int j=1;j<=10;j++){
                    BigInteger bj = BigInteger.valueOf(j);
                    if(bj.multiply(BigInteger.TEN.pow(size-1)).pow(n).compareTo(num)==0){
                        result.add(String.valueOf(j));break;
                    }
                    if(bj.multiply(BigInteger.TEN.pow(size-1)).pow(n).compareTo(num)==1){
                        result.add(String.valueOf(j-1));break;
                    }
                }
            }
            else {//次高位从0开始
                for(int j=0;j<=10;j++){
                    String str = "";
                    for(int k=0;k<result.size();k++){
                        str = str+result.get(k);
                    }
                    str=str+String.valueOf(j);
                    for(int l=0;l<(i-1);l++){
                        str = str+"0";
                    }
                    BigInteger bi = new BigInteger(str);
                    if(bi.pow(n).compareTo(num)==0){
                        result.add(String.valueOf(j));break;
                    }
                    if(bi.pow(n).compareTo(num)==1){
                        result.add(String.valueOf(j-1));break;
                    }
                }
            }
        }
        //3.返回结果
        String str = "";
        for(int k=0;k<result.size();k++){
            str = str+result.get(k);
        }
        return str;
    }

    //计算三个超参阈值range_t,entropy_t,type_t
    public static String[] countHyperThre(int[] Range,int[] ValueTypes,double[] Shannon){
        System.out.println("[Now begin to count Range_t,type_t,entropy_t:]");
        ArrayList<Integer> hyperthre = new ArrayList<>();//用于枚举存储所有超参阈值的可能性，一共5376组，每一组占3个元素，以range_t,entropy_t,type_t为顺序存放
        for(int rt=0;rt<=15;rt++){
            for(int et=0;et<=20;et++){//entropy_t=et/20
                for(int tt=1;tt<=16;tt++){
                    hyperthre.add(rt);
                    hyperthre.add(et);
                    hyperthre.add(tt);
                }
            }
        }
        ArrayList<String> SpaceRangeAll = new ArrayList<>();//枚举所有的地址空间大小和其三个超参阈值
        ArrayList<BigInteger> SpaceRanges = new ArrayList<>();//所有可能的地址空间大小SpaceRange取值
        //int n=0;
        for(int i=0;i<hyperthre.size();i+=3){//取出所有超参阈值集合中的每一组，代入到当前种子地址集合中，求出其SR
            int range_t = hyperthre.get(i);
            double entropy_t = Double.valueOf(hyperthre.get(i+1))/20;
            int type_t = hyperthre.get(i+2);
            int r=0;
            int l=0;
            int w=0;//统计range和list的个数
            BigInteger SpaceRange = BigInteger.valueOf(1);
            //if(i==16125) System.out.println("[Current Hyper-Threshold is : range_t="+range_t+"; entropy_t="+entropy_t+"; type_t="+type_t+"; ]");
            for(int j=0;j<32;j++){
                //if(i==0) System.out.println("[Current countSeed is : Range="+Range[j]+"; Shannon="+Shannon[j]+"; ValueTypes="+ValueTypes[j]+"; ]");
                if(ValueTypes[j]==1) continue;
                if(Range[j]>range_t && Shannon[j]>entropy_t && ValueTypes[j]>=type_t){w++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(16)); continue;}//System.out.println("wildcard this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]>range_t && Shannon[j]>entropy_t && ValueTypes[j]<type_t){l++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(ValueTypes[j])); continue;}//System.out.println("list this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]>=range_t && Shannon[j]<entropy_t && ValueTypes[j]>=type_t){r++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(Range[j]+1)); continue;}//System.out.println("range this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]>=range_t && Shannon[j]<entropy_t && ValueTypes[j]<type_t){l++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(ValueTypes[j])); continue;}//System.out.println("list this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]<=range_t && Shannon[j]>entropy_t && ValueTypes[j]>=type_t){r++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(Range[j]+1)); continue;}//System.out.println("range this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]<=range_t && Shannon[j]>entropy_t && ValueTypes[j]<type_t){l++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(ValueTypes[j])); continue;}//System.out.println("list this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]<range_t && Shannon[j]<entropy_t && ValueTypes[j]>type_t){l++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(ValueTypes[j])); continue;}//System.out.println("list this is sapce Range "+j+" :"+SpaceRange);}
                if(Range[j]<range_t && Shannon[j]<entropy_t && ValueTypes[j]<=type_t){l++;SpaceRange=SpaceRange.multiply(BigInteger.valueOf(ValueTypes[j])); continue;}//System.out.println("list this is sapce Range "+j+" :"+SpaceRange);}
            }//!!!问题：等于的时候怎么算？
            if(r==0 || l==0 || (l==0 && r==0)) continue;
            SpaceRangeAll.add(SpaceRange.toString());
            SpaceRangeAll.add(String.valueOf(range_t));
            SpaceRangeAll.add(String.valueOf(entropy_t));
            SpaceRangeAll.add(String.valueOf(type_t));
            //n++;
            //System.out.println("[Group "+n+" Hyper-Threshold is : range_t="+range_t+"; entropy_t="+entropy_t+"; type_t="+type_t+"; ]");
            if(notInBigArray(SpaceRanges,SpaceRange)){//!!!问题：为什么这个方法没作用，还是有重复值？--解决：BigInteger不能用==判断是否相等
                SpaceRanges.add(SpaceRange);
            }
        }
        BigInteger BalancedSR = BigInteger.valueOf(1);//求BalancedSR
        for(int i=0;i<SpaceRanges.size();i++){
            //System.out.println("SpaceRange "+(i+1)+":"+SpaceRanges.get(i)+"; ");
            BalancedSR = BalancedSR.multiply(SpaceRanges.get(i));
        }
        //System.out.println(SpaceRanges.size());//开几次方
        //System.out.println(BalancedSR);//所有SR的乘积
        //String BalancedSpaceRange = rootN_Decimal(BalancedSR.toString(), SpaceRanges.size(), 2);//开方
        //System.out.println(BalancedSpaceRange);//开方后求出的几何平均值
        BigInteger BalancedSpaceRange = new BigInteger(BigIntegerExtract(BalancedSR,SpaceRanges.size()));//!!!问题：需要将求出的平方根数24956040121815248374.27转换成整数--解决：toStirng().split();
        System.out.println(" * BalancedSpaceRange is: "+BalancedSpaceRange);
        BigInteger spacerangei = new BigInteger(SpaceRanges.get(0).toString());
        BigInteger minimum = spacerangei.subtract(BalancedSpaceRange).abs();
        BigInteger targetSR = new BigInteger(SpaceRanges.get(0).toString());
        for(int i=0;i<SpaceRanges.size();i++){
            spacerangei = new BigInteger(SpaceRanges.get(i).toString());
            //System.out.println("["+BalancedSpaceRange.toPlainString()+"-"+spacerangei+"="+BalancedSpaceRange.subtract(spacerangei).abs()+"]");
            if(minimum.compareTo(BalancedSpaceRange.subtract(spacerangei).abs())>0){
                minimum = spacerangei.subtract(BalancedSpaceRange).abs();
                //System.out.println("the "+(i+1)+" minimum is:"+minimum);
                targetSR = spacerangei;
            }
        }
        System.out.println(" * the minimum range between BanlancedSpaceRange and SpaceRange is: "+minimum+" with SpaceRange in array: "+targetSR);
        //System.out.println(" * All qualified groups are:");
        int n=1;
        String []arr = new String[]{"","",""};//返回数组
        for(int i=0;i<SpaceRangeAll.size();i+=4){//!!!问题：有多组合适的超参阈值怎么办？这里只取了一组为结果
            BigInteger spacerangej = new BigInteger(SpaceRangeAll.get(i));
            if(targetSR.compareTo(spacerangej)==0){
                System.out.println(" ** the "+n+" group");
                System.out.println("    SpaceRange: "+spacerangej);
                System.out.println("    range_t: "+SpaceRangeAll.get(i+1));
                System.out.println("    entropy_t: "+SpaceRangeAll.get(i+2));
                System.out.println("    type_t: "+SpaceRangeAll.get(i+3));
                n++;
                arr[0]=SpaceRangeAll.get(i+1);
                arr[1]=SpaceRangeAll.get(i+2);
                arr[2]=SpaceRangeAll.get(i+3);
                break;
            }
        }
        System.out.println(" * There are "+n+" qualified groups");
        return arr;
    }

    //生成合适的模式表示
    public static ArrayList<String> GeneratePR(int[] Range,int[] ValueTypes,double[] Shannon,int range_t,int type_t,double entropy_t,ArrayList<Integer> values,ArrayList<String> pattern){
        System.out.println("[Now begin to generate appropriate Pattern Representation:]");
        long startGenerate = System.currentTimeMillis();
        //ArrayList<String> pattern = new ArrayList<String> ();
        int sentinel = 1;
        ArrayList<String> GenerateAddr = new ArrayList<>();
        for(int j=0;j<32;j++){
            //if(i==0) System.out.println("[Current countSeed is : Range="+Range[j]+"; Shannon="+Shannon[j]+"; ValueTypes="+ValueTypes[j]+"; ]");
            if(ValueTypes[j]==1)  {
                pattern.add("Single");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: Single; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1) {
                        System.out.print(values.get(i) + ",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]>range_t && Shannon[j]>entropy_t && ValueTypes[j]>=type_t){
                pattern.add("Wildcard");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: Wildcard; ");
                System.out.print("   All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1)
                        System.out.print(values.get(i)+",");
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                for(int i=0;i<=15;i++){
                    GenerateAddr.add(String.valueOf(i));
                }
                GenerateAddr.add("-1");
                System.out.print("}  ; Wildcard:{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,};\n");
                continue;
            }
            if(Range[j]>range_t && Shannon[j]>entropy_t && ValueTypes[j]<type_t){
                pattern.add("List");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: List; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1){
                        System.out.print(values.get(i)+",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]>=range_t && Shannon[j]<entropy_t && ValueTypes[j]>=type_t){
                int max = values.get(sentinel);
                int min = values.get(sentinel);
                pattern.add("Range");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: Range; ");
                System.out.print("   All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1)
                        System.out.print(values.get(i)+",");
                    else if (values.get(i) == -1) {
                        break;
                    }
                }
                System.out.print("}  ; Range:{");
                for(int i=sentinel;i<values.size();i++){
                    if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                    else if(values.get(i) != -1) {
                        if (max < values.get(i)) {
                            max = values.get(i);
                        }
                        if (min > values.get(i)) {
                            min = values.get(i);
                        }
                    }
                }
                //System.out.println("+++"+min+"+++"+max);
                for(int k=min;k<=max;k++){
                    System.out.print(k + ",");
                    GenerateAddr.add(String.valueOf(k));
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]>=range_t && Shannon[j]<entropy_t && ValueTypes[j]<type_t){
                pattern.add("List");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: List; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1){
                        System.out.print(values.get(i)+",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]<=range_t && Shannon[j]>entropy_t && ValueTypes[j]>=type_t){
                int max = values.get(sentinel);
                int min = values.get(sentinel);
                pattern.add("Range");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: Range; ");
                System.out.print("   All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1)
                        System.out.print(values.get(i)+",");
                    else if (values.get(i) == -1) {
                        break;
                    }
                }
                System.out.print("}  ; Range:{");
                for(int i=sentinel;i<values.size();i++){
                    if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                    else if(values.get(i) != -1) {
                        if (max < values.get(i)) {
                            max = values.get(i);
                        }
                        if (min > values.get(i)) {
                            min = values.get(i);
                        }
                    }
                }
                //System.out.println("+++"+min+"+++"+max);
                for (int k = min; k <= max; k++) {
                    System.out.print(k + ",");
                    GenerateAddr.add(String.valueOf(k));
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]<=range_t && Shannon[j]>entropy_t && ValueTypes[j]<type_t){
                pattern.add("List");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: List; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1){
                        System.out.print(values.get(i)+",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]<range_t && Shannon[j]<entropy_t && ValueTypes[j]>type_t){
                pattern.add("List");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: List; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1) {
                        System.out.print(values.get(i) + ",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
            if(Range[j]<range_t && Shannon[j]<entropy_t && ValueTypes[j]<=type_t){
                pattern.add("List");
                System.out.print(" * The appropriate Pattern Representation for n"+(j+1)+" is: List; ");
                System.out.print("    All ValueTypes are as: {");
                for(int i=sentinel;i<values.size();i++){
                    if(values.get(i) != -1) {
                        System.out.print(values.get(i) + ",");
                        GenerateAddr.add(String.valueOf(values.get(i)));
                    }
                    else if (values.get(i) == -1) {
                        sentinel = i+1;
                        break;
                    }
                }
                GenerateAddr.add("-1");
                System.out.print("};\n");
                continue;
            }
        }
        System.out.println(" * Generate PR use time:"+(System.currentTimeMillis() - startGenerate)+"ms");
        return GenerateAddr;
    }

    //十进制转化为十六进制
    public static String stringToHexString(int n) {
        if(n<=9)
            return String.valueOf(n);
        if(n==10) return "A";
        if(n==11) return "B";
        if(n==12) return "C";
        if(n==13) return "D";
        if(n==14) return "E";
        if(n==15) return "F";
        return "";
    }

    //十六进制转化为十进制
    public static int stringToInteger(String n) {
        if(n.equals("A")) return 10;
        if(n.equals("B")) return 11;
        if(n.equals("C")) return 12;
        if(n.equals("D")) return 13;
        if(n.equals("E")) return 14;
        if(n.equals("F")) return 15;
        return Integer.valueOf(n);
    }

    //读取ni的所有取值，统计值出现的次数(种子地址集，第几个半字节位，采用Range或List)
    public static ArrayList<String> generateArr(ArrayList<String> seedSetList,int num,String model){
        ArrayList<String> nybbleAL = new ArrayList<>();
        String[] ni = new String[seedSetList.size()];
        for(int i=0;i<ni.length;i++){
            //正确读取种子地址集中第j+1个半字节位的所有取值，存入ni
            ni[i] = String.valueOf(Character.getNumericValue(seedSetList.get(i).charAt(num-1)));
        }
        /*for(int i=0;i<ni.length;i++) {
            System.out.println(ni[i]);
        }*/
        Map<String, Integer> map = new HashMap<>();
        for (String str : ni) {
            Integer numi = map.get(str);
            map.put(str, numi == null ? 1 : numi + 1);
        }
        Iterator it01 = map.keySet().iterator();
        while (it01.hasNext()) {
            Object key = it01.next();
            System.out.print("\n");
            System.out.print(stringToHexString(Integer.valueOf((String) key)) + " appeared " + map.get(key) + " times; ");
            System.out.print("\n");
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        //list.forEach(System.out::println);
        for (int i = list.size(); i-- > 0; ) {
            String[] temp = String.valueOf(list.get(i)).split("=");
            nybbleAL.add(temp[0]);
            //System.out.println(stringToHexString(Integer.valueOf(temp[0])));
        }
        //对ni排序，找出最大最小元
        if(model.equals("Range")){
            int[] tempArr = new int[ni.length];
            for(int i=0;i<ni.length;i++){
                tempArr[i] = stringToInteger(ni[i]);
            }
            Arrays.sort(tempArr);
            //System.out.println(Integer.parseInt(ni[0])+"; "+Integer.parseInt(ni[ni.length - 1]));
            for(int i=tempArr[0];i<=tempArr[ni.length - 1];i++){
                if(!nybbleAL.contains(String.valueOf(i))){
                    nybbleAL.add(String.valueOf(i));
                }
            }
            for(int i=0;i< nybbleAL.size();i++){
                System.out.print(nybbleAL.get(i)+"; ");
            }
        }
        if(model.equals("List")){
            for(int i=0;i< nybbleAL.size();i++){
                System.out.print(nybbleAL.get(i)+"; ");
            }
            return nybbleAL;
        }
        return nybbleAL;
    }

    //生成地址写入文件
    public static AddrgenRecord WriteToFile(ArrayList<String> seedSetList ,ArrayList<String> GenerateAddr , int BudgetLimit, ArrayList<String> pattern, AddrGen addrGen){
        System.out.println("[Now begin to write addresses to txt file:]");
        System.out.println("[+]Show patterns:");
        for(int i=0;i<32;i++){
            System.out.print(pattern.get(i)+" ");
        }
        System.out.println("");
        for(int i=GenerateAddr.size()-1;i>=0;i--){
            System.out.print(GenerateAddr.get(i)+" ");
        }
        long startWrite = System.currentTimeMillis();
        ArrayList<String> n1 = new ArrayList<>();ArrayList<String> n2 = new ArrayList<>();ArrayList<String> n3 = new ArrayList<>();ArrayList<String> n4 = new ArrayList<>();
        ArrayList<String> n5 = new ArrayList<>();ArrayList<String> n6 = new ArrayList<>();ArrayList<String> n7 = new ArrayList<>();ArrayList<String> n8 = new ArrayList<>();
        ArrayList<String> n9 = new ArrayList<>();ArrayList<String> n10 = new ArrayList<>();ArrayList<String> n11 = new ArrayList<>();ArrayList<String> n12 = new ArrayList<>();
        ArrayList<String> n13 = new ArrayList<>();ArrayList<String> n14 = new ArrayList<>();ArrayList<String> n15 = new ArrayList<>();ArrayList<String> n16 = new ArrayList<>();
        ArrayList<String> n17 = new ArrayList<>();ArrayList<String> n18 = new ArrayList<>();ArrayList<String> n19 = new ArrayList<>();ArrayList<String> n20 = new ArrayList<>();
        ArrayList<String> n21 = new ArrayList<>();ArrayList<String> n22 = new ArrayList<>();ArrayList<String> n23 = new ArrayList<>();ArrayList<String> n24 = new ArrayList<>();
        ArrayList<String> n25 = new ArrayList<>();ArrayList<String> n26 = new ArrayList<>();ArrayList<String> n27 = new ArrayList<>();ArrayList<String> n28 = new ArrayList<>();
        ArrayList<String> n29 = new ArrayList<>();ArrayList<String> n30 = new ArrayList<>();ArrayList<String> n31 = new ArrayList<>();ArrayList<String> n32 = new ArrayList<>();
        int sentinel = 0;
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n1.add(GenerateAddr.get(i));}}
        if(pattern.get(0).equals("Range")){System.out.print("\nn1: ");n1=generateArr(seedSetList,1,"Range");}if(pattern.get(0).equals("List")){System.out.print("\nn1: ");n1=generateArr(seedSetList,1,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n2.add(GenerateAddr.get(i));}}
        if(pattern.get(1).equals("Range")){System.out.print("\nn2: ");n2=generateArr(seedSetList,2,"Range");}if(pattern.get(1).equals("List")){System.out.print("\nn2: ");n2=generateArr(seedSetList,2,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n3.add(GenerateAddr.get(i));}}
        if(pattern.get(2).equals("Range")){System.out.print("\nn3: ");n3=generateArr(seedSetList,3,"Range");}if(pattern.get(2).equals("List")){System.out.print("\nn3: ");n3=generateArr(seedSetList,3,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n4.add(GenerateAddr.get(i));}}
        if(pattern.get(3).equals("Range")){System.out.print("\nn4: ");n4=generateArr(seedSetList,4,"Range");}if(pattern.get(3).equals("List")){System.out.print("\nn4: ");n4=generateArr(seedSetList,4,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n5.add(GenerateAddr.get(i));}}
        if(pattern.get(4).equals("Range")){System.out.print("\nn5: ");n5=generateArr(seedSetList,5,"Range");}if(pattern.get(4).equals("List")){System.out.print("\nn5: ");n5=generateArr(seedSetList,5,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n6.add(GenerateAddr.get(i));}}
        if(pattern.get(5).equals("Range")){System.out.print("\nn6: ");n6=generateArr(seedSetList,6,"Range");}if(pattern.get(5).equals("List")){System.out.print("\nn6: ");n6=generateArr(seedSetList,6,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n7.add(GenerateAddr.get(i));}}
        if(pattern.get(6).equals("Range")){System.out.print("\nn7: ");n7=generateArr(seedSetList,7,"Range"); }if(pattern.get(6).equals("List")){System.out.print("\nn7: ");n7=generateArr(seedSetList,7,"List"); }
        for(int i=sentinel;i<GenerateAddr.size();i++){ if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break; }if(Integer.valueOf(GenerateAddr.get(i)) != -1){ n8.add(GenerateAddr.get(i)); } }
        /*System.out.println("[+]Show n8:");
        for(int i=n8.size()-1;i>=0;i--){System.out.println(n8.get(i)); }*/
        if(pattern.get(7).equals("Range")){System.out.print("\nn8: ");n8=generateArr(seedSetList,8,"Range");}if(pattern.get(7).equals("List")){System.out.print("\nn8: ");n8=generateArr(seedSetList,8,"List"); }
        for(int i=sentinel;i<GenerateAddr.size();i++){ if(Integer.valueOf(GenerateAddr.get(i)) == -1){ i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){ n9.add(GenerateAddr.get(i)); } }
        /*System.out.println("[+]Show n8:");
        for(int i=n8.size()-1;i>=0;i--){ System.out.println(n8.get(i)); }*/
        if(pattern.get(8).equals("Range")){System.out.print("\nn9: ");n9=generateArr(seedSetList,9,"Range");}if(pattern.get(8).equals("List")){System.out.print("\nn9: ");n9=generateArr(seedSetList,9,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n10.add(GenerateAddr.get(i));}}
        if(pattern.get(9).equals("Range")){System.out.print("\nn10: ");n10=generateArr(seedSetList,10,"Range");}if(pattern.get(9).equals("List")){System.out.print("\nn10: ");n10=generateArr(seedSetList,10,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n11.add(GenerateAddr.get(i));}}
        if(pattern.get(10).equals("Range")){System.out.print("\nn11: ");n11=generateArr(seedSetList,11,"Range");}if(pattern.get(10).equals("List")){System.out.print("\nn11: ");n11=generateArr(seedSetList,11,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n12.add(GenerateAddr.get(i));}}
        if(pattern.get(11).equals("Range")){System.out.print("\nn12: ");n12=generateArr(seedSetList,12,"Range");}if(pattern.get(11).equals("List")){System.out.print("\nn12: ");n12=generateArr(seedSetList,12,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n13.add(GenerateAddr.get(i));}}
        if(pattern.get(12).equals("Range")){System.out.print("\nn13: ");n13=generateArr(seedSetList,13,"Range");}if(pattern.get(12).equals("List")){System.out.print("\nn13: ");n13=generateArr(seedSetList,13,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n14.add(GenerateAddr.get(i));}}
        if(pattern.get(13).equals("Range")){System.out.print("\nn14: ");n14=generateArr(seedSetList,14,"Range");}if(pattern.get(13).equals("List")){System.out.print("\nn14: ");n14=generateArr(seedSetList,14,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n15.add(GenerateAddr.get(i));}}
        if(pattern.get(14).equals("Range")){System.out.print("\nn15: ");n15=generateArr(seedSetList,15,"Range");}if(pattern.get(14).equals("List")){System.out.print("\nn15: ");n15=generateArr(seedSetList,15,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n16.add(GenerateAddr.get(i));}}
        if(pattern.get(15).equals("Range")){System.out.print("\nn16: ");n16=generateArr(seedSetList,16,"Range");}if(pattern.get(15).equals("List")){System.out.print("\nn16: ");n16=generateArr(seedSetList,16,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n17.add(GenerateAddr.get(i));}}
        if(pattern.get(16).equals("Range")){System.out.print("\nn17: ");n17=generateArr(seedSetList,17,"Range");}if(pattern.get(16).equals("List")){System.out.print("\nn17: ");n17=generateArr(seedSetList,17,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n18.add(GenerateAddr.get(i));}}
        if(pattern.get(17).equals("Range")){System.out.print("\nn18: ");n18=generateArr(seedSetList,18,"Range");}if(pattern.get(17).equals("List")){System.out.print("\nn18: ");n18=generateArr(seedSetList,18,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n19.add(GenerateAddr.get(i));}}
        if(pattern.get(18).equals("Range")){System.out.print("\nn19: ");n19=generateArr(seedSetList,19,"Range");}if(pattern.get(18).equals("List")){System.out.print("\nn19: ");n19=generateArr(seedSetList,19,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n20.add(GenerateAddr.get(i));}}
        if(pattern.get(19).equals("Range")){System.out.print("\nn20: ");n20=generateArr(seedSetList,20,"Range");}if(pattern.get(19).equals("List")){System.out.print("\nn20: ");n20=generateArr(seedSetList,20,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n21.add(GenerateAddr.get(i));}}
        if(pattern.get(20).equals("Range")){System.out.print("\nn21: ");n21=generateArr(seedSetList,21,"Range");}if(pattern.get(20).equals("List")){System.out.print("\nn21: ");n21=generateArr(seedSetList,21,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n22.add(GenerateAddr.get(i));}}
        if(pattern.get(21).equals("Range")){System.out.print("\nn22: ");n22=generateArr(seedSetList,22,"Range");}if(pattern.get(21).equals("List")){System.out.print("\nn22: ");n22=generateArr(seedSetList,22,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n23.add(GenerateAddr.get(i));}}
        if(pattern.get(22).equals("Range")){System.out.print("\nn23: ");n23=generateArr(seedSetList,23,"Range");}if(pattern.get(22).equals("List")){System.out.print("\nn23: ");n23=generateArr(seedSetList,23,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n24.add(GenerateAddr.get(i));}}
        if(pattern.get(23).equals("Range")){System.out.print("\nn24: ");n24=generateArr(seedSetList,24,"Range");}if(pattern.get(23).equals("List")){System.out.print("\nn24: ");n24=generateArr(seedSetList,24,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n25.add(GenerateAddr.get(i));}}
        if(pattern.get(24).equals("Range")){System.out.print("\nn25: ");n25=generateArr(seedSetList,25,"Range");}if(pattern.get(24).equals("List")){System.out.print("\nn25: ");n25=generateArr(seedSetList,25,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n26.add(GenerateAddr.get(i));}}
        if(pattern.get(25).equals("Range")){System.out.print("\nn26: ");n26=generateArr(seedSetList,26,"Range");}if(pattern.get(25).equals("List")){System.out.print("\nn26: ");n26=generateArr(seedSetList,26,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n27.add(GenerateAddr.get(i));}}
        if(pattern.get(26).equals("Range")){System.out.print("\nn27: ");n27=generateArr(seedSetList,27,"Range");}if(pattern.get(26).equals("List")){System.out.print("\nn27: ");n27=generateArr(seedSetList,27,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n28.add(GenerateAddr.get(i));}}
        if(pattern.get(27).equals("Range")){System.out.print("\nn28: ");n28=generateArr(seedSetList,28,"Range");}if(pattern.get(27).equals("List")){System.out.print("\nn28: ");n28=generateArr(seedSetList,28,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n29.add(GenerateAddr.get(i));}}
        if(pattern.get(28).equals("Range")){System.out.print("\nn29: ");n29=generateArr(seedSetList,29,"Range");}if(pattern.get(28).equals("List")){System.out.print("\nn29: ");n29=generateArr(seedSetList,29,"List");}
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){i++;sentinel = i;break;}if(Integer.valueOf(GenerateAddr.get(i)) != -1){n30.add(GenerateAddr.get(i));}}
        if(pattern.get(29).equals("Range")){System.out.print("\nn30: ");n30=generateArr(seedSetList,30,"Range"); }if(pattern.get(29).equals("List")){ System.out.print("\nn30: ");n30=generateArr(seedSetList,30,"List"); }
        for(int i=sentinel;i<GenerateAddr.size();i++){if(Integer.valueOf(GenerateAddr.get(i)) == -1){ i++;sentinel = i;break; }if(Integer.valueOf(GenerateAddr.get(i)) != -1){ n31.add(GenerateAddr.get(i)); } }
        //*************
        if(pattern.get(30).equals("Range")){ System.out.print("\nn31: ");n31=generateArr(seedSetList,31,"Range"); }if(pattern.get(30).equals("List")){ System.out.print("\nn31: ");n31=generateArr(seedSetList,31,"List"); }
        for(int i=sentinel;i<GenerateAddr.size();i++){ if(Integer.valueOf(GenerateAddr.get(i)) == -1) {i++;sentinel = i;break; }if(Integer.valueOf(GenerateAddr.get(i)) != -1){ n32.add(GenerateAddr.get(i)); } }
        if(pattern.get(31).equals("Range")){ System.out.print("\nn32: ");n32=generateArr(seedSetList,32,"Range"); }if(pattern.get(31).equals("List")){ System.out.print("\nn32: ");n32=generateArr(seedSetList,32,"List"); }
        System.out.print("\n");
        long count = 0;
        AddrgenRecord addrgenRecord = new AddrgenRecord();
        addrgenRecord.setInputFilename(addrGen.getFileName());
        addrgenRecord.setOutputFilename("BSPR-Result.txt");
        Date startdate = new Date();
        addrgenRecord.setStartTime(startdate);
        addrgenRecord.setIsmul(addrGen.getIsmul());
        List<AddrgenRecordDetails> detailsList = new ArrayList<>();
        try {
            File file = new File("BSPR-Result.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            int sentinel2 = 0;
            for(int a=0;a<n1.size();a++){
                for(int b=0;b<n2.size();b++){
                    for(int c=0;c<n3.size();c++){
                        for(int d=0;d<n4.size();d++){
                            for(int e=0;e<n5.size();e++){
                                for(int f=0;f<n6.size();f++){
                                    for(int g=0;g<n7.size();g++){
                                        for(int h=0;h<n8.size();h++){
                                            for(int i=0;i<n9.size();i++){
                                                for(int j=0;j<n10.size();j++){
                                                    for(int k=0;k<n11.size();k++){
                                                        for(int l=0;l<n12.size();l++){
                                                            for(int m=0;m<n13.size();m++){
                                                                for(int n=0;n<n14.size();n++){
                                                                    for(int o=0;o<n15.size();o++){
                                                                        for(int p=0;p<n16.size();p++){
                                                                            for(int q=0;q<n17.size();q++){
                                                                                for(int r=0;r<n18.size();r++){
                                                                                    for(int s=0;s<n19.size();s++){
                                                                                        for(int t=0;t<n20.size();t++){
                                                                                            for(int u=0;u<n21.size();u++){
                                                                                                for(int v=0;v<n22.size();v++){
                                                                                                    for(int w=0;w<n23.size();w++){
                                                                                                        for(int x=0;x<n24.size();x++){
                                                                                                            for(int y=0;y<n25.size();y++){
                                                                                                                for(int z=0;z<n26.size();z++){
                                                                                                                    for(int aa=0;aa<n27.size();aa++){
                                                                                                                        for(int bb=0;bb<n28.size();bb++){
                                                                                                                            for(int cc=0;cc<n29.size();cc++){
                                                                                                                                for(int dd=0;dd<n30.size();dd++){
                                                                                                                                    for(int ee=0;ee<n31.size();ee++){
                                                                                                                                        for(int ff=0;ff<n32.size();ff++){
                                                                                                                                            if((sentinel2+1)<=BudgetLimit) {
                                                                                                                                                String addr = "";
                                                                                                                                                addr = stringToHexString(Integer.valueOf(n1.get(a))) + stringToHexString(Integer.valueOf(n2.get(b))) + stringToHexString(Integer.valueOf(n3.get(c))) + stringToHexString(Integer.valueOf(n4.get(d))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n5.get(e))) + stringToHexString(Integer.valueOf(n6.get(f))) + stringToHexString(Integer.valueOf(n7.get(g))) + stringToHexString(Integer.valueOf(n8.get(h))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n9.get(i))) + stringToHexString(Integer.valueOf(n10.get(j))) + stringToHexString(Integer.valueOf(n11.get(k))) + stringToHexString(Integer.valueOf(n12.get(l))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n13.get(m))) + stringToHexString(Integer.valueOf(n14.get(n))) + stringToHexString(Integer.valueOf(n15.get(o))) + stringToHexString(Integer.valueOf(n16.get(p))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n17.get(q))) + stringToHexString(Integer.valueOf(n18.get(r))) + stringToHexString(Integer.valueOf(n19.get(s))) + stringToHexString(Integer.valueOf(n20.get(t))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n21.get(u))) + stringToHexString(Integer.valueOf(n22.get(v))) + stringToHexString(Integer.valueOf(n23.get(w))) + stringToHexString(Integer.valueOf(n24.get(x))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n25.get(y))) + stringToHexString(Integer.valueOf(n26.get(z))) + stringToHexString(Integer.valueOf(n27.get(aa))) + stringToHexString(Integer.valueOf(n28.get(bb))) + ":"
                                                                                                                                                        +stringToHexString(Integer.valueOf(n29.get(cc))) + stringToHexString(Integer.valueOf(n30.get(dd))) + stringToHexString(Integer.valueOf(n31.get(ee))) + stringToHexString(Integer.valueOf(n32.get(ff)));
                                                                                                                                                if(seedSetList.contains(addr)){
                                                                                                                                                    continue;
                                                                                                                                                }
                                                                                                                                                System.out.println(addr);
                                                                                                                                                //String addr1 = addr.replaceAll("(.{4})", "$1:");
                                                                                                                                                //addr1 = addr1.substring(0, addr1.length()-1);
                                                                                                                                                AddrgenRecordDetails addrgenRecordDetails = new AddrgenRecordDetails();
                                                                                                                                                addrgenRecordDetails.setIpaddr(addr);
                                                                                                                                                detailsList.add(addrgenRecordDetails);
                                                                                                                                                System.out.println(count++);
                                                                                                                                                //FileWriter fwriter = new FileWriter(file.getName(),true);
                                                                                                                                                //fwriter.write(addr + "\n");
                                                                                                                                                //fwriter.close();
                                                                                                                                                sentinel2++;
                                                                                                                                            }else{
                                                                                                                                                System.out.println("跳出所有循环");
                                                                                                                                                Date endtime = new Date();
                                                                                                                                                addrgenRecord.setEndTime(endtime);
                                                                                                                                                addrgenRecord.setTotalNums(count);
                                                                                                                                                addrgenRecord.setAddrgenRecordDetailsList(detailsList);
                                                                                                                                                return addrgenRecord;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(" * Write "+sentinel2+" addresses to file accomplished! Use time:"+(System.currentTimeMillis() - startWrite)+"ms");
        } catch (IOException e) {
            System.out.println(" * Write to file failed.");
        }
        //System.out.println(" * Write to file use time:"+(System.currentTimeMillis() - startWrite)+"ms");
        return addrgenRecord;
    }
    //main function
    public static AddrgenRecord bsprGen(List<String> addrlist, AddrGen addrGen){
        //1.从种子地址集中读取所有种子，将其存入数组
        ArrayList<String> seedSetList = new ArrayList<>();
        long startRead = System.currentTimeMillis();
        //File file = new File("E:\\IPv6扫描\\函数\\BSPR-Gen\\BSPR-Gen\\seed.txt");
        //InputStreamReader input = new InputStreamReader(new FileInputStream(file));
        //BufferedReader bf = new BufferedReader(input);
        Iterator<String> iterator = addrlist.iterator();
        //String str;
        while (iterator.hasNext()) {
            String standardIpaddress = iterator.next();
            if (standardIpaddress != null){
                String s = standardIpaddress.replace(":", "");
                seedSetList.add(s);
                System.out.println(s);
            }
        }
        //bf.close();
        //input.close();
        System.out.println("[Successfully read seed address file!");
        System.out.println(" * Read seed use time:"+(System.currentTimeMillis() - startRead)+"ms");
        //2.开始计算当前种子集合的Range、ValueTypes、Shannon
        System.out.println("[Now begin to count Range,ValueTypes,Shannon:]");
        long startCountSeed = System.currentTimeMillis();
        int[] Range = new int[32];//每个半字节位的极差Range
        int[] ValueTypes = new int[32];//每个半字节位的取值种类数ValueTypes
        double[] Shannon = new double[32];//每个半字节位的香农熵Shannon
        ArrayList<Integer> values = countSeed(seedSetList,Range,ValueTypes,Shannon);//求出每个半字节位的极差Range、香农熵Shannon、取值种类数ValueTypes
        printCountSeed(Range,ValueTypes,Shannon);
        System.out.println(" * Count seed use time:"+(System.currentTimeMillis() - startCountSeed)+"ms");
        //3.开始确定三个超参阈值ranget,entropyt,valuetypest
        long startCountHyper = System.currentTimeMillis();
        String []arr=countHyperThre(Range,ValueTypes,Shannon);
        int range_t=Integer.parseInt(arr[0]);
        double entropy_t=Double.parseDouble(arr[1]);
        int type_t=Integer.parseInt(arr[2]);//System.out.println(range_t+";"+entropy_t+";"+type_t);
        System.out.println(" * Count hyper threshold use time:"+(System.currentTimeMillis() - startCountHyper)+"ms");
        //4.生成合适的模式表示
        ArrayList<String> pattern = new ArrayList<>();
        /*System.out.print("values: {");
        for(int i=0;i< values.size();i++){
            System.out.print(values.get(i)+"; ");
        }
        System.out.print("}");*/
        ArrayList<String> GenerateAddr = GeneratePR(Range,ValueTypes,Shannon,range_t,type_t,entropy_t,values,pattern);//!!!问题：便于返回，将其包含在main func中
        //5.生成地址写入文件
        //pattern.set(31,"List");
        return WriteToFile(seedSetList, GenerateAddr, addrGen.getGenNum(), pattern, addrGen);
    }
}