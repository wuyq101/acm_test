package main

import "fmt"

func main() {
	P := 1
	used := make([]bool, 64)
	for i := 0; i < 64; i++ {
		if used[i] {
			continue
		}
		println(i, next(i))
		list := make([]int, 0)
		list = append(list, i)
		used[i] = true
		cur := i
		for {
			v := next(cur)
			if used[v] {
				break
			}
			list = append(list, v)
			used[v] = true
			cur = v
		}
		fmt.Printf("ring list = %v\n", list)
		P *= L(len(list))
	}
	fmt.Printf("P = %d\n", P)

}

func next(n int) int {
	// n = a,b,c,d,e,f
	// m = b,c,d,e,f,a xor (b and c)
	a := (n >> 5) & 1
	b := (n >> 4) & 1
	c := (n >> 3) & 1
	return (n<<1)&0x3F | (a ^ (b & c))
}

var lucas = map[int]int{
	1: 1,
	2: 3,
}

func L(n int) int {
	v, ok := lucas[n]
	if ok {
		return v
	}
	a := L(n - 1)
	b := L(n - 2)
	v = a + b
	lucas[n] = v
	return v
}
