package main

import "fmt"

func main() {
	a := dec()
	fmt.Printf("sum = %d\n", a)
	b := inc()
	fmt.Printf("sum = %d\n", b)
	// 11，111，222，此类数字，重复计算了
	c := int64(N) * 9
	fmt.Printf("total = %d\n", a+b-c)

}

var N = 100

// 逐个递减
func dec() int64 {
	// dp[i][j] 为长度为i，尾号为j的非递增个数
	dp := make([][]int64, N+1)
	for i := 1; i <= N; i++ {
		dp[i] = make([]int64, 10)
	}
	// 长度为2的，先算好
	dp[1][0] = 0
	for j := 1; j <= 9; j++ {
		dp[1][j] = 1
	}
	for i := 2; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			// 求 dp[i][j] : 长度为i-1,尾号大于等于j的总和
			sum := int64(0)
			for k := j; k <= 9; k++ {
				sum += dp[i-1][k]
			}
			dp[i][j] = sum
		}
	}
	// --------
	fmt.Printf("dp:--------\n")
	for i := 1; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			fmt.Printf("%d ", dp[i][j])
		}
		fmt.Printf("\n")
	}
	fmt.Printf("dp--------\n")
	//------
	sum := int64(0)
	for i := 1; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			sum += dp[i][j]
		}
	}
	return sum
}

// 逐个递增, 长度位n，尾号是d的非递减个数
func inc() int64 {
	// dp[i][j] 为长度为i，尾号为j的非递减个数
	dp := make([][]int64, N+1)
	for i := 1; i <= N; i++ {
		dp[i] = make([]int64, 10)
	}

	dp[1][0] = 0
	for j := 1; j <= 9; j++ {
		dp[1][j] = 1
	}

	for i := 2; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			// 求 dp[i][j] : 长度为i-1,尾号小于等于j的总和
			sum := int64(0)
			for k := 0; k <= j; k++ {
				sum += dp[i-1][k]
			}
			dp[i][j] = sum
		}
	}
	// --------
	fmt.Printf("dp:--------\n")
	for i := 1; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			fmt.Printf("%d ", dp[i][j])
		}
		fmt.Printf("\n")
	}
	fmt.Printf("dp--------\n")
	//------
	sum := int64(0)
	for i := 1; i <= N; i++ {
		for j := 0; j <= 9; j++ {
			sum += dp[i][j]
		}
	}
	return sum
}
