package main

import "fmt"

func main() {
	M := 2
	cnt := 0
	for x1 := 0; x1 <= M; x1++ {
		for y1 := 0; y1 <= M; y1++ {
			if x1 == 0 && y1 == 0 {
				continue
			}
			for x2 := 0; x2 <= M; x2++ {
				for y2 := 0; y2 <= M; y2++ {
					if x2 == 0 && y2 == 0 {
						continue
					}
					if x1 == x2 && y1 == y2 {
						continue
					}
					if check(x1, y1, x2, y2, 0, 0) {
						cnt++
						fmt.Printf("(%d, %d), (%d, %d)\n", x1, y1, x2, y2)
					}
				}
			}
		}
	}
	fmt.Printf("total = %d\n", cnt/2)
}

// 判断两条直线是否垂直，点积=0
func check(x1, y1, x2, y2, x3, y3 int) bool {
	//	fmt.Printf("(%d, %d), (%d, %d), (%d, %d)\n", x1, y1, x2, y2, x3, y3)
	// 三个向量: (x2-x1, y2-y1), (x3-x1, y3-y1), (x3-x2, y3-y2)
	v1 := newVector(x2, y2, x1, y1)
	v2 := newVector(x3, y3, x1, y1)
	v3 := newVector(x3, y3, x2, y2)
	if isRight(v1, v2) || isRight(v1, v3) || isRight(v2, v3) {
		return true
	}
	return false
}

type vector struct {
	x, y int
}

func newVector(x1, y1, x2, y2 int) vector {
	return vector{x: x1 - x2, y: y1 - y2}
}

func isRight(v1, v2 vector) bool {
	return v1.x*v2.x+v1.y*v2.y == 0
}
