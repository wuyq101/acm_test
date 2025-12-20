package main

import "fmt"

/*
https://projecteuler.net/problem=135
等差数列 x y z, x > y > z, x^2 - y^2 - z^2 = n, n>0
设等差数列公差d
x = z+2d
y = z+d
x^2 - y^2 - z^2 = (z+2d)^2 - (z+d)^2 - z^2
= z^2+4zd+4d^2 - z^2 - 2zd - d^2 - z^2
= 3d^2 + 2dz - z^2
= (3d-z)(d+z) = n

令 u = 3d-z, v = d+z
u*v = n, （u,v是n的一对因子）
// 用u和v求出z和d
d = (u+v)/4
z = (3v-u)/4
约束条件：
1. u+v能被4整除
2. z>0 ---> 3v-u>0 ---> 3v>u
*/
func main() {
	MAX := 50000000
	m := make([]int, MAX+1)
	for v := 1; v <= MAX; v++ {
		for u := 1; u < 3*v; u++ {
			p := u * v
			if p > MAX {
				break
			}
			if (u+v)%4 == 0 {
				m[p]++
				/*
					d := (u + v) / 4
					z := (3*v - u) / 4
					x := z + 2*d
					y := z + d
					fmt.Printf("x=%d, y=%d, z=%d  n=%d\n", x, y, z, p)
				*/
			}
		}
	}
	cnt := 0
	for i := 1; i <= MAX; i++ {
		if m[i] == 1 {
			cnt++
			if i < 100 {
				fmt.Println(i)
			}
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}
