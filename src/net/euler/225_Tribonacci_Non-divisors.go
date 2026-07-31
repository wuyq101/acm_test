package main

import "fmt"

func main() {
	a, b, c := int64(1), int64(1), int64(1)
	f := check(a, b, c, 81)
	fmt.Printf("f = %v\n", f)

	idx := 0
	n := int64(1)
	for {
		n += 2
		if check(a, b, c, n) {
			idx++
			fmt.Printf("n=%d, idx=%d\n", n, idx)
			if idx == 124 {
				return
			}
		}
	}
}

func check(a, b, c, n int64) bool {
	//	fmt.Printf("a=%d,b=%d,c=%d\n", a, b, c)
	a, b, c = b, c, (a+b+c)%n
	if a == 1 && b == 1 && c == 1 {
		return true
	}
	if c == 0 {
		return false
	}
	return check(a, b, c, n)
}
