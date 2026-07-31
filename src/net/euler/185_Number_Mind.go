package main

import (
	"fmt"
	"strconv"
	"strings"
)

/*
bfs
*/

func main() {
	/*
		str := `
				70794 ;0 correct
				34109 ;1 correct
				12531 ;1 correct
				90342 ;2 correct
				39458 ;2 correct
				51545 ;2 correct
				`
	*/

	str := `
		   2321386104303845 ;0 correct
		   3847439647293047 ;1 correct
		   3174248439465858 ;1 correct
		   8157356344118483 ;1 correct
		   6375711915077050 ;1 correct
		   6913859173121360 ;1 correct
		   4895722652190306 ;1 correct
		   5616185650518293 ;2 correct
		   2615250744386899 ;2 correct
		   6442889055042768 ;2 correct
		   2326509471271448 ;2 correct
		   5251583379644322 ;2 correct
		   2659862637316867 ;2 correct
		   4513559094146117 ;2 correct
		   5855462940810587 ;3 correct
		   9742855507068353 ;3 correct
		   4296849643607543 ;3 correct
		   7890971548908067 ;3 correct
		   8690095851526254 ;3 correct
		   1748270476758276 ;3 correct
		   3041631117224635 ;3 correct
		   1841236454324589 ;3 correct
		   `

	lines := strings.Split(str, "\n")
	tmp := lines[0:0]
	for _, line := range lines {
		line = strings.TrimSpace(line)
		if len(line) == 0 {
			continue
		}
		tmp = append(tmp, line)
	}
	lines = tmp

	list := make([]Rule, 0)
	for i, line := range lines {
		list = append(list, MakeRules(i, line)...)
		maxID = i
	}
	fmt.Printf("len=%d\n", len(list))
	// group by id
	rules = make([][]Rule, len(lines))
	for _, r := range list {
		id := r.id
		rules[id] = append(rules[id], r)
	}
	conflict = make([][]int, len(rules))
	used = make([][]int, len(rules))
	for i := 0; i < len(rules); i++ {
		fmt.Printf("%d - %d\n", i, len(rules[i]))
		conflict[i] = make([]int, len(rules[i]))
		used[i] = make([]int, len(rules[i]))
	}
	N = len(rules[0][0].str)
	finish = false
	fmt.Printf("maxID=%d N=%d\n", maxID, N)
	start := strings.Repeat("0", N)
	mmmmID = 0
	blacklist = make([]int, N)
	dfs(0, 0, start)
}

var rules [][]Rule
var conflict [][]int
var used [][]int
var N int
var finish bool
var maxID int
var mmmmID int
var blacklist []int

func dfs(id int, mask int, str string) bool {
	if id > mmmmID {
		fmt.Printf("dfs %d %s %b\n", id, str, mask)
		mmmmID = id
	}
	if id == maxID+1 {
		if mask == 1<<N-1 {
			fmt.Printf("----------------finished\n")
			fmt.Printf("----------------str=%s\n", str)
			finish = true
			return true

		}
		// 已经才到最后一组了
		fmt.Printf("----------------finished\n")
		fmt.Printf("not find answer")
		fmt.Printf("blacklist = %v\n", blacklist)
		for i, b := range blacklist {
			if cntBit(b) == 9 {
				fmt.Printf("%d %b\n", i, b)
			}
		}
		return false
	}
	if finish {
		return true
	}
	for i := id; i < len(rules); i++ {
		// 某些规则下已经没有可用的rule，说明全部和以前选中的冲突了，提前退出
		flag := false
		for j := 0; j < len(rules[i]); j++ {
			if conflict[i][j] == 0 {
				flag = true
				break
			}
		}
		if !flag {
			//	fmt.Printf("%d has no valid rule.\n", i)
			return false
		}
	}
	i := id
	for j := 0; j < len(rules[i]); j++ {
		if used[i][j] == 1 || conflict[i][j] == 1 {
			continue
		}
		// 使用rule i下的第j个规则
		used[i][j] = 1
		next := mask | rules[i][j].mask
		nextStr := rules[i][j].apply(str)
		// 同时将每一位不对的数字加入到blacklist中
		// 检查blacklist，如果某一位数字只有一个，那么这一位必须使用它
		pre := make(map[int]int)
		for k := 0; k < N; k++ {
			if rules[i][j].mask&(1<<k) == 0 {
				idx := N - 1 - k
				d := int(rules[i][j].str[idx] - '0')
				pre[idx] = blacklist[idx]
				blacklist[idx] = blacklist[idx] | (1 << d)
				if cntBit(blacklist[idx]) == 9 {
					//	fmt.Printf("%d %b has 9 black list\n", idx, blacklist[idx])
					// 只有一个可选
					for d := 0; d <= 9; d++ {
						if (1<<d)&blacklist[idx] == 0 {
							buf := []byte(nextStr)
							buf[idx] = byte('0' + d)
							nextStr = string(buf)
							next = next | (1 << idx)
							break
						}
					}
				}
			}
		}
		//	fmt.Printf("use rule (%d,%d) %v, next str=%s, next mask=%b\n", i, j, rules[i][j], nextStr, next)

		// 使用j之后，所有冲突的规则，提出掉
		idx := markConflict(i, j)
		//	fmt.Printf("conflict idx=%v\n", idx)

		flag := dfs(id+1, next, nextStr)
		if flag {
			return true
		}
		//	fmt.Printf("(%d,%d), undo conflict %v\n", i, j, idx)
		// undo
		used[i][j] = 0
		//		fmt.Printf("unuse (%d,%d)\n", i, j)
		for _, pair := range idx {
			conflict[pair[0]][pair[1]] = 0
		}
		for idx, v := range pre {
			blacklist[idx] = v
		}
	}
	// 如果id下没有一条规则有效，则说明之前的某个规则选择是错误的，需要回退
	//	fmt.Printf("can't find rule for %d\n", id)
	return false
}

func markConflict(i, j int) [][]int {
	r := rules[i][j]
	idx := make([][]int, 0)
	for i := 0; i < len(rules); i++ {
		for j := 0; j < len(rules[i]); j++ {
			if used[i][j] == 1 || conflict[i][j] == 1 {
				continue
			}
			// 比较这两个rule
			if isConflict(r, rules[i][j]) {
				conflict[i][j] = 1
				idx = append(idx, []int{i, j})
			}
		}
	}
	return idx
}

func isConflict(a, b Rule) bool {
	N := len(a.str)
	for k := 0; k < N; k++ {
		i := a.mask & (1 << k)
		j := b.mask & (1 << k)
		idx := N - 1 - k
		if i == 0 && j == 0 {
			// 在这个位置上，两者都猜错
			continue
		}
		if i == 0 && j != 0 {
			// a猜测k为不是x，而b猜测k为x
			if a.str[idx] == b.str[idx] {
				return true
			}
			// 检查k位剩余的数字中是否还有x，如果已经没有x，视为冲突
			black := blacklist[idx]
			v := int(b.str[idx] - '0')
			if (1<<v)&black != 0 {
				return true
			}
		}
		if i != 0 && j == 0 {
			// a猜测k为x，而b猜测k不是x
			if a.str[idx] == b.str[idx] {
				return true
			}
		}
		if i != 0 && j != 0 {
			// a猜测k为x，而b猜测k为y，x!=y
			if a.str[idx] != b.str[idx] {
				return true
			}
		}
	}
	return false
}

func mask(n, c int) []int {
	if n == 1 {
		if c == 0 || c == 1 {
			// c == 0 or 1
			return []int{c}
		} else {
			// impossible
			return []int{}
		}
	}
	// 第n位使用1
	list := mask(n-1, c-1)
	for i, v := range list {
		list[i] = 1<<(n-1) | v
	}
	//第n位使用0
	tmp := mask(n-1, c)
	list = append(list, tmp...)
	return list
}

type Rule struct {
	id   int
	str  string
	mask int
}

func (r Rule) apply(str string) string {
	buf := []byte(str)
	N := len(r.str)
	for i := 0; i < N; i++ {
		if r.mask&(1<<i) != 0 {
			buf[N-1-i] = r.str[N-1-i]
		}
	}
	return string(buf)
}

func (r Rule) String() string {
	return fmt.Sprintf("id=%d str=%s mask=%b", r.id, r.str, r.mask)
}

func MakeRules(id int, guess string) []Rule {
	//6442889055042768 ;2 correct
	guess = guess[:len(guess)-len(" correct")]
	strs := strings.Split(guess, " ;")
	fmt.Printf("%d %v\n", id, strs)
	str := strs[0]
	c, _ := strconv.Atoi(strs[1])
	m := mask(len(str), c)
	rules := make([]Rule, 0)
	for _, v := range m {
		rules = append(rules, Rule{id: id, str: str, mask: v})
	}
	return rules
}

func cntBit(n int) int {
	cnt := 0
	for n != 0 {
		n &= (n - 1)
		cnt++
	}
	return cnt
}
