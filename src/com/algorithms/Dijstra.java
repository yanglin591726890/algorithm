package com.algorithms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 最短路径算法
 */
public class Dijstra {

    /**
     * dijkstra算法求图像中两个点的最短路径。
     * 也是加点法，从指定点V开始，将图中距离它最近的点V1增加到集合S中。
     * 然后继续选择，剩下点中距离距离集合S最近的点V2增加到S集合中，直到
     * 所有的点加完，就会生成一棵树。
     * <p>
     * 时间复杂度：有n个点要处理，每次都要找距离集合初始点最近的点。所以是n
     * 而最近的点要更新维护所有的点，时间负责都为ei,得出T = n(n+ei)=n^2+m m表示边
     *
     * @param grid 邻接矩阵
     * @param s    出发点
     * @param t    到达点
     * @return s 点到 t的最短路径
     */
    public static int dijkstra(int[][] grid, int s, int t) {
        // 初始化从s到个点的最短路径，开始都是无限远
        int[] distance = new int[grid.length];
        Arrays.fill(distance, Integer.MAX_VALUE);

        //自己到自己为0
        distance[s] = 0;
        int min = s;

        // 这里使用一个数组会好一些，判断contains是时间和空间复杂度都小一些。
        Set<Integer> book = new HashSet<>(distance.length);
        while (true) {
            // 将当前距离s最小的点增加到集合中。
            book.add(min);

            // 遍历min能到达的所有点
            for (int i = 0; i < grid[min].length; i++) {
                // 如果用数组，空间复杂度会小一些。
                if (book.contains(grid[min][i])) {
                    continue;
                }

                // 如果这些点还没有被访问。同时从s-i的距离，大于s-min-i;
                // 那么更新这个距离。
                if (distance[i] > (distance[min] + grid[min][i])) {
                    distance[i] = distance[min] + grid[min][i];
                }
            }

            int newV = Integer.MAX_VALUE;
            // 寻找剩余的节点中，哪一个距离s最近。
            for (int i = 0; i < distance.length; i++) {
                if (book.contains(i)) {
                    continue;
                }
                if (distance[i] < newV) {
                    newV = distance[i];
                    min = i;
                }
            }
            // 如果没有点更新了。
            if (newV == Integer.MAX_VALUE) {
                break;
            }
        }
        return distance[t];
    }


    /**
     * 弗洛伊德算法，求任意两点之间的最短路径。
     * 原理；假设有n个点，求一个点到其他所有点的最短距离需要一个for循环，for 1-n
     *      那么任意两个点之间的最短距离就需要两层for循环，来得到。
     *      如果有一个点K作为中间点，来搭桥，减少距离。那么此时就是这样的：
     *      for (int i=0 ; i < n ; i++) {
     *          for (int j = 0 ; i < n ; i++) {
     *              if(graid[i][j] > (graid[i][K] + graid[K][j])) {
     *                  graid[i][j] = graid[i][K] + graid[K][j]
     *              }
     *          }
     *      }
     *
     *      如果有两个点K1,K2呢？那么就是在K1改变了最短距离的情况下，K2再次遍历改变。
     *      K1 作为中间点：
     *      for (int i=0 ; i < n ; i++) {
     *          for (int j = 0 ; i < n ; i++) {
     *              if(graid[i][j] > (graid[i][K1] + graid[K1][j])) {
     *                  graid[i][j] = graid[i][K1] + graid[K1][j]
     *              }
     *          }
     *      }
     *      K2作为中间点：
     *      for (int i=0 ; i < n ; i++) {
     *          for (int j = 0 ; i < n ; i++) {
     *              if(graid[i][j] > (graid[i][K2] + graid[K2][j])) {
     *                  graid[i][j] = graid[i][K2] + graid[K2][j]
     *              }
     *          }
     *      }
     *
     *      如果是所有节点呢？
     *      那么就是一个作为中间节点的K的遍历for 1-N。得出弗洛伊德算法。
     *
     * @param grid graid[i]中有三个数(v,t,s)=graid[1],graid[2],graid[3]表示从v->t的距离为s。
     * @param n 顶点个数
     * @return
     */
    public static int[][] floyed(int[][] grid, int n) {
        int[][] grap = new int[n + 1][n + 1];
        for (int[] arr : grap) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        for (int[] arr : grid) {
            grap[arr[0]][arr[1]] = arr[2];
        }
        for (int k = 1; k < grap.length; k++) {
            for (int i = 0; i < grap.length; i++) {
                for (int j = 0; j < grap.length; j++) {
                    grap[i][j] = Integer.min(grap[i][j],(grap[i][k]+grap[k][j]));
                }
            }
        }

        return grap;
    }
}
