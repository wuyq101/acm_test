package main

import "fmt"

func main() {
	fmt.Printf("π = 0.%s%s \n", A, B)
	p7 := int64(1)
	p10 := int64(1)
	LA := int64(len(A))
	LB := int64(len(B))
	F := make([]int64, 0)
	F = append(F, LA)
	F = append(F, LB)
	sum := int64(0)
	for i := int64(0); i <= 17; i++ {
		N := (127 + 19*i) * p7
		p7 *= 7
		LC := LA + LB
		LA, LB = LB, LC
		F = append(F, LC)
		for LC < N {
			LC = LA + LB
			LA, LB = LB, LC
			F = append(F, LC)
		}
		d := D(N, LC, F, len(F)-1)
		fmt.Printf("i=%d, N=%d, LC=%d, size=%d, d=%d\n", i, N, LC, len(F), d)
		sum += int64(d) * p10
		p10 *= 10
	}
	fmt.Printf("sum=%d\n", sum)
}

var A = "1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679"
var B = "8214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196"

func D(N, L int64, F []int64, idx int) int {
	// 当前项长度L, L = F[idx-2] + F[idx-1]
	// 要找的位置在第N位
	if idx == 0 {
		return int(A[N-1] - '0')
	}
	if idx == 1 {
		return int(B[N-1] - '0')
	}
	// 看N是位于哪个部分
	// 位于前半部分
	if N <= F[idx-2] {
		return D(N, F[idx-2], F, idx-2)
	}
	return D(N-F[idx-2], F[idx-1], F, idx-1)
}
