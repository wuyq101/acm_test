package main

import "fmt"

func main() {
	for i := 4; i <= 15; i++ {
		cnt := check(i)
		fmt.Printf("i=%d,cnt=%d\n", i, cnt)
	}
}

func check(N int) int {
	cnt := 0
	M := 1 << N
	for b := 1; b < M; b++ {
		for c := b + 1; c < M; c++ {
			if b&c != 0 {
				continue
			}
			bitB := bits(b)
			bitC := bits(c)
			if bitB != bitC {
				continue
			}
			if bitB == 1 {
				continue
			}
			if !(canCmp(b, c) || canCmp(c, b)) {
				cnt++
				//	fmt.Printf("b=%012b, c=%012b\n", b, c)
			}
		}
	}
	return cnt
}

// b=1010010, c=0101001
// 假设A={a1,a2,a3,a4,a5,a6}
// b代表子集{a1,a3,a5}, c代表子集{a2,a4,a6}
// 通过比较最大值，来判断c是否大于b
// 先比较a6 和 a5
func canCmp(b, c int) bool {
	if b == 0 && c == 0 {
		return true
	}
	if lastBit(b) > lastBit(c) {
		return canCmp(b&(b-1), c&(c-1))
	}
	return false
}

func lastBit(n int) int {
	cnt := 0
	for {
		if n&(1<<cnt) != 0 {
			return cnt
		}
		cnt++
	}
}

func bits(n int) int {
	count := 0
	for n > 0 {
		n &= n - 1
		count++
	}
	return count
}
