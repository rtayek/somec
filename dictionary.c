// Implements a dictionary's functionality

#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <string.h>
#include <cs50.h>
#include "dictionary.h"

int total_words;

// Represents a node in a hash table
typedef struct node
{
    char word[LENGTH + 1];
    struct node *next;
}
node;

// TODO: Choose number of buckets in hash table
const unsigned int N = 26;

// Hash table
node *table[N];

// Returns true if word is in dictionary, else false
bool check(const char *word)
{

        // check for null target
        if(word==NULL)
        {
            return false;
        }
        // get the index
        int i=hash(word);
        for(node *n=table[i];n->next!=NULL;n=n->next)
        {
            if(strcasecmp(word,n->word)==0)
            {
                return true;
            }
        }
        return false;
}

// Hashes word to a number
unsigned int hash(const char *word)
{
    // TODO: Improve this hash function
    //used base function from short on hash tables
    int sum = 0;
    for (int j = 0; word[j] != '\0'; j++)
    {
       sum += tolower(word[j]) - 'a';
    }
    return sum/strlen(word) % N;
}

// Loads dictionary into memory, returning true if successful, else false
bool load(const char *dictionary)
{
    total_words = 0;
    char word[LENGTH + 1];
    //open file
    FILE *f_read_stream = fopen(dictionary, "r");
    //check if file is NULL
    if (f_read_stream == NULL)
    {
        return 1;
    }

    while (fscanf(f_read_stream, "%s", word) != EOF)
    {
        // get memory and do saftey check
        node *n = malloc(sizeof(node));
        n->next=NULL;
        n->word[0]='\0';
        if (n == NULL)
        {
            return false;
        }
    strcpy(n->word, word);
    //insert node into hashtable
    int bucket = hash(word);
    node *list = table[bucket];
        if(list != NULL)
        {
            n->next = list; // point next to what root points to.

        }
    table[bucket] = n;
    total_words++;
    }
    fclose(f_read_stream);
    return true;
}

// Returns number of words in dictionary if loaded, else 0 if not yet loaded
unsigned int size(void)
{
    // TODO
    return total_words;
}

// Unloads dictionary from memory, returning true if successful, else false
bool unload(void)
{
    for(int i=0;i<N;i++)
    {
        for(node *n=table[i];n!=NULL;) // iterate over the nodes (if any are present).
        {
            node *next = n->next;
            free(n);
            n = next;
        }
    }
    return true;

}
