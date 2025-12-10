package main

import "fmt"

func main() {
	n := 1
	cnt := 0
	for {
		if isBouncy(n) {
			cnt++
			if 100*cnt == 99*n {
				fmt.Printf("n=%d, cnt=%d\n", n, cnt)
				break
			}
		}
		n++
	}
}

func isBouncy(n int) bool {
	return !isIncreasing(n, 9) && !isDecreasing(n, 0)
}

func isIncreasing(n int, d int) bool {
	if n == 0 {
		return true
	}
	last := n % 10
	return last <= d && isIncreasing(n/10, last)
}

func isDecreasing(n int, d int) bool {
	if n == 0 {
		return true
	}
	last := n % 10
	return last >= d && isDecreasing(n/10, last)
}
