#!/usr/bin/env python
#
#	This is free software; you can redistribute it and/or modify it under
#	the terms of the GNU General Public License as published by the Free
#	Software Foundation; either version 2 of the License, or (at your
#	option) any later version.
#    
#	This software is distributed in the hope that it will be useful, but
#	WITHOUT ANY WARRANTY; without even the implied warranty of
#	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#	General Public License for more details.
# 
#	You should have received a copy of the GNU General Public License along
#	with this software; if not, write to the Free Software Foundation,
#	Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
#
#
#
#	pyFavConv.py:  A simple script that converts an Internet Explorer (5.x,
#	6.x) favorites directory into a Konqueror 2.x or 3.x compatible
#	bookmark file.  Konqueror uses the XML Bookmark Exchange Language
#	(xbel) standard for portable bookmarks.  See
#	http://pyxml.sourceforge.net/topics/xbel/ for details.  
#
#	All questions, comments, feature requests etc should be directed to
#	<dave@mikamyla.com>
#
#	Changelog:
#	April 9, 2002 - Initial Release v0.1
#
	     

import sys, os, string, re

def Usage():
	print "\nUsage: " + sys.argv[0] + "  FAVDIR BOOKMARKFILE [-v]"
	print "   FAVDIR=Directory of IE Favorites"
	print "   BOOKMARKFILE=File to write bookmarks to"
	print "   -v = Be verbose while processing\n"
	sys.exit()

def parse_args():
	argcount=len(sys.argv)
	
	if (argcount < 3):
		Usage()
	
	result = []
	result.append(sys.argv[1])
	result.append(sys.argv[2])
	if (argcount > 3) and (sys.argv[3] == "-v"): 
		result.append(1) #true
	else:
		result.append(0) #false
	return result

def parse_url_file(fd_url):
	line = fd_url.readline()
	while ( line != ''):
		if line[:3] == "URL":
			#we have the href
			#now replace all '&' with '&amp;'
			#**NOTE:  line[4:-2] takes everything after the 
			#"URL=" and cuts off the pesky ^M's 
			result = re.compile('&').sub('&amp;',line[4:-2])
			return result
		else :
			line=fd_url.readline()
	return ' '

def process_directory(path_to_dir,fd_bm_file):
	global count
	#get initial contents of user supplied directory
	curdir_contents=os.listdir(path_to_dir)
	#change to that directory
	os.chdir(path_to_dir)
	for entry in curdir_contents:
		if os.path.isdir(entry):
			fd_bm_file.write("<folder folded=\"yes\">\n<title>"+ entry + "</title>\n")
			process_directory(entry, fd_bm_file)
			os.chdir("..")
			fd_bm_file.write("</folder>\n")
			continue
		#make sure we only get the ".url" files
		if (entry[-3:] == "url"):
			f=open(entry,"r")
			href=parse_url_file(f)
			if (verbose): print href
			f.close()

			#write the bookmark to the bookmark file
			fd_bm_file.write("<bookmark icon=\"www\" href=\"" + href + "\">\n")

			#replace '&' with '&amp;'
			title=re.compile('&').sub('&amp;',(string.split(entry,".url"))[0])
			fd_bm_file.write("<title>" + title + "</title>\n</bookmark>\n")
			count=count + 1

			
	

sourcedir, destfile, verbose = parse_args()

if ( not os.access(sourcedir,os.F_OK)):
	print "Invalid or inaccessible path in " + sourcedir
	Usage()
count=0
g=open(destfile,"w")
g.write("<!DOCTYPE xbel>\n")
g.write("<xbel hide_nsbk=\"yes\">\n")
process_directory(sourcedir,g)
g.write("</xbel>")
g.close()
print count , " bookmarks processed and written to " + destfile + "."

