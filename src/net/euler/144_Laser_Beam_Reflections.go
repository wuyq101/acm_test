package main

import "fmt"

/*
http://projecteuler.net/problem=144

椭圆

4*x^2 + y^2 = 100

激光出发点：(0.0, 10.1)

第一个反射点:(x0,y0) = (1.4， -9.6), 所以光的入射方向v0 = (1.4, -19.7)


求下一个反射点, 假设光的方向是v = (vx, vy), 当前点是(x0,y0)

直线参数方程：
x(t) = x0 + vx*t
y(t) = y0 + vy*t

带入椭圆4*x^2 + y^2 = 100, 得到t的一元二次方程

4*(x0+vx*t)^2 + (y0+vy*t)^2 = 100
4*x0^2 + 8*x0*vx*t + 4*vx^2*t^2 + y0^2 + 2*y0*vy*t + vy^2*t^2 = 100
因为(x0,y0)在椭圆上，所以 4*x0^2 + y0^2 = 100, 带入可得

(4*vx^2+vy^2)*t^2 + (8*x0*vx + 2*y0*vy)*t = 0

t = 0 or t = -(8*x0*vx + 2*y0*vy) / (4*vx^2+vy^2)
t=0对应就是当前点，t=-(8*x0*vx + 2*y0*vy) / (4*vx^2+vy^2)对应就是下一个反射点

所以下一个反射点(x1,y1) = (x0+vx*t, y0+vy*t)

x1 = x0 - vx * (8*x0*vx + 2*y0*vy) / (4*vx^2+vy^2)
y1 = y0 - vy * (8*x0*vx + 2*y0*vy) / (4*vx^2+vy^2)


梯度 就是一个向量，指向函数值增加最快的方向，计算方法是分别对 x 和 y 求偏导
n = (8x, 2y), 我们只要方向，不关系长度, 所以记做(4x,y)
n 垂直于切线，并且指向椭圆外部的一个向量

v' = v - 2 * (v⋅n/n⋅n) * n

v - 2*(v在n方向上的投影）得到新的方向

第一次反射点(1.4, -9.6), 所以法向量n = (4*x,y) = (5.6,-9.6)

v0 = (1.4, -19.7)
计算点积 = v⋅n = 1.4*5.6 + (-19.7)*(-9.6) = 196.96
n⋅n = 5.6*5.6 + (-9.6)*(-9.6) = 123.52
比例系数 = 2 * 196.96 / 123.52 = 3.188

v' = (1.4, -19.7) - 3.188 * (5.6, -9.6) = (-16.45, 10.90)


*/

func main() {
	v := vector{1.4, -19.7}
	p := point{1.4, -9.6}
	cnt := 0
	for {
		n := vector{4 * p.x, p.y}
		nv := nextV(n, v)
		fmt.Printf("p=%v, v=%v\n", p, v)
		np := nextP(p, nv)
		fmt.Printf("np=%v\n", np)
		p, v = np, nv
		fmt.Printf("cnt=%d, p=%v, v=%v\n", cnt, p, v)
		if p.x >= -0.01 && p.x <= 0.01 && p.y > 9.6 {
			break
		}
		cnt++
	}
	// 包含第一个反射点
	fmt.Printf("total cnt=%d\n", cnt+1)
}

func nextV(n, v vector) vector {
	vn := v.x*n.x + v.y*n.y
	nn := n.x*n.x + n.y*n.y
	f := 2 * vn / nn
	return vector{v.x - f*n.x, v.y - f*n.y}
}

func nextP(p point, v vector) point {
	a := 8*p.x*v.x + 2*p.y*v.y
	b := 4*v.x*v.x + v.y*v.y
	x := p.x - v.x*a/b
	y := p.y - v.y*a/b
	return point{x, y}
}

type vector struct {
	x float64
	y float64
}

type point struct {
	x float64
	y float64
}
