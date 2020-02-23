package com.algorithms.dynamicPlain;

/**
 * 一次可以爬1-2个台阶，n个台阶几种方法
 * 问题规模缩小，n个台阶，先求n-1个台阶，n-1个，先求n-2个。f(n) = f(n-1)+f(n-2)
 */
public class Starways {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int a = 1;
        int b = 2;
        int result = a + b;
        for (int i = 2; i < n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public int dg(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public static void main(String[] args) {
        Starways starways = new Starways();
        int stairs = starways.climbStairs(5);
        System.out.println(stairs);
    }

}
