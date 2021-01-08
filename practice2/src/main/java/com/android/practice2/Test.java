package com.android.practice2;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        while(list.size() != 0) {
            int i = (int)Math.random()*list.size();
            list.get(i);
        }

    }


}