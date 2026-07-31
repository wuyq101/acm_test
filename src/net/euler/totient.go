package main

// ø(n) = n * (1 - 1/p1) * (1 - 1/p2) * ...
// n = 20
// p = 2, result = 20 - 20/2 = 10, n = 5
// p = 3 ...
// result = 10 - 10/5 = 8  5,15
func ø(n int) int {
	result := n
	for p := 2; p*p <= n; p++ {
		if n%p == 0 {
			result -= result / p
			for n%p == 0 {
				n /= p
			}
		}
	}
	if n > 1 {
		result -= result / n
	}
	return result
}
