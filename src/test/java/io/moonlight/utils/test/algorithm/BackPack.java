package io.moonlight.utils.test.algorithm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author qt
 * @version 1.0  createAt 2018/8/20
 */
public class BackPack {

    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     *
     * @return: The maximum size
     */
    public int backPack(int m, int[] A) {
        // write your code here
        int n = A.length, i = 0, j = 0;
        int[] dp = new int[m + 1];
        Arrays.fill(dp,0);
        for (i = 0; i < n; i++) {
            for (j = m; j >= 1; j--) {

                if (j >= A[i]) {
                    try {
                        dp[j] = Math.max(dp[j], dp[j - A[i]] + A[i]);
                        System.out.println(i+" "+j+" "+A[i]+" "+ToStringBuilder.reflectionToString(dp));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(j);
                    }
                }
                System.out.println();
            }
        }
        return dp[m];
    }

    @Test
    public void test() {
        int result = new BackPack().backPack(11, new int[]{2, 3, 5, 7});
        System.out.println(result);
    }
}