package main

import "fmt"

/*

ulam type sequence
<2, 2n+1>
该序列只有两个偶数，第一个是2，第二个是 (2n+1)+(2n+3) = 4n+4
之后所有都是奇数，只能由之前的奇数+2，或者+(4n+4)得到

奇数每个奇书是2，所以一个奇数x是否在序列中，主要看x-2, x-4n-4是否在序列中
也就是前一个奇数或者前（2n-2)个奇数是否在序列中

假设A[x] = 0,1 0表示不在序列中，1表示在序列中

A[x] = A[x-2] xor A[x-4n-4] , 前面相关的两个奇数恰好有一个在

前面2n-2位01序列，一共是2^(2n-2)种状态，

首位 xor 尾位 = 下一位，然后整体窗口滑动一位，直到出现一个循环


*/

func main() {
	//	ulam(2, 5)
	//	return

	total := 0
	for n := 2; n <= 10; n++ {
		b := 2*n + 1
		t := ulam(2, b)
		fmt.Printf("n=%d, cnt=%d\n", n, t)
		total += t
	}
	fmt.Printf("total = %d\n", total)
}

func ulam(a, b int) int {
	//	fmt.Printf("a=%d b=%d\n", a, b)
	//第一个偶数是a=2
	//第二个偶数是b+b+2 = 2b+2 , b=5, 2*5=12
	//	fmt.Printf("两个偶数 %d, %d\n", a, b+b+2)
	// 长度 = b+1 = 5+1 = 6
	L := b + 1
	mask := 1<<((b+3)/2) - 1
	//	fmt.Printf("len=%d, mask=%b\n", L, mask)

	// 2b+3 是下一个奇数，要检查它是否在序列中，依赖它前面的6个奇数
	// 需要一个初始化状态，
	mask = 0
	for k := 0; 2*b+1-2*k >= b; k++ {
		mask = mask | (1 << k)
		//	fmt.Printf("mask = %b, 2*b+1-2*k=%d\n", mask, 2*b+1-2*k)
	}
	m := make(map[int]int)
	idx := 2*b + 1
	m[mask] = idx

	// 有了mask之后，就开始生成下一位
	VV := 1<<L - 1
	mask = mask & VV
	//	fmt.Printf("mask=%b\n", mask)

	for {
		idx += 2
		// 取mask的最后1位以及第L位
		low := mask & 1
		high := (mask >> (L - 1)) & 1
		a := low ^ high

		next := (mask << 1) | a
		next = next & VV
		_, ok := m[next]
		if ok {
			//	fmt.Printf("next=%b, idx=%d v=%d\n", next, idx, v)
			mask = next
			break
		}
		//fmt.Printf("next=%b, idx=%d v=%d\n", next, idx, v)
		m[next] = idx
		mask = next
	}
	P := len(m)
	delta := idx - m[mask]
	cnt := 0
	for k := range m {
		if k&1 == 1 {
			cnt++
		}
	}
	fmt.Printf("P=%d cnt=%d delta=%d\n", P, cnt, delta)
	// 每P个奇数中，有cnt个奇数在序列中，第10^11项，通过周期性来计算
	pre := (b+1)/2 + 1
	left := int(1e11) - 2 - pre
	loops := left / cnt
	r := left % cnt
	start := loops*delta + 2*b + 1
	fmt.Printf("start=%d loops=%d r=%d\n", start, loops, r)
	for r > 0 {
		start += 2
		low := mask & 1
		high := (mask >> (L - 1)) & 1
		a := low ^ high
		if a == 1 {
			r--
		}
		next := (mask << 1) | a
		next = next & VV
		mask = next
	}
	//	fmt.Printf("start=%d\n", start)
	return start
}
