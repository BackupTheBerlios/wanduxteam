/* -*- mode: C; c-file-style: "gnu" -*- */
/* nodes.h AdllNode functions
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

#ifndef __NODES_H__
#define __NODES_H__

#include "common.h"
#include "ntree.h"
#include "xml.h"

ADLL_BEGIN_DECLS;

#define NODE_ROOT	1	/* <template>	*/
#define NODE_SECTION	2	/* <section>	*/
#define NODE_OPTION	3	/* <option>	*/
#define NODE_CHOICE	4	/* <choice>	*/
#define NODE_COMMENT	5	/* <comment>	*/
#define NODE_COMMAND	6	/* <command>	*/

#define OPTION_COMBO	1	/* typ=combo	*/
#define OPTION_LIST	2	/* type=list	*/
#define OPTION_RADIO	3	/* type=radio	*/
#define OPTION_CHECK	4	/* type=check	*/
#define OPTION_TEXT	5	/* type=text	*/

typedef struct AdllNode_		AdllNode;
typedef struct AdllNodeTemplate_	AdllNodeTemplate;
typedef struct AdllNodeSection_		AdllNodeSection;
typedef struct AdllNodeOption_		AdllNodeOption;
typedef struct AdllNodeChoice_		AdllNodeChoice;
typedef struct AdllNodeCommand_		AdllNodeCommand;
typedef struct AdllNodeComment_		AdllNodeComment;
typedef struct AdllNodeOptionList_	AdllNodeOptionList;


struct	AdllNodeOptionList_
{
  NTreeNode	*nodeChoice;
  AdllNode	*adllNode;
};

struct	AdllNodeTemplate_
{
  char		*author;
  char		*os_name;
  char		*os_version;
  char		*adll_version;
};

struct	AdllNodeSection_
{
  char		*label;
  char		*name;
  char		*separator;
  char		*terminator;
#ifdef SUPPORT_GTK
  GtkTreeIter	Iter;
#endif
};

struct	AdllNodeOption_
{
  char		*label;
  char		*name;
  int		type;
  int		multi;
  int		nb_select;
  char		*separator;
  char		*terminator;
#ifdef SUPPORT_GTK
  xmlNodePtr	xmlNode;
  GtkWidget	*vbox;
  GtkWidget	*frm_options;
  GtkWidget	*multicombolist;
  GtkWidget	*wnd_multi;
  NTreeNode	**multinodelist;
  int		multiid;
#endif
};

struct	AdllNodeChoice_
{
  char		*label;
  char		*str;
  int		selected;
#ifdef SUPPORT_GTK
  int		listid;
  union {
    GtkWidget	*radio;
    GtkWidget	*entry;
    GtkWidget	*check;
    GtkWidget	*combolist;
  };
#endif
};

struct	AdllNodeCommand_
{
  char		*str;
};

struct	AdllNodeComment_
{
  char		*str;
};

struct	AdllNode_
{
  int		type;
  int		visible;
  int		needed;
  char		*strtype;
  union {
    AdllNodeTemplate	template;
    AdllNodeSection	section;
    AdllNodeOption	option;
    AdllNodeChoice	choice;
    AdllNodeCommand	command;
    AdllNodeComment	comment;
  };
};


NTreeNode		*AdllNodeGetSectionParent(NTreeNode *node);
NTreeNode		*AdllNodeGetOptionParent(NTreeNode *node);
int			AdllNodeParentIsVisible(NTreeNode *node);
AdllNodeOptionList	**AdllNodeGetOptionList(NTreeNode *parent);
NTreeNode		*AdllNodeGetChoiceByIdFromOption(NTreeNode *parentOption, int id);
void			AdllNodeSetChoiceSelected(NTreeNode *selected);
int			AdllNodeOptionHaveChoice(NTreeNode *curnode);
int			AdllNodeOptionIsMulti(NTreeNode *curnode);
ADLL_END_DECLS;

#endif	/* __NODES_H__ */
