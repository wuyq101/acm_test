package main

/*

 ø(i)/(i-1) < 15499/94744
 ø*94744 < 15499*(i-1)


*/
import "fmt"

func main() {
	fmt.Printf("%.9f\n", float64(15499)/float64(94744))
	primes := genPrimes(int(N))
	Pn := int64(1)
	Pd := int64(1)
	best := int64(1)
	// 粗筛 n=p1*p2*p3...
	for _, p := range primes {
		Pn *= int64(p - 1)
		Pd *= int64(p)
		f := float64(Pn) / float64(Pd-1)
		fmt.Printf("%.9f\n", f)
		fmt.Printf("Pn=%d, Pd=%d, %d\n", Pn, Pd, p)
		if Pn*94744 < 15499*(Pd-1) {
			best = Pd
			break
		}
	}
	fmt.Printf("best = %d\n", best)
	// 精筛, n=p1^a*p2^b*p3^c...
	Pn = int64(2)
	Pd = int64(6)
	for _, p := range primes[2:] {
		Pn *= int64(p - 1)
		Pd *= int64(p)
		// k*Pd < best ---> k<best/Pd
		// k*Pn*94744 < 15499*(k*Pd-1)
		// k>15499/(15499*Pd-94744*Pn)
		h := best / Pd
		l := 15499 / (15499*Pd - 94744*Pn)
		fmt.Printf("k range = %d, %d\n", l, h)
		for k := l + 1; k < h; k++ {
			//f := float64(Pn*k) / float64(k*Pd-1)
			//	fmt.Printf("%.9f\n", f)
			//	fmt.Printf("Pn=%d, Pd=%d, k=%d, %d\n", Pn, Pd, k, p)
			if k*Pn*94744 < 15499*(k*Pd-1) {
				if k*Pd < best {
					best = Pd * k
					fmt.Printf("best = %d, k=%d, p=%d\n", best, k, p)
				}
				break
			}
		}
	}
}

var N = int64(100)

func genPrimes(n int) []int {
	composite := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if composite[i] {
			continue
		}
		for j := i + i; j <= n; j += i {
			composite[j] = true
		}
	}
	primes := make([]int, 0, n/2)
	for i := 2; i <= n; i++ {
		if !composite[i] {
			primes = append(primes, i)
		}
	}
	return primes
}

func ø(n int64) []int64 {
	totient := make([]int64, n+1)
	p := make([]int64, n+1)
	for i := int64(0); i <= n; i++ {
		totient[i] = i
		p[i] = i
	}
	for i := int64(2); i <= n; i++ {
		if p[i] != i {
			continue
		}
		totient[i] -= 1
		for j := i + i; j <= n; j += i {
			totient[j] -= p[j] / i
			p[j] = p[j] / i
		}
	}
	return totient
}
