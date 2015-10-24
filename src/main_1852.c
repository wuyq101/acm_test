#include <stdio.h>

#define MAX 1000000

int max(int a, int b){
    return a>b ? a : b;
}

int min(int a, int b){
    return a<b ? a : b;
}

void solve(int l, int n, int p[]){
    int minT=0,maxT=0;
    for(int i=0; i<n; i++){
        minT = max(minT, min(p[i],l-p[i]));
        maxT = max(maxT, max(p[i],l-p[i]));   
    }
    printf("%d %d\n", minT, maxT);
}

int main(){
    int m,l,n,p[MAX];
    scanf("%d",&m);
    for(int i=0; i<m; i++){
        scanf("%d %d", &l, &n);
        for(int j=0; j<n; j++){
            scanf("%d",&p[j]);
        }
        solve(l,n,p);
    }
    return 0;
}

