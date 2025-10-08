//https://leetcode.com/problems/course-schedule/submissions/1794847830/
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < numCourses; i++) {
            graph.put(i, new ArrayList<>());
        }

        for(int[] pre: prerequisites) {
            int course = pre[0], pr = pre[1];
            graph.get(pr).add(course);
        }

        Map<Integer, Integer> stage = new HashMap<>();
        for(int i = 0; i < numCourses; i++) {
            stage.put(i, 0); // unvistiting
        }

        for(int i = 0; i < numCourses; i++) {
            if(stage.get(i) == 0) {
                if(hasCircle(graph, stage, i)) return false;
            }
        }
        return true;
    }

    private boolean hasCircle(Map<Integer, List<Integer>> graph, Map<Integer, Integer> stage, int i) {
        stage.put(i, 1); // visiting
        for(int next: graph.get(i)) {
            if(stage.get(next) == 1) return true;
            if (stage.get(next) == 0 && hasCircle(graph, stage, next)) return true;
        } 
        stage.put(i, 2); // done
        return false;  
    }
}
