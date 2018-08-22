package io.moonlight.utils.test.algorithm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author qt
 * @version 1.0  createAt 2018/8/20
 */

public class CoinChange {

    public int coinChange(int[] A, int M) {
        int[] f = new int[M + 1];
        int n = A.length;

        f[0] = 0;
        int i, j;
        for (i = 1; i <= M; ++i) {
            f[i] = -1;

            for (j = 0; j < n; ++j) {
                if (i >= A[j] && f[i - A[j]] != -1) {

                    if (f[i] == -1 || f[i - A[j]] + 1 < f[i]) {
                        System.out.println(f[i]);
                        f[i] = f[i - A[j]] + 1;
                        System.out.println("all="+i+"  m="+A[j]+" "+ f[i]+""+ToStringBuilder.reflectionToString(f));
                    }

                }
            }
            System.out.println();
        }

        System.out.println(M);
        return f[M];
    }

    @Test
    public void test(){
        int result = new CoinChange().coinChange(new int[]{1,2,5},11);
        System.out.println(result);
    }

    @Test
    public void testArr(){
        int[][] f = new int[4][3];
        System.out.println(f.length);

        Set<Integer> ret = new HashSet<>();



    }
}