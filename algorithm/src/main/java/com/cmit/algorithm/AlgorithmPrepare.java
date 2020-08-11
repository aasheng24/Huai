package com.cmit.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.TreeSet;


public class AlgorithmPrepare {

    public static void prepare() {
        //扫描
        Scanner scanner = new Scanner(System.in);
//        new Scanner(System.in)
        while (scanner.hasNext()) {

            //String
            String string = scanner.nextLine();
            int i = scanner.nextInt();
            int length = string.length();
            char[] ret = string.toCharArray();
            String string2 = string.substring(0);
            String string3 = string.toUpperCase();
            char charAt = string.charAt(0);
            int point = string.indexOf('a');
            string.valueOf(point);


            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder("sd");
            stringBuilder.append('s');
            stringBuilder.toString();
        }

        //集合
        //有序
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        treeSet.add(1);
        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        //hashMap
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("aa","bb");
        hashMap.put("cc","dd");
        //迭代
        for (String key : hashMap.keySet()) {
            String value = hashMap.get(key);
        }

        for (Map.Entry<String,String> entry : hashMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
        }

        Iterator<String> iterator1 = hashMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = iterator1.next();
            String value = hashMap.get(key);
        }

        //排序
        char[] chars = new char[] {'a','c','d','e'};
        Arrays.sort(chars);
        List list = new ArrayList();
        Collections.sort(list);

        //进制转换
        String input = "Ox12";
        int i = Integer.parseInt(input , 16);

        //ASCII码
        //0-9 48 - 57
        //A-Z 65 - 90
        //a-z 97 - 122
        char a = 'A';
        Character.toLowerCase(a);

        String ss = "adaf";
        char[] chars1 = ss.toCharArray();


    }

    public static void silu() {
        //去重，定义一个新的数组，以原有内容为index，遍历如果非零则输出，否则赋值到新的数组

    }






}
