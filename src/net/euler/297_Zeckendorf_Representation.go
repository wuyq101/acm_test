package main

import "fmt"

func main() {
	s := Sigma(N - 1)
	fmt.Printf("s=%d\n", s)
}

var N = int64(1e17)
var fabonacci = genFabonacci()

func genFabonacci() []int64 {
	f := make([]int64, 2)
	f[0] = 1
	f[1] = 2
	a, b, c := f[0], f[1], int64(0)
	for {
		c = a + b
		if c >= N {
			break
		}
		f = append(f, c)
		a, b = b, c
	}
	return f
}

var cache = make(map[int64]int64)

func Sigma(n int64) int64 {
	v, ok := cache[n]
	if ok {
		return v
	}
	if n <= 3 {
		return n
	}
	// 找到最大的，小于n的斐波那契数
	f := int64(0)
	for i := 0; i < len(fabonacci); i++ {
		if fabonacci[i] <= n {
			f = fabonacci[i]
		} else {
			break
		}
	}
	//	fmt.Printf("n=%d,f=%d\n", n, f)
	// 将 1----n, 按照f，分成 1---f-1,f,f+1---n
	//1 -- f-1 --> Sigma(f-1)
	// f
	// f+1 -- n --> 1,2,..,n-f       Sigma(n-f)，每个数字都减f，一共减了n-f+1项
	v = Sigma(f-1) + Sigma(n-f) + n - f + 1
	cache[n] = v
	return v
}
