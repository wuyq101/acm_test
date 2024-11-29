package main

import (
	"fmt"
	"strconv"
)

/**
 * https://projecteuler.net/problem=51
 * 56003, 56113, 56333, 56443, 56663, 56773, and 56993 推出pattern "56**3", 然后知道这个pattern对应的prime family
 * has 6 elements,
 * 56003 -> *6003, 5*003, 56*03, 56**3, 5600*
 * 通过56003可以反推除有上面几种pattern符合
 * 通过枚举所有的prime, 然后找到对应的pattern, 并在对应的pattern,保持一个列表，用来记录数字
 *
 */
func main() {
	// 计算10000000内的所有质数
	MAX := 1000000
	prime := make([]int, MAX)
	for i := 2; i < MAX; i++ {
		prime[i] = 1
	}
	for i := 2; i < MAX; i++ {
		if prime[i] == 0 {
			continue
		}
		for j := i + i; j < MAX; j += i {
			prime[j] = 0
		}
	}
	list := make([]int, 0)
	for i := 2; i < MAX; i++ {
		if prime[i] == 1 {
			list = append(list, i)
		}
	}
	prime = list
	fmt.Printf("%d内的所有质数已经计算完毕,共%d个\n", MAX, len(prime))

	m := make(map[string][]int)
	for i := 0; i < len(prime); i++ {
		list := findPattern(prime[i])
		fmt.Printf("p=%d %q\n", prime[i], list)
		for _, s := range list {
			m[s] = append(m[s], prime[i])
		}
	}
	fmt.Printf("%d内的所有质数的pattern已经计算完毕", MAX)
	key := ""
	minValue := 0
	for k, v := range m {
		if len(v) == 8 {
			if minValue == 0 {
				minValue = v[0]
				key = k
			} else {
				if minValue > v[0] {
					minValue = v[0]
					key = k
				}
			}

		}
	}
	println("--------", key, minValue)
	list = m[key]
	for _, v := range list {
		fmt.Printf("%d\n", v)
	}
}

/*
p=999983 ["*99983" "**9983" "***983" "****83" "9*9983" "9**983" "9***83" "99*983" "99**83" "999*83" "9999*3" "99998*"]
*/
func findPattern(p int) []string {
	m := make(map[string]int)
	s := strconv.Itoa(p)
	buf := []byte(s)
	for i, b := range buf {
		p := []byte(s)
		p[i] = '*'
		dfs(string(p), b, i+1, m)
	}
	list := make([]string, 0, len(m))
	for k := range m {
		list = append(list, k)
	}
	return list
}

func dfs(s string, ch byte, idx int, m map[string]int) {
	if idx == len(s) {
		m[s] = 1
		return
	}
	// 在s的基础上，找到ch，并替换，然后递归调用dfs(s,ch,cnt+1)
	buf := []byte(s)
	for i := idx; i < len(buf); i++ {
		if buf[i] == ch {
			buf[i] = '*'
			dfs(string(buf), ch, i+1, m)
		}
		dfs(s, ch, i+1, m)
	}
}
