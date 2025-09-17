## Base Problem: Unique Paths with Obstacles

**Problem:**
You are given an `m x n` grid filled with **0** (empty cell) and **1** (obstacle). You start at the **top-left corner `(0,0)`** and want to reach the **bottom-right `(m-1, n-1)`**. You may move **right** or **down**.

Return the **number of unique paths**. If the start or end cell is blocked, return 0.

**Example:**

```
Input: grid =
[[0,0,0],
 [0,1,0],
 [0,0,0]]

Output: 2
```

**Solution (Dynamic Programming, Java):**

```java
public class UniquePathsWithObstacles {
    public int uniquePathsWithObstacles(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n];

        dp[0] = (grid[0][0] == 0 ? 1 : 0);

        for (int j = 1; j < n; j++) {
            dp[j] = (grid[0][j] == 0 ? dp[j-1] : 0);
        }

        for (int i = 1; i < m; i++) {
            dp[0] = (grid[i][0] == 0 ? dp[0] : 0);
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    dp[j] = 0;
                } else {
                    dp[j] += dp[j-1];
                }
            }
        }
        return dp[n-1];
    }
}
```

**Complexity:**

* Time: `O(m × n)`
* Space: `O(n)` (rolling array)

---

## Follow-Up 1: Allow 4 Directions (No Revisits)

**Problem:** What if you can move **up, down, left, and right**, but you cannot revisit a cell?

**Solution (DFS + Backtracking):**

```java
public class UniquePathsFourDirections {
    private int m, n;
    private int[][] grid;
    private boolean[][] visited;
    private int count = 0;

    public int countPaths(int[][] grid) {
        this.m = grid.length;
        this.n = grid[0].length;
        this.grid = grid;
        this.visited = new boolean[m][n];

        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return 0;
        dfs(0, 0);
        return count;
    }

    private void dfs(int i, int j) {
        if (i == m-1 && j == n-1) {
            count++;
            return;
        }
        visited[i][j] = true;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n &&
                !visited[ni][nj] && grid[ni][nj] == 0) {
                dfs(ni, nj);
            }
        }
        visited[i][j] = false;
    }
}
```

**Complexity:**

* Time: `O(4^(m×n))` worst case (exponential)
* Space: `O(m × n)`

---

## Follow-Up 2: Shortest Path (Unweighted Grid)

**Problem:** What if instead of counting paths, you want the **minimum number of steps** from `(0,0)` to `(m-1, n-1)`?

**Solution (BFS):**

```java
public class ShortestPathInGrid {
    public int shortestPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return -1;

        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        q.offer(new int[]{0, 0});
        visited[0][0] = true;

        int steps = 0;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int[] cur = q.poll();
                int i = cur[0], j = cur[1];
                if (i == m-1 && j == n-1) return steps;
                for (int[] d : dirs) {
                    int ni = i + d[0], nj = j + d[1];
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n &&
                        !visited[ni][nj] && grid[ni][nj] == 0) {
                        visited[ni][nj] = true;
                        q.offer(new int[]{ni, nj});
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}
```

**Complexity:**

* Time: `O(m × n)`
* Space: `O(m × n)`

---

## Follow-Up 3: Weighted Grid (Minimum Cost Path)

**Problem:** If grid cells contain **positive weights** instead of just obstacles, return the **minimum cost path**.

**Solution (Dijkstra’s Algorithm):**

```java
public class MinCostPath {
    public int minPathCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{0, 0, grid[0][0]});
        dist[0][0] = grid[0][0];

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[0], j = cur[1], cost = cur[2];
            if (i == m-1 && j == n-1) return cost;
            if (cost > dist[i][j]) continue;

            for (int[] d : dirs) {
                int ni = i + d[0], nj = j + d[1];
                if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                    int newCost = cost + grid[ni][nj];
                    if (newCost < dist[ni][nj]) {
                        dist[ni][nj] = newCost;
                        pq.offer(new int[]{ni, nj, newCost});
                    }
                }
            }
        }
        return -1;
    }
}
```

**Complexity:**

* Time: `O((m × n) log(m × n))`
* Space: `O(m × n)`

---

## Follow-Up 4: Enumerate All Paths

**Problem:** Instead of counting, return **all possible paths** from `(0,0)` to `(m-1,n-1)`.

**Solution (DFS + Backtracking):**

```java
public class EnumeratePaths {
    private int m, n;
    private int[][] grid;
    private List<List<int[]>> result = new ArrayList<>();
    private List<int[]> path = new ArrayList<>();

    public List<List<int[]>> allPaths(int[][] grid) {
        this.m = grid.length;
        this.n = grid[0].length;
        this.grid = grid;

        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return result;
        dfs(0, 0);
        return result;
    }

    private void dfs(int i, int j) {
        path.add(new int[]{i, j});
        if (i == m-1 && j == n-1) {
            result.add(new ArrayList<>(path));
        } else {
            if (i+1 < m && grid[i+1][j] == 0) dfs(i+1, j);
            if (j+1 < n && grid[i][j+1] == 0) dfs(i, j+1);
        }
        path.remove(path.size()-1);
    }
}
```

**Complexity:**

* Time: `O(#paths × path_length)`
* Space: `O(m+n + output size)`

---

# 📊 Complexity Summary

| Problem Variant          | Time Complexity                | Space Complexity  |
| ------------------------ | ------------------------------ | ----------------- |
| Base DP (Unique Paths)   | `O(m × n)`                     | `O(n)`            |
| DFS (4-dir, no revisits) | `O(4^(m×n))` worst case (exp.) | `O(m × n)`        |
| BFS (Shortest Path)      | `O(m × n)`                     | `O(m × n)`        |
| Dijkstra (Weighted Grid) | `O((m × n) log(m × n))`        | `O(m × n)`        |
| Enumerate All Paths      | `O(#paths × path_length)`      | `O(m+n + output)` |
