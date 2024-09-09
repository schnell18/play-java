package org.home.attack;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

public class IntegerCacheAttack {
    public static void main(String[] args) throws Exception {
        Integer int1 = Integer.valueOf(124);
        Integer int2 = Integer.valueOf(12);
        System.out.println("Before attack");
        test(int1, int2);

        try {
            Class<?> cls = Integer.class.getDeclaredClasses()[0];
            Field f = cls.getDeclaredField("cache");
            f.setAccessible(true);
            Integer[] cache = (Integer[]) f.get(cls);
            Arrays.fill(cache, BigDecimal.ZERO.intValue());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        int1 = Integer.valueOf(124);
        int2 = Integer.valueOf(12);
        System.out.println("After attack");
        // Becomes true on JVM prior to JDK 9
        test(int1, int2);

    }

    private static void test(Integer int1 , Integer int2) {
        System.out.printf("int1 == int2 %s \n", int1 == int2);
        System.out.printf("int1.equals(int2) == %s \n", int1.equals(int2));
    }
}
