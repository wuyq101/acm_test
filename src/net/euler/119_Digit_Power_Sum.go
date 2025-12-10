package main

import (
	"fmt"
	"sort"
)

func main() {
	for s := 2; s <= 100; s++ {
		fmt.Printf("s = %d\n", s)
		g(s)
	}
	sort.Slice(list, func(i, j int) bool {
		return list[i] < list[j]
	})
	fmt.Printf("list = %v\n", list)
	fmt.Printf("a30 = %d\n", list[29])

}

var list = make([]int64, 0)

func g(s int) {
	// 假设s = 8
	// k从2开始搜索
	// s^k 的位数如果超过 9*len(s), 超过9位数是，肯定不可能了
	n := int64(s)
	cnt := 0
	k := 1
	for {
		k++
		n *= int64(s)
		if n <= 0 {
			return
		}
		d := digit(n)
		if d == s {
			fmt.Printf("find %d\n", n)
			fmt.Printf("s=%d, k=%d, s^k=%d, cnt=%d\n", s, k, n, cnt)
			list = append(list, n)
		}
		if d > s {
			cnt++
		} else {
			cnt = 0
		}
		if cnt > 3 {
			return
		}
		//	fmt.Printf("s=%d, k=%d, s^k=%d, cnt=%d\n", s, k, n, cnt)
	}
}

func digit(n int64) int {
	s := 0
	for n > 0 {
		s += int(n % 10)
		n /= 10
	}
	return s
}
