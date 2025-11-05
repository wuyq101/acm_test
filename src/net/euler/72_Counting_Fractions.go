package main

import "fmt"

func main() {
	sum := int64(0)
	MAX := 1000000
	for i := 2; i <= MAX; i++ {
		sum += int64(phi(i))
	}
	fmt.Printf("sum = %d\n", sum)
}

func phi(n int) int {
	result := n
	for p := 2; p*p <= n; p++ {
		if n%p == 0 {
			result -= result / p
			for n%p == 0 {
				n /= p
			}
		}
	}
	if n > 1 {
		result -= result / n
	}
	return result
}
