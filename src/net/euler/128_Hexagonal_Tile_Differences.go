package main

import (
	"fmt"
	"math"
)

func main() {
	result := make([]int64, 0)
	result = append(result, 1)

	ring := int64(0)
	for {
		i := 2 + 3*ring*(ring+1) // 一圈的开始位置
		p := position(i)
		pd := PD(p)
		if pd == 3 {
			result = append(result, i)
		}
		i = i + 6*(ring+1) - 1 // 一圈的结束位置
		p = position(i)
		pd = PD(p)
		if pd == 3 {
			result = append(result, i)
		}

		ring++
		if len(result) == 2000 {
			fmt.Printf("fisrt 10: %v\n", result[:10])
			fmt.Printf("last = %d\n", result[1999])
			break
		}
	}

}

func PD(p *Pos) int {
	list := p.Neighbors()
	v := make([]int64, 0)
	for _, a := range list {
		d := a.N - p.N
		if d < 0 {
			d = -d
		}
		v = append(v, d)
	}
	return countPrimes(v)
}

func countPrimes(list []int64) int {
	count := 0
	for _, v := range list {
		if isPrime(v) {
			count++
		}
	}
	return count
}

var primes = map[int64]bool{1: false}

func isPrime(n int64) bool {
	v, ok := primes[n]
	if ok {
		return v
	}
	for i := int64(2); i*i <= n; i++ {
		if n%i == 0 {
			primes[n] = false
			return false
		}
	}
	primes[n] = true
	return true
}

func position(n int64) *Pos {
	// 确定n的位置
	// 先找到n属于那一圈
	i := (int64)(math.Sqrt(float64((9-12*(2-n))))-3) / 6
	idx := int64(2)
	for {
		idx = 2 + 3*i*(i+1)
		if idx > n {
			break
		}
		i++
	}
	// 3k(k+1) + 2
	// 属于第i圈，这一圈的个数是6*i个, 最大值: idx-1, 最小值: idx-6*i
	// 一共6条边，每边i个数，从最小值开始算第一条边
	min := idx - 6*i
	for k := int64(0); k < 6; k++ {
		k1 := min + k*i
		k2 := k1 + i - 1
		if k1 <= n && n <= k2 {
			pos := n - k1
			return &Pos{
				ring: i,
				side: k,
				pos:  pos,
				N:    n,
			}
		}
	}
	return nil
}

type Pos struct {
	ring int64
	side int64
	pos  int64
	N    int64
}

func NewPos(ring, side, pos, N int64) *Pos {
	if N == 0 {
		// calc N
		max := 2 + 3*ring*(ring+1)
		min := max - ring*6
		N = min + side*ring + pos
	}
	return &Pos{
		ring: ring,
		side: side,
		pos:  pos,
		N:    N,
	}
}

func (p Pos) String() string {
	return fmt.Sprintf("%d: 第%d圈 第%d条边 第%d个", p.N, p.ring, p.side, p.pos)
}

func (p *Pos) Neighbors() []*Pos {
	list := make([]*Pos, 0, 6)
	// 每条边的开始位置，外圈3个接邻，内圈1个接邻，同圈两个
	if p.pos == 0 {
		// out
		ring := p.ring + 1
		side := p.side
		a := NewPos(ring, side, 0, 0)
		list = append(list, a)
		list = append(list, a.SameRingNeighbors()...)

		// 同圈
		list = append(list, p.SameRingNeighbors()...)
		// 内圈1个
		if p.ring > 1 {
			list = append(list, NewPos(p.ring-1, p.side, 0, 0))
		} else {
			list = append(list, NewPos(0, 0, 0, 1))
		}
		return list
	}
	// 这一圈的最后一个
	// 同圈两个，外圈2个, 内圈2个
	if p.pos == p.ring-1 && p.side == 5 {
		list = append(list, p.SameRingNeighbors()...)
		// 内圈
		ring := p.ring - 1
		side := int64(0)
		pos := int64(0)
		list = append(list, NewPos(ring, side, pos, 0))
		side = int64(5)
		pos = ring - 1
		list = append(list, NewPos(ring, side, pos, 0))
		// 外圈
		ring = p.ring + 1
		side = int64(5)
		pos = p.ring - 1
		list = append(list, NewPos(ring, side, pos, 0))
		list = append(list, NewPos(ring, side, pos+1, 0))
		return list
	}

	// 边上
	// 同圈2个
	list = append(list, p.SameRingNeighbors()...)
	// 内圈2个
	if p.ring > 1 {
		ring := p.ring - 1
		side := p.side
		pos := p.pos
		// pos, pos-1
		list = append(list, NewPos(ring, side, pos-1, 0))
		if pos > ring {
			side = (side + 1) % 6
			pos = 0
		}
		list = append(list, NewPos(ring, side, pos, 0))
	}
	// 外圈2个
	ring := p.ring + 1
	side := p.side
	pos := p.pos
	// pos, pos+1
	list = append(list, NewPos(ring, side, pos, 0))
	pos = pos + 1
	if pos > ring {
		side = (side + 1) % 6
		pos = 0
	}
	list = append(list, NewPos(ring, side, pos, 0))
	return list
}

func (p Pos) SameRingNeighbors() []*Pos {
	list := make([]*Pos, 0, 2)
	// 每条边有ring个，从0 -- ring-1
	// 同圈的两个
	ring := p.ring
	side := p.side
	pos := p.pos
	pos = pos + 1
	if pos >= ring {
		side += 1
		side = side % 6
		pos = 0
	}
	list = append(list, NewPos(ring, side, pos, 0))
	ring = p.ring
	side = p.side
	pos = p.pos - 1
	if pos < 0 {
		pos = ring - 1
		side -= 1
		side = (side + 6) % 6
	}
	list = append(list, NewPos(ring, side, pos, 0))
	return list
}
