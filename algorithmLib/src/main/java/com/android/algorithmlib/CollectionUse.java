package com.android.algorithmlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class CollectionUse {

    /**
     * list
     */
    private void listUse() {
        List list1 = new ArrayList();
        list1.add("abc");
        Iterator iterator = list1.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }
        for (int i=0; i<list1.size();i++) {
            list1.get(i);
        }

        List list2 = new LinkedList();
        list2.add("abc");
        Iterator iterator1 = list2.iterator();
        while(iterator1.hasNext()) {
            iterator1.next();
        }

        Vector vector = new Vector();
        vector.add("abc");
        Iterator iterator2 = vector.iterator();
        while(iterator2.hasNext()) {
            iterator2.next();
        }


    }

    /**
     * set
     */
    private void setUse() {
        Set set = new HashSet();
        set.add("abc");
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }


        Set set1 = new LinkedHashSet();
        set1.add("abc");


        Set set2 = new TreeSet();
        set2.add("abc");

    }

    /**
     * map
     */
    private void mapUse() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("abc", "adc");
        Set<Map.Entry<String,String>> set =  map.entrySet();
        Iterator<Map.Entry<String,String>> iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String,String> entry = iterator.next();
            entry.getKey();
            entry.getValue();

        }
        for(String key : map.keySet()) {
            String value = map.get(key);

        }


        Hashtable<String,String> hashtable = new Hashtable<>();
        hashtable.put("abc","abc");
        for (String key : hashtable.keySet()) {
            System.out.println("the key: "+key);
        }

    }
}
