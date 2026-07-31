package main

import (
	"fmt"
	"math"
)

/*

https://projecteuler.net/problem=210

B(x,y), C(r/4, r/4), O(0,0)

|x| + |y| <= r

case 1: 在点O处是钝角，角BOC是钝角
向量 OB*OC < 0
OB = (x,y), OC = (r/4, r/4)
OB*OC = x*r/4 + y*r/4 < 0 --> r/4 * (x+y) < 0 --> x+y<0
即B点在直线 y=-x的下方, 并且点不在直线 y=x的上
令 u = -(x+y), v = x-y, 坐标变换，做一个45度的坐标变换，
这样可以发现，符合的点刚好是r行，观察可以发现，每行r列，因此总数是 r*r


case 2: 在点C处是钝角
向量 CB*CO < 0
CB = (x-r/4, y-r/4), CO = (-r/4,-r/4)
(x-r/4)*(-r/4) + (y-r/4)*(-r/4) < 0, x+y > r/2
B点在直线x+y=r/2的上方, 用同样的方法可以得到，点数是 r*r/2


case 3: 在点B处是钝角
向量 BO * BC <0
BO = (-x,-y), BC = (r/4-x, r/4-y)
BO * BC = (-x)*(r/4-x) + (-y)*(r/4-y) < 0,
(x-r/8)^2 + (y-r/8)^2 < r^2/32

B点在以OC为直径的圆内，圆心(r/8,r/8), 半径: √2r/8
设 u=x=r/8, v=y-r/8

R^2 = r^2/32
u^2 + v^2 < R^2

u^2 <= R^2-1





*/

func main() {
	N := int64(1e9)
	C1 := N * N
	C2 := C1 / 2
	R2 := C1 / 32
	umax := sqrt(R2 - 1)
	C3 := int64(0)
	for u := int64(0); u <= umax; u++ {
		// u^2 + v^2 < R^2
		vmax := sqrt(R2 - u*u - 1)
		if u == 0 {
			C3 += (2*vmax + 1)
		} else {
			C3 += (4*vmax + 2)
		}
	}
	// 减掉圆内，在直线OC上的点
	C3 -= (N/4 - 1)

	T := C1 + C2 + C3
	fmt.Println(T)
}

func sqrt(x int64) int64 {
	r := int64(math.Sqrt(float64(x)))
	for r*r > x {
		r--
	}
	for (r+1)*(r+1) <= x {
		r++
	}
	return r
}
