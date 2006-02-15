/* -*- mode: C; c-file-style: "gnu" -*- */
/* dlist.c Double Linked-list utility
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

#include "dlist.h"


void dlist_init(DList *list, DListDestroyFunc func)
{
  list->size = 0;
  list->destroy = func;
  list->head = NULL;
  list->tail = NULL;
}

void	dlist_destroy(DList *list)
{
  void	*data;

  while (dlist_size(list) > 0) {
    if (dlist_remove(list, dlist_tail(list), (void **)&data) == TRUE
	&& list->destroy != NULL) {
      list->destroy(data);
    }
  }
  memset(list, 0, sizeof(DList));
}

int		dlist_ins_next(DList *list, DListElmt *elm, const void *data)
{
  DListElmt	*new;

  if (elm == NULL && dlist_size(list) != 0)
    return (FALSE);
  if ((new = (DListElmt *) malloc(sizeof (DListElmt))) == NULL)
    return (FALSE);
  new->data = (void *) data;
  if (dlist_size(list) == 0) {
    list->head = new;
    list->head->prev = NULL;
    list->head->next = NULL;
    list->tail = new;
  }
  else {
    new->next = elm->next;
    new->prev = elm;
    if (elm->next == NULL)
      list->tail = new;
    else
      elm->next->prev = new;
    elm->next = new;
  }
  list->size++;
  return (TRUE);
}

int		dlist_ins_prev(DList *list, DListElmt *elm, const void *data)
{
  DListElmt	*new;

  if (elm == NULL && dlist_size(list) != 0)
    return (FALSE);
  if ((new = (DListElmt *) malloc(sizeof (DListElmt))) == NULL)
    return (FALSE);
  new->data = (void *) data;
  if (dlist_size(list) == 0) {
    list->head = new;
    list->head->prev = NULL;
    list->head->next = NULL;
    list->tail = new;
  }
  else {
    new->next = elm;
    new->prev = elm->prev;
    if (elm->prev == NULL)
      list->head = new;
    else
      elm->prev->next = new;
    elm->prev = new;
  }
  list->size++;
  return (TRUE);
}

int dlist_remove(DList *list, DListElmt *elm, void **data)
{
  if (elm == NULL || dlist_size(list) == 0)
    return (FALSE);
  *data = elm->data;
  if (elm == list->head) {
    list->head = elm->next;
    if (list->head == NULL)
      list->tail = NULL;
    else
      elm->next->prev = NULL;
  }
  else {
    elm->prev->next = elm->next;
    if (elm->next == NULL)
      list->tail = elm->prev;
    else
      elm->next->prev = elm->prev;
  }
  free(elm);
  list->size--;
  return (TRUE);
}
