// https://leetcode.com/problems/path-with-minimum-effort/description/
class Solution {
    public int minimumEffortPath(int[][] heights) {
       
    int m = heights.length, n = heights[0].length;
    int[][] direction = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
    Integer[][] dist = new Integer[m][n];
    dist[0][0] = 0;
    heap.offer(new int[]{0, 0, 0});
    
    while(!heap.isEmpty()) {
      int[] curr = heap.poll();
      int x = curr[0], y = curr[1], ds = curr[2];
      if(x == m - 1 && y == n - 1) {
        return ds;
      }
      for(int[] dir: direction) {
        int nx = x + dir[0], ny = y + dir[1];
        
        if(nx < 0 || nx >= m || ny < 0 || ny >= n) {
          continue;
        }
        int cost = Math.max(ds, Math.abs(heights[x][y] - heights[nx][ny]));

        if(dist[nx][ny] == null || dist[nx][ny] > cost) {
          dist[nx][ny] = cost;
          heap.offer(new int[]{nx, ny, cost});
        }        
      } 
    }
    return -1;
  }
}
