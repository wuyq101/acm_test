package main

import "fmt"

/*
an+1 = an/3,  an = 0 (mod 3)   D


an+1 = (4an+2)/3,  an = 1 (mod 3)   U


an+1 = (2an-1)/3,  an = 2 (mod 3)   d



*/

func main() {
	str := "DdDddUUdDD"
	M := int64(1e6)
	g(M, str)
	str = "UDDDUdddDDUDDddDdDddDDUDDdUUDd"
	M = int64(1e15)
	g(M, str)
}

func g(M int64, str string) int64 {
	v, step := f(str)
	for v < M {
		v += step
	}
	fmt.Printf("v = %d, step=%d\n", v, step)
	return v
}

// 返回一个当前最小的v值，以及vstep, 最小的增长步长
func f(str string) (int64, int64) {
	if len(str) == 0 {
		return 0, 1
	}
	v, s := f(str[1:])
	fmt.Printf("%s\t%d\t%d\n", str[1:], v, s)
	k := int64(0)
	// 下一层的X1 = v+k*s
	switch str[0] {
	case 'D':
		// X1 = X0/3
		// X0 = 3*X1 = 3*(v+k*s)
		// 所以最小的X0是k=0市，3v, 增长步骤是3s
		return 3 * v, 3 * s
	case 'U':
		// X1 = (4X0+2)/3
		// 4X0 = 3X1-2
		// 4X0 = 3(v+k*s)-2
		// X0是整数，且3(v+k*s)-2 = 0 (mod 4), k={0,1,2,3}
		for k = 0; k < 4; k++ {
			if (3*(v+k*s)-2)%4 == 0 {
				break
			}
		}
		vnew := (3*(v+k*s) - 2) / 4
		// X1的增长是S，X0的增长是  4S*3/4 = 3S, k是4个4个增长的
		return vnew, 3 * s
	case 'd':
		// X1 = (2X0-1)/3
		// 2X0 = 3X1+1 = 3(v+k*s)+1 = 3v+3k*s+1
		for k = 0; k < 2; k++ {
			if (3*v+3*k*s+1)%2 == 0 {
				break
			}
		}
		vnew := (3*v + 3*k*s + 1) / 2
		// 增长步数 2S*3/2 = 3S
		return vnew, 3 * s
	}
	return 0, 1
}
