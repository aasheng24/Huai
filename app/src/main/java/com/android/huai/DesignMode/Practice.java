package com.android.huai.DesignMode;

import java.util.Arrays;
import java.util.Comparator;

public class Practice {
    private Practice() {

    }

    private static Practice practice = null;

    public Practice getPractice() {
        if (practice == null) {
            synchronized (Practice.class) {
                if (practice == null) {
                    practice = new Practice();
                }
            }
        }
        return practice;
    }

    public int compareVersion(String version1, String version2) {
        int ret = 0;
        String[] v1 = version1.split(".");
        String[] v2 = version2.split(".");
        int shortlength = v1.length >= v2.length ? v2.length : v1.length;
        for (int i = 0; i <= shortlength - 1; i++) {
            if (Integer.parseInt(quLing(v1[i])) > Integer.parseInt(quLing(v2[i]))) {
                ret = 1;
                break;
            }else if (Integer.parseInt(quLing(v1[i])) < Integer.parseInt(quLing(v2[i]))){
                ret = -1;
                break;
            } else {
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

    private String quLing(String start) {
        StringBuilder ret = new StringBuilder(start);
        while (ret.length() >=2 && ret.charAt(0) == '0') {
            ret.deleteCharAt(0);
        }
        return ret.toString();
    }

    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) {
            return 0;
        }

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] > o2[1]) {
                    return 1;
                } else if (o1[1] < o2[1]) {
                    return -1;
                }
                return 0;
            }
        });

        int ret = 1;
        int index = points[0][1];
        for (int[] haha : points) {
            if (haha[0] > index) {
                index = haha[1];
                ret++;
            }
        }
        return ret;
    }

    StringBuilder stringBuilder = new StringBuilder(124);

}
