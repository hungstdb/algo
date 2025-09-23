class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        for(int i = 0; i < n; i++) {
            jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }
       Arrays.sort(jobs, (a,b) -> a[1] - b[1]);

       TreeMap<Integer, Integer> dp = new TreeMap<Integer, Integer>();
       dp.put(0, 0);
       for(int i = 0; i < n; i++) {
            int currProfit = dp.floorEntry(jobs[i][0]).getValue() + jobs[i][2];

            if(currProfit >  dp.lastEntry().getValue() ) {
                dp.put(jobs[i][1], currProfit);
            }
       }
       return dp.lastEntry().getValue();

    }
}
