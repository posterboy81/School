/*
	COMP 2510 / Assignment 5
	Matthew Simpson / A00820997
*/

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

#define LINESIZE 1024
#define IDSIZE 10

FILE *output;
char word[LINESIZE];
char id[IDSIZE];
char inputidprompt[] = "enter a word: ";
char inputintprompt[] = "enter an int: ";

int i; 
int *p = &i;

int get_word(const char prompt[], char word[], size_t n);
int is_valid_id(const char s[]);
int get_id(char id[IDSIZE]);
int get_int(const char prompt[], int *p);
int get_score();



/* 
	main function 
	it mostly works, and it can write to a specified file, 
	i just haven't gotten it looping yet (without getting 
	stuck in an infinite loop, that is)	
*/

int main(int argc, char *argv[]){
	
	
	if(argc == 2){
		output = fopen(argv[1], "w");
	}
	
	while(1){
		get_id(word);		
		get_score();
		printf("%s %s\n", word, " is valid, writing to file");
		printf("%s %s %3d", ": ", word, i);
		fprintf(output, "%s %s %3d\n", word, " ", i);
		
	}	
	
	fclose(output);
	return 0;
}


/* 
	If a word of less than length n is entered, that word is 
	stored in the array word[] and function returns 1.
	If the user presses end-of-file key, function returns 0.
*/

int get_word(const char prompt[], char word[], size_t n){
	
	char input[LINESIZE];
	char temptext[LINESIZE];
	char quitword[] = "quit";
	
	while(1){
		printf("%s", prompt);
		if(fgets(input, LINESIZE, stdin) != NULL){
			if(strcmp(input, quitword) > 0){
				fclose(output);
				exit(0);
			}
			clearerr(stdin);
			
			if(sscanf(input, "%s", temptext)==1){
				if(strlen(temptext) == n){
					strcpy(word, temptext);				
					return 1;
				}
			}
		}	
	}
	return 0;
}

/*
	checks if a string starts with an A or an a and also if the 
	string is fewer than 10 characters long. 
	
	I ran out of time so the validation is not complete. 
*/

int is_valid_id(const char s[]){
	size_t i;
	int digitcheck;
	printf("%s\n", "START IS_VALID_ID");
	printf("%ld\n", strlen(s));
	if(strlen(s) == 9){ /* is the string length 9?*/
		printf("%s\n", "id is correct length");
		if(s[0] == 'a' || s[0] == 'A'){ /* does the string start with an a */
			printf("%s\n", "starts with an a");
				for(i = 1; i < strlen(s); i++){ /* check the rest of the characters are digits*/
					digitcheck = isdigit(s[i]);
					if(digitcheck < 1){
						printf("%s %ld %s\n", "character ", i+1, " is not a digit");	
						return 0;
					} else {
						printf("%s %ld %s\n", "character", i+1, " is valid");
					}
				}
				if(digitcheck > 0){
						return 1;
				}
		}else {
			printf("%s\n", "doesn't start with an a");
			return 0;
		}			
	} else {
		printf("%s\n", "id is wrong length");
		return 0;
	}	
	return 0;	
}

/*
	combines get_word() and is_valid_id() into one  function. 
*/
int get_id(char id[IDSIZE]){

	printf("\n%s %s\n", "THE WORD IS ", word);
	get_word(inputidprompt, id, 9);
	printf("%s %s\n", "THE WORD IS ", id);
	if(is_valid_id(word) == 1){
		return 1;
	}
	return 0;
}

/*
	if an integer is successfully read from stdin, it is passed back to the 
	caller via pointer *p & the function returns 1. if the user presses the 
	end-of-FILE key the function returns 0.
*/
int get_int(const char prompt[], int *p){ 
	char input[IDSIZE];
	int a = 0;
	printf("%s\n", prompt);
	
	if(fgets(input, IDSIZE, stdin) != NULL){
		a = sscanf(input, "%d", p);
		printf("%d", a);
		clearerr(stdin);
	}
	return 0;
}

/*
	get an int and turn it into a score. 
*/

int get_score(){
	int min = 0, max = 100;
	
	get_int(inputintprompt, p);
	
	if(min <= *p && *p <= max){
		return *p;
	}
	
	return 0;
}

