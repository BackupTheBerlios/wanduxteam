#!/usr/bin/perl
#
# makeids.pl
#
# Generates the pstwabids.h and pstwabids.c files from known_ids
#

use strict;

my $DEBUG=0;
my $infile = "known_ids";
my $outh = "pstwabids.h";
my $outc = "pstwabids.c";

my @lines;

open( KNOWN, $infile ) || die "Couldn't open $infile: $!";

my $lineno=0;

while(<KNOWN>)
{
  $lineno++;
  if(/(.*)#/) #strip comments 
  {
    $_ = $1;
  }

  chomp;
  if(/([^,]+),\s*([^,]+)\s*(.*)/) #\s*,\s*([^,]+)(.*)#.*$/)
  {
    my $hexid=$1;
    my $defstr=$2;
    if( $3 =~ /,\s*(\S+)/ )
    {
      push @lines, [$hexid, $defstr, $1];
    }
    else
    {
      push @lines, [$hexid, $defstr];
    }
  }
  else
  {
    if(! /^\s*$/ )
    {
      die "Syntax error: \"$_\" in $infile at line $lineno";
    }
  }
}

close( KNOWN );

foreach my $i (@lines)
{
  debug("Found line: hex: " . $$i[0] . ", define: " . $$i[1] . ", the rest: " . $$i[2] . "\n");
}

#the header file
open( HEADER, ">$outh" )||die "Error opening $outh: $!";

print HEADER <<EOF
/*
*  This file was generated automatically by makeids.pl
*
*  if you want to modify anything in this file then modify known_ids and then run makeids.pl
*    ...and send me a copy :)
*
*/

#ifndef PSTWABIDS_H
#define PSTWABIDS_H

char *id_get_str( int id );
char *ldid_get_str( int id );

#define MT_SINT16   0x0002 //Signed 16bit value
#define MT_SINT32   0x0003 //Signed 32bit value
#define MT_BOOL     0x000B //Boolean (non-zero = true)
#define MT_EMBEDDED 0x000D //Embedded Object
#define MT_STRING   0x001E //Null terminated String
#define MT_UNICODE  0x001F //Unicode string
#define MT_SYSTIME  0x0040 //Systime - Filetime structure
#define MT_OLE_GRID 0x0048 //OLE Guid
#define MT_BINARY   0x0102 //Binary data

#define MT_SINT32_ARRAY  0x1003 // Array of 32bit values
#define MT_STRING_ARRAY  0x101E // Array of Strings
#define MT_UNICODE_ARRAY 0x101F // Array of Unicode
#define MT_BINARY_ARRAY  0x1102 // Array of Binary data


EOF
;

foreach my $i (@lines) 
{
  printf HEADER "#define %-60s %s\n", $$i[1], $$i[0];
}

print HEADER "#endif\n";

close( HEADER );


#the .c file
open( SOURCE, ">$outc" )||die "Error opening $outh: $!";

print SOURCE <<EOF
/*
*  This file was generated automatically by makeids.pl
*
*  if you want to modify anything in this file then modify known_ids and then run makeids.pl
*    ...and send me a copy :)
*
*/

#include <stdio.h>
#include "pstwabids.h"

char *id_get_str( int id )
{
  switch( id )
  {
EOF
;

foreach my $i (@lines) 
{
  printf SOURCE "    case \%-60s: return \"\%s\";\n", $$i[1], $$i[1];
}

print SOURCE <<EOF
    default:
      return NULL;
  }
}

EOF
;

print SOURCE <<EOF

char *ldid_get_str( int id )
{
  switch( id )
  {
EOF
;

foreach my $i (@lines)
{
  if( defined $$i[2] )
  {
    printf( SOURCE "    case \%-60s: return \"\%s\";\n", $$i[1], $$i[2] );
  }
}


print SOURCE <<EOF
    default:
      return NULL;
  }
}

EOF
;


sub debug
{
  if( $DEBUG )
  {
    printf @_;
  }
}

