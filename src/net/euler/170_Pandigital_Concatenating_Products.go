package main

import (
	"fmt"
	"sort"
	"strconv"
)

func main() {
	str := "9876543210"
	for {
		fmt.Printf("%s\n", str)
		for i := 1; i < 512; i++ {
			// 根据i指定的隔板位置，拆分成k段
			list := split(str, i)
			// 检查每段是否有‘0’开头，淘汰
			if hasLeadingZero(list) {
				continue
			}
			g := GCD(list)
			if g == 1 {
				continue
			}
			//	fmt.Printf("find a possible split %v, gcd=%d\n", list, g)
			for _, f := range factors(g) {
				if f == 1 {
					continue
				}
				b := divide(list, f)
				if check(f, b) {
					fmt.Printf("find a possible split %v, gcd=%d\n", list, g)
					fmt.Printf("p=%s\n", str)
					fmt.Printf("f=%d, b=%v\n", f, b)
					return
				}
			}
		}
		str = next(str)
		if str == "" {
			break
		}
	}
}

func check(a int, b []string) bool {
	str := strconv.Itoa(a)
	for _, s := range b {
		str += s
	}
	if len(str) != 10 {
		return false
	}
	buf := []byte(str)
	sort.Slice(buf, func(i, j int) bool {
		return buf[i] < buf[j]
	})
	for i := 0; i <= 9; i++ {
		if buf[i] != byte('0'+i) {
			return false
		}
	}
	return true
}

func divide(list []string, d int) []string {
	result := make([]string, 0)
	for _, s := range list {
		a, _ := strconv.Atoi(s)
		v := a / d
		result = append(result, strconv.Itoa(v))
	}
	return result
}

func factors(n int) []int {
	f := make([]int, 0)
	for i := 1; i*i <= n; i++ {
		if n%i == 0 {
			f = append(f, i)
			if n/i != i {
				f = append(f, n/i)
			}
		}
	}
	return f
}

func GCD(list []string) int {
	g, _ := strconv.Atoi(list[0])
	for i := 1; i < len(list); i++ {
		a, _ := strconv.Atoi(list[i])
		g = gcd(g, a)
		if g == 1 {
			return 1
		}
	}
	return g
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func hasLeadingZero(list []string) bool {
	for _, s := range list {
		if s[0] == '0' {
			return true
		}
	}
	return false
}

// 得到降序排列的下一个排列
func next(s string) string {
	buf := []byte(s)
	// 找 s[i-1] > s[i]
	idx := -1
	for i := len(s) - 1; i >= 1; i-- {
		if buf[i-1] > buf[i] {
			idx = i - 1
			break
		}
	}
	if idx == -1 {
		fmt.Printf("已经是最小的排列\n")
		return ""
	}
	j := len(s) - 1
	for i := len(s) - 1; i > idx; i-- {
		if buf[i] < buf[idx] {
			j = i
			break
		}
	}
	// swap
	buf[idx], buf[j] = buf[j], buf[idx]
	//reverse idx+1---len-1
	for i, j := idx+1, len(s)-1; i < j; i, j = i+1, j-1 {
		buf[i], buf[j] = buf[j], buf[i]
	}
	return string(buf)
}

// 长度为10的字符串，拆分成多个字符串，中间有9个‘隔板’，每个位置有两种选择，0代表不拆，1表示拆分
// 2^9 = 512, 排除掉0（所以都是一个字符串的情况，还有511中情况）
func split(s string, mask int) []string {
	buf := make([]string, 0)
	pre := ""
	for i := 0; i < len(s); i++ {
		pre += s[i : i+1]
		if mask&1 == 1 {
			buf = append(buf, pre)
			pre = ""
		}
		mask >>= 1
	}
	if len(pre) > 0 {
		buf = append(buf, pre)
	}
	return buf
}
