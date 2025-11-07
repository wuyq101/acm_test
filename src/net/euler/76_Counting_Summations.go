package main

import "fmt"

/*
dp[i][j]:  i,不超过j的分区数

dp[0][j] = 1
dp[i][0] = 0

// 分解为i由j-1的分区，以及至少一个j的分区数，两种
dp[i][j] = dp[i][j-1] + dp[i-j][j]

dp[5][5] = dp[5][4] + dp[1][5]
	 = dp[5][3] + dp[1][4] + 1
	 = dp[5][2] + dp[2][3] + 2
	 = dp[5][1] + dp[3][2] + dp[2][2] + dp[-1][3] + 2
	 = 1 + dp[3][1] + dp[1][2] + dp[2][1] + dp[0][2] + 2
	 = 1 + 1 + 1 + 1 + 0 + 2
	 = 6


*/

func main() {

	for i := 1; i <= 100; i++ {
		if i == 100 {
			cnt := dp(i, i)
			fmt.Printf("%d %d\n", i, cnt)
		}
	}
}

var m [101][101]int

func dp(i, j int) int {
	fmt.Printf("dp %d %d\n", i, j)
	if i < 0 {
		return 0
	}
	if i == 0 || i == 1 {
		return 1
	}
	if j == 1 {
		return 1
	}
	if i > 0 && j > i {
		return dp(i, i)
	}
	if m[i][j] != 0 {
		return m[i][j]
	}
	fmt.Printf("split (%d %d) and (%d %d)\n", i, j-1, i-j, j)
	m[i][j] = dp(i, j-1) + dp(i-j, j)
	return m[i][j]
}
