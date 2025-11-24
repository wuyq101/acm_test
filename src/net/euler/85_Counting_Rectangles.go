package main

import "fmt"

func main() {
	/*
		n := 1
		m := 1
		idx := 0
		for {
			cnt := count(n, m)
			fmt.Printf("n=%d m=%d cnt=%d\n", n, m, cnt)
			if cnt >= 2000000 {
				break
			}
			idx = 1 - idx
			if idx == 0 {
				m++
			} else {
				n++
			}
		}
	*/
	n := 1
	delta := 2000000
	for {
		m := 1
		for {
			cnt := count(n, m)
			b := cnt - 2000000
			if b < 0 {
				b = -b
			}
			if b < delta {
				delta = b
				fmt.Printf("n=%d m=%d cnt=%d delta=%d\n", n, m, cnt, delta)
			}
			if cnt >= 2000000 {
				break
			}
			m++
		}
		n++
		if n >= 1000 {
			break
		}
	}
}

func count(n, m int) int {
	// 行数 i
	// 列数 j
	cnt := 0
	for i := 1; i <= n; i++ {
		for j := 1; j <= m; j++ {
			cnt += (n - i + 1) * (m - j + 1)
		}
	}
	return cnt
}
