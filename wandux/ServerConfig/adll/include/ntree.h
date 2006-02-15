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

#ifndef __NTREE_H__
#define __NTREE_H__

#include <stdlib.h>
#include <string.h>

#include "common.h"

ADLL_BEGIN_DECLS;

/**
 * @addtogroup  Libraries
 * 
 * @{
 */

/**
 * @defgroup NTree N-ary Trees
 * @ingroup  Libraries
 * @brief  The  NTreeNode struct and its associated functions 
 * provide a N-ary tree data structure, where nodes in
 * the tree can contain arbitrary data.
 *
 * @{
 */

typedef struct NTreeNode_ NTreeNode;
typedef void (*NTreeDestroyFunc) (void *data);
typedef int (*NTreeFindFunc)	 (NTreeNode *node, void *data);
typedef int (*NTreeTraverseFunc) (NTreeNode *node, void *data);

/**
 * NTreeNode
 *
 *
 * The NTreeNode struct represents one node in a N-ary Tree.
 * The data field contains the actual data of the node.
 * The next and prev fields point to the node's siblings
 * (a sibling is another NTreeNode with the same parent).
 * The parent field points to the parent of the NTreeNode,
 * or is NULL if the NTreeNode is the root of the tree.
 * The children field points to the first child of the NTreeNode.
 * The other children are accessed by using the next pointer of each child.
 * 
 */
struct		NTreeNode_
{
  void		*data;		/**< Data stored at this element. */
  NTreeNode	*next;		/**< Next tree node. */
  NTreeNode	*prev;		/**< Previous tree node. */
  NTreeNode	*parent;	/**< Parent tree node. */
  NTreeNode	*children;	/**< First children tree node. */
};

/**
 * Creates a new NTreeNode containing the given data.
 * Used to create the first node in a tree.
 *
 * @param data the data of the new node.
 * @returns a new NTreeNode
 */
NTreeNode *ntree_node_new(const void *data);

/**
 * Removes the NTreeNode and its children from the tree,
 * freeing any memory allocated.
 *
 * @param root the root of the tree/subtree to destroy.
 * @param func the function to call for each node to destroy.
 */
void ntree_node_destroy(NTreeNode *root, NTreeDestroyFunc func);

/**
 * Unlinks a NTreeNode from a tree, resulting in two separate trees.
 * 
 * @param node the NTreeNode to unlink, which becomes the root of a new tree.
 */
void ntree_node_unlink(NTreeNode *node);

/**
 * Inserts data in a new NtreeNode before the given sibling.
 * 
 * @param parent the NTreeNode to place the new NTreeNode under.
 * @param sib the sibling NTreeNode to place the new NTreeNode before.
 * @param data the data for the new NTreeNode.
 * @returns the new NTreeNode.
 */
NTreeNode *ntree_ins_before(NTreeNode *parent, NTreeNode *sib, const void *data);

/**
 * Inserts data in a new NtreeNode after the given sibling.
 * 
 * @param parent the NTreeNode to place the new NTreeNode under.
 * @param sib the sibling NTreeNode to place the new NTreeNode after.
 * @param data the data for the new NTreeNode.
 * @returns the new NTreeNode.
 */
NTreeNode *ntree_ins_after(NTreeNode *parent, NTreeNode *sib, const void *data);

/**
 * Inserts a new NTreeNode as the first child of the given parent.
 * 
 * @param parent the NTreeNode to place the new NTreeNode under.
 * @param data the data for the new NTreeNode.
 * @returns the new NTreeNode.
 */
NTreeNode *ntree_ins_prepend(NTreeNode *parent, const void *data);

/**
 * Gets the root of a tree.
 * 
 * @param node a NTreeNode
 * @returns the root of the tree.
 */
NTreeNode *ntre_get_root(NTreeNode *node);

/**
 * find a parent of a NTreeNode using a user find function
 * 
 * @param node a NTreeNode
 * @param func the function which is called for each visited node.
 * @returns the NTreeNode finded, or #NULL if not.
 */
NTreeNode *ntree_find_parent(NTreeNode *node, NTreeFindFunc func);

/**
 * Traverses a tree starting at the given root NTreeNode.
 * It calls the given function for each node visited.
 * The traversal can be halted at any point by returning #TRUE from func.
 * Traverses preorder means that the function visite a node, then, its chiddren.
 *
 * @param node the root NTreeNode of the tree to traverse.
 * @param func the function to call for each visited NTreeNode.
 * @param data user data to pass to the function.
 * @returns 
 */
int	ntree_traverse_preorder(NTreeNode *node,  NTreeTraverseFunc func, void *data);

/**
 * Inserts a new NTreeNode as the last child of the given parent.
 * 
 * @param parent the NTreeNode to place the new NTreeNode under.
 * @param data the data for the new NTreeNode.
 * @returns the new NTreeNode.
 */
#define ntree_ins_append(parent, node)	ntree_ins_before((parent), NULL, (node))
#define ntree_next(node)		((node)->next)
#define ntree_prev(node)		((node)->prev)
#define ntree_parent(node)		((node)->parent)
#define ntree_data(node)		((node)->data)
#define ntree_children(node)		((node)->children)


/** @} */
/** @} */

ADLL_END_DECLS;

#endif /* __NTREE_H__ */
