package main

import (
	"fmt"
	"strings"
)

func main() {
	// 数字10^25次超过int64的范围
	// 10^25 ----> 5*10^24
	a := "1" + strings.Repeat("0", 25)
	v := f(a)
	fmt.Printf("%d\n", v)
}

var cache = map[string]int64{}

func f(a string) int64 {
	if a == "0" || a == "1" {
		return 1
	}
	v, ok := cache[a]
	if ok {
		return v
	}
	fmt.Printf("a=%s\n", a)
	if isOdd(a) {
		// 奇数，1 + ....
		v = f(divide2(substract(a, 1)))
		cache[a] = v
		return v
	}
	// 偶数，2 + ....
	// 1+1 +...
	// 2+...
	v = f(divide2(substract(a, 2))) + f(divide2(a))
	cache[a] = v
	return v
}

func isOdd(n string) bool {
	// 看最低位
	d := byte(n[len(n)-1]) - '0'
	return d%2 == 1
}

// n - m, m<10
func substract(n string, m int) string {
	d := byte(n[len(n)-1]) - '0'
	if d >= byte(m) {
		d -= byte(m)
		return n[:len(n)-1] + string(d+'0')
	}
	d = d + byte(10-m)
	n = substract(n[:len(n)-1], 1) + string(d+'0')
	for len(n) > 1 && n[0] == '0' {
		n = n[1:]
	}
	return n
}

// n/2
func divide2(n string) string {
	nums := make([]int, len(n))
	for i := 0; i < len(n); i++ {
		nums[i] = int(n[i] - '0')
	}
	// /2
	for i := 0; i < len(nums); i++ {
		if nums[i]%2 == 1 {
			nums[i+1] += 10
			nums[i] -= 1
		}
		nums[i] /= 2
	}
	n = ""
	for i := 0; i < len(nums); i++ {
		n += fmt.Sprintf("%d", nums[i])
	}
	for len(n) > 1 && n[0] == '0' {
		n = n[1:]
	}
	return n
}
