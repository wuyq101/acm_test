package main

import (
	"fmt"
	"strconv"
)

/*
 * https://projecteuler.net/problem=65
 * e = [2;1,2,1,1,4,1,1,6,1,1,8,1,1,10,1,1,12,1,1,,,1,2k,1,,,]
 */
func main() {
	a := []int{}
	for i := 1; i <= 33; i++ {
		a = append(a, 1)
		a = append(a, i*2)
		a = append(a, 1)
	}
	fmt.Printf("len = %d\n", len(a))
	fmt.Printf("a=%v\n", a)

	for k := 0; k < 99; k++ {
		//	fmt.Printf("----------k=%d\n", k)
		f := Fraction{Numerator: "0", Denominator: "1"}
		for i := k; i >= 0; i-- {
			//		fmt.Printf("%d %v, a=%d\n", i, f, a[i])
			d := strconv.Itoa(a[i])
			fd := Fraction{Numerator: d, Denominator: "1"}
			tmp := FAdd(fd, f)
			//		fmt.Printf("%v = %v + %v\n", tmp, fd, f)
			f = FInv(tmp)
		}
		f2 := Fraction{Numerator: "2", Denominator: "1"}
		f = FAdd(f, f2)
		ds := DigitSum(f.Numerator)
		fmt.Printf("%d: %d  -- %v\n", k, ds, f)
	}

	println("-------------")
	e = []int{2}
	for i := 1; i <= 33; i++ {
		e = append(e, 1)
		e = append(e, i*2)
		e = append(e, 1)
	}
	for i := 0; i < 100; i++ {
		fmt.Printf("%d = %v\n", i, x(i))
	}
}

var e []int

func x(n int) Fraction {
	return Fraction{Numerator: p(n), Denominator: q(n)}
}

var ps = map[int]string{}
var qs = map[int]string{}

func p(n int) string {
	if n == 0 {
		return "2"
	}
	if n == 1 {
		return "3"
	}
	v, ok := ps[n]
	if ok {
		return v
	}
	an := strconv.Itoa(e[n])
	v = Add(Mul(p(n-1), an), p(n-2))
	ps[n] = v
	return v
}

func q(n int) string {
	if n == 0 {
		return "1"
	}
	if n == 1 {
		return "1"
	}
	v, ok := qs[n]
	if ok {
		return v
	}
	an := strconv.Itoa(e[n])
	v = Add(Mul(q(n-1), an), q(n-2))
	qs[n] = v
	return v
}
