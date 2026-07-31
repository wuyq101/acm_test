package main

import "fmt"

/*
f(p,q) 为p,q的最大的不能线性表示的数
定理一：西尔维斯特公式（Sylvester's Theorem）
f(p,q) = p*q-p-q
gcd(p,q)=1, 两个数互质

N 不能表示为 p*x+q*y, x,y>=0
将N表示为p的剩余类，即将N按照对p的余数分为p类

0: 0, p, 2p,
1: 1, p+1, 2p+1,...
...
p-1: p-1, 2p-1, 3p-1,...

我们找到每一类中的最小的数M，那么M之后，这一类中的数，都可以用通过M不断+p得到
每一类中的M，可以通过 p*x + q*y, 将x=0, y从0到 p-1 得到
那么这些数据中最大数是 (p-1)*q, 将这个数-p,就得到了p,q无法线性表示的最大的数f(p,q) = (p-1)*q-p = p*q-p-q

定理二：
f(A,B,C) = d*f(A/d,B/d,C) + C*(d-1)
假设gcd(A,B) = d, 并且gcd(d,C)=1.
任何能凑数的数N，都具有如下性质：
N = A*x + B*y + C*w = d*(A/d)*x + d*(B/d)*y + C*w = d*((A/d)*x + (B/d)*y) + C*w
假设w = q*d+z
代回到N，得到N = d*((A/d)*x + (B/d)*y) + C*(q*d+z) = d*(A/d * x + B/d * y + C*q) + C*z

其中A/d * x + B/d * y + C*q , 刚好就是用 {A/d, B/d,C}能凑出来的数字，我们记作K，那么
N = d*K + C*z
所以任何用N,用(A,B,C)都出来的数字，我们都可以通过N=d*K+C*z, 用{A/d,B/d,C}凑出来。

现在我们将N，用d的余数作分组，分为d组
z=0, N=d*K+C*0
z=1, N=d*K+C*1
..
z=d-1, N=d*K+C*(d-1)
分析每个分组中的最大的不能线性表示的数, N = d*K+C*z
显然N是d和C的线性表示，只有当K无法凑出的时候，N才不能被凑出。根据上面的定义。K是用{A/d,B/d,C}凑出来的。
当K无法被凑出时，根据定义，K=f(A/d,B/d,C)

另外， A=pq,B=pr,C=qr.
d = gcd(A,B) = p
A/d = q, B/d = r
那么K = f(q,r,qr)
观察{q,r,qr}, 其中qr是冗余信息，任何需要用到qr的地方，都可以直接用r来表示。所以f(q,r,qr) = f(q,r).
所以K=f(q,r), 根据定理一，K = f(q,r) = q*r-q-r

N = d*K + C*z, z最大取(d-1)
代入得到
f(pq,pr,qr) = p*f(q,r,qr)+qr*(p-1) = p*(q*r-q-r)+qr*(p-1) = pqr-pq-pr+pqr-qr = 2pqr-pq-pr-qr
*/

func main() {
	primes = genPrimes(5000)
	sum := int64(0)
	for i := 0; i < len(primes); i++ {
		for j := i + 1; j < len(primes); j++ {
			for k := j + 1; k < len(primes); k++ {
				p, q, r := int64(primes[i]), int64(primes[j]), int64(primes[k])
				x := f(p, q, r)
				sum += x
				//fmt.Printf("p=%d,q=%d,r=%d,x=%d\n", p, q, r, x)
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

var primes []int

func f(p, q, r int64) int64 {
	return 2*p*q*r - p*q - p*r - q*r
}

func genPrimes(max int) []int {
	list := make([]bool, max+1)
	for i := 0; i < len(list); i++ {
		list[i] = true
	}
	list[0], list[1] = false, false
	for i := 0; i < len(list); i++ {
		if !list[i] {
			continue
		}
		for j := i + i; j < len(list); j += i {
			list[j] = false
		}
	}
	primes := make([]int, 0)
	for i := 0; i < len(list); i++ {
		if list[i] {
			primes = append(primes, i)
		}
	}
	return primes
}
