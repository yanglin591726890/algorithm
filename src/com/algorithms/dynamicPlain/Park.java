package com.algorithms.dynamicPlain;

import com.sun.org.apache.regexp.internal.RE;

public class Park {
    public int minTaps(int n, int[] ranges) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int[][] aar = new int[ranges.length][2];
        for (int i = 0; i < ranges.length; i++) {
            aar[i][0] = Integer.max(0, i - ranges[i]);
            aar[i][1] = Integer.min(n, i + ranges[i]);
        }


        return dg(n, ranges, aar, new int[ranges.length]);
    }


    public int dg(int n, int[] ranges, int[][] arr, int[] cache) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (cache[n] != 0) {
            return cache[n];
        }
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < ranges.length; i++) {
            if (arr[i][1] < n) {
                continue;
            }
            if (arr[i][0] >= n) {
                continue;
            }
            min = Integer.min(min, dg(arr[i][0], ranges, arr, cache));
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        cache[n] = min + 1;
        return cache[n];
    }

    public static void main(String[] args) {
        Park p = new Park();
        int[] arr = new int[]{ 0, 0, 0, 0};
        int minTaps = p.minTaps(3, arr);
        System.out.println(minTaps);
    }
}
