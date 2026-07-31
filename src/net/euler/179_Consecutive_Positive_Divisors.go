package main

import "fmt"

func main() {
	M := 10000000
	nums := make([]int, M+1)
	nums[0] = 1
	nums[1] = 1
	nums[2] = 0
	for i := 2; i <= M; i++ {
		if nums[i] > 0 {
			continue
		}
		for j := i + i; j <= M; j += i {
			k := j
			c := 0
			for k%i == 0 {
				c++
				k /= i
			}
			if nums[j] == 0 {
				nums[j] = 1
			}
			nums[j] *= (c + 1)
		}
	}
	cnt := 0
	for i := 2; i < M; i++ {
		if i < 100 {
			fmt.Printf("i=%d, nums[i]=%d\n", i, nums[i])
		}
		if nums[i] == nums[i+1] {
			cnt++
		}
	}
	fmt.Printf("cnt = %d\n", cnt)

}
