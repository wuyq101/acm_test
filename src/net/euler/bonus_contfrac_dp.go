package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	S := make([][]int, 13)
	for i := 1; i <= 12; i++ {
		S[i] = make([]int, 3)
		name := fmt.Sprintf("dp_cleaned_%d.txt", i)
		f, _ := os.ReadFile(name)
		lines := strings.Split(string(f), "\n")
		for _, line := range lines {
			strs := strings.Split(line, " ")
			if len(strs) != 5 {
				continue
			}
			v, _ := strconv.Atoi(strs[4])
			str := strs[0]
			str = str[1:]
			a, _ := strconv.Atoi(str)
			str = strs[3]
			str = str[:len(str)-1]
			d, _ := strconv.Atoi(str)
			tr := a + d + 1
			S[i][tr] += v
		}
		fmt.Printf("S[%d]=%v\n", i, S[i])
	}
	fmt.Printf("========\n")
	// 逐个处理
	// S[0] = [0 1 1]
	// 当n=1的时候，Tr=-1的0个，忽略
	// n=1, tr=0, 1个，对后续的影响
	// tr(M)=0, M1 = 0, M2=-2, M3=0, M4=2, M5=0, M6=-2, M7=0, M8 = 2..., 奇数次存活，偶数次死亡
	for i := 3; i <= 11; i += 2 {
		S[i][1] -= S[1][1]
	}
	// n=1, tr=1, 1个， 对后续的影响
	// k=3的倍数时，tr=2, 无影响
	// k=5,7,11 k=1,5 (mod 6), tr=1
	// k=2,4 (mod 6) tr=-1
	for i := 2; i <= 12; i++ {
		if i%3 == 0 {
			continue
		}
		r := i % 6
		if r == 1 || r == 5 {
			S[i][2] -= S[1][2]
		}
		if r == 2 || r == 4 {
			S[i][0] -= S[1][2]
		}
	}
	// S[2]=[0 2 2]
	// n=2, tr=-1 0个
	// n=2, tr=0, 2个，
	S[6][1] -= S[2][1]
	S[10][1] -= S[2][1]
	// n=2, tr=1, 2个
	S[4][0] -= S[2][2]
	S[8][0] -= S[2][2]
	S[10][2] -= S[2][2]

	// S[3]=[6 6 6]
	// n=3, tr=-1, 6个
	S[6][0] -= S[3][0]
	S[12][0] -= S[3][0]
	// n=3, tr=0, 6个
	S[9][1] -= S[3][1]
	// n=3, tr=1, 6个
	S[6][0] -= S[3][2]
	S[12][0] -= S[3][2]

	// S[4]=[24 32 24]
	// n=4, tr=-1, 24个
	S[8][0] -= S[4][0]
	// n=4, tr=0, 32个
	S[12][1] -= S[4][1]
	// n=4, tr=1
	S[8][0] -= S[4][2]

	// S[5]=[120 160 120]
	// n=5, tr=-1, 120个
	S[10][0] -= S[5][0]
	// n=5, tr=0, 160个
	// n=5, tr=1, 120个
	S[10][0] -= S[5][2]

	//S[6]=[480 594 450]
	S[12][0] -= S[6][0]
	S[12][0] -= S[6][2]

	sum := 0
	for i := 1; i <= 12; i++ {
		fmt.Printf("S[%d]=%v\n", i, S[i])
		sum += S[i][0] + S[i][1] + S[i][2]
	}
	fmt.Printf("sum=%d\n", sum)

	for i := 1; i <= 0; i++ {
		dest, _ := os.Create(fmt.Sprintf("dp_cleaned_%d.txt", i))
		name := fmt.Sprintf("dp_%d.txt", i)
		f, _ := os.ReadFile(name)
		lines := strings.Split(string(f), "\n")
		for _, line := range lines {
			strs := strings.Split(line, " ")
			if len(strs) != 5 {
				continue
			}
			str := strs[0]
			str = str[1:]
			a, _ := strconv.Atoi(str)
			str = strs[3]
			str = str[:len(str)-1]
			d, _ := strconv.Atoi(str)
			tr := a + d
			if tr >= -1 && tr <= 1 {
				dest.WriteString(line + "\n")
			}
		}
		dest.Close()
	}
}
