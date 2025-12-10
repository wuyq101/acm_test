package main

import "fmt"

func main() {
	n := 50
	m = map[int]int{}
	cnt := f(n)
	fmt.Printf("n=%d, cnt=%d\n", n, cnt)
}

var m = map[int]int{}

func f(n int) int {
	v, ok := m[n]
	if ok {
		return v
	}
	if n == 0 {
		return 1
	}
	sum := 0
	for j := 1; j <= 4; j++ {
		if n >= j {
			sum += f(n - j)
		}
	}
	m[n] = sum
	return sum
}
