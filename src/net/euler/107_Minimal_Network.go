package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	N := 40
	G := makeGraph(N)

	buf, err := os.ReadFile("107_network.txt")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(string(buf), "\n")
	for i, line := range lines {
		if len(line) == 0 {
			continue
		}
		strs := strings.Split(line, ",")
		for j, str := range strs {
			if str == "-" {
				continue
			}
			w, _ := strconv.Atoi(str)
			G[i][j] = w
		}
	}
	printGraph(G)
	S := sum(G)
	fmt.Printf("sum=%d\n", S)
	T := bfs(G)
	fmt.Printf("save %d\n", S-T)
}

func bfs(G [][]int) int {
	total := 0
	visited := make([]bool, len(G))
	visited[0] = true
	N := len(G)
	edges := make([]Edge, 0)
	for i := 1; i < N; i++ {
		if G[0][i] > 0 {
			e := Edge{from: 0, to: i, weight: G[0][i]}
			edges = append(edges, e)
		}
	}
	for len(edges) > 0 {
		// 找到最小的边
		idx, e := findMinEdge(edges)
		edges = append(edges[:idx], edges[idx+1:]...)
		total += e.weight
		if !visited[e.to] {
			visited[e.to] = true
		}
		G[e.from][e.to] = 0
		G[e.to][e.from] = 0
		edges = filterEdges(edges, visited)
		// 添加新的边, 从to到其他点
		for i := 0; i < N; i++ {
			if visited[i] {
				continue
			}
			if G[e.to][i] > 0 {
				next := Edge{from: e.to, to: i, weight: G[e.to][i]}
				if !hasEdge(edges, next) {
					edges = append(edges, next)
				}
			}
		}
		// 过滤掉已经遍历过的点之间的边
		edges = filterEdges(edges, visited)
		// 检查是否所有点都已经遍历
		allVisited := true
		for i := 0; i < N; i++ {
			if !visited[i] {
				allVisited = false
				break
			}
		}
		if allVisited {
			break
		}
	}
	fmt.Printf("total=%d\n", total)
	return total
}

func filterEdges(edges []Edge, visited []bool) []Edge {
	result := make([]Edge, 0, len(edges))
	for _, edge := range edges {
		if visited[edge.from] && visited[edge.to] {
			continue
		}
		result = append(result, edge)
	}
	return result
}

func hasEdge(list []Edge, e Edge) bool {
	for _, edge := range list {
		if edge.Equal(e) {
			return true
		}
	}
	return false
}

func contains(list []int, v int) bool {
	for _, n := range list {
		if n == v {
			return true
		}
	}
	return false
}

func findMinEdge(list []Edge) (int, Edge) {
	min := list[0]
	idx := 0
	for i := 1; i < len(list); i++ {
		if list[i].weight < min.weight {
			min = list[i]
			idx = i
		}
	}
	return idx, min
}

type Edge struct {
	from   int
	to     int
	weight int
}

func (e Edge) String() string {
	return fmt.Sprintf("from=%d, to=%d, weight=%d", e.from, e.to, e.weight)
}

func (e Edge) Equal(a Edge) bool {
	return (e.from == a.from && e.to == a.to) || (e.to == a.from && e.from == a.to)
}

func makeGraph(N int) [][]int {
	G := make([][]int, N)
	for i := 0; i < N; i++ {
		G[i] = make([]int, N)
	}
	return G
}

func sum(G [][]int) int {
	s := 0
	N := len(G)
	for i := 0; i < N; i++ {
		for j := i + 1; j < N; j++ {
			s += G[i][j]
		}
	}
	return s
}

func printGraph(G [][]int) {
	for i := 0; i < len(G); i++ {
		for j := 0; j < len(G[i]); j++ {
			fmt.Printf("%d ", G[i][j])
		}
		fmt.Printf("\n")
	}
}
