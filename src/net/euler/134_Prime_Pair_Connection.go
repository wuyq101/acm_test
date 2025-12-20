package main

import "fmt"

func main() {
	primes := genPrimes(1000010)
	sum := 0
	for i := 0; i < len(primes); i++ {
		p := primes[i]
		if p < 5 || p > 1000000 {
			continue
		}
		p2 := primes[i+1]
		n := f(p, p2)
		sum += n
	}
	fmt.Printf("sum = %d\n", sum)
}

func f(p1, p2 int) int {
	// 存在k，是的 10^a * k + p1 = 0 mod p2
	// a是p1的位数
	t := 10
	p := p1
	for p >= 10 {
		p /= 10
		t *= 10
	}
	t1 := t % p2
	//fmt.Printf("%d %d\n", p1, t)
	k := 0
	v := p2 - p1
	for {
		k++
		n := t1 * k
		if n%p2 == v {
			n2 := k*t + p1
			//		fmt.Printf("p1=%d p2=%d k=%d n=%d n2=%d\n", p1, p2, k, n, n2)
			return n2
		}
	}
	return 0

}

func genPrimes(max int) []int {
	list := make([]bool, max+1)
	for i := 0; i < len(list); i++ {
		list[i] = true
	}
	list[0], list[1] = false, false
	for i := 0; i < len(list); i++ {
		if !list[i] {
			continue
		}
		for j := i + i; j < len(list); j += i {
			list[j] = false
		}
	}
	primes := make([]int, 0)
	for i := 0; i < len(list); i++ {
		if list[i] {
			primes = append(primes, i)
		}
	}
	return primes
}
