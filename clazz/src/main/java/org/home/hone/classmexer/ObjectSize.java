package org.home.hone.classmexer;
import com.javamex.classmexer.MemoryUtil;

public class ObjectSize {
    public static void main(String[] args) {
        String str = "Hello world! 你好世界";
        System.out.printf(
            "mem=%d deep mem=%d\n",
            MemoryUtil.memoryUsageOf(str),
            MemoryUtil.deepMemoryUsageOf(str)
        );

        int [] scores = {1, 2, 3, 4};
        System.out.printf(
            "mem=%d deep mem=%d\n",
            MemoryUtil.memoryUsageOf(scores),
            MemoryUtil.deepMemoryUsageOf(scores)
        );

        Integer [] scores2 = {1, 2, 3, 4};
        System.out.printf(
            "mem=%d deep mem=%d\n",
            MemoryUtil.memoryUsageOf(scores2),
            MemoryUtil.deepMemoryUsageOf(scores2)
        );
    }
}
