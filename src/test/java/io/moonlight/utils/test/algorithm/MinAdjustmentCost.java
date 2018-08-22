package io.moonlight.utils.test.algorithm;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import java.util.List;

/**
 * @author qt
 * @version 1.0  createAt 2018/8/20
 */
public class MinAdjustmentCost {

    /*
     * @param A: An integer array
     * @param target: An integer
     * @return: An integer
     */
    public int MinAdjustmentCost(List<Integer> A, int target) {
        // write your code here
        if (A.size() < 2) {
            return 0;
        }
        // 有m行
        int m = A.size();

        long[][] dp = new long[m][101];
        int i = 0, j = 0;
        for (i = 0; i < 101; i++) {
            dp[0][i] = Math.abs(A.get(0) - i);
        }
        System.out.println(ToStringBuilder.reflectionToString(dp));
        for (i = 1; i < m; i++) {
            for (j = 0; j < 101; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                int dif = Math.abs(j - A.get(i));
                int max = Math.min(100, j + target);
                int min = Math.max(0, j - target);
                for (int k = min; k <= max; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + dif);
                }
            }
        }
        long ans = Integer.MAX_VALUE;
        for (j = 0; j < 101; j++) {
            ans = Math.min(ans, dp[m - 1][j]);
        }
        return (int) ans;
    }

    @Test
    public void test() {
        int ret = new MinAdjustmentCost().MinAdjustmentCost(Lists.newArrayList(1, 4, 2, 3), 1);
        System.out.println(ret);

    }

}