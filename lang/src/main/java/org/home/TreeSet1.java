package org.home;

import java.util.Set;
import java.util.TreeSet;

public class TreeSet1 {
    public static void main(String[] args) {
        TreeSet<Integer> s = new TreeSet<>();
        for (int i = 606; i < 613; i++) if (i % 2 == 0) s.add(i);
        Set<Integer> subs = s.subSet(608, true, 611, true);
        s.add(609);
        System.out.println(s + " " + subs);
    }
}
