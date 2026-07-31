package main

import "fmt"

/*
158 Lexicographical Neighbours
https://projecteuler.net/problem=158

一个排列的逆序数，可以由Fn生成函数得到

Fn = 1 * (1+q) * (1+q+q^2) * (1+q+q^2+q^3) * ... * (1+q+q^2+...+q^(n-1))

展开之后 q^i前面的系数，就是逆序数为i的排列的个数
假设我们已经由了一个n-1的排列，现在我们要把第n个数据加进来，假设n是最大的，
如果n查到最右边，增加0个逆序数对 q^0
如果把 n 往左移动一位，它就会压过原本最后那一个数，增加 1 个逆序对，贡献 q^1
如果把 n 往左移动两位，就会压过原本最后两个数，增加 2 个逆序对, 贡献 q^2
...
如果n放到最左边，会增加n-1个逆序数，贡献 q^(n-1)
所以第n个数，在原理n-1的某个排列下，都会贡献 (1+q+q^2+...+q^(n-1))个逆序数

那一个长度为n的序列，按照从小到大，从短到长的顺序，
每一步对应的多项式相乘就得到Fn, 然后Fn展开的每个系数都对应逆序数为i的排列的个数

Fn中考察逆序数=1的系数，从每个括号中挑选包含q，其他括号只能选择1，然后相加，得到Fn中q^1的系数 = n-1

然后从26个字母中挑选不同的n个字母，C(26,n)

假设选出的 n 个字母排序后为 x1 < x2 < ... < xn
题目要求：整个字符串除了一个地方是升序，其余全是降序。
s1 > s2 > ... > sk < sk+1 > sk+2 > ... > sn
挑选k个字母放到左边，降序排列
剩余的字母放到右边，也是降序排列, 然后必须保证两段连接处 sk < sk+1
除了纯降序的不行，其他的情况下，任意k个选择，总是会由一个sk < sk+1
所以总的情况就是 C(n,k) - 1
k的范围是 1 --- n-1
(1+1)^2 = ∑C(n,k)  k从0到n，

∑[C(n,k)-1],  k从1到n-1
= 2^n - (第0项 + 第n项) - (n-1)
= 2^n -2 - (n-1) = 2^n-n-1
*/

func main() {
	result := 0
	for n := 1; n <= 26; n++ {
		v := C(26, n) * (pow(2, n) - n - 1)
		fmt.Printf("n=%d, v=%d\n", n, v)
		result = max(result, v)
	}
	fmt.Printf("result = %d\n", result)
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func C(n, m int) int {
	if m == 0 {
		return 1
	}
	return C(n-1, m-1) * n / m
}

func pow(n, p int) int {
	if p == 0 {
		return 1
	}
	if p%2 == 0 {
		v := pow(n, p/2)
		return v * v
	}
	return n * pow(n, p-1)
}
