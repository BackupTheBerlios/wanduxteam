/* -*- mode: C; c-file-style: "gnu" -*- */
/* gen.c File generator
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
 *
 */

#include "gen.h"
// generate
int	AdllGen(AdllDoc *doc, char *output)
{
  FILE	*f;

  f = fopen(output, "w");
  if (f == NULL)
    exit(1);
  printf(_("Writing file ."));
  fflush(0);
  AdllGenWrite(f, doc->root);
  printf("\n");
  fclose(f);
}

int		AdllGenWriteOption(FILE *f, NTreeNode *node)
{
  AdllNode	*anode;
  AdllNode	*panode;

  anode = (AdllNode *) ntree_data(node);
  panode = (AdllNode *) ntree_data(node->parent);
  fwrite(anode->option.name, strlen(anode->option.name), 1, f);
  if (node->children != NULL)
    AdllGenWrite(f, node->children);
  if (panode->type == NODE_OPTION) {
    if (node->next != NULL) {
      if (anode->option.terminator != NULL)
	fwrite(anode->option.terminator,
	       strlen(anode->option.terminator), 1, f);
      if (panode->option.separator != NULL)
	fwrite(panode->option.separator,
	       strlen(panode->option.separator), 1, f);
    }
    else {
      if (anode->option.terminator != NULL)
	fwrite(anode->option.terminator,
	       strlen(anode->option.terminator), 1, f);
    }
  }
  else if (panode->type == NODE_SECTION ||
	   panode->type == NODE_CHOICE) {
    if (anode->option.terminator != NULL)
      fwrite(anode->option.terminator,
	     strlen(anode->option.terminator), 1, f);
  }
  return (0);
}

int		AdllGenWriteChoice(FILE *f, NTreeNode *node)
{
  AdllNode	*anode;
  AdllNode	*panode;

  anode = (AdllNode *) ntree_data(node);
  panode = (AdllNode *) ntree_data(node->parent);
  //printf ("node parent data: %s\n", panode->parent->data);
  if (anode->choice.selected != 1)
    return (0);
  if (anode->choice.str != NULL) {
    fwrite(anode->choice.str, strlen(anode->choice.str), 1, f);
  }
  if (node->children != NULL)
    {
      AdllGenWrite(f, node->children);
    }
  panode->option.nb_select++;
  if (panode->option.separator != NULL)
    {
      if (panode->option.nb_select > 0 && strcmp(panode->option.label, "Activer le serveur X ")) {
	fwrite(panode->option.separator, strlen(panode->option.separator),
	       1, f);
      }
    }
}

int		AdllGenWriteSection(FILE *f, NTreeNode *node)
{
  AdllNode	*anode;

  anode = (AdllNode *) ntree_data(node);
  fwrite(anode->section.name, strlen(anode->section.name), 1, f);
  if (node->children != NULL)
    AdllGenWrite(f, node->children);
  if (anode->section.separator != NULL)
    fwrite(anode->section.separator, strlen(anode->section.separator), 1, f);
  printf(anode->section.separator);
  return (0);
}

int		AdllGenWrite(FILE *f, NTreeNode *root)
{
  NTreeNode	*node;
  AdllNode	*anode;

  for (node = root; node != NULL; node = node->next) {
    anode = (AdllNode *) ntree_data(node);
    if (anode->type == NODE_SECTION) {
      AdllGenWriteSection(f, node);
    }
    else if (anode->type == NODE_COMMAND) {

      fwrite(anode->command.str, strlen(anode->command.str), 1, f);
    }
    else if (anode->type == NODE_OPTION) {
      AdllGenWriteOption(f, node);
    }
    else if (anode->type == NODE_CHOICE) {

      AdllGenWriteChoice(f, node);
    }
    else if (anode->type == NODE_ROOT) {
      if (node->children != NULL)
	AdllGenWrite(f, node->children);
    }
  }
  return (0);
}
