package main

import (
	"fmt"
	"os"
	"sort"
	"strings"
)

func main() {
	buf, err := os.ReadFile("keylog.txt")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(string(buf), "\n")
	m := make(map[string]int)
	for _, line := range lines {
		if len(line) == 0 {
			continue
		}
		m[line] = 1
	}
	list := make([]string, 0, len(m))
	for k := range m {
		list = append(list, k)
	}
	sort.Strings(list)
	for _, v := range list {
		fmt.Printf("%s\n", v)
	}
	// 针对每个数字，分析他们的前后关系
	// 比如7，发现没有数字在它之前，那么它可以放第一个
	// 比如数字0，发现没有数字在它之后，那么它可以放最后一个
	result := ""
	for len(m) > 0 {
		cnt := make(map[byte]int)
		exist := make(map[byte]bool)
		for s := range m {
			for i, c := range s {
				ch := byte(c)
				cnt[ch] += i
				exist[ch] = true
			}
		}
		for i := 0; i < 10; i++ {
			ch := '0' + byte(i)
			if exist[ch] && cnt[ch] == 0 {
				fmt.Printf("ch = %c, cnt = %d, exist = %v\n", ch, cnt[ch], exist[ch])
				result += string(ch)
				for i, s := range list {
					if len(s) > 0 && s[0] == ch {
						delete(m, s)
						list[i] = ""
						if len(s) > 1 {
							m[s[1:]] = 1
							list[i] = s[1:]
						}
					}
				}

				///-------------
				list = make([]string, 0, len(m))
				for k := range m {
					list = append(list, k)
				}
				sort.Strings(list)
				for _, v := range list {
					fmt.Printf("%s\n", v)
				}
				//--------------
			}
		}
	}
	fmt.Printf("result len=%d, %s\n", len(result), result)

}
