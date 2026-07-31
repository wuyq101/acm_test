package main

import (
	"fmt"
	"sort"
)

/*
根据对称性，所有的上半平面中的点以圆心为对称点，在下半平面，都有对称点。
因此我们只要计算上半平面的情况即可，即y>0的情况。
*/

func main() {
	I := 105
	lines := make(map[Line]int64)
	for x := -I; x <= I; x++ {
		for y := 0; y <= I; y++ {
			if y == 0 && x <= 0 {
				continue
			}
			if x*x+y*y < I*I {
				g := 0
				if x < 0 {
					g = gcd(-x, y)
				} else {
					g = gcd(x, y)
				}
				lines[Line{x / g, y / g}]++
			}
		}
	}
	// 按照与（0，1）的夹角从小到大排序，逆时针方向
	list := make([]Line, 0, len(lines))
	for l := range lines {
		list = append(list, l)
	}
	sort.Slice(list, func(i, j int) bool {
		// 向量叉乘
		return list[i].x*list[j].y-list[i].y*list[j].x > 0
	})
	//fmt.Printf("size = %d\n", len(list))
	//fmt.Printf("list=%v\n", list)
	//fmt.Printf("lines=%v\n", lines)
	pre := make([]int64, len(list))
	pre[0] = lines[list[0]]
	for i := 1; i < len(list); i++ {
		pre[i] = pre[i-1] + lines[list[i]]
	}
	//fmt.Printf("pre=%v\n", pre)
	total := int64(0)
	for i := 0; i < len(list); i++ {
		for j := i + 1; j < len(list); j++ {
			//fmt.Printf("i=%d,j=%d\n", i, j)
			a := lines[list[i]] * lines[list[j]]
			// 从射线i，j，k上，各取一个点，可以组成一个合法的三角形
			b := int64(0)
			if j-1 >= i+1 {
				b = pre[j-1] - pre[i]
			}
			total += a * b
		}
	}
	total *= 2
	fmt.Printf("total = %d\n", total)
}

type Line struct {
	x, y int
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
