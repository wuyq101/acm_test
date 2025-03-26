package main

import "fmt"

func main() {
	a := Fraction{Numerator: "2", Denominator: "1"}
	b := Fraction{Numerator: "12", Denominator: "29"}
	c := FAdd(a, b)
	fmt.Println(c)
	println(MOD("1000009999", "34"))
	cnt := 0
	for i := 1; i <= 1000; i++ {
		f := Sqrt(i)
		if len(f.Numerator) > len(f.Denominator) {
			fmt.Printf("sqrt(2)[%d] = %s\n", i, f)
			cnt++
		}
	}
	println(cnt)
}

func Sqrt(n int) Fraction {
	v1 := Fraction{Numerator: "1", Denominator: "1"}
	return FAdd(v1, f(n))
}

var m = map[int]Fraction{}

// n 迭代次数
func f(n int) Fraction {
	if n == 1 {
		return Fraction{Numerator: "1", Denominator: "2"}
	}
	v, ok := m[n]
	if ok {
		return v
	}
	f := f(n - 1)
	v2 := Fraction{Numerator: "2", Denominator: "1"}
	f = FInv(FAdd(v2, f))
	m[n] = f
	return f
}
