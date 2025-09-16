// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/
/*
1. Restate the problem

"We need the longest subarray such that max(subarray) - min(subarray) <= k. Since nums.length can be up to 10^5, we need an O(n log n) or ideally O(n) solution. Brute force checking all subarrays (O(n²)) would be too slow."

2. Idea

Use a sliding window (left and right pointers).

As we expand right, we track the max and min in the window.

If the difference exceeds k, shrink from the left.

To track max/min efficiently, use monotonic deques:

maxDeque: decreasing order → front holds current max.

minDeque: increasing order → front holds current min.

3. Algorithm

Initialize left = 0.

For each right in range [0, n-1]:

    Insert nums[right] into maxDeque and minDeque (maintain monotonic property).
    
    While maxDeque[0] - minDeque[0] > k:
    
    Move left forward, and pop from deques if out of range.
    
    Update result = max(result, right - left + 1).

Return result.

5. Complexity

Each element is added/removed from each deque at most once → O(n).

Extra space: O(n) in worst case.

6. Example Walkthrough

For nums = [10,1,2,4,7,2], k = 5:

Expand window → [10] valid.

Add 1 → [10,1], max=10, min=1, diff=9 → shrink.

Continue sliding … longest valid window found = length 4 ([2,4,7,2]).

*/
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
