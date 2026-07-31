package main

import "fmt"

func main() {
	m = make([]map[int]int, 10)
	for i := 0; i < 10; i++ {
		m[i] = map[int]int{}
	}
	A := 0
	B := int(1e12)
	cnt := int64(0)
	for d := 1; d <= 9; d++ {
		sum = int64(0)
		F(A, B, d)
		fmt.Printf("d=%d sum=%d\n", d, sum)
		cnt += sum
	}
	fmt.Printf("total=%d\n", cnt)
}

var sum = int64(0)

func F(A, B, d int) {
	//  在[A,B]范围内，找 f(n)=n
	// f(n)单调递增
	// f(A,d) < f(n,d) < f(B,d)
	// 如果f(A,d) >= B， 则说明从A到B的区间内都没有f(n,d)=n
	// 如果f(B,d) <= A, 则说明从A到B的区间内都没有f(n,d)=n
	// 否则，可能有解，二分继续查找
	if A > B {
		return
	}
	if A == B {
		v := f(A, d)
		if A == v {
			fmt.Printf("%d find one %d\n", d, A)
			sum += int64(A)
		}
		return
	}
	a := f(A, d)
	if a > B {
		return
	}

	b := f(B, d)
	if b < A {
		return
	}
	mid := (A + B) / 2
	F(A, mid, d)
	F(mid+1, B, d)
}

var m []map[int]int

func f(n, d int) int {
	v, ok := m[d][n]
	if ok {
		return v
	}

	if n < 10 {
		if n >= d {
			return 1
		}
		return 0
	}
	// n = a*10^k + r
	p := 1
	for p*10 <= n {
		p *= 10
	}
	a := n / p
	r := n - a*p
	if a == d {
		// 最高位上有 r+1个d
		v = r + 1 + f(r, d) + d*f(p-1, d)
		m[d][n] = v
		return v
	}
	if a < d {
		v = f(r, d) + a*f(p-1, d)
		m[d][n] = v
		return v
	}
	v = f(r, d) + a*f(p-1, d) + p
	m[d][n] = v
	return v
}
