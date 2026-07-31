package main

import "fmt"

/*

a^2 - b^2 = N, N<=1000000


a^2 - b^2 <=N, a>b

b^2 >= a^2-N, 当a取b+1时

b^2 + 2b + 1 - b^2 <= N
b <= (N-1)/2
b最大不超过

*/

func main() {
	N := 1000000
	cnt := 0
	for b := 1; b <= N/2; b++ {
		// 对于一个固定的b，有多少种a
		for a := b + 1; a*a-b*b <= N; a++ {
			if (a-b)%2 == 0 {
				fmt.Printf("a=%d, b=%d %d\n", a, b, a*a-b*b)
				cnt++
			}
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}
