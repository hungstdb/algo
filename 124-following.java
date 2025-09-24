/*
Problem: Maximum Sum of Non-Adjacent Nodes in a Binary Tree

You are given the root of a binary tree. Each node has an integer value.
Find the maximum sum of values of nodes such that no two directly connected nodes are both chosen.


Example 1
      3
     / \
    2   3
     \    \
      3    1


Output:

7


Explanation: Choose nodes [3, 3, 1] = 7.
(You cannot take both root 3 and child 2 or 3 together).

Example 2
      3
     / \
    4   5
   / \   \
  1   3   1


Output:

9


Explanation: Choose nodes [4, 5] = 9.

🔹 Why It’s Hard

Similar to the House Robber problem, but extended to a tree structure.

At each node, you must decide:

Take this node → then you cannot take its children.

Skip this node → then you are free to take its children.

This requires DFS + DP on trees.

Time: O(n), each node processed once.

Space: O(h) recursion stack (h = tree height).

*/

class Solution {
    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    // return [skip, take]
    private int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // If we take this node, we cannot take its children
        int take = node.val + left[0] + right[0];

        // If we skip this node, we can freely take max of children
        int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{skip, take};
    }
}


  
