#include <stdio.h>

struct Piece{
	char type; //type char ‘G’ for general, ‘R’ for chariot, ‘H’ for horse and ‘C’ for cannon
	int r;
	int c;
};

	int N;
	struct Piece blackGeneral;
	struct Piece red[7];



int dr[4] = {0,-1,0,1};
int dc[4] = {-1,0,1,0};

int checkGeneral(int r, int c){
	if(c!=blackGeneral.c){
		return 0;
	}
	// 是否对头老将，检查c列上是否有其他棋子,在两个老将之间
	for (int i=0;i<N;i++){
		if (red[i].c==c && red[i].r>blackGeneral.r && red[i].r<r){
				return 0;
		}		
	}
	return 1;
}

// 检查车，是否可以吃到老将
int checkChariot(int r, int c){
	if (r==blackGeneral.r){
		// 在同一行	
		// 检查在老将和车之间是否有其他棋子
	}
	if (c==blackGeneral.c){
		// 在同一列
	}
	return 0;
}

int checkCannon(int r,int c){
	// 检测炮，是否可以吃到老将
	return 1;
}

int checkHorse(int r,int c){
	return 1;
}

int check(){
	// 检查红色的棋子是否会继续将军
	for(int i=0;i<N;i++){
		if (red[i].type=='G'){
			if (checkGeneral(red[i].r, red[i].c)){
				return 1;
			}
		}
		if (red[i].type=='R'){
			if (checkChariot(red[i].r, red[i].c)){
				return 1;
			}
		}
		if (red[i].type=='H'){
			if (checkHorse(red[i].r, red[i].c)){
				return 1;
			}
		}
		if (red[i].type=='C'){
			if (checkCannon(red[i].r, red[i].c)){
				return 1;
			}
		}
	}
	return 0;
}

int solve(){
	// 黑色将军的行走路径 r=1,2,3 c=4,5,6
	// 检查黑色将军走一格之后，红色的棋子是否会继续将军
	int preR = blackGeneral.r;
	int preC = blackGeneral.c;
	for (int k=0;k<4;k++){
		blackGeneral.r = preR+dr[k];
		blackGeneral.c = preC+dc[k];
		if (blackGeneral.r>=1 && blackGeneral.r<=3 && blackGeneral.c>=4 && blackGeneral.c<=6) {
			int result = check();
			if (result==0){
				printf("NO\n");
				return 0;
			}
		}
	}
	printf("YES\n");
	return 1;
}

/*
 * http://poj.org/problem?id=4001
 */
int main(){
	while(1) {
		blackGeneral.type = 'G';
		scanf("%d%d%d", &N, &blackGeneral.r, &blackGeneral.c);
	//	printf("red cnt %d\n", N);
	//	printf("black general %c at %d %d\n", blackGeneral.type, blackGeneral.r, blackGeneral.c);
		if (N==0 && blackGeneral.r==0 && blackGeneral.c==0) {
			break;
		}

		// 换行符
		char tmp;
		scanf("%c",&tmp);

		for(int i=0;i<N;i++){
			scanf("%c%d%d\n",&red[i].type, &red[i].r, &red[i].c);
		}

		/*
	for(int i=0;i<N;i++){
		printf("red %c at %d %d\n",redPieces[i].type,redPieces[i].r,redPieces[i].c);
	}
	*/

	solve();
		
	}
	return 0;
}


