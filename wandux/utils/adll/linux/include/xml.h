/* -*- mode: C; c-file-style: "gnu" -*- */
/* xml.h Xml functions
 *
 * ADLL is the legal property of its developers, Mehdi Bennani, Joffrey Audin,
 * Olivier Audry, Vincent Malguy, Etienne Grignon.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#ifndef __XML_H__
#define __XML_H__

#include <libxml/parser.h>
#include <libxml/tree.h>

#include "common.h"
#include "error.h"

ADLL_BEGIN_DECLS;

typedef struct AdllXmlData_ AdllXmlData;

struct	AdllXmlData_
{
  xmlDocPtr	doc;
  xmlNode	root;
  char		*file;
};

void		my_xml_error(void *userData, xmlErrorPtr error);
AdllXmlData    	**open_all_xml(char **files, int nb);
int		open_xml(const char *file, AdllXmlData **xmldata);

ADLL_END_DECLS;

#endif /* __XML_H__ */
