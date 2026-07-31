package main

import "fmt"

func main() {
	for i := int64(1); i <= M; i++ {
		u[i] = 1
	}
	for i := int64(2); i <= M; i++ {
		if composites[i] {
			continue
		}
		u[i] = -1
		for j := i + i; j <= M; j += i {
			composites[j] = true
			// j中包含一个i的因子
			u[j] *= -1
		}
		// i^2
		for j := i * i; j <= M; j += i * i {
			u[j] = 0
		}
	}
	t := int64(0)
	for i := int64(1); i <= M; i++ {
		t += int64(u[i]) * (N / (i * i))
	}
	fmt.Printf("%d\n", t)
}

var N = int64(1 << 50)
var M = int64(1 << 25)
var u = make([]int8, M+1)
var composites = make([]bool, M+1)

// 容斥原理，Möbius function
func µ(n int64) int64 {
	cnt := 0
	k := 0
	p := int64(2)
	for p <= n {
		if n%p == 0 {
			cnt++
		}
		for n%p == 0 {
			k++
			n /= p
			// p^k, k>=2 所以肯定是0
			if k == 2 {
				return 0
			}
		}
		p++
		k = 0
	}
	// (-1)^cnt
	if cnt&1 == 0 {
		return 1
	}
	return -1
}
