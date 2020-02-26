package com.algorithms.dynamicPlain;

import java.util.Arrays;

/**
 * 动态规划之最长公共子序列问题。最长公共子序列问题的难点在于
 * text1 = "abcde", text2 = "ace" 最长公共子序列是 "ace"，它的长度为 3。
 * 难以穷举。子序列是可以非连续的。这一题和棋盘上只能往下往右移动求走法是一样的。
 * 1. 这里变化的两有两个，text1,text2.所以dp应该考虑二维的。
 * 2. 问题规模变小。text1/text2的长度减小一个，变成子问题。子问题的最长公共子序列和原问题有什么区别
 * 区别在于，如果最后一个数一样，那么原问题的最长子序列为子问题的加1.否则和子问题一样。
 */
public class LCS {

    /**
     * @param text1
     * @param text2
     * @return
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.length() == 0) {
            return 0;
        }
        if (text2 == null || text2.length() == 0) {
            return 0;
        }

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int[] array : dp) {
            Arrays.fill(array, 0);
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[text1.length()][text2.length()];
        //return dg(text1.length() - 1, text1, text2.length() - 1, text2);
    }

    /**
     * 递归的方式：从最后一位开始比较，相等，则返回子序列的最长子序列加1，否则找子序列
     * 中最长的返回。
     * base:如果i==0||j==0，说明走到头了。返回0
     *
     * @param i
     * @param text1
     * @param j
     * @param text2
     * @return
     */
    public static int dg(int i, String text1, int j, String text2) {
        if (i <= -1 || j <= -1) {
            return 0;
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            return dg(i - 1, text1, j - 1, text2) + 1;
        }

        return Integer.max(dg(i - 1, text1, j, text2), dg(i, text1, j - 1, text2));
    }


    public static void main(String[] args) {
        int subsequence = longestCommonSubsequence("abcde", "ace");
        System.out.println(subsequence);
    }
}
