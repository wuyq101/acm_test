package main

import (
	"fmt"
	"sort"
)

func main() {
	//	rad := make([]int, 0)
	E := make([]int, 0, 10000)
	E = append(E, 1)
	for i := 2; i <= 100000; i++ {
		//  分解i，得到所有的因子和次数，必须是只有1次的，才可以
		f, c := factors(i)
		//		fmt.Printf("%d %v %v\n", i, f, c)
		if isRad(c) {
			//fmt.Printf("%d %v %v\n", i, f, c)
			// 用f中的因子，从小到大，生成所有小于100000的数
			list := gen(i, f)
			E = append(E, list...)
			fmt.Printf("%d len = %d\n", i, len(E))
			if len(E) >= 10000 {
				fmt.Printf("%v\n", E[9999])
				return
			}
		}
	}
}

func gen(i int, f []int) []int {
	// [6]
	// [2,3]
	list := []int{i}
	m := make(map[int]bool)
	m[i] = true
	for len(list) > 0 {
		v := list[0]
		list = list[1:]
		for _, p := range f {
			k := v * p
			if k > 100000 {
				break
			}
			if !m[k] {
				list = append(list, k)
				m[k] = true
			}
		}
	}
	list = list[0:0]
	for k := range m {
		list = append(list, k)
	}
	sort.Ints(list)
	return list
}

func isRad(cnt []int) bool {
	for _, c := range cnt {
		if c != 1 {
			return false
		}
	}
	return true
}

func factors(n int) ([]int, []int) {
	f := make([]int, 0)
	c := make([]int, 0)
	for i := 2; i <= n; i++ {
		if n%i == 0 {
			f = append(f, i)
			cnt := 0
			for n%i == 0 {
				cnt++
				n /= i
			}
			c = append(c, cnt)
		}
	}
	return f, c
}
