package main

import "fmt"

// https://projecteuler.net/problem=71
// 找到分母小于10^6的有序分数, 最接近3/7的最简分数
func main() {
	fmt.Printf("71\n")
	// right
	right := Fraction{3, 7}
	// left
	left := Fraction{1, 7}
	fmt.Printf("left = %v, right = %v\n", left, right)
	for d := 1; d <= 1000000; d++ {
		// left = a/b
		// a/b < x/d
		// a*d/b*d < x*b/ b*d
		// a*d < x*b
		// a*d/b < x
		j := left.num * d / left.den
		//		fmt.Printf("j = %d\n", j)
		// right := a/b
		// a/b > x/d
		// a*d/b*d > x*b/ b*d
		// a*d > x*b
		// a*d/b > x
		k := right.num * d / right.den
		//		fmt.Printf("k = %d\n", k)

		for i := j; i <= k; i++ {
			//		for i := 1; i <= d; i++ {
			if gcd(i, d) != 1 {
				continue
			}
			f := Fraction{i, d}
			//	fmt.Printf("f = %v\n", f)
			if cmp(f, right) >= 0 {
				break
			}
			if cmp(f, left) == 1 {
				left = f
				//		fmt.Printf("update left = %v\n", f)
			}

		}
	}
	fmt.Printf("left = %v, right = %v\n", left, right)
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
