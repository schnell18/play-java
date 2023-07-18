package org.home.hone.collection;

import java.util.List;

public class ListUtils {
    private ListUtils() {
        throw new AssertionError("Instantiate this class is prohibited");
    }

    /*
    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        T bigger = list.get(0);
        for (int i=1; i < list.size(); i++) {
            if (bigger.compareTo(list.get(i)) == -1) {
                bigger = list.get(i);
            }
        }
        return bigger;
    }
    */

    public static <T extends Comparable<T>> T max(List<T> list) {
        T bigger = list.get(0);
        for (int i=1; i < list.size(); i++) {
            if (bigger.compareTo(list.get(i)) == -1) {
                bigger = list.get(i);
            }
        }
        return bigger;
    }

    public static void swap(List<?> list, int i, int j) {
        swapInternal(list, i, j);
    }

    private static <E> void swapInternal(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
