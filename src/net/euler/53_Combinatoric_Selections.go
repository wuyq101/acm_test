package main

/*
 * https://projecteuler.net/problem=53
 */
func main() {
	cnt := 0
	for n := 1; n <= 100; n++ {
		for i := 1; i <= n; i++ {
			if combinatorics(n, i) {
				cnt++
			}
		}
	}
	println(cnt)
}

func combinatorics(n, r int) bool {
	if r > n-r {
		r = n - r
	}
	// n * (n-1) * (n-2) * ... * (n-r+1)
	factors := make([]int, 0, r)
	for i := n; i >= n-r+1; i-- {
		factors = append(factors, i)
	}

	for i := r; i >= 1; i-- {
		k := i
		for k > 1 {
			for j := 0; j < len(factors); j++ {
				g := gcd(factors[j], k)
				if g == 1 {
					continue
				}
				factors[j] /= g
				k /= g
			}
		}
		//		fmt.Printf("%d --> %v\n", i, factors)
	}

	// cmp with 1000000
	s := 1
	MAX := 1000000
	for _, v := range factors {
		s *= v
		if s >= MAX {
			return true
		}
	}
	return s >= MAX
	return false
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
