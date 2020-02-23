package com.algorithms.dynamicPlain;

public class BuyStock {

    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }

        int min = prices[0];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            min = Integer.min(prices[i], min);
            max = Integer.max(prices[i] - min, max);
        }
        return max;
    }

    public int maxProfit1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[] arr = new int[prices.length];

        arr[0] = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            min = Integer.min(prices[i], min);
            arr[i] = Integer.max(prices[i] - min, arr[i - 1]);
        }
        return arr[prices.length - 1];
        //return dg(prices, prices.length - 1);
    }


    /**
     * 暴力递归
     *
     * @param prices
     * @param n
     * @return
     */
    public int dg(int[] prices, int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return prices[0] < prices[1] ? prices[1] - prices[0] : 0;
        }
        // 问题在这里，怎么找最小值。
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
        }

        // 递推方程
        return Integer.max(prices[n] - min, dg(prices, n - 1));
    }

    public int maxProfit(int k, int[] prices) {
        if (prices.length <= 1 || k == 0) {
            return 0;
        }
        // 如果重复的次数多余一半，那么只要前一个大于后一个就买进
        if (k >= (prices.length / 2)) {
            int max = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i - 1] < prices[i]) {
                    max += prices[i] - prices[i - 1];
                }
            }
            return max;
        }

        int[][][] dp = new int[prices.length][k + 1][2];
        for (int i = 0; i <= k; i++) {
            dp[0][i][0] = 0;
            dp[0][i][1] = -prices[0];
        }
        for (int i = 0; i < prices.length; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Integer.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Integer.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[prices.length - 1][k][0];
    }

    /**
     * 买股票第一题，K=1，时的最大收益
     *
     * @param prices
     * @return
     */
    public static int stock1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }

        //int[][] dp = new int[prices.length][2];
        // base 条件，第一天不持有股票，则为0
        //dp[0][0] = 0;
        // base 条件，第一天持有股票，则只能是今天买入。
        //dp[0][1] = -prices[0];

        int dp_i_0 = 0;
        int dp_i_1 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 低i天没有持有股票，可能的远因是前一天也没有持股，那么今天无论买卖多少次都一样。
            // 也可能是因为前一天持有股票，今天卖出，有收益price[i]
            // dp[i][1][0] = Integer.max(dp[i-1][1][0],dp[i-1][1][1]+price[i])
            // 由于当前天的最大收益，只和昨天持有股票和没有持有股票相关，那么可以使用变量存储相邻
            // 昨天的最大收益即可。
            //dp[i][0] = Integer.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp_i_0 = Integer.max(dp_i_0, dp_i_1 + prices[i]);

            // dp[i][1][1] = Integer.max(dp[i-1][1][1],dp[i-1][0][0]-price[i])
            // dp[i-1][0][0] = 0 变成下面这种。
            //dp[i][1] = Integer.max(dp[i - 1][1], -prices[i]);
            dp_i_1 = Integer.max(dp_i_1, -prices[i]);
        }
        // 返回最后一天，不持有股票的最大收益。
        //return dp[prices.length - 1][0];
        return dp_i_0;
    }

    /**
     * 当K 为无限大时,即可以交易无限次。这里也可以使用贪心算法
     * 把股票价格作为y，天数作为x，最大收益，就是坐标轴上一个开始时的最低点到最高点的距离。
     * 多次交易，就是每次把这个距离去掉形成两个数组，再次找两个数组中的最大距离。
     * <p>
     * 动态规划的原理就是K为无限大，那么k=k-1;
     * 得出 dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-price[i])
     * = max(dp[i-1][k][1],dp[i-1][k][0]-price[i])
     * 那么K就可以消除了。dp[i][1] = max(dp[i-1][1],dp[i-1][0]-price[i])
     * <p>
     * 或者说K无限大上无所谓。某一天的收益就是
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+price[1])
     * dp[i][1] = max(dp[i-1][1],dp[i-1][0]-price[1])
     * <p>
     * 或者这里直接用贪心算法，只要有收益，就买卖。
     *
     * @return
     */
    public static int stock2(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Integer.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Integer.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }

    /**
     * 只能买两次，即K=2
     *
     * @param prices
     * @return
     */
    public static int stock3(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[][][] dp = new int[prices.length][3][2];
        // 不管第几天，没有购买，那么就是0,每购买，却有状态不正确。
        for (int i = 0; i < prices.length; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MAX_VALUE;
        }
        // base条件，第一天不管几次购买，只要持有，就是-price[0]
        for (int i = 0; i <= 2; i++) {
            dp[0][i][0] = 0;
            dp[0][i][1] = -prices[0];
        }

        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= 2; j++) {
                dp[i][j][0] = Integer.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Integer.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[prices.length - 1][2][0];
    }

    /**
     * 有冷冻期，买了股票之后一天不能再次买。卖时要等一天。所以递归方程变成
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+price[i])
     * <p>
     * 今天的持股的最大收益是昨天持股的最大收益与前两天没股今天买的最大收益。
     * dp[i][1] = max(dp[i-1][1],dp[i-2][0]-price[i])
     *
     * @param prices
     * @return
     */
    public static int stock4(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        // 第一天
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[1][0] = Integer.max(dp[0][0], dp[0][1] + prices[1]);
        dp[1][1] = Integer.max(dp[0][1], -prices[1]);

        for (int i = 2; i < prices.length; i++) {
            // 卖股票无所谓
            dp[i][0] = Integer.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 前两天不持股，那么今天可以持有股。
            dp[i][1] = Integer.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }

        /*
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        int dp_i_2 = 0; // dp[i-2][0]
        for (int i = 0; i < prices.length; i++) {
            int tmp = dp_i_0;
            dp_i_0 = Integer.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Integer.max(dp_i_1, dp_i_2 - prices[i]);
            dp_i_2 = tmp;// 一直为dp[i-2][0]
        }
*/
        return dp[prices.length - 1][0];

    }

    /**
     * 无限次买卖，但是在卖出时需要支付手续费。
     *
     * @param prices
     * @param fee
     * @return
     */
    public static int stock5(int[] prices, int fee) {
        if (prices.length <= 1) {
            return 0;
        }

        int dp_i_0 = 0;
        int dp_i_1 = -prices[0];
        for (int i = 0; i < prices.length; i++) {
            // 前一天持股，今天卖出去了。那么今天要给消费。
            dp_i_0 = Integer.max(dp_i_0, dp_i_1 + prices[i] - fee);
            dp_i_1 = Integer.max(dp_i_1, dp_i_0 - prices[i]);
        }

        return dp_i_0;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{2, 4, 1};
        int[] prices1 = new int[]{3, 3, 5, 0, 0, 3, 1, 4};
        BuyStock buyStock = new BuyStock();
        //int i = buyStock.maxProfit(prices1);
        int i = buyStock.maxProfit(2, prices1);
        System.out.println(i);
    }
}
