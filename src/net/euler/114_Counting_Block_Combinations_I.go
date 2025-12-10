package main

import "fmt"

func main() {
	for i := 1; i <= 50; i++ {
		m = map[int]int{}
		cnt := f(i)
		fmt.Printf("i=%d, cnt=%d\n", i, cnt)
	}
}

var m = map[int]int{}

func f(n int) int {
	if n == 0 {
		return 1
	}
	v, ok := m[n]
	if ok {
		return v
	}
	// 左边第一格，设置为灰色
	sum := f(n - 1)
	// 左边第一个，设置为红色, 红色长度为j,j>=3
	for j := 3; j <= n; j++ {
		if n == j {
			sum += 1
		} else {
			sum += f(n - j - 1)
		}
	}
	m[n] = sum
	return sum
}
