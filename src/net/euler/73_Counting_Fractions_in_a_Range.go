package main

import "fmt"

func main() {
	// right
	right := Fraction{1, 2}
	// left
	left := Fraction{1, 3}
	fmt.Printf("left = %v, right = %v\n", left, right)
	cnt := 0
	MAX := 12000
	for d := 1; d <= MAX; d++ {
		j := left.num * d / left.den
		k := right.num * d / right.den
		for i := j; i <= k; i++ {
			if gcd(i, d) != 1 {
				continue
			}
			f := Fraction{i, d}
			if cmp(f, right) >= 0 {
				break
			}
			if cmp(f, left) == 1 {
				cnt++
			}

		}
	}
	fmt.Printf("left = %v, right = %v, cnt=%d\n", left, right, cnt)
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

type Fraction struct {
	num int
	den int
}

func (f Fraction) String() string {
	return fmt.Sprintf("%d/%d", f.num, f.den)
}

func cmp(a, b Fraction) int {
	if a.num*b.den > b.num*a.den {
		return 1
	}
	if a.num*b.den < b.num*a.den {
		return -1
	}
	return 0
}
