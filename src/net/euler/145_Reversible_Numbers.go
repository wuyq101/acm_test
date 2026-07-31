package main

import "fmt"

// 暴力枚举
func main() {
	cnt := 0
	for i := 1; i < 1e9; i++ {
		if f(i) {
			cnt++
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}

func f(n int) bool {
	if n%10 == 0 {
		return false
	}
	v := reverse(n)
	return isOdd(v + n)
}

func reverse(n int) int {
	v := 0
	for n > 0 {
		a := n % 10
		n /= 10
		v = v*10 + a
	}
	return v
}

func isOdd(n int) bool {
	for n > 0 {
		a := n % 10
		n /= 10
		if a%2 == 0 {
			return false
		}
	}
	return true
}
