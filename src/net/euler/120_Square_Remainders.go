package main

import "fmt"

func main() {
	sum := 0
	for i := 3; i <= 1000; i++ {
		v := r(i)
		sum += v
	}
	fmt.Printf("sum=%d\n", sum)
}

func r(a int) int {
	// (a-1)^n + (a+1)^n mod a^2
	k := a * a
	A := make([]int, 0)
	A = append(A, a-1)
	B := make([]int, 0)
	B = append(B, a+1)
	C := make([]int, 0)
	C = append(C, (2*a)%k)
	for {
		v := A[len(A)-1]
		v = (v * (a - 1)) % k
		A = append(A, v)
		v = B[len(B)-1]
		v = (v * (a + 1)) % k
		B = append(B, v)
		v = (A[len(A)-1] + B[len(B)-1]) % k
		C = append(C, v)
		// 检查是否重复，如果重复了，退出
		if isRepeat(A, B, C) {
			// find max
			m := 0
			for _, v := range C {
				if v > m {
					m = v
				}
			}
			fmt.Printf("a=%d,len=%d, r_max=%d\n", a, len(C), m)
			fmt.Printf("A=%v\nB=%v\nC=%v\n", A, B, C)
			return m
		}
	}
	return 0
}

func isRepeat(A, B, C []int) bool {
	a := A[len(A)-1]
	b := B[len(B)-1]
	c := C[len(C)-1]
	for i := 0; i <= len(A)-2; i++ {
		if A[i] == a && B[i] == b && C[i] == c {
			return true
		}
	}
	return false
}
