// {{{ #include <stuff>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "libwab.h"
// }}}

void use() // {{{
{
  fprintf( stderr, 
    "Use:  wabread [options] <filename.wab>\n"
    "\n"
    "  Options:\n"
    "   -d #        set debugging (logical or 1,2,3,4...)\n"
    "   -h          heuristic record dump: attempt to\n"
    "                 recover a broken .wab file \n"
    "   -c          display extra crud.\n"
  );
}
// }}}

#define MODE_NORMAL 0
#define MODE_HEURISTIC 1


//int do_heuristic( char *path );

int mode;

struct wab_header wabhead;

void parse_cmdline( int argv, char **argc );

char *filename = NULL;

int main( int argv, char **argc ) // {{{
{
	int i;
	struct wab_handle *wh;

	if( argv < 2 ) {
		use();
		return 1;
	}

	parse_cmdline( argv, argc );

	switch( mode ) {
		case MODE_NORMAL:
		{
			if( NULL == ( wh = open_wab( filename ) ) ) {
				fprintf( stderr, "Error opening %s\n", argc[1] );
				return 1;
			}

			DEBUG( DB_DATA_DUMP, dump_wab_header( wh ););

			DEBUG( DB_DATA_DUMP,
			for( i=0; i<TABLE_COUNT; i++) {
				fprintf( stderr, "\nDumping lookup table %d\n", i );
				dump_table( i, wh );
			}
			);
			dump_records( wh );
			break;
		}

		case MODE_HEURISTIC:
			return do_heuristic( filename );
		break;

		default:
			fprintf( stderr, "ERROR: Unknown mode (this should never happen).\n");
			exit(1);
	}


	return 0;
}
// }}}
void parse_cmdline( int argv, char **argc ) // {{{
{
	int i;
	int verbose=0;

	for( i=1; i<argv; i++ ) {
		if( strlen( argc[i] ) > 1 && argc[i][0] == '-' ) { //it's an option
			char *p = argc[i] + 1;
			for( ; *p != '\0'; p++ ) {
				switch( *p ) {
					case 'd':
					{
						if( '\n' == *(p+1) || '\0' == *(p+1) ) {
							if( i + 1 >= argv ) {
								fprintf( stderr, "Error: option requires a value");
								use();
								exit(1);
							}
							i++;
							p=argc[i];
						} else {
							p++;
						}
						sscanf( p, "%d", &dodebug );

						fprintf( stderr, "Setting dodebug to %d\n", dodebug );
						goto next_arg;
					}

					case 'v':
						verbose++;
						break;

					case 'h':
						mode = MODE_HEURISTIC;
						break;

					default:
						fprintf( stderr, "Error: unknown option \"%c\"", *p );
						use();
						exit(1);
				}
			}
		}
		else {
			filename = argc[i];
		}
		next_arg: ;
	}

	if( NULL == filename ) {
		fprintf( stderr, "Error: you must specify a filename.\n");
		use();
		exit(1);
	}

	dodebug |= ((1 << verbose) - 1);
	DEBUG( DB_VERBOSE2, printf("dodebug set to %d\n", dodebug ); )
}
// }}}

