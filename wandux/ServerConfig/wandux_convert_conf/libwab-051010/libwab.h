#ifndef LIBWAB_H
#define LIBWAB_H
#include "pstwabids.h"
#include <stdio.h>

//{{{ debug stuff

#define DB_LDIF_OUT  1 << 0 //print ldif output to stdout (default)
#define DB_DATA_DUMP 1 << 1 //dump everything we load
#define DB_VERBOSE   1 << 2 //loud
#define DB_VERBOSE2  1 << 3 //very loud
#define DB_VERBOSE3  1 << 4 //very loud indeed
#define DB_DITCH     1 << 5 //quit when we might identify new data (make program better)
#define DB_LOW_LEVEL 1 << 6 //print low-level call info (offset in file IO useful)

extern int dodebug;

#define DEBUG(x, y) if( (dodebug & x) ){ y; fflush(stdout); }
// }}}
//{{{ constants

#define TABLE_COUNT 6 

#define STR_SIZE 0x40
#define IDX_SIZE 4

#define OFF_BIG_DUMP 0x2b050

#define TYPE_IDX 4000
#define TYPE_TXT 34000
// }}}

//Structures from the WAB format

typedef unsigned int t_recordid; //record id

struct tabledesc //{{{ These tell us about the lookup tables
{
	int type;
	int size;
	int offset;
	int count;
};
// }}}
struct wab_header // {{{ The Header at the start of the WAB file
{
	char magic[16]; //seems to always be the same, don't know
	int  count1;
	int  count2;
	struct tabledesc tables[TABLE_COUNT];
};
// }}}
struct wab_handle // {{{ returned from open_wab() and closed by close_wab()
{
	FILE *fp;
	struct wab_header wabhead;
};
// }}}
struct txtrecord // {{{
{
	char str[STR_SIZE];
	t_recordid recid;
};
// }}}
struct idxrecord // {{{
{
	t_recordid recid;
	int offset;
};
// }}}

#define MYSTERY_COUNT 30

struct rec_header // {{{
{
	unsigned int mystery0; //seems to be 0x1 and becomes 0x0 when the record is deleted?
	unsigned int mystery1; //seems to always be 0x1
	unsigned int recid;
	unsigned int opcount;
	unsigned int mystery4; //always seems to be 0x20
	unsigned int mystery5;
	unsigned int mystery6;
	unsigned int datalen;
};
// }}}
struct op_data // {{{
{
	int *len;    //the length (in bytes) of the actual data)
	int *acnt;   //if the data is an array then this points to the element count
	void *data;
};
// }}}
struct wab_record // {{{
{
	struct rec_header rhead;
	void *fdata; //that weird '1' element
	int *oplist;
	void *data;
	struct op_data *opdata;
};
// }}}

//some forward declearations
void use();

//low-level
int rread( void *buf, int size, FILE *fp );

//open/close
struct wab_handle *open_wab( char *filename );
int close_wab( struct wab_handle *wh );

int do_heuristic( char *path );

//dumping functions
void dump_records( struct wab_handle *wh );
void dump_index( struct wab_handle *wh );
void dump_wab_header( struct wab_handle *wh );
void dump_table( int table_id, struct wab_handle *wh );
void dump_rec_head( struct rec_header *rhead ); 
void dump_wab_record( struct wab_record* mrec );
void dump_opdata( int opno, struct wab_record *wrec );

void cheap_uni2ascii(char *src, char *dest, int l);

//wab_record -> ldif
int write_ldif( FILE *dest, struct wab_record *mrec );

//read sections
int read_idxrec( struct idxrecord *irec, FILE *fp );
int read_txtrec( struct txtrecord *trec, FILE *fp );
int read_wab_record( struct wab_record *mrec, FILE *fp );

//primitive print 
void print_unicode_str( FILE*fp, char *buf );
void binary_print( FILE *fp, unsigned char *buf, int len );
void dump_string_table(char*, unsigned int, unsigned int);

//heuristics
void seek_to_probable_record( FILE *fp );

#endif
