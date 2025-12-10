package main

import (
	"sort"
	"strconv"
)

/*
F329468, first and last 9 digits has 1-9.
*/

func main() {
	/*
		a := "100023"
		b := "3999999"
		c := Add(a, b)
		fmt.Printf("%s + %s = %s\n", a, b, c)
		a = "1"
		b = "1"
		cnt := 2
		for {
			c = Add(a, b)
			cnt++
			a, b = b, c
			flag := check(cnt, c)
			if flag {
				fmt.Printf("c=%s\n", c)
				fmt.Printf("cnt = %d\n", cnt)
				return
			}
		}
	*/
}

func check(idx int, s string) bool {
	//fmt.Printf("check %s, len = %d\n", s, len(s))
	if len(s) < 9 {
		return false
	}
	if isPandigital(s[len(s)-9:]) {
		//	fmt.Printf("check %s, idx = %d\n", s, idx)
	} else {
		return false
	}
	return isPandigital(s[0:9])
}

func isPandigital(s string) bool {
	start := []byte(s)
	sort.Slice(start, func(i, j int) bool {
		return start[i] < start[j]
	})
	for i := 0; i < 9; i++ {
		if start[i] != byte('0'+i+1) {
			return false
		}
	}
	return true
}

func Add(a, b string) string {
	if len(a) == 0 {
		return b
	}
	if len(b) == 0 {
		return a
	}
	x := byte(a[len(a)-1])
	y := byte(b[len(b)-1])
	s := int(x - '0' + y - '0')
	pre := Add(a[:len(a)-1], b[:len(b)-1])
	if s < 10 {
		return pre + strconv.Itoa(s)
	}
	pre = Add(pre, "1")
	return pre + strconv.Itoa(s%10)
}
