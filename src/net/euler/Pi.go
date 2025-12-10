package main

import (
	"fmt"
)

func main() {
	/*
		N := 5000000000
		cnt := int(0)
		for i := 0; i < N; i++ {
			x := rand.Float64()
			y := rand.Float64()
			d := math.Sqrt(x*x + y*y)
			if d <= 1.0 {
				cnt++
			}
		}
		pi := 4.0 * float64(cnt) / float64(N)
		fmt.Printf("pi = %f\n", pi)
	*/
	f()
}

func f() {
	N := 1000000000000
	// ^_^
	pi := float64(0)
	for i := 0; i < N; i++ {
		pi += 1.0 / (float64(i+1) * float64(i+1))
	}
	fmt.Printf("pi = %f\n", pi*2)
}
