package main

import (
	"fmt"
	"os"
	"sort"
	"strings"
)

/*
 * https://projecteuler.net/problem=54
 */
func main() {

	//Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
	h := NewHand([]string{"TH", "JH", "KH", "QH", "AH"})
	fmt.Printf("rank %d royal flush\n", h.Rank())

	//Straight Flush: All cards are consecutive values of same suit.
	h = NewHand([]string{"4H", "3H", "6H", "5H", "2H"})
	fmt.Printf("rank %d straight flush\n", h.Rank())

	//Four of a Kind: Four cards of the same value.
	h = NewHand([]string{"4H", "4S", "4D", "4C", "2H"})
	fmt.Printf("rank %d Four of a Kind\n", h.Rank())

	//Full House: Three of a kind and a pair.
	h = NewHand([]string{"4H", "2S", "4D", "4C", "2H"})
	fmt.Printf("rank %d Full House\n", h.Rank())

	//Flush: All cards of the same suit.
	h = NewHand([]string{"4H", "8H", "TH", "5H", "2H"})
	fmt.Printf("rank %d Flush\n", h.Rank())

	// Straight: All cards are consecutive values.
	h = NewHand([]string{"4D", "3S", "6H", "5C", "2H"})
	fmt.Printf("rank %d straight\n", h.Rank())

	// Three of a Kind: Three cards of the same value.
	h = NewHand([]string{"4H", "8S", "4D", "4C", "2H"})
	fmt.Printf("rank %d Three of a Kind\n", h.Rank())

	// Two Pairs: Two different pairs.
	h = NewHand([]string{"8H", "8S", "4D", "4C", "2H"})
	fmt.Printf("rank %d Two Pairs\n", h.Rank())

	// One Pair: Two cards of the same value.
	h = NewHand([]string{"4H", "8S", "4D", "9C", "2H"})
	fmt.Printf("rank %d One Pair\n", h.Rank())

	//High Card: Highest value card.
	h = NewHand([]string{"4H", "8S", "5D", "9C", "2H"})
	fmt.Printf("rank %d High Card\n", h.Rank())

	// 读取数据
	buf, _ := os.ReadFile("54_poker.txt")
	lines := strings.Split(string(buf), "\n")
	fmt.Printf("read %d lines\n", len(lines))
	for _, line := range lines {
		if len(line) > 0 {
			f(line)
		}
	}
	// 读取数据
	// 读取数据
	// 读取数据
	fmt.Printf("Player 1 win %d hands\n", cnt)
}

var cnt = 0

func f(line string) {
	s := strings.Split(line, " ")
	h1 := s[0:5]
	h2 := s[5:]
	d := cmp(NewHand(h1), NewHand(h2))
	if d == 1 {
		fmt.Println(line)
		cnt++
	}
}

func cmp(h1, h2 *Hand) int {
	r1 := h1.Rank()
	r2 := h2.Rank()
	if r1 > r2 {
		return 1
	}
	if r1 < r2 {
		return -1
	}
	if r1 == HighCard {
		// 两个都是HighCard
		return cmpHighCard(h1, h2)
	}
	if r1 == OnePair {
		// 两个都是OnePair
		return cmpOnePair(h1, h2)
	}
	/*
		if r1 == TwoPair {
			// 两个都是TwoPair
			return cmpTwoPair(h1, h2)
		}
		if r1 == ThreeOfAKind {
			// 两个都是ThreeOfAKind
			return cmpThreeOfAKind(h1, h2)
		}
		if r1 == Straight {
			// 两个都是Straight
			return cmpStraight(h1, h2)
		}
		if r1 == Flush {
			// 两个都是Flush
			return cmpFlush(h1, h2)
		}
	*/

	// 比较剩余的内容

	fmt.Printf("h1,rank=%v %v ---- h2,rank=%v %v\n", h1.Rank(), h1, h2.Rank(), h2)
	panic("same rank")
	return 0
}

func cmpOnePair(h1, h2 *Hand) int {
	// 先比较对子大小，然后再比较剩余的高张
	p1 := h1.getPairValue()
	p2 := h2.getPairValue()
	if p1 > p2 {
		return 1
	}
	if p1 < p2 {
		return -1
	}
	// 比较剩余的高张
	v1 := h1.getHighCard(p1)
	v2 := h2.getHighCard(p2)
	return cmp_high(v1, v2)
}

func (h *Hand) getPairValue() int {
	v := h.getValues()
	for i := 1; i < 5; i++ {
		if v[i] == v[i-1] {
			return v[i]
		}
	}
	return 0
}

func (h *Hand) getHighCard(a int) []int {
	v := h.getValues()
	list := make([]int, 0)
	for _, c := range v {
		if c != a {
			list = append(list, c)
		}
	}
	return list
}

func cmpHighCard(h1, h2 *Hand) int {
	v1 := h1.getValues()
	v2 := h2.getValues()
	return cmp_high(v1, v2)
}

func cmp_high(v1, v2 []int) int {
	for i := len(v1) - 1; i >= 0; i-- {
		if v1[i] > v2[i] {
			return 1
		}
		if v1[i] < v2[i] {
			return -1
		}
	}
	return 0
}

type Hand struct {
	Pokers [][]byte
	rank   int
	values []int
}

func (h *Hand) String() string {
	s := ""
	for _, p := range h.Pokers {
		s += string(p) + " "
	}
	return s
}

const (
	HighCard      = 1
	OnePair       = 2
	TwoPair       = 3
	ThreeOfAKind  = 4
	Straight      = 5
	Flush         = 6
	FullHouse     = 7
	FourOfAKind   = 8
	StraightFlush = 9
	RoyalFlush    = 10
)

func (h *Hand) Rank() int {
	if h.rank > 0 {
		return h.rank
	}
	// 是否为皇家同花顺
	if h.isRoyalFlush() {
		h.rank = 10
		return h.rank
	}

	// 是否为同花顺
	if h.isStraightFlush() {
		h.rank = 9
		return h.rank
	}

	// 是否为四条
	if h.isFourOfAKind() {
		h.rank = 8
		return h.rank
	}

	// 是否为葫芦
	if h.isFullHouse() {
		h.rank = 7
		return h.rank
	}

	// 是否为同花
	if h.isFlush() {
		h.rank = 6
		return h.rank
	}

	// 是否为顺子
	if h.isStraight() {
		h.rank = 5
		return h.rank
	}

	// 是否为三条
	if h.isThreeOfAKind() {
		h.rank = 4
		return h.rank
	}

	// 是否为两对
	if h.isTwoPair() {
		h.rank = 3
		return h.rank
	}

	// 是否为一对
	if h.isOnePair() {
		h.rank = 2
		return h.rank
	}

	// 是否为高牌
	if h.isHighCard() {
		h.rank = 1
		return h.rank
	}
	return 0
}

func (h *Hand) isRoyalFlush() bool {
	// 统计花色
	// 前面的牌值是TJQKA
	return h.isFlush() && h.isStraight() && h.values[0] == 10
}

func (h *Hand) isStraightFlush() bool {
	return h.isFlush() && h.isStraight()
}

func (h *Hand) isFourOfAKind() bool {
	return h.isMatch(2, 1, 4)
}

func (h *Hand) isFullHouse() bool {
	return h.isMatch(2, 2, 3)
}

func (h *Hand) isThreeOfAKind() bool {
	return h.isMatch(3, 1, 1, 3)
}

func (h *Hand) isTwoPair() bool {
	return h.isMatch(3, 1, 2, 2)
}

func (h *Hand) isOnePair() bool {
	return h.isMatch(4, 1, 1, 1, 2)
}

func (h *Hand) isHighCard() bool {
	return h.isMatch(5, 1, 1, 1, 1, 1)
}

func (h *Hand) isMatch(vcnt int, rcnt ...int) bool {
	values := h.getValues()
	m := make(map[int]int)
	for _, v := range values {
		m[v]++
	}
	cnt := make([]int, 0)
	for _, v := range m {
		cnt = append(cnt, v)
	}
	sort.Ints(cnt)
	if len(cnt) != vcnt {
		return false
	}
	for i := 0; i < vcnt; i++ {
		if cnt[i] != rcnt[i] {
			return false
		}
	}
	return true
}

func (h *Hand) getValues() []int {
	if h.values != nil {
		return h.values
	}
	values := make([]int, 0)
	for _, p := range h.Pokers {
		values = append(values, int(p[0]))
	}
	for i := 0; i < 5; i++ {
		values[i] = normalValue(byte(values[i]))
	}
	sort.Ints(values)
	h.values = values
	return values
}

func (h *Hand) isStraight() bool {
	v := h.getValues()
	return v[0]+1 == v[1] && v[0]+2 == v[2] && v[0]+3 == v[3] && v[0]+4 == v[4]
}

func normalValue(b byte) int {
	if b > '0' && b <= '9' {
		return int(b - '0')
	}
	if b == 'T' {
		return 10
	}
	if b == 'J' {
		return 11
	}
	if b == 'Q' {
		return 12
	}
	if b == 'K' {
		return 13
	}
	if b == 'A' {
		return 14
	}
	return 0
}

func (h *Hand) isFlush() bool {
	// 统计花色
	m := make(map[byte]int)
	for _, p := range h.Pokers {
		m[p[1]] = 1
	}
	return len(m) == 1
}

func NewHand(pokers []string) *Hand {
	list := make([][]byte, 0, len(pokers))
	for _, p := range pokers {
		list = append(list, []byte(p))
	}
	return &Hand{Pokers: list}
}
