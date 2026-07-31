package main

import (
	"fmt"
	"math"
	"strconv"
)

func main() {
	// 最后一位是0的平方数，只能是xxx0的平方，最后两位都是0
	// 1_2_3_4_5_6_7_8_9_0
	M := int64(1929394959697989990)
	m := int64(math.Sqrt(102030405060708090))
	m = (m / 10) * 10
	str := strconv.FormatInt(M, 10)
	fmt.Printf("len=%d\n", len(str))
	fmt.Printf("m=%d, n=%d\n", m, int64(math.Sqrt(float64(M))))
	// 尾数是3 或者7 n=3 or 7 (mod 10)
	for i := m; i*i <= M; i += 10 {
		k := i / 10
		if k%10 == 3 || k%10 == 7 {
			str := strconv.FormatInt(i*i, 10)
			if len(str) == 19 && check(str) {
				fmt.Printf("i = %d, i*i=%d\n", i, i*i)
			}
		}
	}
}

func check(s string) bool {
	s = s[:17]
	a := byte('1')
	for i := 0; i < len(s); i++ {
		if i%2 == 0 {
			if s[i] != a {
				return false
			}
			a++
		}
	}
	return true
}
