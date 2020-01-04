package org.home.hone;

public class Uniq {
    public int[] uniq(int[] list) {
        int j = 0;
        // count the number of unique elements so that we
        // can initialize properly sized result Array w/o waste
        for (int i = 0; i < list.length - 1; i ++) {
            if (list[i] != list[i + 1]) {
                j++;
            }
        }
        int[] ret = new int[j + 1];
        j = 0;
        for (int i = 0; i < list.length - 1; i ++) {
            if (list[i] != list[i + 1]) {
                ret[j++] = list[i];
            }
        }
        ret[j++] = list[list.length - 1];
        return ret;
    }
}
