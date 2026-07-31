package main

import "fmt"

func main() {
	m := make(map[int64]bool)
	sum := int64(0)
	for row := 0; row <= 50; row++ {
		// C(n,k) = (n!)/(k!(n-k)!)
		for k := 0; k <= row; k++ {
			c := C(row, k)
			if !m[c] {
				fmt.Printf("c=%d\n", c)
				m[c] = true
				sum += c
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

var primes = []int64{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47}

func C(n, k int) int64 {
	if k == 0 || k == n {
		return 1
	}
	if k > n-k {
		k = n - k
	}
	nums := make([]int64, k)
	for i := 0; i < k; i++ {
		nums[i] = int64(n - i)
	}
	for i := 2; i <= k; i++ {
		d := int64(i)
		for j := 0; j < k; j++ {
			g := gcd(nums[j], d)
			nums[j] /= g
			d /= g
			if d == 1 {
				break
			}
		}
	}
	//	fmt.Printf("C(%d,%d) = %v\n", n, k, nums)
	for _, p := range primes {
		cnt := 0
		for i := 0; i < k; i++ {
			if nums[i]%p != 0 {
				continue
			}
			v := nums[i]
			for v%p == 0 {
				cnt++
				v /= p
			}
			if cnt > 1 {
				return 0
			}
		}

	}
	p := int64(1)
	for _, v := range nums {
		p *= v
	}
	return p
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
