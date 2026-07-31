package main

import (
	"fmt"
	"math"
)

/*

https://projecteuler.net/problem=285

round(√((k*a+1)^2+(k*b+1)^2)) == k

令 x = k*a+1
   y = k*b+1

 k-0.5 < √(x^2+y^2) < k+0.5

 此时x,y处在第一象限的一个1/4圆环内，R_in = k-0.5, R_out = k+0.5

 又，a,b均匀分布在[0,1]上，所以 x,y 均匀分布在[1,k+1]上，

 所以 (x,y)处在一个正方形中，面积是k^2

 概率Pk = 圆环和正方形的交集/正方形的面积

 A(R), 半径为R的1/4圆和正方形的交集面积， R>=√2

 正方形点 A(1,1), 原点(0,0),
 和 x=1交与点B, B(1,√(R^2-1)), 和 直线y=1交与点C, C(√(R^2-1),1)

 求OC和x轴的夹角theta, sin(theta) = 1/R, theta = arcsin(1/R)
 角BOC = π/2 - 2*theta = π/2 - 2*arcsin(1/R)

 扇形OBC的面积 = π*R^2 * theta/2π = R^2/2 * (π/2 - 2*arcsin(1/R))


 四边形OBAC的面积，连接OA，四边形的面积 = 2*三角形OAC的面积
  = 2 * 1/2 * AC * 1 = √(R^2-1) - 1


  A(R) = R^2/2 * (π/2 - 2*arcsin(1/R)) - √(R^2-1) + 1

  Pk = (A(k+0.5) - A(k-0.5)) / k^2

  Ek = Pk * k = (A(k+0.5) - A(k-0.5)) / k
*/

func main() {
	sum := 0.0
	N := 10
	for k := 1.0; k <= float64(N); k++ {
		sum += E(k)
	}
	fmt.Printf("sum=%.5f\n", sum)

	sum = 0.0
	N = 100000
	for k := 1.0; k <= float64(N); k++ {
		sum += E(k)
	}
	fmt.Printf("sum=%.5f\n", sum)
}

func E(k float64) float64 {
	return (A(k+0.5) - A(k-0.5)) / k
}

// R=0.5,1.5.....
func A(R float64) float64 {
	if R < 1 {
		return 0
	}
	theta := math.Asin(1.0 / R)
	v := (math.Pi/2 - 2*theta) * R * R / 2
	v -= math.Sqrt(R*R - 1)
	v += 1
	return v
}
