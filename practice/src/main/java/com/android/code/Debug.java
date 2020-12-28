package com.android.algorithmlib;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LeekcodeDebug {
    public static void main(String[] args) {
        //String v1 = "1.0.3";
        //String v2 = "1.1.3";
        //System.out.println(Integer.parseInt("00012"));;
        //int ret = compareVersion(v1,v2);

        /*int[][] pos = {{10,16},{2,8},{1,6},{7,12}};
        findMinArrowShots(pos);*/

        //int N = 1024;
        //Integer integer = new Integer(123);
        //int ret = change(N);
        /*while (N >= 9) {
            if(dandiao(N)) {
                ret = N;
                break;
            }
            N--;
        }*/
        String str = "1234";
        Integer integer = Integer.valueOf(str);
        int ret = (int) integer;
        StringBuilder sb = new StringBuilder(str);
        sb.replace(0, sb.length(), "haha");
        System.out.println(ret);


    }

    public static int change(int N) {
        System.out.println("N: "+N);
        String str = String.valueOf(N);
        StringBuilder stringBuilder = new StringBuilder(str).reverse();
        int index = stringBuilder.length();
        for(int i = 0; i < stringBuilder.length() ; i++) {

            /*if (i > index) {
                stringBuilder.setCharAt(i,'9');
                continue;

            }*/

            if (i == stringBuilder.length() - 1) {
                continue;
            }

            if(stringBuilder.charAt(i) < stringBuilder.charAt(i + 1)) {
                stringBuilder.setCharAt(i,'9');
                stringBuilder.setCharAt(i+1,(char)(stringBuilder.charAt(i+1) - 1));
                index = i;
            }
        }
        stringBuilder.reverse();
        for(int i = 0; i < stringBuilder.length() ; i++) {
            if (i > stringBuilder.length() - index -1) {
                stringBuilder.setCharAt(i,'9');
            }
        }
        return Integer.valueOf(stringBuilder.toString());
    }

    public static boolean dandiao(int N) {
        boolean ret = true;
        int index = N;
        while (N > 0) {
            if (N%10 > index) {
                return false;
            } else {
                index = N%10;
                N = N/10;
            }
            //System.out.println(N);
        }
        return ret;
    }

    public static int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] point1, int[] point2) {
                if (point1[1] > point2[1]) {
                    return 1;
                } else if (point1[1] < point2[1]) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (int i = 0; i < points.length; i ++ ){
            System.out.println(Arrays.toString(points[i]));
        }

        int pos = points[0][1];
        int ans = 1;
        for (int[] balloon: points) {
            System.out.println(Arrays.toString(balloon));
            if (balloon[0] > pos) {
                pos = balloon[1];
                ++ans;
            }
        }
        return ans;
    }


    public static int compareVersion(String version1, String version2) {
        System.out.println("compareVersion start ");
        int ret = 0;
        //when it is . or |, use "\\." "\\|" instead
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int shortlength = v1.length >= v2.length ? v2.length : v1.length;
        for (int i = 0; i <= shortlength - 1; i++) {
            if (Integer.parseInt(quLing(v1[i])) > Integer.parseInt(quLing(v2[i]))) {
                System.out.println("compareVersion > ");
                ret = 1;
                break;
            }else if (Integer.parseInt(quLing(v1[i])) < Integer.parseInt(quLing(v2[i]))){
                System.out.println("compareVersion < ");
                ret = -1;
                break;
            } else {
                System.out.println("compareVersion = ");
                continue;
            }

        }
        if (ret == 0) {
            String[] longOne = v1.length > v2.length ? v1 : v2;

            for (int i = shortlength; i<= longOne.length -1; i++) {
                if (!quLing(longOne[i]).equals("0")) {
                    ret = v1.length > v2.length ? 1:-1;
                    break;
                }
            }
        }
        System.out.println();
        return ret;
    }

    private static String quLing(String start) {
        StringBuilder ret = new StringBuilder(start);
        while (ret.length() >=2 && ret.charAt(0) == '0') {
            ret.deleteCharAt(0);
        }
        return ret.toString();
    }

    private static char findTheDifference(String s, String t) {
        StringBuilder ss = new StringBuilder(s);
        StringBuilder tt = new StringBuilder(t);

    }


}