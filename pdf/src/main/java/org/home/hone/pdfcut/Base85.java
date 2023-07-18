package org.home.hone.pdfcut;

import java.io.UnsupportedEncodingException;

public class Base85 {

    public static byte[] base85(byte[] quatro) {
        long remainder = (((long)quatro[3]) & 0xFF) * 256 * 256 * 256
            + (((long)quatro[2]) & 0xFF) * 256 * 256
            + (((long)quatro[1]) & 0xFF) * 256
            + (((long)quatro[0]) & 0xFF);

        byte[] results = new byte[5];
        for (int i = 5; i > 0; i--) {
            int alpha = pow(85, i - 1);
            long beta = remainder / alpha;
            remainder = remainder % alpha;
            if (beta == 0)
                results[i - 1] = 'z';
            else
                results[i - 1] = (byte) (beta + '!');
        }
        return results;
    }

    private static int pow(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i ++)  {
             result *= base;
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] input = "一帆风顺".getBytes("utf-8");
        byte[] result = base85(input);
        System.out.println(new String(result));
    }
}
