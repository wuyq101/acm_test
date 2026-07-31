package main

import "fmt"

func main() {

	N := 12
	ncfs := make([][][]int, 13)
	for i := 1; i <= N; i++ {
		ncfs[i] = make([][]int, 0)
	}
	ncfs[1] = [][]int{{0}, {1}}
	ncfs[2] = [][]int{{1, 1}, {1, 2}, {1, 3}}
	for i := 3; i <= 12; i++ {
		m := make(map[string][]int)
		fn := func(seqs [][]int, generator func([]int) [][]int) {
			for _, seq := range seqs {
				list := generator(seq)
				for _, s := range list {
					str := key(s)
					_, ok := m[str]
					if !ok {
						m[str] = s
					}
				}
			}
		}
		// (a,b) --> (a+1,1,b+1)
		fn(ncfs[i-1], A)
		// (c) --> (a,0,b), c=a+b
		fn(ncfs[i-2], B)
		for _, v := range m {
			ncfs[i] = append(ncfs[i], v)
		}
		fmt.Printf("level %d len=%d\n", i, len(m))
	}
	sum := 0
	for i := 1; i <= 12; i++ {
		cnt := 0
		for _, seq := range ncfs[i] {
			if Valid(seq) {
				cnt += i
			}
		}
		fmt.Printf("level %d, cnt=%d\n", i, cnt)
		sum += cnt
	}
	fmt.Printf("sum=%d\n", sum)
}

func Valid(seq []int) bool {
	n := len(seq)
	// 判断seq的周期长度是不是n，检查是否存在更短的周期
	for k := 1; k < n; k++ {
		if n%k == 0 {
			// 检查每一个i == i+k
			flag := true
			for i := 0; i < n; i++ {
				if seq[i] != seq[(i+k)%n] {
					flag = false
					break
				}
			}
			if flag {
				return false
			}
		}
	}
	return true
}

// 通过两种方式生成长度更多的序列
// 方式1: (a,b) --> (a+1,1,b+1) 长度增加1
func A(seq []int) [][]int {
	n := len(seq)
	fn := func(i int) []int {
		list := make([]int, n+1)
		if i == n-1 {
			// 最后一位特殊处理
			for k := 0; k < n; k++ {
				list[k] = seq[k]
			}
			list[0] = seq[0] + 1
			list[n-1] = seq[n-1] + 1
			list[n] = 1
			return list
		}
		// 第i, i+1元素中间增加一个1， seq[i]++
		for k := 0; k < i; k++ {
			list[k] = seq[k]
		}
		for k := i + 2; k < n; k++ {
			list[k+1] = seq[k]
		}
		list[i] = seq[i] + 1
		list[i+1] = 1
		list[i+2] = seq[i+1] + 1
		return list
	}
	result := make([][]int, 0)
	m := make(map[string]bool)
	for i := 0; i < n; i++ {
		s := norm(fn(i))
		str := key(s)
		if !m[str] {
			m[str] = true
			result = append(result, s)
		}
	}
	return result
}

// 方式2: (c) --> (a,0,b), a+b=c, 长度增加2
func B(seq []int) [][]int {
	n := len(seq)
	// 将第i个元素拆成a,0,b
	fn := func(i, a int) []int {
		list := make([]int, n+2)
		for k := 0; k < i; k++ {
			list[k] = seq[k]
		}
		list[i] = a
		list[i+1] = 0
		list[i+2] = seq[i] - a
		for k := i + 1; k < n; k++ {
			list[k+2] = seq[k]
		}
		return list
	}
	result := make([][]int, 0)
	m := make(map[string]bool)
	for i := 0; i < n; i++ {
		for j := 0; j <= seq[i]; j++ {
			s := norm(fn(i, j))
			str := key(s)
			if !m[str] {
				m[str] = true
				result = append(result, s)
			}
		}
	}
	return result
}

func norm(seq []int) []int {
	// 长度位n的序列，旋转n次，可以得到n个不同的序列，只保留最后一位值是最大的那个序列，如果最后一位一样，看倒数第二位，依此类推
	n := len(seq)
	list := seq
	h := seq[0]
	for i := 0; i < n; i++ {
		h = max(h, seq[i])
	}
	for i := 0; i < n; i++ {
		if seq[i] == h {
			// 以当前位为结尾的序列，符合要求
			j := (i + 1) % n
			tmp := make([]int, n)
			for k := 0; k < n; k++ {
				tmp[k] = seq[(j+k)%n]
			}
			if cmp(tmp, list) > 0 {
				list = tmp
			}
		}
	}
	return list
}

func cmp(a, b []int) int {
	for k := len(a) - 1; k >= 0; k-- {
		if a[k] > b[k] {
			return 1
		}
		if a[k] < b[k] {
			return -1
		}
	}
	return 0
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func key(seq []int) string {
	return fmt.Sprintf("%v", seq)
}
