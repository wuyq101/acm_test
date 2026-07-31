package main

/*

https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
a*x + b*y = g




*/

// 扩展欧几里得,返回g,x,y
// a*x + b*y = g
func ExGCD(a, b int) (int, int, int) {
	// 最后一步的时候
	// a*1 + 0*0 = g (g=a)
	if b == 0 {
		return a, 1, 0
	}
	g, x, y := ExGCD(b, a%b)
	// 上一层返回的内容
	// b*x + (a%b)*y = g
	// a%b = a - (a/b)*b
	// 要将这些参数整理乘 a*X + b*Y = g的形式
	// 将余数用a,b表示，代入，得到
	// b*x + (a - (a/b)*b)*y = g
	// b*x + a*y - (a/b)*b*y = g
	// a*y + b*x - (a/b)*b*y = g
	// a*y + b*(x-(a/b)*y) = g
	// a*X + b*Y = g
	// X = y, Y = x - (a/b)*y
	return g, y, x - (a/b)*y
}
