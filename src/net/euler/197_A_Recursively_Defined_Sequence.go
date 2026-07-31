package main

/*
迭代几项之后，竖列会进入一个稳态的周期为2的极限环
*/

import (
	"fmt"
	"math"
)

func main() {
	x := -1.0
	sum := 0.0
	for i := 0; i < 1000; i++ {
		x = f(x)
		fmt.Printf("%d %.10f\n", i, x)
		if i == 998 || i == 999 {
			sum += x
		}
	}
	fmt.Printf("sum=%.9f\n", sum)

}

func f(x float64) float64 {
	e := 30.403243784 - x*x
	return math.Floor(math.Pow(2, e)) * 1e-9
}
