package main

import (
	"fmt"
	"strconv"
)

/**
 * https://projecteuler.net/problem=56
 */
func main() {
	max := 0
	for a := 1; a < 100; a++ {
		sa := strconv.Itoa(a)
		for b := 1; b < 100; b++ {
			s := Pow(sa, b)
			ds := DigitSum(s)
			if max < ds {
				max = ds
				fmt.Println(a, b, ds, s)
			}
		}
	}
	fmt.Printf("max = %d\n", max)
}

func DigitSum(s string) int {
	sum := 0
	for _, c := range s {
		sum += int(c - '0')
	}
	return sum
}
