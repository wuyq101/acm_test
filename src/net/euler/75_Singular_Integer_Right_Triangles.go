package main

import "fmt"

//
// a = m^2 - n^2
// b = 2mn
// c = m^2 + n^2
// a^2 + b^2 = c^2
// (m^2 - n^2)^2 + (2mn)^2 = (m^2 + n^2)^2
// a+b+c = m^2 - n^2 + 2mn + m^2 + n^2 = 2m^2 + 2mn = 2m(m+n)
// m>n, gcd(m,n)=1
// m=2, n=1, a=3, b=4, c=5 ✅
// m=3, n=2, a=5, b=12, c=13 ✅
// m=3, n=1, a=8, b=6, c=10 ---> 3,4,5 重复， m和n必须一个奇数一个偶数 ❌
// ...
// m < L/2

func main() {
	println("75_Singular_Integer_Right_Triangles")
	MAX := 1500000
	length := make([]int, 1500001)
	for m := 2; m <= MAX/2; m++ {
		// 2 * m ^2 + 2mn <=L
		// n <= (L-2m^2)/2m = L/2m - m
		mm := min(MAX/2/m-m, m-1)
		for n := mm; n >= 1; n-- {
			if gcd(m, n) == 1 && (m-n)%2 == 1 {

				/*
					a := m*m - n*n
					b := 2 * m * n
					c := m*m + n*n
					fmt.Printf("find %d %d %d\n", a, b, c)
				*/

				d := 2 * m * (m + n)
				t := d
				for t <= MAX {
					length[t]++
					t += d
				}
			}
		}
		//		fmt.Printf("m=%d\n\n\n", m)
	}
	cnt := 0
	for i := 1; i <= MAX; i++ {
		if length[i] == 1 {
			cnt++
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}

func mim(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func gcd(m, n int) int {
	for m%n != 0 {
		m, n = n, m%n
	}
	return n
}
