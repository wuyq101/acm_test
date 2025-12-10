package main

import "fmt"

func main() {
	for i := 1; i <= 200; i++ {
		cache = map[int]int{}
		cnt := f(50, i)
		fmt.Printf("i=%d, cnt=%d\n", i, cnt)
		if cnt >= 1000000 {
			return
		}
	}
}

var cache = map[int]int{}

func f(m, n int) int {
	if n == 0 {
		return 1
	}
	v, ok := cache[n]
	if ok {
		return v
	}
	// 左边第一格，设置为灰色
	sum := f(m, n-1)
	// 左边第一个，设置为红色, 红色长度为j,j>=3
	for j := m; j <= n; j++ {
		if n == j {
			sum += 1
		} else {
			sum += f(m, n-j-1)
		}
	}
	cache[n] = sum
	return sum
}
