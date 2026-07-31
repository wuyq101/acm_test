package main

import (
	"fmt"
	"sort"
)

/*
http://projecteuler.net/problem=152

假设p是一个素数, 将左边分成两部分 1/p^2, 以及其他不包含p的倍数的平方倒数和

.... + 1/p^2 + ... = 1/2
把除1/p^2之外的其他和记为 A/B

A/B + 1/p^2 = (A*p^2 + B) / (B*p^2) = 1/2

因为最后约分之后是1/2， 所以分子 A*p^2 + B = 0 (mod p^2)
---> B = 0 (mod p^2),  但是B是除p^2之外的其他平方倒数和，B中肯定不包含p^2, 因此矛盾。

得到一个结论，如果p是一个素数，那么在结果中，p不能单独出现，必须包含 1/(2p)^2 , 1/(3p)^2, 1/(4p)^2, 等

题目的范围是 2--80之间的数，所以40以上的素数都被排除
40以上的素数: 41, 43, 47, 53, 59, 61, 67, 71, 73, 79


考虑中间范围的素数,比如37
p = 37, 2p = 74
1/p^ + 1/(2p)^2 = 1/p^2 * (1/1^2 + 1/2^2) = (1/p^2) * (5/4)
由于其他的平方倒数和都不包含p^2, 所以这里已经无法约分掉 p^2,  因此 37 也必须被排除掉。

p = 31, 2p = 62 , 同上，也排除

p = 29, 2p = 58 , 同上，也排除

p = 23, 2p = 46 , 3p = 69,  (1/p^2 ) * ( 1 + 1/4 + 1/9) = (1/p^2) *  (49/36), 也无法将p^2约分掉, 也排除

p = 19, 2p = 38 , 3p = 57, 4p = 76,  (1/p^2 ) * ( 1 + 1/4 + 1/9 + 1/16) = (1/p^2) *  (65/36), 也无法将p^2约分掉, 也排除



2
3
5
7
11
13
17
19
23
29
31
37
41
43
47
53
59
61
67
71
73
79


运行combine(13)
1/144= + 1/13^2  + 1/39^2  + 1/52^2
13的倍数： 13, 26, 39, 52, 65, 78
在这些组合中，只有{13,39,52}是可用的，因此将(26,65,78)排除,同时(13,39,52)如果被选中，必须同时出现

*/

func main() {
	// 2 -- 45
	N := 80
	prime := make([]int, N+1)
	for i := 2; i < N; i++ {
		prime[i] = 1
	}
	prime[0] = 0
	prime[1] = 0
	for i := 1; i*i <= N; i++ {
		if prime[i] == 0 {
			continue
		}
		for k := i + i; k <= N; k += i {
			prime[k] = 0
		}
	}
	candidate := make([]int, N+1)
	for i := 2; i <= N; i++ {
		candidate[i] = 1
	}
	for i := 2; i <= N; i++ {
		if prime[i] == 1 {
			if posssible(i) {
				fmt.Printf("posssible: %d\n", i)
			} else {
				k := 1
				for k*i <= N {
					candidate[k*i] = 0
					k++
				}
			}
		}
	}
	// combine 13
	candidate[26] = 0
	candidate[65] = 0
	candidate[78] = 0

	combine(13)
	combine(7)
	combine(5)
	// 剩余的那些，1个数字独立成一个group
	for i := 2; i <= N; i++ {
		if i%13 == 0 || i%7 == 0 {
			continue
		}
		if candidate[i] == 1 {
			f := Frac{1, int64(i * i)}
			values := []int{i}
			groups = append(groups, Group{f, values})
		}
	}

	fmt.Printf("len = %d\n", len(groups))
	sort.Slice(groups, func(i, j int) bool {
		return groups[i].f.Cmp(groups[j].f) > 0
	})
	for i := 0; i < len(groups); i++ {
		fmt.Printf("%d  %v\n", i, groups[i])
	}
	groupSums := make([]Frac, len(groups))
	for i := len(groups) - 1; i >= 0; i-- {
		if i == len(groups)-1 {
			groupSums[i] = groups[i].f
		} else {
			groupSums[i] = Add(groupSums[i+1], groups[i].f)
		}
	}
	for i := 0; i < len(groups); i++ {
		fmt.Printf("%d  %v\n", i, groupSums[i])
	}

	use := make([]bool, 81)
	cnt := 0

	m := make(map[string]bool)

	target := Frac{1, 2}

	// 检查group[i]和当前的数字是否冲突
	canUse := func(i int) bool {
		list := groups[i].list
		for _, v := range list {
			if use[v] {
				return false
			}
		}
		return true
	}
	canUse(1)

	var dfs func(f Frac, i int)
	dfs = func(f Frac, i int) {
		if f.n == 1 && f.d == 2 {
			str := ""
			for j := 2; j <= N; j++ {
				if use[j] {
					str += fmt.Sprintf(" + 1/%d^2 ", j)
				}
			}
			if m[str] {
				return
			}
			cnt++
			m[str] = true
			fmt.Printf("%d %s\n", cnt, str)
		}
		// 和已经超过了
		if f.Cmp(target) > 0 {
			return
		}
		if i >= len(groups) {
			return
		}
		// 剪枝
		// 如果i之后的所有group和加上f都不能满足，直接返回
		v := Add(f, groupSums[i])
		if v.Cmp(target) < 0 {
			return
		}
		/*
			v := Frac{0, 1}
			for j := i; j < len(groups); j++ {
				if canUse(j) {
					v = Add(v, groups[j].f)
				}
			}
			v = Add(v, f)
			if v.Cmp(target) < 0 {
				return
			}
		*/

		// 针对第i组，决定是否和之前选择的组合冲突
		valid := true
		g := groups[i]
		for _, v := range g.list {
			if use[v] {
				valid = false
				break
			}
		}
		if !valid {
			// 说明这个group和之前的冲突了，跳过
			dfs(f, i+1)
		} else {
			// 这个group是可以使用的
			// use it
			for _, v := range g.list {
				use[v] = true
			}
			dfs(Add(f, g.f), i+1)
			// unuse
			for _, v := range g.list {
				use[v] = false
			}
			// 不使用，直接到下一个group
			dfs(f, i+1)
		}
	}
	dfs(Frac{0, 1}, 0)

	fmt.Printf("cnt=%d\n", cnt)
}

type Frac struct {
	n, d int64
}

func (f Frac) String() string {
	return fmt.Sprintf("%d/%d", f.n, f.d)
}

func (f Frac) Cmp(f2 Frac) int {
	// a/b c/d 比较大小
	// 避免整数溢出
	g := gcd(f.d, f2.d)
	lcm := f.d / g * f2.d
	v := f.n*(lcm/f.d) - f2.n*(lcm/f2.d)
	if v < 0 {
		return -1
	}
	if v > 0 {
		return 1
	}
	return 0
}

func Add(f1, f2 Frac) Frac {
	// a/b + c/d = (a*d+b*c)/(b*d)
	// 避免溢出
	// 1. 先求两个分母的最大公约数
	g1 := gcd(f1.d, f2.d)
	// 2. 分母的最小公倍数
	lcm := f1.d / g1 * f2.d

	n := f1.n*(lcm/f1.d) + f2.n*(lcm/f2.d)
	g := gcd(n, lcm)
	return Frac{n / g, lcm / g}
}

func gcd(a, b int64) int64 {
	for b != 0 {
		a, b = b, a%b
	}
	return a
}

// 检查素数p以及它的倍数，组成的平方倒数和是否可以将p^2约分掉
// 如果可以，那么p是可能的，如果不行，那么p就需要被排序掉
// true代表可以，false代表不行
func posssible(p int) bool {
	k := 0
	for (k+1)*p <= 80 {
		k++
	}
	// 1, 1/2^2, 1/3^2, ..., 1/k^ 各种组合是否可以将p^2约分掉
	P := int64(p * p)
	var dfs func(n, d int64, i int) bool
	dfs = func(n, d int64, i int) bool {
		if n > 0 && n%P == 0 {
			return true
		}
		if i > k {
			return false
		}
		// 使用 1/i^2
		// n/d + 1/i^2 = (n*i*i + d) / (d*i*i)
		a := n*int64(i*i) + d
		b := d * int64(i*i)
		if dfs(a, b, i+1) {
			return true
		}
		// 不使用 1/i^2
		return dfs(n, d, i+1)
	}
	return dfs(0, 1, 1)
}

// 包含p的数字，哪些组合是最终可以将p^2,p约分掉的
func combine(p int) {
	k := 0
	for (k+1)*p <= 80 {
		k++
	}
	use := make([]bool, k+1)
	var dfs func(f Frac, i int)
	dfs = func(f Frac, i int) {
		if i > k {
			return
		}
		// 判断当前的分数 分子分母是否已经不包含p^2的因子了
		if f.d%int64(p) != 0 && f.n > 0 {
			if p == 5 {
				// 55 65的都去掉
				if use[11] || use[13] {
					return
				}
			}
			fmt.Printf("%v=", f)
			values := make([]int, 0)
			for i := 1; i <= k; i++ {
				if use[i] {
					fmt.Printf(" + 1/%d^2 ", i*p)
					values = append(values, i*p)
				}
			}
			fmt.Printf("\n")
			groups = append(groups, Group{f, values})
			return
		}
		// 使用i
		use[i] = true
		v := Add(f, Frac{1, int64(i * i * p * p)})
		dfs(v, i+1)
		use[i] = false
		// 不使用i
		dfs(f, i+1)
	}
	dfs(Frac{0, 1}, 1)
}

type Group struct {
	f    Frac
	list []int
}

func (g Group) String() string {
	return fmt.Sprintf("%v %v", g.f, g.list)
}

var groups = make([]Group, 0)
