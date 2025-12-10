package main

import "fmt"

/*
n = 6
A = {11,18,19,20,22,25}

n = 7
A = {20,31,38,39,40,42,45}
先推断，然后验证是否符合条件，并且在S(A) = 115 + 20*7 = 255的基础上，逐个减少，并搜索是否存在可行解.

*/

var find = false

func main() {
	N := 134
	for {
		split(N, 7, nil)
		if find {
			return
		}
		N++
	}
}

// 将N分成n个不同的正整数相加
func split(N int, n int, pre []int) []int {
	if n == 1 {
		if len(pre) > 0 && N <= pre[len(pre)-1] {
			return nil
		}

		pre = append(pre, N)
		if check(pre) {
			find = true
			fmt.Printf("%v\n", pre)
		}
		return pre
	}

	for i := 1; i <= N; i++ {
		if contains(pre, i) {
			continue
		}
		if len(pre) > 0 && i <= pre[len(pre)-1] {
			continue
		}
		// 使用i
		pre = append(pre, i)
		split(N-i, n-1, pre)
		// 不使用i
		pre = pre[:len(pre)-1]
	}
	return nil
}

func contains(list []int, n int) bool {
	for _, v := range list {
		if v == n {
			return true
		}
	}
	return false
}

func check(list []int) bool {
	for k := 2; k < len(list); k++ {
		sa := 0
		for i := 0; i < k; i++ {
			sa += list[i]
		}
		sb := 0
		for i := len(list) - k + 1; i < len(list); i++ {
			sb += list[i]
		}
		if sa <= sb {
			return false
		}
	}
	// 1. S(B) != S(C)
	// 2. if B contains more elements than C, then S(B) > S(C)
	// N = 6
	// 000001
	// ...
	// 111111
	M := 1 << len(list)
	for b := 1; b < M; b++ {
		for c := 1; c < M; c++ {
			if b&c != 0 {
				continue
			}
			SB := subsum(list, b)
			SC := subsum(list, c)
			if SB == SC {
				return false
			}
			bitB := bits(b)
			bitC := bits(c)
			if bitB > bitC && SB < SC {
				return false
			}
			if bitB < bitC && SB > SC {
				return false
			}
		}
	}
	return true
}

func bits(n int) int {
	count := 0
	for n > 0 {
		n &= n - 1
		count++
	}
	return count
}

func subsum(list []int, mask int) int {
	sum := 0
	for i := 0; i < len(list); i++ {
		if mask&(1<<i) != 0 {
			sum += list[i]
		}
	}
	return sum
}
