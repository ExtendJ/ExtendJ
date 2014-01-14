#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <ctype.h>

#define BUFFER_START 100
#define REFERENCE_SEPARATOR ":REF:"

static void *xrealloc(void *ptr, size_t size)
{
	ptr = realloc(ptr, size);
	if(!ptr) {
		fprintf(stderr, "Out of memory\n");
		exit(1);
	}
	return ptr;
}

static void *xmalloc(size_t size)
{
	void *ptr = malloc(size);
	if(!ptr) {
		fprintf(stderr, "Out of memory\n");
		exit(1);
	}
	return ptr;
}

static size_t locate_last_nonspace(char *text, size_t start_pos)
{
	size_t pos;
	if(start_pos == 0)
		return start_pos;
	pos = start_pos - 1;
	while(isspace(text[pos]) && pos != 0) 
		pos--;
	
	return pos;
}

static void locate_array_start(char *text, size_t *current_pos)
{
	if(*current_pos == 0)
		return;

	*current_pos = *current_pos - 1;
	while(text[*current_pos] != '[' && *current_pos != 0) 
		*current_pos = *current_pos - 1;

	return;
		
	
}

static void locate_generic_start(char *text, size_t *current_pos) 
{
	if(*current_pos == 0)
		return;
	while(1) {
		*current_pos = *current_pos - 1;
		if(*current_pos == 0) 
			return;
		else if(text[*current_pos] == '<')
			return;
		else if(text[*current_pos] == '>') 
			locate_generic_start(text, current_pos);
		
	}
}

static void locate_generic_method_references(char *text, size_t **insert_indexes)
{	
	size_t current_size = BUFFER_START;
	size_t i = 0;
	size_t current_pos;
	size_t buffer_pos = 0;
	*insert_indexes = xmalloc(sizeof(size_t) * current_size);
	(*insert_indexes)[0] = 0;
	while(text[i + 2]) {
		if(text[i] == ':' && text[i+1] == ':' && text[i+2] != ':') {
			current_pos = locate_last_nonspace(text, i);
			while(text[current_pos] == ']') {
				locate_array_start(text, &current_pos);
				if(current_pos == 0)
					return;
				current_pos = locate_last_nonspace(text, current_pos);
			}

			if(text[current_pos] == '>') {
				locate_generic_start(text, &current_pos);
				if(current_pos == 0)
					return;
				else if(text[current_pos] == '<') {
					if(buffer_pos + 1 == current_size) {
						current_size *= 2;
						*insert_indexes = xrealloc(*insert_indexes, sizeof(size_t) * current_size);
					}
					(*insert_indexes)[buffer_pos] =  current_pos;
					buffer_pos++;
					(*insert_indexes)[buffer_pos] = 0;
				}
				
			}
			else {
				i++;
			}
		}
		i++;
	}
}

int main(int argc, char *argv[])
{
	FILE *f;
	size_t current_size;
	char *text;
	size_t index;
	int character;
	size_t i;
	size_t ref_index = 0;
	size_t *insert_indexes;

	if(argc != 3) {
		fprintf(stderr, "Error, incorrect input arguments\n");
		exit(1);
	}

	f = fopen(argv[1], "r");
	if(!f) {
		fprintf(stderr, "Error, incorrect input file path\n");
		exit(1);
	}

	current_size = BUFFER_START;
	text = xmalloc(current_size);
	index = 0;

	while((character = fgetc(f)) != EOF) {
		text[index] = character;
		index++;
		if(index == current_size) {
			current_size *= 2;
			text = xrealloc(text, current_size);
		}
	}

	fclose(f);
	text[index] = '\0';
	if(index < 2) 
		return 0;

	f = fopen(argv[2], "w");

	locate_generic_method_references(text, &insert_indexes);

	for(i = 0; i < index; i++) {
		if(i == insert_indexes[ref_index] && insert_indexes[ref_index] != 0) {
			fprintf(f, "%s", REFERENCE_SEPARATOR);
			ref_index++;
		}
		fprintf(f, "%c", text[i]);
	}

	fclose(f);

	free(insert_indexes);
	free(text);
	return 0;
}