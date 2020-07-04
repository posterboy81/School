/*
	COMP 2510 / Assignment 4
	Matthew Simpson / A00820997
*/

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

#define LINESIZE 1024
FILE *output;
char word[LINESIZE];

int get_word(const char prompt[], char word[], size_t n);
int is_valid_id(const char s[]);


/* 
	main function 
	it mostly works, and it can write to a specified file, 
	i just haven't gotten it looping yet (without getting 
	stuck in an infinite loop, that is)	
*/

int main(int argc, char *argv[]){
	int counter = 0;
	char inputprompt[] = "enter a word: ";
	
	if(argc == 2){
		output = fopen(argv[1], "w");
	}
	
	while(1){
		printf("\n%s %s\n", "THE WORD IS ", word);
		get_word(inputprompt, word, 9);
		printf("%s %s\n", "THE WORD IS ", word);
		if(is_valid_id(word) == 1){
			counter++;
			printf("%s %s\n", word, " is valid, writing to file");
			printf("%d %s %s\n", counter, ": ", word);
			fprintf(output, "%d %s %s\n", counter, ": ", word);
		}
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


	printf("%s\n", "START GET_WORD");

	while(1){
		printf("%s", prompt);
		
		if(fgets(input, LINESIZE, stdin) != NULL){
			if(strcmp(input, quitword) > 0){
				fclose(output);
				exit(0);
			}
			
			printf("%s %s\n", "you entered: ", input);
			printf("%s %ld %s\n", "this word has ", strlen(input) -1, " characters");
			clearerr(stdin);
			
			if(sscanf(input, "%s", temptext)==1){
				if(strlen(temptext) == n){
					printf("%s\n", temptext);
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
