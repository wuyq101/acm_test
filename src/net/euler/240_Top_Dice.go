package main

import (
	"fmt"
)

/*
https://projecteuler.net/problem=240

1111 ways, 5个6面的骰子，扔出最大的3个🎲和为15

15 = 6+6+3
   = 6+5+4
   = 5+5+5



*/

func main() {
	fact = make([]int64, 21)
	fact[0] = 1
	for i := 1; i <= 20; i++ {
		fact[i] = int64(i) * fact[i-1]
	}

	dfs(70, 12, []int{})
	fmt.Printf("S=%d\n", S)
}

var N = 10
var S = int64(0)
var fact []int64

func dfs(sum, idx int, pre []int) {
	//	fmt.Printf("dfs,sum=%d,idx=%d,pre=%v\n", sum, idx, pre)
	if sum == 0 {
		if len(pre) == N {
			s := p(pre)
			S += s
			//		S.Add(S, big.NewInt(s))
			fmt.Printf("%v,s=%d S=%v\n", pre, s, S)
		}
		return
	}
	if len(pre) >= N || idx == 0 {
		return
	}
	if sum >= idx {
		pre = append(pre, idx)
		dfs(sum-idx, idx, pre)
		pre = pre[:len(pre)-1]
	}
	dfs(sum, idx-1, pre)
}

func p(list []int) int64 {
	// top ten 固定，剩余的都小于等于list中的最小值a
	a := list[N-1]
	s := 0
	if a == 1 {
		// 全部都只能选择1
		s = 10
	}
	v := int64(a - 1)
	sum := int64(0)
	// 前10个数，固定为list中的数字。后面10个数，假设有k个是a，k==0...10, 剩余的10-k个数，从1---a-1,
	for k := s; k <= 10; k++ {
		// 10+k个已经固定，剩余的10-k, (a-1)^(10-k)
		// C(20,10+k)* （列表+k个a）全排列
		// 20!/(10+k)!(10-k)! * (10+k)
		p := permutaion(list, k)
		if v > 1 {
			// (a-1)^(10-k)
			for i := 0; i < 10-k; i++ {
				p *= v
			}
		}
		sum += p
	}
	return sum
}

func permutaion(list []int, k int) int64 {
	cnt := make([]int, 13)
	for _, v := range list {
		cnt[v]++
	}
	p := fact[20]
	for i := 1; i <= 12; i++ {
		if cnt[i] == 0 {
			continue
		}
		c := cnt[i]
		if i == list[N-1] {
			c += k
		}
		p /= fact[c]
	}
	p /= fact[10-k]
	return p
}
