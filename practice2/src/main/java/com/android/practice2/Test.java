package com.android.practice2;

public class Test {
    public static void main(String[] args) {
        findTheDifference("adbd","adbde");
    }

    private static  char findTheDifference(String s, String t) {
        StringBuilder sb = new StringBuilder(t);
        int start = sb.indexOf(s);
        sb.delete(start, start + s.length());
        return sb.toString().toCharArray()[0];
    }

}