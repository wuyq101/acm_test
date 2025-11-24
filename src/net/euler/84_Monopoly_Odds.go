package main

import (
	"fmt"
	"math/rand"
	"strings"
)

func main() {
	b := NewBoard()
	b.shuffle()
	for i := 0; i < 1000000000; i++ {
		b.Play()
	}
	b.Print()
}

type Board struct {
	Grids      []string
	GridsIdx   int
	Scores     [][]int
	ScoresIdx  int
	Count      []int
	CHCards    []string
	CHCardsIdx int
	CCCards    []string
	CCCardsIdx int
}

func NewBoard() Board {
	grids := []string{
		"GO", "A1", "CC1", "A2", "T1", "R1", "B1", "CH1", "B2", "B3",
		"JAIL", "C1", "U1", "C2", "C3", "R2", "D1", "CC2", "D2", "D3",
		"FP", "E1", "CH2", "E2", "E3", "R3", "F1", "F2", "U2", "F3",
		"G2J", "G1", "G2", "CC3", "G3", "R4", "CH3", "H1", "T2", "H2",
	}
	scores := [][]int{{0, 0, 0}, {0, 0, 0}}
	cnt := make([]int, len(grids))
	cnt[0] = 1
	return Board{
		Grids:   grids,
		Scores:  scores,
		Count:   cnt,
		CHCards: makeCHCards(),
		CCCards: makeCCCards(),
	}
}

func makeCHCards() []string {
	return []string{
		"Advance to GO",
		"Go to JAIL",
		"Go to C1",
		"Go to E3",
		"Go to H2",
		"Go to R1",
		"Go to next R",
		"Go to next R",
		"Go to next U",
		"Go back 3 squares",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
	}
}

func makeCCCards() []string {
	return []string{
		"Advance to GO",
		"Go to JAIL",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
		"NONE",
	}
}

func (b *Board) Print() {
	// 求次数最多的3个格子
	m1 := -1
	idx1 := -1
	for i := 0; i < len(b.Count); i++ {
		if m1 < b.Count[i] {
			m1 = b.Count[i]
			idx1 = i
		}
	}
	fmt.Printf("%s = %d\n", b.Grids[idx1], m1)
	m2 := -1
	idx2 := -1
	for i := 0; i < len(b.Count); i++ {
		if m2 < b.Count[i] && i != idx1 {
			m2 = b.Count[i]
			idx2 = i
		}
	}
	fmt.Printf("%s = %d\n", b.Grids[idx2], m2)
	m3 := -1
	idx3 := -1
	for i := 0; i < len(b.Count); i++ {
		if m3 < b.Count[i] && i != idx1 && i != idx2 {
			m3 = b.Count[i]
			idx3 = i
		}
	}
	fmt.Printf("%s = %d\n", b.Grids[idx3], m3)
}

func (b *Board) shuffle() {
	rand.Shuffle(len(b.CHCards), func(i, j int) {
		b.CHCards[i], b.CHCards[j] = b.CHCards[j], b.CHCards[i]
	})
	rand.Shuffle(len(b.CCCards), func(i, j int) {
		b.CCCards[i], b.CCCards[j] = b.CCCards[j], b.CCCards[i]
	})
}

func (b *Board) roll() int {
	r1 := rand.Intn(4) + 1
	r2 := rand.Intn(4) + 1
	idx := b.ScoresIdx % 3
	b.Scores[0][idx] = r1
	b.Scores[1][idx] = r2
	b.ScoresIdx = (b.ScoresIdx + 1) % 3
	return r1 + r2
}

func (b *Board) Play() {
	score := b.roll()
	if b.isDouble() {
		b.jail()
		return
	}
	next := (b.GridsIdx + score) % 40
	//fmt.Printf("score = %d, next=%s\n", score, b.Grids[next])
	grid := b.Grids[next]
	if strings.HasPrefix(grid, "CC") {
		b.GridsIdx = next
		b.takeCCCard()
		return
	}
	if strings.HasPrefix(grid, "CH") {
		b.GridsIdx = next
		b.takeCHCard()
		return
	}
	if grid == "G2J" {
		b.jail()
		return
	}
	b.GridsIdx = next
	b.Count[b.GridsIdx] += 1
}

func (b *Board) takeCHCard() {
	card := b.CHCards[b.CHCardsIdx]
	b.CHCardsIdx = (b.CHCardsIdx + 1) % 16
	switch card {
	case "Go to JAIL":
		b.jail()
	case "Advance to GO":
		b.start()
	case "Go to C1":
		b.jump("C1")
	case "Go to E3":
		b.jump("E3")
	case "Go to H2":
		b.jump("H2")
	case "Go to R1":
		b.jump("R1")
	case "Go to next R":
		b.next("R")
	case "Go to next U":
		b.next("U")
	case "Go back 3 squares":
		b.back(3)
	case "NONE":
		b.Count[b.GridsIdx] += 1
	}
}

func (b *Board) takeCCCard() {
	card := b.CCCards[b.CCCardsIdx]
	b.CCCardsIdx = (b.CCCardsIdx + 1) % 16
	if card == "Go to JAIL" {
		b.jail()
		return
	}
	if card == "Advance to GO" {
		b.start()
		return
	}
	b.Count[b.GridsIdx] += 1
}

func (b *Board) isDouble() bool {
	for i := 0; i < 3; i++ {
		if b.Scores[0][i] != b.Scores[1][i] {
			return false
		}
	}
	return true
}

func (b *Board) next(grid string) {
	next := b.GridsIdx
	for {
		next = (next + 1) % 40
		if strings.HasPrefix(b.Grids[next], grid) {
			b.GridsIdx = next
			b.Count[b.GridsIdx] += 1
			return
		}
	}
}

func (b *Board) back(n int) {
	b.GridsIdx = (b.GridsIdx - n) % 40
	if b.GridsIdx < 0 {
		b.GridsIdx += 40
	}
	if b.Grids[b.GridsIdx] == "G2J" {
		b.jail()
	} else {
		b.Count[b.GridsIdx] += 1
	}
}

func (b *Board) jump(grid string) {
	switch grid {
	case "C1":
		b.GridsIdx = 11
	case "E3":
		b.GridsIdx = 24
	case "H2":
		b.GridsIdx = 39
	case "R1":
		b.GridsIdx = 5
	}
	b.Count[b.GridsIdx] += 1
}

func (b *Board) start() {
	b.GridsIdx = 0
	b.Count[0] += 1
}

func (b *Board) jail() {
	b.GridsIdx = 10
	b.Count[10] += 1
}
