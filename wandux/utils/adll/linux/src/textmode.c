/* -*- mode: C; c-file-style: "gnu" -*- */
/* textmode.h Mode Text functions
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

#include "textmode.h"

void	TextKeyPress(void)
{
  //  printf(_("Press enter to continue, or ctrl-c to abort."));
  //  getchar();
  //  printf("\n");
}

void	TextPrintEmptylines(int n)
{
  int	i;

  if (n <= 0)
    n =  EMPTY_LINES;
  for (i = 0; i < n; i++)
    printf("\n");
}


void	TextPrintIntro(NTreeNode *root, AdllDoc *doc)
{
  AdllNode	*adllRoot;

  adllRoot = (AdllNode *) root->data;
  //  printf("\n");
  //  printf(_("Wellcome to %s %s\n"), PACKAGE, VERSION);
  //  printf(_("I'm going to process the file '%s'\n"), doc->xml->file);
}

 void TextPrintSectionRec(NTreeNode *cur)
{
  NTreeNode	*sectionParent;
  AdllNode	*adllNode;

  sectionParent = AdllNodeGetSectionParent(cur);
  if (sectionParent != NULL)
    TextPrintSectionRec(sectionParent);
  else
    return ;
  adllNode = (AdllNode *) sectionParent->data;
  if (adllNode->type == NODE_SECTION)
    printf("%s : ", adllNode->section.label);
}

 void	TextPrintOptionNameRec(NTreeNode *cur)
{
  NTreeNode	*n = NULL;
  AdllNode	*adllNode;

  adllNode = (AdllNode *) cur->data;
  if (adllNode->type != NODE_OPTION)
    return ;
  if (cur->parent) {
    for (n = cur->parent; n != NULL; n = n->parent) {
      adllNode = (AdllNode *) n->data;
      if (adllNode->type == NODE_CHOICE)
	continue ;
      if (adllNode->type != NODE_OPTION)
       break ;
      if (adllNode->type == NODE_OPTION) {
	TextPrintOptionNameRec(n);
	break ;
      }
    }
  }
  adllNode = (AdllNode *) cur->data;
  if (strlen(adllNode->option.label) != 0)
    printf("%s : ", adllNode->option.label);
}

void		TextPrintSectionHeader(NTreeNode *cur)
{
  printf("\n");
  printf(_("## SECTION : "));
  TextPrintSectionRec(cur);
  printf("##\n\n");
}

void	TextPrintOptionName(NTreeNode *cur)
{
  TextPrintOptionNameRec(cur);
  printf("\n\n");
}
