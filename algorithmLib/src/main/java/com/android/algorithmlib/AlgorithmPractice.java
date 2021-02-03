package com.android.algorithmlib;

public class AlgorithmPractice {

    public static void main(String[] args) {
        System.out.println("hello world");

    }


    /**
     * 冒泡排序
     */
    private static int[] sortMaoPao(int[] ints) {
        int[] ret = ints;
        for (int i=0; i<ret.length - 1;i++) {//外层循环控制排序躺数
            for (int j=0;j<ret.length -1 - i;j++) {//内层循环控制每一趟排序次数
                if (ret[j] > ret[j+1]) {
                    int temp = ret[j];
                    ret[j] = ret[j+1];
                    ret[j+1] = temp;
                }
            }
        }
        return ret;
    }

    /**
     * 单链表反序
     */
    private static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private static Node reverseNode(Node head) {
        Node pre = null;
        while (head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return head;
    }
}