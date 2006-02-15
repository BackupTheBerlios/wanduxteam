/* -*- mode: C; c-file-style: "gnu" -*- */
/* nodes.c AdllNode functions
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
#include "nodes.h"

static int	AdllNodeFindSectionCB(NTreeNode *node, void *data)
{
  if (((AdllNode *) data)->type == NODE_SECTION)
    return (1);
  return (0);
}

static int	AdllNodeFindOptionCB(NTreeNode *node, void *data)
{
  if (((AdllNode *) data)->type == NODE_OPTION)
    return (1);
  return (0);
}

static int	AdllNodeFindNoVisibleCB(NTreeNode *node, void *data)
{
  if (((AdllNode *) data)->visible == 0)
    return (1);
  return (0);
}

static int	AdllNodeFindChoiceNoSelectedCB(NTreeNode *node, void *data)
{
  if (((AdllNode *) data)->type == NODE_CHOICE)
    if (((AdllNode *) data)->choice.selected == 0)
      return (1);
  return (0);
}

NTreeNode	*AdllNodeGetSectionParent(NTreeNode *node)
{
  return (ntree_find_parent(node, AdllNodeFindSectionCB));
}

NTreeNode	*AdllNodeGetOptionParent(NTreeNode *node)
{
  return (ntree_find_parent(node, AdllNodeFindOptionCB));
}

int		AdllNodeParentIsVisible(NTreeNode *node)
{
  NTreeNode	*nodeNoVisible;

  if (((AdllNode *) node->data)->visible == 0)
    return (0);
  nodeNoVisible = ntree_find_parent(node, AdllNodeFindNoVisibleCB);
  if (nodeNoVisible == NULL)
    return (1);
  return (0);
}

int		AdllNodeOptionIsMulti(NTreeNode *node)
{
  NTreeNode	*n;

  for (n = node; n != NULL; n = n->prev) {
    if (((AdllNode *) n->data)->type == NODE_OPTION) {
      if (((AdllNode *) n->data)->option.multi == 1)
	return (0);
    }
  }
  for (n = node; n != NULL; n = n->next) {
    if (((AdllNode *) n->data)->type == NODE_OPTION) {
      if (((AdllNode *) n->data)->option.multi == 1)
	return (0);
    }
  }
  return (1);
}

int		AdllNodeParentChoiceIsSelected(NTreeNode *node)
{
  NTreeNode	*nodeChoiceNoSelected;

  nodeChoiceNoSelected = ntree_find_parent(node, AdllNodeFindChoiceNoSelectedCB);
  if (nodeChoiceNoSelected == NULL)
    return (1);
  return (0);
}

AdllNodeOptionList	**AdllNodeGetOptionList(NTreeNode *parent)
{
  NTreeNode		*n;
  int			i,j;
  AdllNodeOptionList	**ret;

  ((AdllNode *) ntree_data(parent))->option.nb_select = 0;
  if (parent->children == NULL)
    return (NULL);
  for (n = parent->children, i = 0; n != NULL; n = n->next, i++)
    if (((AdllNode *) n->data)->type != NODE_CHOICE)
      i--;
  if (i <= 0)
    return (NULL);
  ((AdllNode *) ntree_data(parent))->option.nb_select = i;
  ret = malloc(sizeof (AdllNodeOptionList *) * (i+1));
  for (j = 0, n = parent->children; j != i; j++, n = n->next) {
    if (((AdllNode *) n->data)->type != NODE_CHOICE) {
      j--;
      continue ;
    }
    ret[j] = malloc(sizeof (AdllNodeOptionList));
    ret[j]->nodeChoice = n;
    ret[j]->adllNode = (AdllNode *) n->data;
  }
  ret[j] = NULL;;
  return (ret);
}

int		AdllNodeOptionHaveChoice(NTreeNode *curnode)
{
  AdllNode	*n;

  if (curnode->children == NULL)
    return (0);
  n = (AdllNode *) curnode->children->data;
  if (n->type == NODE_CHOICE)
    return (1);
  return (0);
}


void		AdllNodeSetChoiceSelected(NTreeNode *selected)
{
  NTreeNode	*n;
  AdllNode	*adllnode;

  for (n = selected; n != NULL; n = n->prev) {
    adllnode = (AdllNode *) n->data;
    adllnode->choice.selected = 0;
  }
  for (n = selected; n != NULL; n = n->next) {
    adllnode = (AdllNode *) n->data;
    adllnode->choice.selected = 0;
  }
  adllnode = (AdllNode *) selected->data;
  adllnode->choice.selected = 1;
  ((AdllNode *) ntree_data(selected->parent))->option.nb_select = 1;
}

