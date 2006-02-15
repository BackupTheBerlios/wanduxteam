// {{{ #include <stuff>
#include "libwab.h"

#include <stdlib.h>
#include <strings.h>
#include <string.h>
#include <time.h>
// }}}

#define ASSERT(x)   if( !(x) ){ fprintf( stderr, "Assertion failed.\n"); exit(1); }
#define MAX_OPCOUNT 500 

//set this to one and heuristic will try to load *anything*
//for testing only.
#define STUPID 0

//int skip_crud=1;
int dodebug=1;
int rread_crash=1;

char *m_cheap_uni2ascii( char *src );

char *id_get_str2( int opcode ) // {{{
{
  static char buf[ 80 ];
  char *result = id_get_str( opcode );
  if( NULL == result )
  {
    sprintf( buf, "%s (0x%x)", "UNKNOWN OPCODE", opcode);
    result = buf;
  }

  return result;
}
// }}}
char *ldid_get_str2( int opcode ) // {{{
{
  static char buf[ 80 ];
  char *result = ldid_get_str( opcode );
  if( NULL == result )
  {
    sprintf( buf, "%s (0x%x)", "UNKNOWN FDIF ATTRIB", opcode );
    result = buf;
  }

  return result;
}
// }}}

void print_opdata( FILE *fp, int opno, struct wab_record *wrec, char *prefix, char *suffix );
int read_opdata( struct op_data *opdata, int opcode, void **p );

struct wab_handle *open_wab( char *filename ) // {{{
{
	struct wab_handle *wh;
	DEBUG( DB_VERBOSE, fprintf( stderr, "Loading MAB header...\n"); );

	if( NULL == ( wh = (struct wab_handle*)malloc( sizeof( struct wab_handle ) ) ) ) {
		fprintf( stderr, "open_wab(): Error malloc()ing the wab_handle\n");
		exit(1);
	}

	if( NULL == ( wh->fp = fopen( filename, "r" ) ) ) {
		fprintf( stderr, "open_wab(): Error opening file \"%s\".\n", filename );
		exit(1);
	}

	if( 1 < fread( &wh->wabhead, sizeof( struct wab_header ), 1, wh->fp ) ) {
		fprintf( stderr, "open_wab(): Error reading header.\n");
		exit(1);
	}

	return wh;
}
// }}}
int close_wab( struct wab_handle *wh ) // {{{
{
	fclose( wh->fp );
	free( wh );
	return 0;
}
// }}}
void dump_wab_header( struct wab_handle *wh ) // {{{
{
	int i;
	struct wab_header *whead = &wh->wabhead;

	fprintf( stderr, "Count 1: %d (0x%x)\n", whead->count1, whead->count1 );
	fprintf( stderr, "Count 2: %d (0x%x)\n", whead->count2, whead->count2 );

	for( i=0; i<TABLE_COUNT; i++ ) {
		fprintf( stderr, "Table %d\n", i );
		fprintf( stderr, "  type: %d (0x%x)\n", whead->tables[i].type, whead->tables[i].type );
		fprintf( stderr, "  size: %d (0x%x)\n", whead->tables[i].size, whead->tables[i].size );
		fprintf( stderr, "  offset: %d (0x%x)\n", whead->tables[i].offset, whead->tables[i].offset );
		fprintf( stderr, "  count: %d (0x%x)\n", whead->tables[i].count, whead->tables[i].count );
	}
}
// }}}
void dump_table( int table_id, struct wab_handle *wh ) // {{{
{
	//int recno=0; //store the record number
	//char rrec[STR_SIZE], rec[STR_SIZE]; //store unicode string and ascii string
	//int idxno=0;
	//int thisoff=0;
	int i;

	struct txtrecord trec;
	struct idxrecord irec;

	ASSERT( table_id < TABLE_COUNT );

	struct tabledesc *tdesc = &wh->wabhead.tables[table_id];

	//seek to the start of the table
	if( -1 == fseek( wh->fp, tdesc->offset, SEEK_SET ) ) {
		fprintf( stderr, "Couldn't seek to table at offset %x\n.", tdesc->offset);
		exit(1);
	}

	//dump the table entries
	for( i = 0; i < tdesc->count; i++ ) {
		switch( tdesc->type ) {
			case TYPE_IDX:
				read_idxrec( &irec, wh->fp );
				break;

			case TYPE_TXT:
				read_txtrec( &trec, wh->fp );
				break;

			default:
				fprintf( stderr, "Unknown table type: %d (0x%x)\n", tdesc->type, tdesc->type);
				break;
		}
	}

}
// }}}
void rfseek( int off, FILE *fp ) // {{{ seek or die
{
	if( 0 != fseek( fp, off, SEEK_SET ) ) {
		fprintf( stderr, "Error seeking to %x\n", off );
		exit(1);
	}
}
// }}}
void dump_records( struct wab_handle *wh ) // {{{
{
	FILE *fp = wh->fp;
	int i,j;
	struct idxrecord irec;
	//struct rec_header mrec;
	int bookmark;
	int last=0;
	struct wab_record wrec;
	memset( &wrec, 0, sizeof( struct wab_record ) );

	for( i=0; i<TABLE_COUNT; i++ ) {
		if( wh->wabhead.tables[i].type == TYPE_IDX ) {
			rfseek( wh->wabhead.tables[i].offset, fp );

			for( j=0; j<wh->wabhead.tables[i].count; j++ ) {
				rread( &irec, sizeof( struct idxrecord ), fp );
				bookmark = ftell( fp );

				rfseek( irec.offset, fp );

				//TODO: read

				if( dodebug & DB_VERBOSE2 )
					fprintf( stderr, "\n*** Reading WAB entry %d of %d\n", j, wh->wabhead.tables[i].count);
				else if( dodebug & DB_VERBOSE2 )
					fprintf( stderr, "\n*** Reading WAB entry (%d/%d), id=0x%x at %x (from last: %x %x)\n",
					j, wh->wabhead.tables[i].count,
					irec.recid, irec.offset, irec.offset -
					last, (irec.offset - last)/2 );

				last = irec.offset;
				if( 0 != read_wab_record( &wrec, fp ) ) {
					fprintf(stderr, "Error reading WAB record.  Either the file is corrupt or libwab is broken.\n");

					fprintf(stderr, "If you think that libwab is screwing up then the author would just love a copy of your .wab file.\n");
				}
				DEBUG( DB_DATA_DUMP, dump_wab_record( &wrec ) );
				DEBUG( DB_LDIF_OUT, write_ldif( stdout, &wrec ); );


				rfseek( bookmark, fp );
			}
		}
	}
}
// }}}
void dump_wab_record( struct wab_record* mrec ) // {{{
{
	int i;
	for( i=0; i<mrec->rhead.opcount; i++) {
		DEBUG(DB_VERBOSE2, fprintf( stderr, "Dumping %d, %x\n", i, mrec->oplist[i]) );
		dump_opdata( i, mrec );
	}
}
// }}}
void dump_rec_head( struct rec_header *rhead ) // {{{
{
	fprintf( stderr, " Mystery 0: %d(0x%x)\n", rhead->mystery0, rhead->mystery0 );
	fprintf( stderr, " Mystery 1: %d(0x%x)\n", rhead->mystery1, rhead->mystery1 );
	fprintf( stderr, " Index No: %d(0x%x)\n", rhead->recid, rhead->recid );
	fprintf( stderr, " Opcount: %d(0x%x)\n", rhead->opcount, rhead->opcount );
	fprintf( stderr, " Mystery 4: %d(0x%x)\n", rhead->mystery4, rhead->mystery4 );
	fprintf( stderr, " Mystery 5: %d(0x%x)\n", rhead->mystery5, rhead->mystery5 );
	fprintf( stderr, " Mystery 6: %d(0x%x)\n", rhead->mystery6, rhead->mystery6 );
	fprintf( stderr, " Data length: %d(0x%x)\n", rhead->datalen, rhead->datalen );
}
// }}}
int read_wab_record( struct wab_record *wrec, FILE *fp ) // {{{
//wrec->oplist MUST be malloced() or NULL
//realloc() is used here, you must free()
{
	int i;
	struct rec_header *rhead;
	void *p;
	rhead = &wrec->rhead;

	// First we read the header
	int res = rread( rhead, sizeof( struct rec_header ), fp );
	if( 0 != res ) return res;

	//some consistancy checks pulled directly from my ass.
	if( rhead->mystery4 != 0x20 ) {
		fprintf( stderr, "THERE'S A RECORD IN HERE WITH MYSTERY4 != 20!!!\n");
		DEBUG(DB_DITCH, exit(1););
	}

	if( rhead->mystery5 != rhead->mystery6 ) {
		fprintf( stderr, "THERE'S A RECORD IN HERE WITH MYSTERY5 != MYSTERY6!!!\n");
		DEBUG(DB_DITCH, exit(1);)
	}

	DEBUG(
		DB_DATA_DUMP,
		dump_rec_head( &wrec->rhead )
	     );

	if( rhead->opcount > MAX_OPCOUNT ) {
		fprintf(stderr, "ERROR: opcount (%d) is larger than MAX_OPCOUNT (%d).  Probable corruption.\n",
			rhead->opcount, MAX_OPCOUNT );
		return -1;
	}

	// Now we load the opcode list
	// realloc it
	DEBUG(DB_VERBOSE2, fprintf(stderr, "Reading opcode list (%d)\n", rhead->opcount ));
	if( NULL == (wrec->oplist = (int*)realloc( wrec->oplist, rhead->opcount * sizeof( int ) ) ) ) {
		fprintf( stderr, "ERROR: read_wab_record(): can't realloc() oplist.\n");
		return -1;
	}

	// And read it
	res = rread( wrec->oplist, sizeof( int ) * rhead->opcount, fp );
	if( 0 != res ) return res;

	DEBUG(
		DB_DATA_DUMP,
		// now we dump it
		for( i=0; i<rhead->opcount; i++)
		fprintf( stderr, "OPLIST[%d]: %d(0x%x)\n", i, wrec->oplist[i], wrec->oplist[i]  );
	)

	// now we try to read the elewrecs
#if 0
	if( NULL == (wrec->opdata.data=
	(void**)realloc( wrec->opdata, rhead->opcount * sizeof( void* ) ) )
	)
	{
		fprintf( stderr, "realloc() error, aborting.\n");
		exit(1);
	}
#endif

	if( NULL == (wrec->opdata=
	(struct op_data*)realloc( wrec->opdata, rhead->opcount * sizeof( struct op_data ) ) ) ) {
		fprintf( stderr, "ERROR: read_wab_record(): can't realloc() opdata.\n");
		return -1;
	}

	if( NULL == (wrec->data=(void*)realloc( wrec->data, rhead->datalen ) ) ) {
		fprintf( stderr, "ERROR: couldn't allocate memory for record.\n");
		return -1;
	}

	memset( wrec->opdata, 0, rhead->opcount * sizeof( struct op_data ) );

	res = rread( wrec->data, rhead->datalen, fp );
	if( 0 != res ) return res;

	p = wrec->data;

	//populate opdata pointer table
	for(i=0; i<rhead->opcount; i++ ) {
		DEBUG( DB_VERBOSE2, fprintf( stderr, "Reading opdata %d\n", i ); );
		if( 0 != read_opdata( &(wrec->opdata[i]), wrec->oplist[i], &p ) ) {
			fprintf( stderr, "Error reading opdata, can't continue in record. :(\n");
			return -1;
		}
	}

	DEBUG( DB_VERBOSE2, fprintf( stderr, "Record loaded successfully...\n"); );

	return 0;
}
// }}}

#define OFFSET_20 (sizeof(int) * (5) )

int do_heuristic( char *path ) // {{{
{
	struct wab_handle *wh;
	struct wab_record wrec;
	struct rec_header rhead;
	int my20;

	rread_crash = 0;

	DEBUG(DB_VERBOSE, fprintf(stderr, "Entering heuristic mode.\n"));

	memset( &wrec, 0, sizeof( struct wab_record ) );

	if( NULL == ( wh = open_wab( path ) ) ) {
		fprintf( stderr, "Error opening %s\n", path );
		return 1;
	}

	if( 0 != fseek( wh->fp, 0, SEEK_SET ) ) {
		fprintf( stderr, "Error seeking to start of file. \n" );
		return 1;
	}

	DEBUG(DB_VERBOSE, fprintf(stderr, "Starting search...\n"));
	while( ! feof( wh->fp ) ) {
		if( 1 != fread( &my20, sizeof( my20 ), 1, wh->fp) ) {
			if( feof( wh->fp ) ) {
				fprintf( stderr, "Heuristic hit EOF.  All done.\n" );
				return 1;
			}
			else {
				fprintf( stderr, "Error reading from file.\n" );
				return 1;
			}
		}

		long lastoff = ftell( wh->fp );

		if( STUPID || 0x20 == my20 ) { //found *something*...try to load it

			DEBUG(DB_VERBOSE,
				fprintf(stderr, "Found a possible record at %lx -> %lx\n", lastoff, lastoff - OFFSET_20 ));
			//Assuming this is a header then we go to where the start would be
			if( 0 == fseek( wh->fp, - OFFSET_20, SEEK_CUR ) ) {
				DEBUG(DB_VERBOSE, fprintf(stderr, "Trying to read@%lx...\n", ftell( wh->fp ) ) );
				rread( &rhead, sizeof( struct rec_header ), wh->fp );
				//dump_rec_head( &rhead );
				if( STUPID || rhead.mystery0 == 0 || rhead.mystery0 == 1 ) {
					fseek( wh->fp, lastoff - OFFSET_20, SEEK_SET );

					//if( 0 == rhead.mystery0 ) { fprintf(stderr, "DELETED...\n"); }
					DEBUG(DB_VERBOSE, fprintf(stderr, "This one is probable: %lx\n", ftell( wh->fp ) ) );
					if( 0 != read_wab_record( &wrec, wh->fp ) ) {
						fprintf( stderr, "read_wab_record() couldn't load a record.." );
					}
					else {
						//fprintf(stderr, "Output...\n");
						if( 0 != write_ldif( stdout, &wrec ) ) {
							fprintf(stderr, "write_ldif() failed.\n");
						}
						//fprintf(stderr, "...done\n");
					}
				}
			}
		}

		//we seek to just after the 0x20 (even if an attempt to load
		//the last record was made it could have been broken)
		fseek( wh->fp, lastoff, SEEK_SET );
	}

	return 0;
}
// }}}

//TODO: there's some UGLY pointer stuff going on with **p below...
//TODO: this function is UUUUUUUUUGLY
int read_opdata( struct op_data *opdata, int opcode, void **p ) // {{{
{
	//int opbuf = *(int*)*p;

	//char *prbuf;

	//The opdata should start with the opcode (which we are given)
	//rread( &opbuf, sizeof( int ), fp );

	if( *(int*)*p != opcode ) {
		fprintf( stderr, "read_opdata(): given opcode 0x%x != read opcode 0x%x\n",
		opcode, *(int*)*p);
		return -1;
	}

	*p += sizeof( int );

	//now we read the size of the opdata
	//rread( &opbuf, sizeof( int ), fp );
	/*
	opbuf = *(int*)p;
	((int*)*p)++;
	*/


	//read in data
	switch( opcode & 0xffff ) {
		case MT_SINT16: //Signed 16bit value
		case MT_SINT32: //Signed 32bit value
		case MT_BOOL: //Boolean (non-zero = true)
		case MT_EMBEDDED: //Embedded Object
		case MT_STRING: //Null terminated String
		case MT_UNICODE: //Unicode string
		case MT_SYSTIME: //Systime - Filetime structure
		case MT_OLE_GRID: //OLE Guid
		case MT_BINARY: //Binary data
			//allocate our buffer
			opdata->len = (int*)*p;
			*p += sizeof( int );
			opdata->data = *p;
			*p += *opdata->len;

			DEBUG( DB_VERBOSE2, fprintf( stderr, "Size of opdata, (0x%x) == %d(0x%d)\n", opcode, *opdata->len, *opdata->len); );
#if 0
			if( NULL == (opdata->data = (void*)realloc( opdata->data, opdata->length )) )
			{
			ffprintf( stderr, stderr, "Couldn't allocate buffer for opdata\n");
			exit(1);
			}

			//read the data
			rread( opdata->data, opbuf, fp );
#endif
			break;

		case MT_SINT32_ARRAY: // Array of 32bit values
			fprintf( stderr, "TODO: WARNING: MT_SINT32_ARRAY LOADING UNTESTED!!!\n");
			//TODO: don't die, just skip record
			fprintf( stderr, "TODO: don't die here.\n");
			exit(1);
			//opdata->length = (opbuf + 1) * sizeof( int );
			//*(int*)opdata->data = opbuf;
			//rread( opdata->data + sizeof(int), opdata->length, fp );
			break;

		case MT_BINARY_ARRAY: // Array of Binary data
		case MT_STRING_ARRAY: // Array of Strings
		case MT_UNICODE_ARRAY: // Array of Unicode
		{
			//the format here is:
			//[int: element count (already read at this point)][int: arraydatasize (pending read)]
			// [int elementlength][element]
			// [int elementlength][element]
			// ...
			//what I'm allocating arraydatasize + sizeof(int) and prepending
			//the array element count to the start of the data
			//void *bbuf = *buf;

			// read the size of the entire array data
			//rread( &opdata->length, sizeof( int ), fp );
			opdata->acnt = (int*)*p;
			*p += sizeof( int );
			opdata->len = (int*)*p;
			*p += sizeof( int );


			DEBUG( DB_VERBOSE2, fprintf( stderr, "Unicode array element count: %d (0x%x)\n", *opdata->acnt, *opdata->acnt ); )
			DEBUG( DB_VERBOSE2, fprintf( stderr, "Unicode array data length: %d (0x%x)\n", *opdata->len, *opdata->len ); )
			// we store the element count at start, so increment
			//opdata->length += sizeof( int ); //we prefix the element count (sucks)

			/*
			if( NULL == (opdata->data = (void*)realloc( opdata->data,  opdata->length ) ) )
			{
			ffprintf( stderr, stderr, "Couldn't allocate buffer.\n");
			exit(1);
			}
			*/

			// store int element count at start of buffer, data after that
			//memcpy(opdata->data, &opbuf, sizeof( int ));
			//rread( opdata->data + sizeof( int ), opdata->length - sizeof( int ), fp );
			opdata->data = *p;
			*p += *opdata->len;
			break;
		}

		default:
		printf ("Unknown data packing format found 0x%x\n", opcode);
		return -1;
	}

	return 0;
}
// }}}
void dump_opdata( int opno, struct wab_record *wrec ) // {{{
{
  int opcode = wrec->oplist[opno];
  //void *opdata = wrec->opdata[opno];
  //struct op_data *opdata = &wrec->opdata[opno];
  int id=(opcode>>16) & 0xffff;
  //int *opbuf=NULL;

  //spew data
  DEBUG( DB_DATA_DUMP, fprintf( stderr, "  Data[%x:%s:%s]: ", opcode, id_get_str2( id ), ldid_get_str2( id ) ); )

//  DEBUG( DB_DATA_DUMP, fprintf( stderr, "%-40s ", id_get_str( id ) ); );
  switch( opcode & 0xffff )
  {

    case MT_SINT16:
    /*
      if( !skip_crud )
	fprintf( stderr, "%hd\n", *((short int*)opdata->data));
      break;
      */

    case MT_SINT32:
    /*
      if( !skip_crud )
	fprintf( stderr, "%d\n", *((int*)opdata->data));
      break;
      */

    case MT_BOOL:
    /*
      if( !skip_crud )
      { //TODO: So a boolean is a single char?  Or an int?
	if( *(int*)opdata->data )
	  fprintf( stderr, "TRUE\n");
	else
	  fprintf( stderr, "FALSE\n");
      }
      break;
      */

    case MT_EMBEDDED:
    /*
      if( !skip_crud )
	fprintf( stderr, "Can't print embedded object\n");
      break;
      */

    case MT_STRING:

    case MT_UNICODE:
      //if( skip_crud )fprintf( stderr, "%-40s ", id_get_str( id ) );
      //print_unicode_str( stderr, opdata->data );
      //fprintf( stderr, "\n" );

      print_opdata( stderr, opno, wrec, "", "\n" );
      break;

    case MT_BINARY_ARRAY:

      print_opdata( stderr, opno, wrec, "BIN: ", "\n" );
      /*
      if( !skip_crud )
      {
	int size,i;
	void *p=opdata->data;
	//memcpy( &elements, p, sizeof( int ) );
	p+=sizeof( int );
	fprintf( stderr, "Looking at a binary array of %d elements.\n", *opdata->acnt);
	for( i=0; i<*opdata->acnt; i++ )
	{
	  memcpy( &size, p, sizeof( int ));
	  p+=sizeof( int );
	  binary_print( stderr, p, size );
	  p+=size;
	}
      }

      */
      break;


    case MT_UNICODE_ARRAY:
#if 0
    {
      int size,i;
      void *p=opdata->data;

      //DEBUG( DB_VERBOSE2,  fprintf( stderr, "%-40s ", id_get_str( id ) ); )
      //memcpy( &elements, p, sizeof( int ) );
      //p+=sizeof( int );
      DEBUG( DB_VERBOSE2, fprintf( stderr, "Looking at a unicode array of %d elements.\n", *opdata->acnt); );
      for( i=0; i<*opdata->acnt; i++ )
      {
	memcpy( &size, p, sizeof( int ));
	p+=sizeof( int );
	DEBUG(DB_VERBOSE2, fprintf( stderr, " element size: %d (0x%x)\n", size, size ); );
	print_unicode_str( stderr, p );
	fprintf( stderr, "\n" );
	p+=size;
      }
      break;
    }
#endif

    case MT_OLE_GRID:
    case MT_SYSTIME:
    case MT_BINARY:
      print_opdata( stderr, opno, wrec, "", "\n" );
      break;

    default:
      fprintf( stderr, "Unknown data type 0x%x\n", (opcode & 0xffff) );
      break;
  }

  return ;
}
// }}}
void print_opdata( FILE *fp, int opno, struct wab_record *wrec, char *prefix, char *suffix ) // {{{
{
	int opcode = wrec->oplist[opno];
	//void *opdata = wrec->opdata[opno];
	struct op_data *opdata = &wrec->opdata[opno];
	int id=(opcode>>16) & 0xffff;
	//int *opbuf=NULL;

	//spew data
	//DEBUG( DB_VERBOSE2, fprintf( stderr, "  Data[%x:%s]: ", opcode, id_get_str2( id ) ); )

	//DEBUG( DB_VERBOSE, fprintf( stderr, "%-40s ", id_get_str2( id ) ); );
	switch( opcode & 0xffff ) {
		case MT_SINT16:
			fprintf( fp, "%s", prefix );
			fprintf( fp, "%hd", *((short int*)opdata->data));
			fprintf( fp, "%s", suffix);
			break;

		case MT_SINT32:
			fprintf( fp, "%s", prefix );
			fprintf( fp, "%d", *((int*)opdata->data));
			fprintf( fp, "%s", suffix);
			break;

		case MT_BOOL:
			fprintf( fp, "%s", prefix );
			{ //TODO: So a boolean is a single char?  Or an int?
				if( *(int*)opdata->data )
					fprintf( fp, "TRUE");
				else
					fprintf( fp, "FALSE");
			}
			fprintf( fp, "%s", suffix);
			break;

		case MT_EMBEDDED:
			fprintf( fp, "%s", prefix );
			fprintf( stderr, "Can't print embedded object\n");
			fprintf( fp, "%s", suffix);
			break;

		case MT_STRING:
			fprintf( fp, "%s", prefix );
			fprintf( fp, "%s", (char*)opdata->data );
			fprintf( fp, "%s", suffix);
			break;

		case MT_UNICODE:
			fprintf( fp, "%s", prefix);
			print_unicode_str( fp, opdata->data );
			fprintf( fp, "%s", suffix);
			break;

		case MT_BINARY_ARRAY:
		{
			int size,i;
			int optype=((opcode >> 16) & 0xFFFF);
			void *p=opdata->data;
			//memcpy( &elements, p, sizeof( int ) );
			//p+=sizeof( int );
			fprintf( stderr,
			"Looking at a binary array of %d elements (opcode 0x%x optype 0x%x opno 0x%x).\n",
			*opdata->acnt, opcode, optype, opno );
			for( i=0; i<*opdata->acnt; i++ ) {
				memcpy( &size, p, sizeof( int ));
				p+=sizeof( int );
				if ( optype == PR_MAB_MEMBER ) {
					char *buf;
					int j;
					fprintf( fp, "%s%s", prefix, "cn=" );
					buf = p;
					for ( j = 24; j < size; j += 2 ) {
						if ( buf[j] == '\0' ) {
							if ( j + 10 < size &&
								buf[j+2] == 'S' &&
								buf[j+4] == 'M' &&
								buf[j+6] == 'T' &&
								buf[j+8] == 'P' )
							{
								fprintf( fp, "%s", ",mail=" );
								j += 10;
								} else {
								if ( j + 2 < size ) {
									fprintf( fp, " " );
								}
							}
						} else {
							fprintf( fp, "%c", buf[j] );
						}
					}
				} else {
					fprintf( fp, "%d: %s", size, prefix );
					binary_print( fp, p, size );
				}
				p+=size;
				fprintf( fp, "%s", suffix);
			}
		}

		break;


		case MT_UNICODE_ARRAY:
		{
			int size,i;
			void *p=opdata->data;

			DEBUG( DB_VERBOSE2,  fprintf( stderr, "%-40s ", id_get_str2( id ) ); )
			//memcpy( &elements, p, sizeof( int ) );
			//p+=sizeof( int );
			DEBUG( DB_VERBOSE2, fprintf( stderr, "Looking at a unicode array of %d elements.\n", *opdata->acnt); );
			for( i=0; i<*opdata->acnt; i++ ) {
				fprintf( fp, "%s", prefix );
				memcpy( &size, p, sizeof( int ));
				p+=sizeof( int );
				DEBUG(DB_VERBOSE2, fprintf( stderr, " element size: %d (0x%x)\n", size, size ); );
				print_unicode_str( fp, p );
				p+=size;
				fprintf( fp, "%s", suffix);
			}
		break;
		}

		case MT_OLE_GRID:
			fprintf( fp, "%s", prefix );
			fprintf( stderr, "I can't print an OLE GRID (I don't even know what one is).\n");
			fprintf( fp, "%s", suffix);
			break;

		case MT_SYSTIME:
			fprintf( fp, "%s", prefix );
			//struct tm datime;
			binary_print( fp, opdata->data, *opdata->len );
			fprintf( fp, "%s", suffix);
			break;

		case MT_BINARY:
			fprintf( fp, "%s", prefix );
			binary_print( fp, opdata->data, *opdata->len );
			fprintf( fp, "%s", suffix);
			break;

		default:
			fprintf( stderr, "Unknown data type 0x%x\n", (opcode & 0xffff) );
			break;
	}

	return ;
}
// }}}
struct op_data *wrec_getfield( struct wab_record *mrec, int opcode ) // {{{
{
	int i;
	for(i=0; i<mrec->rhead.opcount; i++) {
		DEBUG(DB_VERBOSE2, fprintf(stderr, "wrec_getfield(): oplist %d/%d: 0x%x for 0x%x\n",
			i, mrec->rhead.opcount, mrec->oplist[i], opcode) );
		if( ((mrec->oplist[i] >> 16) & 0xffff) == opcode ) {
			DEBUG(DB_VERBOSE2, fprintf(stderr, "found it...\n"); );
			return &mrec->opdata[i];
		}
	}
	return NULL;
}
// }}}
char *wrec_getstring( struct wab_record *mrec, int opcode ) // {{{
{
	struct op_data *opdata = wrec_getfield( mrec, opcode );
	//TODO: check if this is really a string and return null if not (the whole
	//point of this function)
	if( NULL == opdata )
		return NULL;
	return opdata->data;
}
// }}}

/*
char *get_ldid( int opcode ) // {{{
{
  switch( (opcode >> 16) & 0xffff )
  {
    case PR_DISPLAY_NAME:
    	return "dc";
    case PR_MAB_ADDRESS_STR:
    case PR_MAB_ALTERNATE_EMAILS:
	return "mail";

    default:
      return NULL;
  }
}
// }}}
*/

int write_ldif( FILE *dest, struct wab_record *mrec ) // {{{
{
	char *s, *us;
	char ldidstr[1024]; 
	int i;
	DEBUG( DB_VERBOSE2, fprintf( stderr, "write_ldif() is calling wrec_getstring()\n") );
	if( NULL == ( us = wrec_getstring( mrec, PR_DISPLAY_NAME ) ) ) {
		fprintf( stderr, "Couldn't read display name for record.\n");
		return -1;
	}

	s = m_cheap_uni2ascii( us );

	if( 0 == strcmp( "Main Identity's Contacts", s ) ) return 0; //these seem to be junk records

	DEBUG( DB_VERBOSE2, fprintf( stderr, "write_ldif() is returning from wrec_getstring() %s\n", s) );

	fprintf( dest, "# %s", s );
	if( mrec->rhead.mystery0 == 0 ) {
		fprintf( dest, " DELETED" );
	}

	fprintf( dest, "\n" );
	fprintf( dest, "dc: cn=%s\ncn: %s\n", s, s );

	free( s );

	for( i=0; i<mrec->rhead.opcount; i++ ) {
		char *ldid;

		if( ((mrec->oplist[i] >> 16) & 0xffff) == PR_DISPLAY_NAME)
		continue;

		if( NULL == (ldid = ldid_get_str( (mrec->oplist[i] >> 16) & 0xffff ) ) ) {
			DEBUG(DB_VERBOSE2, fprintf(stderr, "Couldn't find ldid for 0x%x\n", (mrec->oplist[i] >> 16) & 0xffff); );
			continue;
		}

		snprintf( ldidstr, 1024, "%s: ", ldid );
		print_opdata( dest, i, mrec, ldidstr, "\n" );
	}


	fprintf( dest, "\n" );

	return 0;
}
// }}}
void binary_print( FILE *fp, unsigned char *buf, int len ) // {{{
{
	int i;
	//fprintf( stderr, "BIN[0x%x]:[", len);
	for( i=0; i<len; i++) {
		fprintf( fp, "%02x", (unsigned int)buf[i]);
		if( i<len-1 )fprintf( fp, " ");
	}
	if( sizeof( int ) < len ) {
		fprintf( fp, " " );
		for( i=0; i<len; i++) {
			if (buf[i] >= ' ' && buf[i] < 127) {
				fprintf( fp, "%c", buf[i] );
			} else {
				fprintf( fp, "." );
			}
		}
	}
	//fprintf( stderr, "]");
	if( sizeof( int ) == len ) {
		fprintf( stderr, " (int? %d) (unsigned int? %d)", *(int*)buf, *(unsigned int*)buf );
	}
	//fprintf( stderr, "\n");
}
// }}}
void print_unicode_str( FILE*fp, char *buf ) // {{{
{
	char *prbuf;
	prbuf = m_cheap_uni2ascii( buf );
	fprintf( fp, "%s", prbuf );
	free( prbuf );
}
// }}}
int read_txtrec( struct txtrecord *trec, FILE *fp ) // {{{
{
	char strbuf[STR_SIZE];
	int thisoff = ftell( fp );

	if( 0 != rread( strbuf, STR_SIZE, fp ) ) return -1;
	if( 0 != rread( &trec->recid, sizeof( int ), fp ) ) return -1;

	cheap_uni2ascii( strbuf, trec->str, STR_SIZE);

	fprintf( stderr, "  0x%x: Index %d(%x), String: %s\n",
		thisoff, trec->recid, trec->recid, trec->str );

	return 0;
}
// }}}
int read_idxrec( struct idxrecord *irec, FILE *fp ) // {{{
{
	int thisoff=ftell( fp );
	if( 0 != rread( irec, sizeof( struct idxrecord ), fp ) ) return -1;
	fprintf( stderr, "  0x%x: record id: %d(0x%x) offset: %x\n",
		thisoff, irec->recid, irec->recid, irec->offset );

	return 0;
}
// }}}
int rread( void *buf, int size, FILE *fp ) // {{{
{
	DEBUG( DB_LOW_LEVEL, fprintf( stderr, "rread() reading %d(0x%x) bytes at 0x%lx\n", size, size, ftell( fp ) ););

	if( 1 != fread( buf, size, 1, fp ) ) {
		fprintf( stderr, "READ ERROR at %lx\n", ftell( fp ) );
		if( rread_crash ) exit( 1 );
		else return -1;
	}

	return 0;
}
// }}}
char *m_cheap_uni2ascii( char *src ) // {{{
{
	int len=0;
	char *result = NULL;
	short *p= (short*)src;
	for( len=0; p[len] != 0; len++ );

	len = len*2;

	if( NULL == ( result = (char*)malloc( len + 2 ) ) )
		return NULL;

	cheap_uni2ascii( src, result, len );

	return result;

}
// }}}
void cheap_uni2ascii(char *src, char *dest, int l) // {{{
{
	for (; l > 0; l -=2) {
		*dest = *src;
		dest++; src +=2;
	}
	*dest = 0;
}
// }}}
