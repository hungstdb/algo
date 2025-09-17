# Google L5 Coding Interview Simulation: Minimum Path Sum Variants

## Base Problem: Minimum Path Sum

**Problem:**
Given an `m x n` grid filled with **non-negative numbers**, find a path from the **top-left corner `(0,0)`** to the **bottom-right `(m-1, n-1)`** which minimizes the **sum of all numbers along its path**. You can only move **right** or **down**.

**Example:**

```
Input: grid =
[[1,3,1],
 [1,5,1],
 [4,2,1]]

Output: 7
Explanation: Path = 1 → 3 → 1 → 1 → 1
```

---

## Base Solution (Dynamic Programming with Rolling Array)

```java
public class MinPathSum {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n]; // rolling array

        dp[0] = grid[0][0];
        // Fill first row
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        // Process rows
        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0]; // update first column
            for (int j = 1; j < n; j++) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[n - 1];
    }
}
```

### Why initialize first row and first column?

* First row: can only be reached **from the left**, so prefix sums across row.
* First column: can only be reached **from above**, so cumulative sum down column.
* This creates the **boundary conditions** for DP recurrence:

  ```
  dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
  ```

**Complexity:**

* Time: `O(m × n)`
* Space: `O(n)` (1D rolling array)

---

## Alternative Variant (Using While Loop)

```java
public class MinPathSumWhile {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n];

        dp[0] = grid[0][0];

        int j = 1;
        while (j < n) {
            dp[j] = dp[j-1] + grid[0][j];
            j++;
        }

        int i = 1;
        while (i < m) {
            dp[0] += grid[i][0];
            j = 1;
            while (j < n) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j-1]);
                j++;
            }
            i++;
        }
        return dp[n-1];
    }
}
```

---

## Follow-Up 1: With Obstacles

**Problem:** Some cells contain obstacles (represented by `-1`). Find the minimum path sum while avoiding obstacles. If destination is unreachable, return -1.

**Solution (DP with Checks):**

```java
public class MinPathSumObstacles {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        if (grid[0][0] != -1) dp[0] = grid[0][0];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == -1) {
                    dp[j] = Integer.MAX_VALUE;
                    continue;
                }
                if (i == 0 && j == 0) continue;
                int top = dp[j];
                int left = (j > 0 ? dp[j-1] : Integer.MAX_VALUE);
                int best = Math.min(top, left);
                if (best != Integer.MAX_VALUE) dp[j] = best + grid[i][j];
            }
        }
        return dp[n-1] == Integer.MAX_VALUE ? -1 : dp[n-1];
    }
}
```

**Complexity:**

* Time: `O(m × n)`
* Space: `O(n)`

---

## Follow-Up 2: Allow 4 Directions

**Problem:** What if you can move **up, down, left, right** and want the minimum cost path?

**Solution (Dijkstra’s Algorithm):**

```java
public class MinPathSumFourDirections {
    public int minPathSum(int[][] grid) {
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

## Follow-Up 3: Return the Path (Not Just Cost)

**Problem:** Return the actual path taken.

**Solution (DP + Parent Pointers):**

```java
public class MinPathSumWithPath {
    public List<int[]> minPathWithTrace(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        int[][][] parent = new int[m][n][2];

        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
            parent[i][0] = new int[]{i-1, 0};
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
            parent[0][j] = new int[]{0, j-1};
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (dp[i-1][j] < dp[i][j-1]) {
                    dp[i][j] = dp[i-1][j] + grid[i][j];
                    parent[i][j] = new int[]{i-1, j};
                } else {
                    dp[i][j] = dp[i][j-1] + grid[i][j];
                    parent[i][j] = new int[]{i, j-1};
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        int i = m-1, j = n-1;
        while (!(i == 0 && j == 0)) {
            path.add(new int[]{i, j});
            int[] p = parent[i][j];
            i = p[0];
            j = p[1];
        }
        path.add(new int[]{0, 0});
        Collections.reverse(path);
        return path;
    }
}
```

**Complexity:**

* Time: `O(m × n)`
* Space: `O(m × n)` for parent pointers

---

# 📊 Complexity Summary

| Problem Variant         | Time Complexity         | Space Complexity |
| ----------------------- | ----------------------- | ---------------- |
| Base DP (Right/Down)    | `O(m × n)`              | `O(n)`           |
| With Obstacles          | `O(m × n)`              | `O(n)`           |
| 4 Directions (Dijkstra) | `O((m × n) log(m × n))` | `O(m × n)`       |
| Return Path             | `O(m × n)`              | `O(m × n)`       |
