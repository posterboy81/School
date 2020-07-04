#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define IDSIZE 10
#define NAMESIZE 20
#define LINESIZE 1024

typedef struct {
	char lastname[NAMESIZE]; 
	char firstname[NAMESIZE];	
} name;

typedef struct {
	char id[IDSIZE]; 	
	name name;
	int score; 	
} record;

typedef struct node node;
struct node {
	record nodedata;
	struct node *next;
};

/*	singly linked list of records represented by a pointer to its first node */ 
typedef node *record_list;
						
/* --------------------------------------------------functions */

/* 	initializes the record list specified via *plist to an 
	empty list. called once when a record_list is created and 
	before it is used */ 
void list_init(record_list *plist){
	*plist = NULL;
}

/* 	called when we want to empty out a record_list.  needs to 
	deallocate all memory associated with the record_list and 
	reset it back to an empty state */ 
void list_clear(record_list *plist){
	node *p, *q;
	for(p = *plist; p != NULL; p=q){
		q = p->next;
		free(p);
	}
	list_init(plist);
}

/* 	Inserts a new record (*precord) into the specified list (*plist)
	in sorted order. The order is descending by score, followed by 
	ascending by name (last, then first), and then ID.
	Function allocates a new node, copies the record into the node, 
	looks for the right spot to insert the node, and inserts it.
	Returns 1 if successful and 0 if not. */
int list_insert(record_list *plist, const record *precord){
	node **tracer;
	node *newnode = malloc(sizeof(node));
	int compare;

	if(newnode == NULL){
		return 0;
	}
	newnode->nodedata = *precord;
	
	for(tracer = plist; *tracer != NULL; tracer = &(*tracer)->next){
	
		compare = newnode->nodedata.score - (*tracer)->nodedata.score;		
		if(compare > 0){
			break;
		} else if(compare == 0){
			compare = strcmp((*tracer)->nodedata.name.lastname, newnode->nodedata.name.lastname);
		}
		
		if(compare > 0){
			break;
		} else if(compare == 0){
			compare = strcmp((*tracer)->nodedata.name.firstname, newnode->nodedata.name.firstname);
		}
		
		if(compare > 0){
			break;
		} else if(compare == 0){
			compare = strcmp((*tracer)->nodedata.id, newnode->nodedata.id);
		}	
		
		if(compare > 0){
			break;
		}
	}
	newnode->next = *tracer;
	*tracer = newnode;
	return 1;

}

/* --------------------------------------------------read/print data */

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
	
	if(id_temp[0] == 'a'){
		if(strlen(id_temp) == 9){
			for(i =1; i <10; i++){
				if(!(isdigit(id_temp[i]) == 0)){
					strcpy(newrecord.id, id_temp);
				}
			}
		}
	}

	if((strlen(lname_temp) < NAMESIZE) || (strlen(fname_temp) < NAMESIZE)){
		strcpy(newrecord.name.lastname, lname_temp);
		strcpy(newrecord.name.firstname, fname_temp);
	}

	if(score_temp > 0 || score_temp < 100){
		newrecord.score = score_temp;
	}
	
	return newrecord;
}

void print_list(const record_list printlist){
	record_list temp;
	
	for(temp = printlist; temp != NULL; temp = temp->next){
		printf("%d, %s, %s, %s\n", temp->nodedata.score, temp->nodedata.name.lastname, temp->nodedata.name.firstname, temp->nodedata.id);
	}

}


/* --------------------------------------------------main function */

int main(int argc, char *argv[]){
	
	FILE 	*inputfile = NULL;
	char 	newline[LINESIZE];
	record newrecord;
	record_list newlist;
	
	
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
			newrecord = read_data(newline);
			list_insert(&newlist, &newrecord);
	}
	
	printf("%s\n", "------------------------------------output");

	print_list(newlist);
	
	return 0;
}

