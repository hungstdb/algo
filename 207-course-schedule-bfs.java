//https://leetcode.com/problems/course-schedule/
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for(int i = 0; i < numCourses; i++) {
            graph.put(i, new ArrayList<>());
            inDegree.put(i, 0);
        }

        for(int[] pre: prerequisites) {
            int course = pre[0], pr = pre[1];
            graph.get(pr).add(course);
            inDegree.put(course, inDegree.get(course) + 1);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int course: inDegree.keySet()) {
            if(inDegree.get(course) == 0) {
                queue.offer(course);
            }
        }
        int taken = 0;
        while(!queue.isEmpty()) {
            Integer curr = queue.poll();
            taken++;
            for(int next: graph.get(curr)) {
                inDegree.put(next, inDegree.get(next) -1);
                if(inDegree.get(next) == 0) queue.offer(next);
            }

        }
        return taken == numCourses;
    }
}
