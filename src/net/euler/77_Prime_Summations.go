package main

import "fmt"

// https://projecteuler.net/problem=77

/*
10 = 7 + 3

	= 5 + 5
	= 5 + 3 + 2
	= 3 + 3 + 3 + 2
	= 2 + 2 + 2 + 2 + 2

10一共有5中组合，有质数相加

dp[n][m] 表示n由不超过m的质数组成的分区数
dp[n][m] =
a 为小于等于m的最大质数
dp[n][m] = dp[n][a-1] + dp[n-a][a] (第一项，不包含a的分区，第二项，至少包含一个a的分区)
*/
func main() {
	n := 71
	dp = make([][]int, n+1)
	for i := 0; i <= n; i++ {
		dp[i] = make([]int, n+1)
		for j := 0; j <= n; j++ {
			dp[i][j] = -1
		}
	}
	cnt := count(n, n)
	fmt.Printf("n=%d cnt=%d\n", n, cnt)
}

var dp [][]int

func count(n, m int) int {
	fmt.Printf("count %d %d\n", n, m)
	// 找不到质数来表示剩余的n
	if n > 0 && m == 1 {
		return 0
	}
	if n < 0 {
		return 0
	}
	// n已经分配完成
	if n == 0 && m >= 2 {
		return 1
	}

	if dp[n][m] >= 0 {
		return dp[n][m]
	}

	// 判断m是否为质数，如果不是，用m-1来代替
	if !isPrime(m) {
		return count(n, m-1)
	}
	// 分解为两部分 不包含m的分区 + 至少包含1个m的分区
	fmt.Printf("split (%d %d) and (%d %d)\n", n, m-1, n-m, m)
	dp[n][m] = count(n, m-1) + count(n-m, m)
	return dp[n][m]
}

var primes map[int]bool = make(map[int]bool)

func isPrime(n int) bool {
	v, ok := primes[n]
	if ok {
		return v
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			primes[n] = false
			return false
		}
	}
	primes[n] = true
	return true
}
