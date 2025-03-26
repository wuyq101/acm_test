package main

func Unique[T comparable](list []T) []T {
	m := make(map[T]struct{}, len(list))
	for _, v := range list {
		m[v] = struct{}{}
	}
	u := make([]T, 0, len(m))
	for v := range m {
		u = append(u, v)
	}
	return u
}
