package main

import (
	"fmt"
)

/*
https://projecteuler.net/problem=171
0<n<10^20
最多20位数字，所以f(n)的最大值是9*9*20 = 1620
枚举1--1620直接的所有平方数

假设一个是A是一个平方数，它可以拆成

A = ∑ai^2 {ai | 0<=ai<=9}

36 = 4^2 + 4^2 + 2^2 ---> (4,4,2)的全排列
   = 6^2,   6开头，后面可以加0， 因为结果是求最后0位数的和，所以6后面一直可以加8个0
   = 5^2+1^2*11, （11个1和1个5组成的全排列）
   = 4^2+2^2+1^2*16 (4,2,1,...,1)
   = 4^2 + 3^2 + 11 * 1 ....




*/

func main() {
	for i := 1; i*i <= 1620; i++ {
		fmt.Printf("%d*%d=%d\n", i, i, i*i)
		dfs(i*i, 9, []int{})
	}
	fmt.Printf("sum=%d\n", sum)
}

var sum = int64(0)
var M = int64(1000000000)

func p(list []int) {
	tmp := make([]int, 0)
	for _, v := range list {
		tmp = append(tmp, v)
	}
	// 补充满20位
	for len(tmp) < 20 {
		tmp = append(tmp, 0)
	}
	list = tmp
	m := make(map[int]int)
	for _, v := range list {
		m[v]++
	}
	t := int64(0)
	for j, _ := range m {
		// 数字j在第i位
		if j == 0 {
			continue
		}
		m[j]--
		cnt := perCnt(m)
		m[j]++
		t = (t + int64(j)*cnt%M) % M
	}
	sum = (sum + t*int64(111111111)%M) % M
	return
}

func perCnt(m map[int]int) int64 {
	total := 0
	for _, c := range m {
		total += c
	}
	// 分子 =
	nums := make([]int, 0, total)
	for total > 0 {
		nums = append(nums, total)
		total--
	}
	// 分母
	dens := make([]int, 0)
	for _, c := range m {
		if c == 1 {
			continue
		}
		// 有重复的,除以c!
		for c > 0 {
			dens = append(dens, c)
			c--
		}
	}
	for i := 0; i < len(dens); i++ {
		if dens[i] == 1 {
			continue
		}
		for k := 0; k < len(nums); k++ {
			g := gcd(dens[i], nums[k])
			if g > 1 {
				dens[i] /= g
				nums[k] /= g
			}
			if dens[i] == 1 {
				break
			}
		}
	}
	prod := int64(1)
	for _, v := range nums {
		prod = (int64(v) * prod) % M
	}
	return prod
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func dfs(n int, idx int, pre []int) {
	if n == 0 {
		if len(pre) <= 20 {
			//	fmt.Printf("find one combination %v\n", pre)
			p(pre)
			return
		}
	}
	if idx == 0 || len(pre) == 20 {
		return
	}
	if n >= idx*idx {
		// use idx
		pre = append(pre, idx)
		dfs(n-idx*idx, idx, pre)
		pre = pre[:len(pre)-1]
	}
	// not use idx
	dfs(n, idx-1, pre)
}
