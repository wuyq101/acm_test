package main

import "fmt"

func main() {
	m, n := 47, 43
	dp = make([][]int, m+1)
	for i := 0; i <= m; i++ {
		dp[i] = make([]int, n+1)
	}
	dp[1][1] = 0

	total := 0
	for i := 1; i <= m; i++ {
		for j := 1; j <= n; j++ {
			total += u(i, j)
			total += s(i, j)
		}

	}
	fmt.Printf("total = %d\n", total)
}

// 正向长方形的个数，横线(m+1)条中选2条，竖线(n+1)条中选2条
func u(m, n int) int {
	return m * (m + 1) * n * (n + 1) / 4
}

// 从n中任选3个点的组合
func c3(n int) int {
	return n * (n - 1) * (n - 2) / 6
}

// 斜向长方形的个数
// 使用dp的方式来推到，假设宽m,高n，斜向长方形的个数是s(m,n)
// 新增一列，变成m+1列，s(m+1,n), 斜向的交点新增了2n+1个点
// 从2n+1个点中，任意取3点，其中最高点A,最低点B，中间点C，都可以对应一个新增的长方形。
// B最为这个长方形的最右侧，-1的斜率延长到与高度A相等的点A'，1的斜率延长到与低点B高度相等的点B'，角A'CB'构成新长方形的右边角。
// s(m+1,n) = s(m,n) + c3(2n+1)

var dp = [][]int{}

func s(m, n int) int {
	if m < n {
		return s(n, m)
	}
	if m == 1 && n == 1 {
		return dp[1][1]
	}
	if dp[m][n] > 0 {
		return dp[m][n]
	}
	if m == n {
		dp[m][n] = s(n, n-1) + 8*c3(n+1)
	} else {
		dp[m][n] = s(m-1, n) + c3(2*n+1)
	}

	return dp[m][n]
}

// 斜向长方形的个数
// 在 m * n 的交叉网格中，对角线其实是两组斜率分别为 1 和 -1 的平行线段。
// 其中斜率为1的直线 y-x = ci   -m <= ci <= n
// 其中斜率为-1的直线 y+x = di   0 <= di <= m+n
// 任意挑选两条斜率为1的直线，和任意挑选两条斜率为-1的直线，就可以组成一个长方形
// c1 < c2, d1 < d2, 4个交点必须在长方形内部
func d(m, n int) int {
	cnt := 0
	for c1 := -m; c1 < n; c1++ {
		for c2 := c1 + 1; c2 <= n; c2++ {
			for d1 := 0; d1 <= m+n; d1++ {
				for d2 := d1 + 1; d2 <= m+n; d2++ {
					// L1: y-x = c1, L2: y-x=c2
					// L3: y+x = d1, L4: y+x=d2
					// 求4个交点，是否在长方形内

					// L1和L3交点
					x := float64(d1-c1) / 2
					y := float64(c1+d1) / 2
					if !in(x, y, m, n) {
						continue
					}
					// L1和L4交点
					x = float64(d2-c1) / 2
					y = float64(c1+d2) / 2
					if !in(x, y, m, n) {
						continue
					}
					// L2和L3交点
					x = float64(d1-c2) / 2
					y = float64(c2+d1) / 2
					if !in(x, y, m, n) {
						continue
					}
					// L2和L4交点
					x = float64(d2-c2) / 2
					y = float64(c2+d2) / 2
					if !in(x, y, m, n) {
						continue
					}
					cnt++
				}
			}
		}
	}
	return cnt
}

func in(x, y float64, m, n int) bool {
	return x >= 0 && x <= float64(m) && y >= 0 && y <= float64(n)
}
