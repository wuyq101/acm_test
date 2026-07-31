package main

import "fmt"

func main() {
	N := 1000000
	cnt := 0
	m := make(map[int]int)
	for b := 1; b <= N/2; b++ {
		// 对于一个固定的b，有多少种a
		for a := b + 1; a*a-b*b <= N; a++ {
			if (a-b)%2 == 0 {
				v := a*a - b*b
				//	fmt.Printf("a=%d, b=%d %d\n", a, b, a*a-b*b)
				cnt++
				m[v] = m[v] + 1
			}
		}
	}
	fmt.Printf("m size=%d\n", len(m))
	cnt = 0
	for _, v := range m {
		if v <= 10 {
			cnt++
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}
