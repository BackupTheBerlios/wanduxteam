/* -*- mode: C; c-file-style: "gnu" -*- */
/* ntree.h Tree n-branch
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

#include "ntree.h"

NTreeNode *ntree_node_new(const void *data)
{
  NTreeNode *new;

  if ((new = (NTreeNode *) malloc(sizeof (NTreeNode))) == NULL)
    return (NULL);
  new->next = NULL;
  new->prev = NULL;
  new->parent = NULL;
  new->children = NULL;
  new->data = (void *) data;
  return (new);
}

void ntree_node_destroy(NTreeNode *root, void (*destroy)(void *data))
{
  NTreeNode *node;
  NTreeNode *next;

  ntree_node_unlink(root);
  node = root;
  while (node != NULL) {
    next = node->next;
    ntree_node_destroy(node->children, destroy);
    destroy(node->data);
    free(node);
    node = next;
  }
}

void ntree_node_unlink(NTreeNode *node)
{
  if (node->prev)
    node->prev->next = node->next;
  else if (node->parent)
    node->parent->children = node->next;
  node->parent = NULL;
  if (node->next) {
    node->next->prev = node->prev;
    node->next = NULL;
  }
  node->prev = NULL;
}


NTreeNode *ntree_ins_before(NTreeNode *parent,
                            NTreeNode *sib,
                            const void *data)
{
  NTreeNode *new;

  if (sib != NULL && sib->parent != parent)
    return (NULL);
  if ((new = ntree_node_new(data)) == NULL)
    return (NULL);
  new->parent = parent;
  if (sib) {
    if (sib->prev) {
      new->prev = sib->prev;
      new->prev->next = new;
      new->next = sib;
      sib->prev = new;
    }
    else {
      new->parent->children = new;
      new->next = sib;
      sib->prev = new;
    }
  }
  else {
    if (parent->children) {
      sib = parent->children;
      while (sib->next)
	sib = sib->next;
      new->prev = sib;
      sib->next = new;
    }
    else
      new->parent->children = new;
  }
  return (new);
}

NTreeNode *ntree_ins_after(NTreeNode *parent,
                           NTreeNode *sib,
                           const void *data)
{
  NTreeNode *new;

  if (sib != NULL && sib->parent != parent)
    return (NULL);
  if ((new = ntree_node_new(data)) == NULL)
    return (NULL);
  new->parent = parent;
  if (sib) {
    if (sib->next) {
      sib->next->prev = new;
    }
    new->next = sib->next;
    new->prev =  sib;
    sib->next = new;
  }
  else {
    if (parent->children) {
      new->next = parent->children;
      parent->children->prev = new;
    }
    parent->children = new;
  }
  return (new);
}

NTreeNode *ntree_ins_prepend(NTreeNode *parent, const void *data)
{
  return (ntree_ins_before(parent, parent->children, data));
}

NTreeNode *ntree_get_root(NTreeNode *node)
{
  if (node == NULL)
    return (NULL);
  while (node->parent)
    node = node->parent;
  return (node);
}

NTreeNode	*ntree_find_parent(NTreeNode *node, NTreeFindFunc  func)
{
  NTreeNode	*parent;

  if (node == NULL)
    return (NULL);
  if (node->parent == NULL)
    return (NULL);
  for (parent = node->parent; parent != NULL; parent = parent->parent) {
    if (func(parent, parent->data) == 1) {
      return (parent);
    }
  }
  return (NULL);  
}

int	ntree_traverse_preorder(NTreeNode *node,
                                NTreeTraverseFunc func,
                                void *data)
{
  if (node->children) {
    NTreeNode *child;
    
    if (func(node, data))
      return (1);
    child = node->children;
    while (child)
      {
	NTreeNode *current;
	
	current = child;
	child = current->next;
	if (ntree_traverse_preorder(current, func, data))
	  return (1);
      }
  }
  else if (func (node, data))
    return (1);
  return (0);
}
