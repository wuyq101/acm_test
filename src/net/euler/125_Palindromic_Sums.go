package main

import "fmt"

/*
https://projecteuler.net/problem=125
1^2+2^2+3^2+...+n^2 = n(n+1)(2n+1)/6
所有小于10^8的回文数字,并且是连续的平方和
k<n, k^2 + (k+1)^2 + ... + (n-1)^2+n^2 = n(n+1)(2n+1)/6 - k(k+1)(2k+1)/6 + k^2
对一个特定的回文数字K，595， 求是否存在k和n
595 = 6^2 + 7^2 + 8^2 + 9^2 + 10^2 + 11^2 + 12^2
先固定k，然后求n，直到找到n，或者k超出范围
*/
func main() {
	MAX := 100000000
	sum := 0
	A := make(map[int]bool, 0)
	for i := 1; i < MAX; i++ {
		if !isPalindromic(i) {
			continue
		}
		A[i] = true
	}
	fmt.Printf("len(A) = %d\n", len(A))

	B := make(map[int]bool, 0)
	for i := 1; i*i < MAX; i++ {
		t := i * i
		for j := i + 1; j*j < MAX; j++ {
			t += j * j
			if t <= MAX && !B[t] {
				B[t] = true
			}
			if t > MAX {
				break
			}
		}
	}
	fmt.Printf("len(B) = %d\n", len(B))
	cnt := 0
	for k, _ := range A {
		if B[k] {
			//	fmt.Printf("%d\n", k)
			sum += k
			cnt++
		}
	}
	fmt.Printf("sum = %d, cnt=%d\n", sum, cnt)
}

func isPalindromic(n int) bool {
	s := fmt.Sprintf("%d", n)
	for i := 0; i < len(s)/2; i++ {
		if s[i] != s[len(s)-1-i] {
			return false
		}
	}
	return true
}

func findKN(K int) (int, int) {
	k := 1
	for {
		// 1 --- k-1
		k1 := k - 1
		v := k1 * (k1 + 1) * (2*k1 + 1) / 6
		//	fmt.Printf("k=%d, v=%d\n", k, v)
		// K = n(n+1)(2n+1)/6 - k(k+1)(2k+1)/6 + k^2 = n(n+1)(2n+1)/6 - (k(k+1)(2k+1)/6 - k^2)
		v = K + v
		if v < 0 {
			break
		}
		// n(n+1)(2n+1) = v
		// 二分法查找n
		left, right := k, v
		for left <= right {
			mid := (left + right) / 2
			t := mid * (mid + 1) * (2*mid + 1) / 6
			//fmt.Printf("k=%d left=%d right=%d mid=%d, t=%d, v=%d\n", k, left, right, mid, t, v)
			if t == v {
				return k, mid
			}
			if t > v {
				right = mid - 1
			} else {
				left = mid + 1
			}
		}
		k++
	}
	return -1, -1
}
