package com.jjhome.play.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.jjhome.play.collection.ListUtils.swap;
import static com.jjhome.play.collection.ListUtils.max;
import static org.junit.Assert.*;

public class ListUtilsTest {

    @Test
    public void testSwap() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        swap(list, 1, 4);
        assertEquals(5, list.get(1).intValue());
        assertEquals(2, list.get(4).intValue());
    }

    @Test
    public void testMax() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertEquals(6, max(list).intValue());
        List<Long> list2 = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L);
        assertEquals(6, max(list2).intValue());
        List<String> list3 = Arrays.asList("a", "b", "c", "d", "e", "f");
        assertEquals("f", max(list3));
    }


}