package com.algorithms.dynamicPlain;

/**
 * 连续递增的最长子序列
 */
public class LCIS {

    public static int findLengthOfLCIS(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int max = 1;
        int cur = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                cur += 1;
            } else {
                cur = 1;
            }
            max = Integer.max(cur, max);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 5, 4, 7};
        int lengthOfLCIS = findLengthOfLCIS(nums);
        System.out.println(lengthOfLCIS);
    }
}
