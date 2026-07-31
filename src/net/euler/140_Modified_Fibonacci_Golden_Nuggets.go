package main

import (
	"fmt"
	"sort"
)

/*
http://projecteuler.net/problem=140
AG(x) = xG1 + x^2G2 + x^3G3 + ... + x^kGk + ...
Gk = Gk-1 + Gk-2
G1 = 1 G2 = 4

1式: AG(x) = xG1 + x^2G2 + x^3G3 + ... + x^kGk + ...
2式 = 1式*x
          xAG(x) = x^2G1 + x^3G2 + x^4G3 + ... + x^kGk + ..

	  (1-x)AG(x) = xG1 +(4-1)x^2 + x^3G1 + x^4G2 + ...
	             = x + 3x^2 + x^2AG(x)

        AG(x) = (3x^2+x)/(1-x-x^2)

	AG(x)是自然数，x是有理数，假设AG(x) = n

	n-nx-nx^2 = 3x^2+x
	(n+3)x^2 +(n+1)x - n = 0
	方程有有理数解，所以判别式delta必须是一个平方数
	delta = (n+1)^2 + 4(n+3)*n =  n^2 + 2n + 1 + 4n^2 + 12n
	      = 5n^2 + 14n + 1
	设delta = m^2
	5n^2 + 14n +1 = m^2
	25n^2 + 70n + 5 = 5m^2
	(5n + 7)^2 + 5 - 49 = 5m^2
	(5n+7)^2 - 5m^2 = 44
	设x = 5n+7, y = m

	广义佩尔方程
	x^2 - 5y^2 = 44

	对应的标准佩尔方程
	x^2 - 5y^2 = 1
	基础解： x=9, y=4
	所以基本单位是: 9+4√5
	推推公式
	(u+v√5)*(9+4√5) = 9u+20v + (4u+9v)√5
	u' = 9u+20v
	v' = 4u+9v

	枚举广义佩尔方程的基础解
	x=7, y=1 ---> (9*7+20*1 + 4*7+9*1)=(83, 37)
	x=8, y=2 ---> (9*8+20*2 + 4*8+9*2)=(112, 50)
	     y=3
	     y=4
	x=13,y=5 ---> (9*13+20*5 + 4*13+9*5)=(217, 97)
	     y=6
	x=17,y=7  -->对应n=2,
	     y=8
	     y=9
	     y=10
	     y=11
	     y=12
	     y=13
	x=32 y=14  --->对应n=5, 下一组(9*32+20*14 + 4*32+9*14)=(568, 254)
	     y=15
	     y=16
	     y=17
	     y=18
	x=43 y=19
	     y=20
	     y=21
	     y=22
	     y=23
	     y=24
	     y=25
	     y=26
	     y=27
	     y=28
	     y=29
	     y=30
	     y=31
	     y=32
	     y=33
	     y=34
	     y=35
	     y=36
        x=83 y=37 (发现重复）
	所以一共有6组基本解(7,1) (8,2) (13,5) (17,7) (32,14) (43,19)


	     n = (x-7)/5
*/

func main() {
	list := make([]Unit, 0)
	list = append(list, Unit{7, 1})
	list = append(list, Unit{8, 2})
	list = append(list, Unit{13, 5})
	list = append(list, Unit{17, 7})
	list = append(list, Unit{32, 14})
	list = append(list, Unit{43, 19})
	cnt := 0
	sum := int64(0)
	for {
		p := list[0]
		list = list[1:]
		u, v := p.u, p.v
		if u > 7 && (u-7)%5 == 0 {
			cnt++
			n := (u - 7) / 5
			fmt.Printf("find n = %d\n", n)
			sum += n
			if cnt == 30 {
				break
			}
		}
		u, v = 9*u+20*v, 4*u+9*v
		list = append(list, Unit{u, v})
		sort.Slice(list, func(i, j int) bool {
			return list[i].u < list[j].u
		})
	}
	fmt.Printf("sum = %d\n", sum)
}

type Unit struct {
	u int64
	v int64
}
