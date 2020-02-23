package com.algorithms.dynamicPlain;

import java.util.Arrays;

/**
 * 丢骰子
 * https://leetcode-cn.com/problems/number-of-dice-rolls-with-target-sum/
 */
public class Dice {

    /**
     * 动态规划
     *
     * @param d
     * @param f
     * @param target
     * @return
     */
    public int numRollsToTarget1(int d, int f, int target) {
        int[][] dy = new int[d + 1][target + 1];

        // 初始化
        for (int i = 1; i <= Integer.min(f, target); i++) {
            dy[1][i] = 1;
        }
        // 从两个筛子时候开始，
        for (int i = 2; i <= d; i++) {
            for (int j = 1; j <= target; j++) {
                for (int k = 1; k <= f; k++) {
                    // 如果j<k 则不能再分了
                    if (j >= k) {
                        dy[i][j] = (dy[i][j] + dy[i - 1][j - k]) % 1000000007;
                    }
                }
            }
        }
        return dy[d][target];
    }

    /**
     * 递归版本，本题
     *
     * @param d
     * @param f
     * @param target
     * @param arr
     * @return
     */
    public int memo(int d, int f, int target, int[][] arr) {
        if (d <= 0 || target <= 0) {
            return 0;
        }
        // 当前筛子最大都得不到target，或者target小于筛子个数，都返回0
        if (d * f < target || target < d) {
            return 0;
        }

        if (d == 1) {
            if (f < target) {
                return 0;
            } else {
                return 1;
            }
        }

        // 缓存，备忘录
        if (arr[d][target] != -1) {
            return arr[d][target];
        }

        int result = 0;
        for (int i = 1; i <= f; i++) {
            // 本题的递归方程，当筛子规模缩小时，及d-1时，d个筛子到target的结果为，
            // 最后一个筛子为1加上d-1个筛子得到target-1的结果，一直加到最后一个筛子为f时d-1个筛子
            // 得到target-f的结果。
            result = (result + memo(d - 1, f, target - i, arr)) % 1000000007;
        }

        arr[d][target] = result;
        return arr[d][target];
    }

    public int aa(int d, int f, int target) {
        int[][] memo = new int[d + 1][target + 1];
        for (int[] aar : memo) {
            Arrays.fill(aar, -1);
        }
        int i = memo(d, f, target, memo);
        return i;
    }

    public static void main(String[] args) {
        Dice dice = new Dice();
        int numRollsToTarget = dice.aa(1, 6, 3);
        System.out.println(numRollsToTarget);
    }
}
