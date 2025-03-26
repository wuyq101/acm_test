package main

import (
	"fmt"
)

/**
 * @see https://projecteuler.net/problem=61
 */
func main() {

	p3 := genPolygonal(func(i int) int { return i * (i + 1) / 2 })
	fmt.Printf("%d\np3=%v\n\n", len(p3), p3)

	p4 := genPolygonal(func(i int) int { return i * i })
	fmt.Printf("%d\np4=%v\n\n", len(p4), p4)

	p5 := genPolygonal(func(i int) int { return i * (3*i - 1) / 2 })
	fmt.Printf("%d\np5=%v\n\n", len(p5), p5)

	p6 := genPolygonal(func(i int) int { return i * (2*i - 1) })
	fmt.Printf("%d\np6=%v\n\n", len(p6), p6)

	p7 := genPolygonal(func(i int) int { return i * (5*i - 3) / 2 })
	fmt.Printf("%d\np7=%v\n\n", len(p7), p7)

	p8 := genPolygonal(func(i int) int { return i * (3*i - 2) })
	fmt.Printf("%d\np8=%v\n\n", len(p8), p8)

	m[1] = p3
	m[2] = p4
	m[3] = p5
	m[4] = p6
	m[5] = p7
	m[6] = p8

	// 6中多边形的排序, 一共有5! = 120种
	// 针对每种顺序，尝试找到一个解
	p("", "12345")
}

func find(s string) int {
	s = s + "6"
	// 根据第p个排列, 找到对应的符合条件的数字串
	sets := make([][]int, 0)
	for i := 0; i < len(s); i++ {
		list := m[int(s[i]-'0')]
		if i == 0 {
			for _, v := range list {
				sets = append(sets, []int{v})
			}
		} else {
			tmp := make([][]int, 0)
			for _, set := range sets {
				for _, v := range list {
					if set[len(set)-1]%100 == v/100 {
						tmp = append(tmp, append(set, v))
					}
				}
			}
			sets = tmp
		}
	}

	// 检查首尾是否满足
	tmp := make([][]int, 0)
	for _, set := range sets {
		if set[0]/100 == set[len(set)-1]%100 {
			tmp = append(tmp, set)
			fmt.Printf("find a solution %v\n", set)
			sum := 0
			for _, v := range set {
				sum += v
			}
			fmt.Printf("sum = %d\n", sum)
		}
	}
	sets = tmp

	fmt.Printf("%d, %v\n", len(sets), sets)
	return 0
}

var m = map[int][]int{}

func p(s string, b string) {
	if len(s) == 5 {
		fmt.Printf("%s\n", s)
		find(s)
		return
	}
	// 从b当中取一个，加入到s当中
	for i, c := range b {
		ss := s + string(c)
		bb := b[:i] + b[i+1:]
		p(ss, bb)
	}
}

func genPolygonal(fn func(int) int) []int {
	list := make([]int, 0)
	for i := 1; i < 1000; i++ {
		v := fn(i)
		if v >= 1000 && v <= 9999 {
			list = append(list, v)
		}
		if v > 9999 {
			break
		}
	}
	return list
}
