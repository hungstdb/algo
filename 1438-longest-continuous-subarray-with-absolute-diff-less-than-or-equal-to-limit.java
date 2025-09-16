// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/
class Solution {
    public int longestSubarray(int[] nums, int limit) {
         Deque<Integer> maxDeque = new LinkedList<>(); // decreasing
        Deque<Integer> minDeque = new LinkedList<>(); // increasing
        int left = 0, right = 0, result = 0;

        while (right < nums.length) {
            int val = nums[right];

            // Maintain decreasing maxDeque
            while (!maxDeque.isEmpty() && val > maxDeque.peekLast()) {
                maxDeque.pollLast();
            }
           
            // Maintain increasing minDeque
            while (!minDeque.isEmpty() && val < minDeque.peekLast()) {
                minDeque.pollLast();
            }
            minDeque.offerLast(val);
            maxDeque.offerLast(val);

            // Shrink window if invalid
            while (!maxDeque.isEmpty() && !minDeque.isEmpty() &&
                   maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (nums[left] == maxDeque.peekFirst()) {
                    maxDeque.pollFirst();
                }
                if (nums[left] == minDeque.peekFirst()) {
                    minDeque.pollFirst();
                }
                left++;
            }

            // Update result
            result = Math.max(result, right - left + 1);

            // Expand window
            right++;
        }

        return result;
    }
}
