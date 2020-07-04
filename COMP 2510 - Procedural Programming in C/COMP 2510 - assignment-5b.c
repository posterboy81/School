/*
	COMP 2510 / Assignment 5
	Matthew Simpson / A00820997
*/

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

#define LINESIZE 1024

int main(int argc, char *argv[]){

	FILE * input; 
	char line[LINESIZE];
	char newid[LINESIZE];
	int temp;
	int total = 0, average = 0;
	int counter = 0;
	
	
	if(argc == 2){
		input = fopen(argv[1], "r");
	}
	if(input == NULL){
		printf("%s", "could not open");
		return 1;
	}
	
	while(fgets(line, LINESIZE, input) != NULL){
		sscanf(line, "%s %d", newid, &temp);
		counter++;
		total += temp;
		average = total / counter;
		printf("%s %s %d\n", newid, " ", temp);
		printf("%s %d\n", "average score: ", average);
	}
	
	fclose(input);
	
	return 0;
	
}
