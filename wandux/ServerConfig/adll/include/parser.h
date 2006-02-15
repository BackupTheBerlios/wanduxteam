/* -*- mode: C; c-file-style: "gnu" -*- */
/* parser.h Xml Parsing
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

#ifndef __PARSER_H__
#define __PARSER_H__

#include "common.h"
#include "nodes.h"
#include "ntree.h"
#include "option.h"
#include "xml.h"

ADLL_BEGIN_DECLS;

typedef struct	AdllDoc_ AdllDoc;

struct	AdllDoc_
{
  int		parseOK;
  AdllXmlData	*xml;
  NTreeNode	*root;
#ifdef SUPPORT_GTK
  GtkWidget	*ntb_base;
  GtkWidget	*ntb_options;
  GtkTreeStore	*tst_section;
  GtkWidget	*vbx_options;
#endif
};

AdllDoc		**AdllParseAll(AdllXmlData **xmls);
AdllDoc		*AdllParseXml(AdllXmlData *xml);
void		AdllParseXmlRec(AdllDoc *doc, xmlNodePtr node, NTreeNode *treeparent);
AdllNode	*AdllNodeNew();

ADLL_END_DECLS;

#endif /* __PARSER_H__ */
