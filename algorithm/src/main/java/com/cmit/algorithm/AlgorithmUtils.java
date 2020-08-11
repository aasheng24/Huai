package com.cmit.algorithm;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class AlgorithmUtils {
    public List<List<Integer>> threeNums(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Arrays.sort(nums);
        int[] ss = new int[]{2,3};
         List<Integer> ret2 = Arrays.asList(new Integer[]{1,2,3});
        //return ret;
        /*
        import java.util.Scanner;
        public class Main{
            public static void main(String[] args) {
                Scanner input = new Scanner(System.in);
                System.out.println()

            }
        }

        */
        return null;

    }

    /*
     回文判断
     */
    public static boolean huiwen(int x) {
        String before = ""+x;
        StringBuilder builder = new StringBuilder(before);
        String rever = builder.reverse().toString();
        if (before.equals(rever)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     单向链表反转
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int value) {
            val = value;
        }
    }
    public static ListNode listReverse(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode pre = null, cur =node, nex = null;
        while(cur != null) {
            cur.next = pre;
            pre = cur;
            cur = cur.next;
        }
        return pre;
    }

    public static void test() {
        int a = 3;
        int b = 1;
        if (a == b) {

        }
    }
}
