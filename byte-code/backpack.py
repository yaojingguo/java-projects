class Solution:
  #Function to return max value that can be put in knapsack of capacity W.
  def knapSack(self,W, wt, val, n):
    dp = [None * (n + 1)]
    dp[0] = 0


