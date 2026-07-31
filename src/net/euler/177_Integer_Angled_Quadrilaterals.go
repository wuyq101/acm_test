package main

import (
	"fmt"
	"math"
)

/*
在四边形ABCD中，假设对角线AC长度=1
<BAC = a
<CAD = b
<ACB = c
<ACD = d

a+c<180  ---> 点B存在
b+d<180  ---> 点D存在
a+b<180  ---> 三角形约束
c+d<180  ---> 三角形约束
在∆ABC中，根据正弦定理，有

AB/sin(c) = AC/sin(180-a-c) = AC/sin(a+c) = 1/sin(a+c)
AB = sin(c)/sin(a+c)

同理可得
AD = sin(d)/sin(b+d)

在∆ABD中，已知AB，AD的长度，以及<BAD = a+b, 过D向AB做垂线，可以求得<ABD的正切值

tg(<ABD) = AD*sin(a+b)/(AB-AD*cos(a+b)) , 求出<ABD的角度 = j




*/

func main() {
	total := 0
	cs := make([]float64, 180) // cos
	sn := make([]float64, 180) // sin
	for i := 1; i < 180; i++ {
		r := float64(i) * math.Pi / 180.0
		cs[i] = math.Cos(r)
		sn[i] = math.Sin(r)
	}
	m := make(map[uint64]bool)
	for a := 1; a <= 45; a++ {
		for b := a; b < 180; b++ {
			if a+b >= 180 {
				break
			}
			for c := a; c < 180; c++ {
				// a+c<180
				if a+c >= 180 {
					break
				}
				AB := sn[c] / sn[a+c]
				for d := a; d < 180; d++ {
					if c+d >= 180 || b+d >= 180 {
						break
					}
					AD := sn[d] / sn[b+d]
					// tg(<BAD) = AD*sin(a+b)/(AB-AD*cos(a+b)) = X/Y  = sn[i]/cs[i]
					X := AD * sn[a+b]
					Y := AB - AD*cs[a+b]
					// X/Y = sn[i]/cs[i] ---> X*cs[i] - Y*sn[i] = 0
					for i := 1; i < 180; i++ {
						if math.Abs(X*cs[i]-Y*sn[i]) >= 1e-9 {
							continue
						}
						// 检查所有的角度
						// <ABD = i ok
						// <ADB = 180-a-b-i
						j := 180 - a - b - i
						// <ACB = 180-a-c-i
						k := 180 - a - c - i
						// <BDC = a+i-d
						l := a + i - d
						if j > 0 && k > 0 && l > 0 {
							// 顺时针 a,b,j,l,d,c,k,i
							v := key([]int{a, b, j, l, d, c, k, i})
							if !m[v] {
								fmt.Printf("a=%d,b=%d,c=%d,d=%d,i=%d,j=%d,k=%d,l=%d\n", a, b, c, d, i, j, k, l)
								total++
								m[v] = true
							}
						}
					}

				}
			}
		}
	}
	fmt.Printf("total = %d\n", total)
}

// 选择8次，计算180进制数，取最小一个，哈希同构，判断不同的序列，但是可能实际上是同一个形状
func key(nums []int) uint64 {
	L := len(nums)
	v := uint64(math.MaxUint64)
	loop := func(u []int) {
		for k := 0; k < L; k += 2 {
			// 从位置k开始计算
			f := uint64(0)
			for i := 0; i < L; i++ {
				idx := (k + i) % L
				f = f*180 + uint64(u[idx])
			}
			if f < v {
				v = f
			}
		}
	}
	//  镜像翻转
	rev := make([]int, L)
	for i := 0; i < L; i++ {
		rev[i] = nums[L-i-1]
	}
	loop(nums)
	loop(rev)
	return v
}
