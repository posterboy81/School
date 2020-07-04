#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define IDSIZE 		10
#define NAMESIZE 	20
#define BLOCK 		3

/* --------------------------------structures */

typedef struct {
	char last[NAMESIZE];
	char first[NAMESIZE];
} name;

typedef struct {
	char id[IDSIZE];
	name name;
	int score;
} record;

typedef struct {
	record *data; 
	size_t nalloc;
	size_t nused;
} record_list;

/* --------------------------------functions */ 

/* INITIALIZE THE LIST */ 	
void list_init(record_list *plist){
	plist->data 		= 0;
	plist->nalloc 		= 0;
	plist->nused		= 0;
}

/* CLEAR THE LIST OF ALL RECORDS */
void list_clear(record_list *plist){
	free(plist->data);
	plist->data 		= 0;
	plist->nalloc		= 0;
	plist->nused		= 0;
}

/* INSERT A NEW RECORD AND ALLOCATE MEMORY AS NECESSARY */ 
int list_insert(record_list *plist, const record *prec){
	if(plist->nalloc == plist->nused){
		record *temporary 	= realloc(plist->data, (plist->nalloc + BLOCK) * sizeof(record));
		if(temporary == 0){
			fprintf(stderr, "memory allocation failed!");
			return 0;
		}
		plist->data = temporary;
		plist->nalloc = (plist->nalloc + BLOCK);	 
	}
	plist->data[plist->nused] = *prec;
	printf("%s %s %s %s %d\n", "adding: ", prec->id, prec->name.first, prec->name.last, prec->score);
	plist->nused++;
	return 1;
}


int main(void){

/* --------------------------------hard coded data */
	
	record_list newList;
	record_list *pNewList;

	record hSimpson 	= {"a11111111", {"homer", "simpson"}, 65};
	record *pHomer;
	record mSimpson 	= {"a22222222", {"marge", "simpson"}, 90};
	record *pMarge;
	record nFlanders 	= {"a33333333", {"ned", "flanders"}, 95};
	record *pNed;
	record mFlanders	= {"a44444444", {"maude", "flanders"}, 100};
	record *pMaude;
	
	pNewList			= &newList;
	pHomer 				= &hSimpson;
	pMarge 				= &mSimpson;
	pNed 				= &nFlanders;
	pMaude 				= &mFlanders;
	
/* --------------------------------use the functions */

	list_init(pNewList);
	
	list_insert(pNewList, pHomer);
	printf("%s %ld\n", "number of spots allocated: ", pNewList->nalloc);
	printf("%s %ld\n", "number of spots used: ", pNewList->nused);
	list_insert(pNewList, pMarge);
	printf("%s %ld\n", "number of spots allocated: ", pNewList->nalloc);
	printf("%s %ld\n", "number of spots used: ", pNewList->nused);
	list_insert(pNewList, pNed);
	printf("%s %ld\n", "number of spots allocated: ", pNewList->nalloc);
	printf("%s %ld\n", "number of spots used: ", pNewList->nused);
	list_insert(pNewList, pMaude);
	printf("%s %ld\n", "number of spots allocated: ", pNewList->nalloc);
	printf("%s %ld\n", "number of spots used: ", pNewList->nused);

	printf("%s\n", "CLEARING THE LIST");
	list_clear(pNewList);
	printf("%s %ld\n", "number of spots allocated: ", pNewList->nalloc);
	printf("%s %ld\n", "number of spots used: ", pNewList->nused);
	

	return 0;
}
