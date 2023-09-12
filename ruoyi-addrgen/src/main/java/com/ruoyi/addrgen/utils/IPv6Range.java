package com.ruoyi.addrgen.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class IPv6Range {

    public static void main(String[] args) {
        String saddr = "2409:8914:687:ca6:5196:b5a9:435:8131";
        String eaddr = "2409:8914:687:ca6:5196:b5a9:435:8101";

        try {
            List<String> ipv6List = getIPv6Range(saddr, eaddr);
            for (String ip : ipv6List) {
                System.out.println(ip);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getIPv6Range(String saddr, String eaddr) throws UnknownHostException {
        InetAddress startAddress = InetAddress.getByName(saddr);
        InetAddress endAddress = InetAddress.getByName(eaddr);

        // Compare addresses to determine the start and end
        BigInteger start = new BigInteger(1, startAddress.getAddress());
        BigInteger end = new BigInteger(1, endAddress.getAddress());
        if (start.compareTo(end) > 0) {
            BigInteger temp = start;
            start = end;
            end = temp;
        }

        // Generate the list of IPv6 addresses between start and end
        List<String> ipv6List = new ArrayList<>();
        for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
            byte[] addressBytes = ByteBuffer.allocate(16).put(i.toByteArray()).array();
            InetAddress ipv6Address = InetAddress.getByAddress(addressBytes);
            ipv6List.add(ipv6Address.getHostAddress());
        }

        return ipv6List;
    }

    public static String[] extractParenthesis(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        List<String> parts = new ArrayList<>();
        int startIndex = 0;
        int leftBracketCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                leftBracketCount++;
                if (leftBracketCount == 1) {
                    startIndex = i;
                }
            } else if (c == ')') {
                leftBracketCount--;
                if (leftBracketCount == 0) {
                    parts.add(str.substring(startIndex, i+1));
                }
            }
        }
        if (leftBracketCount != 0) {
            throw new IllegalArgumentException("Unmatched brackets in input string");
        }
        return parts.toArray(new String[0]);
    }
}