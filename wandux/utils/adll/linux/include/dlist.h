/* -*- mode: C; c-file-style: "gnu" -*- */
/* dlist.h Double Linked-list utility
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


#ifndef __DLIST_H__
#define __DLIST_H__

#include <stdlib.h>
#include <string.h>

#include "common.h"

ADLL_BEGIN_DECLS;

/**
 * @defgroup  Libraries Misceanellous libraries.
 * 
 * @{
 */

/**
 * @defgroup DList double-linked list.
 * @ingroup  Libraries
 * @brief DList Implementation details.
 *
 * Types and functions related to DList.
 *
 * @{
 */

/**
 * conveniance typedef
 */
typedef struct	DListElmt_	DListElmt;

/**
 * conveniance typedef
 */
typedef struct	DList_		DList;

/**
 * User fonction to destroy data.
 *
 * Initialised with \ref dlist_init and
 * used by \ref dlist_destroy 
 */
typedef void (*DListDestroyFunc) (void *data);

/**
 * A List element
 */
struct	DListElmt_
{
  void		*data;		/**< Data stored at this element. */
  DListElmt	*next;		/**< Next list node. */
  DListElmt	*prev;		/**< Previous list node. */
};

/**
 * DList
 *
 * A node in a double-linked list.
 *
 * DList is a double-linked list; that is, an element of the
 * list points back to the previous element and points to the
 * next element.
 */
struct	DList_
{
  int			size;			/**< The number of element */
  int			(*cmp)(const void *d1, const void *d2);	/**< compare function */
  DListDestroyFunc     	destroy;		/**< destroy function */
  DListElmt		*head;			/**< The fist element node. */
  DListElmt		*tail;			/**< The last element node. */
};

/**
 * Initialize a Double-Linked list.
 *
 * @param list address to the list to initialize.
 * @param func adress to the destroy function for data.
 */
void dlist_init(DList *list, DListDestroyFunc func);

/**
 * Destroy a Double-Linked list.
 *
 * @param list address to destroy.
 */
void dlist_destroy(DList *list);

/**
 * Insert data into the list after the given existing element.
 * If elm == NULL and list size = 0, data will be at the head of list.
 *
 * @param list the list to modify.
 * @param elm existing element to insert after, or #NULL to head.
 * @param data the value to insert.
 * @returns #TRUE on success, #FALSE if errors.
 */
int dlist_ins_next(DList *list, DListElmt *elm, const void *data);

/**
 * Insert data into the list before the given existing element.
 * If elm == NULL and list size = 0, data will be at the head of list.
 *
 * @param list the list to modify.
 * @param elm existing element to insert before, or #NULL to head.
 * @param data the value to insert.
 * @returns #TRUE on success, #FALSE if errors.
 */
int dlist_ins_prev(DList *list, DListElmt *elm, const void *data);

/**
 * Remove an element from the list. The address of
 * address data is stocked in **data.
 *
 * @param list address of the list.
 * @param elm existing element to remove.
 * @param data the data from the element to remove.
 * @returns #TRUE if a value was found to remove.
 */
int dlist_remove(DList *list, DListElmt *elm, void **data);


/**
 * Give the list size.
 *
 * @param list address to the list.
 */
#define dlist_size(list)	((list)->size)

/**
 * Give the element head of list.
 *
 * @param list address to the list.
 */
#define dlist_head(list)	((list)->head)

/**
 * Give the element tail of list.
 *
 * @param list address to the list. 
 */
#define dlist_tail(list)	((list)->tail)

/**
 * Evaluate if an element is at head list.
 *
 * @param elm element to evaluate.
 */
#define dlist_is_head(elm)	((elm)->prev == NULL ? 1 : 0)

/**
 * Evaluate if an element is at tail list.
 *
 * @param elm element to eval
 */
#define dlist_is_tail(elm)	((elm)->next == NULL ? 1 : 0)

/**
 * Conveniance macro to get data element.
 *
 * @param elm element to eval
 */
#define dlist_data(elm)		((elm)->data)

/**
 * Gets the next element in the list.
 *
 * @param elm element to eval.
 */
#define dlist_next(elm)		((elm)->next)

/**
 * Gets the previous element in the list.
 *
 * @param elm element to eval.
 */
#define dlist_prev(elm)		((elm)->prev)

/** @} */
/** @} */

ADLL_END_DECLS;

#endif /* __DLIST_H__ */
