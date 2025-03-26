package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	buf, err := os.ReadFile("67_triangle.txt")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(string(buf), "\n")
	m := make([][]int, 0)
	for _, line := range lines {
		if len(line) == 0 {
			continue
		}
		vals := strings.Split(line, " ")
		row := make([]int, 0)
		for _, val := range vals {
			v, err := strconv.Atoi(val)
			if err != nil {
				panic(err)
			}
			row = append(row, v)
		}
		m = append(m, row)
	}
	fmt.Printf("%d\n", maxPathSum(m))
}

func maxPathSum(m [][]int) int {
	dp := make([][]int, len(m))
	dp[0] = m[0]
	for i := 1; i < len(m); i++ {
		dp[i] = make([]int, len(m[i]))
		for j := 0; j < len(m[i]); j++ {
			a, b := 0, 0
			if j == 0 {
				a = 0
			} else {
				a = dp[i-1][j-1]
			}
			if j == i {
				b = 0
			} else {
				b = dp[i-1][j]
			}
			dp[i][j] = max(a, b) + m[i][j]
		}
	}
	max := 0
	i := len(m) - 1
	for j := 0; j < len(m[i]); j++ {
		if max < dp[i][j] {
			max = dp[i][j]
		}
	}
	return max
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
