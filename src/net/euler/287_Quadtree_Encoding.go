package main

import "fmt"

/*

2^N * 2^N 正方形

0, 表示split，分成4个2^(N-1) * 2^(N-1)子正方形, 然后按照top-left,top-right, bottom-left, bottom-right的顺序进行编码
10, 表示正方形全黑
11， 表示正方形全白




(0,0)在bottom-left

y=2^N-1
y=2^N-2
...
y=2
y=1
y=0       x=0   x=1   x=2    x=3 ...   x=2^N-2  x=2^N-1


一个方块的颜色规则有坐标点x,y决定，
满足 (x-2^(N-1))^2 + (y-2^(N-1))^2 <= 2^(2N-2), 颜色为黑，否则为白。

观察这个不等式，是一个圆的方程。即圆的内部是黑色，圆的外部是白色，注意边界可能是锯齿形。
圆心：(2^(N-1), 2^(N-1)), 半径：2^(N-1)


*/

func main() {
	size := D(0, 0, 1<<N-1, 1<<N-1)
	fmt.Printf("size = %d\n", size)
}

var N = 24
var a = 1 << (N - 1)
var r = 1 << (N - 1)

func D(x1, y1, x2, y2 int) int {
	if x2 == x1 {
		// 10, or 11, 可以直接描述了
		return 2
	}
	// 计算整片是否同色，如果同色，那么返回10, 否则返回11，长度也是2
	// 4个顶点的颜色
	if isBlack(x1, y1, x2, y2) || isWhite(x1, y1, x2, y2) {
		// 10, 长度是2
		return 2
	}
	mx := (x1 + x2) / 2
	my := (y1 + y2) / 2
	// 如果不是同色，那么先用0，表示split
	// 然后四个区域进行递归
	//                   x2,y2
	//
	//          mx,my
	//
	// x1,y1
	return 1 + D(x1, my+1, mx, y2) + D(mx+1, my+1, x2, y2) + D(x1, y1, mx, my) + D(mx+1, y1, x2, my)
}

func isBlack(x1, y1, x2, y2 int) bool {
	// 四个顶点,
	return isInCircle(x1, y1) && isInCircle(x2, y1) && isInCircle(x1, y2) && isInCircle(x2, y2)
}

func isInCircle(x, y int) bool {
	return (x-a)*(x-a)+(y-a)*(y-a) <= r*r
}

func isWhite(x1, y1, x2, y2 int) bool {
	// xnear
	xnear := near(x1, x2)
	ynear := near(y1, y2)
	return !isInCircle(xnear, ynear)
}

func near(x1, x2 int) int {
	if x2 < a {
		return x2
	}
	if x1 > a {
		return x1
	}
	return a
}
