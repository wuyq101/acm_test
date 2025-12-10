package main

import "fmt"

/*
https://projecteuler.net/problem=108

1/x + 1/y = 1/n

(x+y)/xy = 1/n
n(x+y) = xy

设x = n+a, y = n+b

xy = (n+a)(n+b) = n^2 + (a+b)n + ab
n(x+y) = n(2n+a+b) = 2n^2 + (a+b)n + ab

===> ab = n^2
所有x,y的解，就是n^2的约数
*/
func main() {
	N := 4
	for {
		A, B := primes(N)
		cnt := count(B)
		//	fmt.Printf("N=%d count = %d\n", N, cnt)
		if cnt >= 1000 {
			fmt.Printf("A = %v\nB = %v\n", A, B)
			fmt.Printf("N=%d count = %d\n", N, cnt)
			return
		}
		N++
	}
}

func count(B []int) int {
	s := 1
	for _, v := range B {
		s *= (2*v + 1)
	}
	return (s + 1) / 2
}

func primes(n int) ([]int, []int) {
	p := 2
	A := make([]int, 0)
	B := make([]int, 0)
	for n > 1 {
		cnt := 0
		for n%p == 0 {
			cnt++
			n /= p
		}
		if cnt > 0 {
			A = append(A, p)
			B = append(B, cnt)
		}
		p++
	}
	return A, B
}
