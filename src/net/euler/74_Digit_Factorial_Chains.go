package main

import "fmt"

func main() {
	cnt := 0
	for i := 0; i < 1000000; i++ {
		d := digitFactorialChains(i)
		if d == 60 {
			cnt++
			fmt.Printf("i=%d\n", i)
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}

func digitFactorialChains(n int) int {
	m := make(map[int]int)
	m[n] = 1
	for {
		next := digitFactorial(n)
		if m[next] > 0 {
			break
		}
		m[next] = 1
		n = next
	}
	return len(m)
}

func digitFactorial(n int) int {
	sum := 0
	for n > 0 {
		d := n % 10
		n /= 10
		sum += df[d]
	}
	return sum
}

var df [10]int

func init() {
	df[0] = 1
	for i := 1; i < 10; i++ {
		df[i] = df[i-1] * i
	}
}
