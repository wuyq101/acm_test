package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	buf, err := os.ReadFile("105_sets.txt")
	if err != nil {
		panic(err)
	}
	s := 0
	lines := strings.Split(string(buf), "\n")
	for _, line := range lines {
		if len(line) == 0 {
			continue
		}
		vals := strings.Split(line, ",")
		list := make([]int, 0)
		for _, val := range vals {
			v, err := strconv.Atoi(val)
			if err != nil {
				panic(err)
			}
			list = append(list, v)
		}
		if check(list) {
			fmt.Printf("%v\n", list)
			s += sum(list)
		}
	}
	fmt.Printf("s=%d\n", s)
}

func sum(list []int) int {
	s := 0
	for _, v := range list {
		s += v
	}
	return s
}

func check(list []int) bool {
	for k := 2; k < len(list); k++ {
		sa := 0
		for i := 0; i < k; i++ {
			sa += list[i]
		}
		sb := 0
		for i := len(list) - k + 1; i < len(list); i++ {
			sb += list[i]
		}
		if sa <= sb {
			return false
		}
	}
	// 1. S(B) != S(C)
	// 2. if B contains more elements than C, then S(B) > S(C)
	// N = 6
	// 000001
	// ...
	// 111111
	M := 1 << len(list)
	for b := 1; b < M; b++ {
		for c := 1; c < M; c++ {
			if b&c != 0 {
				continue
			}
			SB := subsum(list, b)
			SC := subsum(list, c)
			if SB == SC {
				return false
			}
			bitB := bits(b)
			bitC := bits(c)
			if bitB > bitC && SB < SC {
				return false
			}
			if bitB < bitC && SB > SC {
				return false
			}
		}
	}
	return true
}
func bits(n int) int {
	count := 0
	for n > 0 {
		n &= n - 1
		count++
	}
	return count
}

func subsum(list []int, mask int) int {
	sum := 0
	for i := 0; i < len(list); i++ {
		if mask&(1<<i) != 0 {
			sum += list[i]
		}
	}
	return sum
}
