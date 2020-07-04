#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define IDSIZE 		10
#define NAMESIZE 	20
#define LINESIZE	1024
#define ARRAYSIZE 	100

/* ---------------------------------------------------------------- structures */

typedef struct {
	char last[NAMESIZE];
	char first[NAMESIZE];
} name;

typedef struct {
	char id[IDSIZE]; /* 'a' followed by 8 digits */
	name name;
	int score; /* between 0 and 100 inclusive */ 
} record;

/* ---------------------------------------------------------------- comparisons */ 


int cmp_ScoreDesc_IdAsc(const void *a, const void *b){
    const record *pp = a;
    const record *qq = b;
    int nScore = qq->score - pp->score;
    if(nScore != 0){
    	return nScore;
    }else{
	    return strcmp(pp->id, qq->id);
    }
}

int cmp_ScoreDesc_NameAsc(const void *a, const void *b){
	const record *pp = a;
	const record *qq = b;
	int nName = strcmp(pp->name.last,qq->name.last);
	int nScore = qq->score - pp->score;
	if(nScore != 0){
		return nScore;
	} else {
		return nName;
	}
}

int cmp_ScoreDesc_NameAsc_IdAsc(const void *a, const void *b){
	const record *pp = a;
	const record *qq = b;
	int nScore 	= qq->score - pp->score;
	int lName 	= strcmp(pp->name.last, qq->name.last);
	int fName 	= strcmp(pp->name.first, qq->name.first);
	int id 		= strcmp(pp->id, qq->id);
	
	if(nScore != 0){
		return nScore;
	} else if(lName != 0 && fName != 0){
		return lName;
	} else {
		return id;
	}   
}


/* ---------------------------------------------------------------- functions */ 

/* 	sorts the array in descending order of scores. 
	if there are multiple same scores, sort in order 
	ascending by ID */ 
void sort_score_desc_id_asc(record a[], size_t n) {
	qsort(a, n, sizeof(a[0]), cmp_ScoreDesc_IdAsc);
}

/* 	sorts the array in descending order of scores.
	if there are multiple same scores, sort in order 
	ascending ny names. */ 
void sort_score_desc_name_asc(record a[], size_t n) {
	qsort(a, n, sizeof(a[0]), cmp_ScoreDesc_NameAsc);
}

/* 	sorts the array in descending order of scores. 
	if there are multiple, also sort ascending order 
	by names. if there are multiple name matches (f & l)
	sort in ascending order by ID */ 
void sort_score_desc_name_asc_id_asc(record a[], size_t n){
	qsort(a, n, sizeof(a[0]), cmp_ScoreDesc_NameAsc_IdAsc);
}

/* ---------------------------------------------------------------- read the data */ 

record read_data(char inputline[]){
	
	record newrecord;
	char id_temp[LINESIZE];
	char fname_temp[LINESIZE];
	char lname_temp[LINESIZE];
	int score_temp;
	size_t i;
	
	if(sscanf(inputline, "%s %s %s %d", id_temp, lname_temp, fname_temp, &score_temp) < 4){
		printf("%s %s %s", "this record is invalid", " ", inputline);
	}
	
	/* check the id */
	
	if(id_temp[0] == 'a'){
		if(strlen(id_temp) == 9){
			for(i =1; i <10; i++){
				if(!(isdigit(id_temp[i]) == 0)){
					strcpy(newrecord.id, id_temp);
				}
			}
		}
	}
	
	/* check the names */
	
	if((strlen(lname_temp) < NAMESIZE) || (strlen(fname_temp) < NAMESIZE)){
		strcpy(newrecord.name.last, lname_temp);
		strcpy(newrecord.name.first, fname_temp);
	}
	
	/* check the score */ 
	
	if(score_temp > 0 || score_temp < 100){
		newrecord.score = score_temp;
	}
	
	return newrecord;
}

void print_record(const record printrecord){
	printf("%d, %s, %s, %s\n", printrecord.score, printrecord.name.last, printrecord.name.first, printrecord.id);
}
	


/* ----------------------------------------------------------------main method */

int main(int argc, char *argv[]){
	
	FILE 	*inputfile = NULL;
	record recordArray[ARRAYSIZE];
	char newline[LINESIZE];
	size_t c1 = 0;
	size_t c2 = 0;
	

	if(argc == 2){
		inputfile = fopen(argv[1], "r");
	}	
	if(inputfile == NULL){
		printf("%s", "could not open");
		return 1;
	}
 	printf("%s\n", "------------------------------------input");
	while(fgets(newline, LINESIZE, inputfile) != NULL){
		printf("%s\n", newline);
		recordArray[c1] = read_data(newline);
		c1++;	
	}
 	
 	printf("%s\n", "------------------------------------print score desc, id asc");
	sort_score_desc_id_asc(recordArray, c1);
 	
	for(c2 = 0; c2 < c1; c2++){
		print_record(recordArray[c2]);
	}

 	printf("%s\n", "------------------------------------print score desc, name asc");
	sort_score_desc_name_asc(recordArray, c1);
 	
	for(c2 = 0; c2 < c1; c2++){
		print_record(recordArray[c2]);
	}

	printf("%s\n", "------------------------------------print score desc, name asc, ID asc");
	sort_score_desc_name_asc_id_asc(recordArray, c1);
 	
	for(c2 = 0; c2 < c1; c2++){
		print_record(recordArray[c2]);
	}

	return 0;
}
