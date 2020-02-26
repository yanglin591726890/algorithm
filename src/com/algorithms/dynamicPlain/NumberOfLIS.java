package com.algorithms.dynamicPlain;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/
 * 最长递增子序列的个数
 */
public class NumberOfLIS {


    /**
     * 这一题是再最长递增子序列的基础上的题目，求最长递归子序列的个数，那么肯定要先求
     * 最长递增序列，这个所以是个动态规划的题目，dp数组是子序列的长度。但是是求个数，
     * 是在最长的基础上的，也就是dp数组的基础上求的。所以需要一个数组记录count的个数。
     *
     * @param nums
     * @return
     */
    public static int findNumberOfLIS(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        int[] dp = new int[nums.length];
        // 记录有种路
        int[] count = new int[nums.length];
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        dp[0] = 1;
        int max = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // 如果大于，则更新dp[i]的最大值
                    if ((dp[j] + 1) > dp[i]) {
                        dp[i] = dp[j] + 1;
                        // 走到dp[i]的可能有count[j]种
                        count[i] = count[j];
                    } else if (dp[i] == (dp[j] + 1)) {
                        // 如果dp[i]==dp[j]+1 则说明有新的方式得到dp[i]
                        count[i] += count[j];
                    }
                }
            }
            max = Integer.max(max, dp[i]);
        }
        int result = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] == max) {
                result += count[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 4, 3, 5, 4, 7, 2};
        int numberOfLIS = findNumberOfLIS(nums);
        System.out.println(numberOfLIS);
    }

}
