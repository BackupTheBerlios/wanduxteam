/* -*- mode: C; c-file-style: "gnu" -*- */
/* textmain.h Mode Text main program
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

void	TextProcessOptionList(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNodeOptionList   	**optionlist;
  int			i,j;
  int			lastoption;
  int			selected;
  char			s[BUFFER_IN];

  TextPrintOptionName(curnode);
  optionlist = AdllNodeGetOptionList(curnode);
  lastoption = TabCount(optionlist) - 1;
  i = 0;
  for (;;) {
    for (j = i; j < i + 14 && j<= lastoption; j ++) {
      printf(" %3i %-20s\n", j,
	     optionlist[j]->adllNode->choice.label);
    }
    if (j > lastoption && j < i + 14)
      TextPrintEmptylines(14 - j + i);
    printf("\n");
    printf(_("Enter a number to choose the corresponding option.\n"));
    if (lastoption >= 14)
      printf(_("Press enter for the next page."));
    printf("\n\n> ");
    fflush(0);
    getstring(s);
    if (strlen(s) == 0) {
      i += 14;
      if (i > lastoption)
	i = 0;
      TextPrintEmptylines(0);
      TextPrintSectionHeader(curnode);
      TextPrintOptionName(curnode);
      continue ;
    }
    selected = atoi(s);
   if (selected >= 0 && selected <= lastoption)
      break ;
   TextPrintEmptylines(0);
   TextPrintSectionHeader(curnode);
   TextPrintOptionName(curnode);
   printf("treenode: %s\n", (char *)curnode->data);
  }
  AdllNodeSetChoiceSelected(optionlist[selected]->nodeChoice);
  TabFree(optionlist);
}

void	TextProcessOptionCombo(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNodeOptionList   	**optionlist;
  int			i,j;
  int			lastoption;
  int			selected;
  char			s[BUFFER_IN];

  TextPrintOptionName(curnode);
  optionlist = AdllNodeGetOptionList(curnode);
  lastoption = TabCount(optionlist) - 1;
  i = 0;
  for (;;) {
    for (j = i; j < i + 14 && j<= lastoption; j ++) {
      printf(" %3i %-20s\n", j,
	     optionlist[j]->adllNode->choice.label);
    }
    if (j > lastoption && j < i + 14)
      TextPrintEmptylines(14 - j + i);
    printf("\n");
    printf(_("Enter one range, or many ranges separated by commas.(ex: 2-5;7;9-12)\n"));
    if (lastoption >= 14)
      printf(_("Press enter for the next page."));
    printf("\n\n> ");
    fflush(0);
    getstring(s);
    if (strlen(s) == 0) {
      i += 14;
      if (i > lastoption)
	i = 0;
      TextPrintEmptylines(0);
      TextPrintSectionHeader(curnode);
      TextPrintOptionName(curnode);
      printf("treenode: %s\n", (char *)curnode->data);
      continue ;
    }
    /*
      Parser la ligne de commandes
      if (selected >= 0 && selected <= lastoption)
    */
    break ;
   TextPrintEmptylines(0);
   TextPrintSectionHeader(curnode);
   TextPrintOptionName(curnode);
  }
  /* selectionner les choices */
  TabFree(optionlist);
}

void	TextProcessOptionText(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode		*adllnode;
  char			s[BUFFER_IN];
  AdllNodeOptionList   	**optionlist;

  adllnode = (AdllNode *) curnode->data;
  optionlist = AdllNodeGetOptionList(curnode);
  TextPrintOptionName(curnode);
  printf("%s [%s] > ",  optionlist[0]->adllNode->choice.label,
	 optionlist[0]->adllNode->choice.str != NULL ? 
	 optionlist[0]->adllNode->choice.str : "");
  fflush(0);
  getstring(s);
  if (strlen(s) != 0) {
    if (optionlist[0]->adllNode->choice.str != NULL)
      free(optionlist[0]->adllNode->choice.str);
    optionlist[0]->adllNode->choice.str = strdup(s);
    optionlist[0]->adllNode->choice.selected = 1;
  }
  TabFree(optionlist);
  optionlist[0]->adllNode->choice.selected = 1;
  /* hack pour la generation, pas touche ! */
}

int			TextProcessOption(NTreeNode *curnode,
                                          AdllDoc *doc)
{
  AdllNode		*adllnode;
  AdllNodeOption	*option;

  adllnode = (AdllNode *) curnode->data;
  option = &(adllnode->option);
  if (!AdllNodeParentIsVisible(curnode))
    return (0);
  if (!AdllNodeOptionHaveChoice(curnode))
    return (0);
  TextPrintSectionHeader(curnode);
  if (option->type == OPTION_LIST ||option->type == OPTION_RADIO)
    TextProcessOptionList(curnode, doc);
  if (option->type == OPTION_TEXT)
    TextProcessOptionText(curnode, doc);
  if (option->type == OPTION_COMBO)
    TextProcessOptionCombo(curnode, doc);
  return (0);
}

int		TextNodeProcess(NTreeNode *curnode, void *data)
{
  AdllNode	*adllnode = (AdllNode *) curnode->data;

  if (!AdllNodeParentChoiceIsSelected(curnode))
    return (0);
  if (adllnode->type == NODE_OPTION)
    return (TextProcessOption(curnode, (AdllDoc *) data));
  return (0);
}

void	AdllAskOutput(AdllOption *option)
{
  char	s[BUFFER_IN];

  //  printf("\n");
  //  printf(_("Please give a filename to write to [%s]: "),
  //	 option->output != NULL ? option->output : "");
//  fflush(0);
//  getstring(s);
//  if (strlen(s) != 0) {
//   if (option->output != NULL)
//     free(option->output);
  if (option->output == NULL)
    option->output = strdup("adll.cfg");
//  }
  if (option->output == NULL || strlen(option->output) == 0)
    AdllAskOutput(option);
}

void	AdllLunchTextMode(AdllDoc *doc, AdllOption *options)
{
       if (options->output == NULL)
	 {
	   TextPrintIntro(doc->root, doc);
	   TextKeyPress();
	   ntree_traverse_preorder(doc->root,TextNodeProcess , (void *) doc);
	   AdllAskOutput(options);
	   AdllGen(doc, options->output);
	 }
       else
	 {
	   AdllGen(doc, options->output);
	 }

}
