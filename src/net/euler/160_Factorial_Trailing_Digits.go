package main

import "fmt"

/*

Factorial Trailing Digits
https://projecteuler.net/problem=160

求N = 10^12的阶乘的尾数（除0之外的最后5位)

设N=100, 将1到100分为3组

第一组：
2的倍数
2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,90,92,94,96,98,100
它们的乘积
每个数字提取一个2, 2^50 * (50!)


第二组：
5的倍数
5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100
每个数字提取一个5，5^20 * (20!)

第一组和第二组重复的数字：
10，20，30，40，50，60，70，80，90, 100
每个数字提取一个10，10^10 * (10!)


第三组：
剩余不是2的倍数，也不是5的倍数的数
3,7,9,11,13,17,19,21,23,27,29,31,33,37,39,41,43,47,49,51,53,57,59,61,63,67,69,71,73,77,79,81,83,87,89,91,93,97


*/

func main() {
	// 第三类数据，与10不互质
	// 前10^5个数，3,7,9,11,13,17... 最终乘积是1
	// 所以前10^12刚好是 10^7个 10^5的完整周期，最终对乘积的贡献也是1
	p := int64(1)
	for i := int64(1); i < 100000; i++ {
		if gcd(i, 10) == 1 {
			p *= i
			p %= 100000
		}
	}
	A := p
	fmt.Printf("A=%d\n", A)
	// 剩下就是考虑2的倍数和5的倍数的问题
	n := int64(1e12)
	V := f(n)
	fmt.Printf("f(%d)=%d\n", n, V)
	c2 := h(n, 2)
	c5 := h(n, 5)
	P := pow(2, c2-c5)
	V = V * P % M
	fmt.Printf("V=%d\n", V)
	fmt.Printf("cache size=%d\n", len(cache))
}

func pow(n int64, p int) int64 {
	if p == 0 {
		return 1
	}
	if p%2 == 0 {
		v := pow(n, p/2)
		return (v * v) % M
	}
	return (n * pow(n, p-1)) % M
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func h(n, p int64) int {
	cnt := 0
	for n > 0 {
		cnt += int(n / p)
		n /= p
	}
	return cnt
}

var M = int64(100000)

var cache = map[int64]int64{}

// n!在提出2和5的因子(去掉尾部的0)之后 mod 10^5
func f(n int64) int64 {
	v, ok := cache[n]
	if ok {
		return v
	}

	//fmt.Printf("f(%d)\n", n)
	if n == 0 || n == 1 {
		cache[n] = 1
		return 1
	}
	// 先处理与10互质的数, 通过计算这部分数从1--100000中，对最终的贡献是1
	k := n % M
	p := int64(1)
	for i := int64(1); i <= k; i++ {
		if gcd(i, 10) == 1 {
			p *= i
			p %= M
		}
	}
	A := p

	// 2的倍数部分
	B := f(n / 2)

	// 5的倍数部分
	C := f(n / 5)

	// 10的倍数部分
	D := f(n / 10)

	//	fmt.Printf("A=%d,B=%d,C=%d,D=%d\n", A, B, C, D)

	result := (A * B) % M
	result = (result * C) % M
	result = (result * inv(D)) % M
	cache[n] = result
	return result
}

// n*x = 1 (mod M)
func inv(n int64) int64 {
	for i := int64(1); i <= M; i++ {
		p := i * n
		if p%M == 1 {
			return i
		}
	}
	fmt.Printf("inv(%d) not found\n", n)
	return -1
}
