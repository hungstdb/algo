class Solution {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int[][] dp;
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int LIP = 0;
        dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j <n; j++) {
                dp[i][j] = -1;
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j< n; j++) {
                LIP = Math.max(LIP, dfs(matrix, i, j, Integer.MIN_VALUE));
            }
        }
        return LIP;
    }
    private int dfs(int[][] matrix, int row, int col, int prevVal) {
        int m = matrix.length, n = matrix[0].length;
        if(row >= m || row < 0 || col >=n || col < 0 || prevVal >= matrix[row][col] )  {
            return 0;
        }
        int res = 1;
        if(dp[row][col] != -1) return dp[row][col];

        for(int[] d: directions) {
            int nx = row + d[0], ny = col + d[1];
            res = Math.max( res, 1 + dfs(matrix, nx, ny, matrix[row][col]));
        }
        return dp[row][col] = res;
    }
}
