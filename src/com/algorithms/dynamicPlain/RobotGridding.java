package com.algorithms.dynamicPlain;

import java.util.Arrays;

/**
 * 机器人走网格，
 * https://leetcode-cn.com/problems/unique-paths/
 */
public class RobotGridding {
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }

        int[][] dynam = new int[m + 1][n + 1];
        for (int[] arr : dynam) {
            Arrays.fill(arr, 0);
        }
        for (int i = 1; i <= m; i++) {
            dynam[i][1] = 1;
        }
        Arrays.fill(dynam[1], 1);

        for (int i = 2; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                dynam[i][j] = dynam[i - 1][j] + dynam[i][j - 1];
            }
        }

        return dynam[m][n];
    }

    public int dg(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        return dg(m - 1, n) + dg(m, n - 1);
    }

    /**
     * 备忘录算法，递归的去冗余，动态规划的自顶向下实现。
     *
     * @param m
     * @param n
     * @param memo
     * @return
     */
    private int memorandom(int m, int n, int[][] memo) {
        if (m == 1 || n == 1) {
            return 1;
        }
        if (memo[m][n] != 0) {
            return memo[m][n];
        }
        memo[m][n] = memorandom(m - 1, n, memo) + memorandom(m, n - 1, memo);
        return memo[m][n];
    }

    public int memo(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }

        int[][] memo = new int[m + 1][n + 1];
        for (int[] arr : memo) {
            Arrays.fill(arr, 0);
        }
        return memorandom(m, n, memo);
    }


    public static void main(String[] args) {
        RobotGridding gridding = new RobotGridding();
        //int dg = gridding.dg(7, 3);
        //int memo = gridding.memo(7, 3);
        //System.out.println(memo);
        int uniquePaths = gridding.uniquePaths(7, 3);
        System.out.println(uniquePaths);
        //System.out.println(dg);
    }
}
