/* -*- mode: C; c-file-style: "gnu" -*- */
/* error.c Error reporting
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

#include "error.h"

DList	*adllErrorList = NULL;

void	adll_error_init()
{
  adllErrorList = malloc(sizeof (DList));
  dlist_init(adllErrorList, adll_error_destroyCB);
}

void		adll_error_push(int level, const char *format, ...)
{
  AdllError	*adllError;
  va_list	ap;
  char		strerror[1024];

  if (adllErrorList == NULL)
    adll_error_init();
  va_start(ap, format);
  adllError = malloc(sizeof (AdllError));
  adllError->level = level;
  adllError->msg = NULL;
  vsprintf(strerror, format, ap);
  va_end(ap);
  adllError->msg = strdup(strerror);
  dlist_ins_next(adllErrorList, dlist_tail(adllErrorList), adllError);
}

void    adll_error_print()
{
  AdllError	*error;

  if (adllErrorList == NULL)
    return ;
  if ((dlist_remove(adllErrorList,
		    dlist_head(adllErrorList),
		    (void **) &error)) == -1)
    return ;
  if (error->level == FATAL_ERROR)
    printf(_("*** Error *** "));
  else if (error->level == WARN_ERROR)
    printf(_("** Warning ** "));
  puts(error->msg);
  adll_error_destroyCB((void *) error);
  if (error->level == FATAL_ERROR)
    exit(EXIT_FAILURE);
}

void	adll_errors_print()
{
  AdllError	*error;

  if (adllErrorList == NULL)
    return ;
  while (dlist_size(adllErrorList) != 0) {
    adll_error_print();
  }
}

void	adll_error_list_destroy()
{
  dlist_destroy(adllErrorList);
  adllErrorList = NULL;
}


void	 adll_error_destroyCB(void *data)
{
  AdllError *error;

  if (error->msg != NULL)
    free(error->msg);
  free(data);
}

int	adll_error_ge_nb()
{
  return (dlist_size(adllErrorList));
}

char	*adll_error_get(int *level)
{
  AdllError	*error;
  char		*ret;

  if (adllErrorList == NULL)
    return (NULL);
  if ((dlist_remove(adllErrorList,
		    dlist_head(adllErrorList),
		    (void **) &error)) == -1)
    return (NULL);
  *level = error->level;
  ret = strdup(error->msg);
  adll_error_destroyCB((void *) error);
}

char	**adll_errors_get(int **level)
{
  char	**ret;
  char	*msg;
  int	i;

  ret = malloc(sizeof (char *) * (dlist_size(adllErrorList) + 1));
  *level = malloc(sizeof(int) * (dlist_size(adllErrorList) + 1));
  for (i = 0; (ret[i] = adll_error_get(&(*level[i]))) != NULL; i ++)
    ;
  return (ret);
}
