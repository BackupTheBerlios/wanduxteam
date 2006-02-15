/* -*- mode: C; c-file-style: "gnu" -*- */
/* utils.h Various functions utility
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

#include "common.h"

void	TabFree(void **tab)
{
  int	i;

  if (tab == NULL)
    return ;
  for (i = 0; tab[i]; i++)
    free(tab[i]);  
}

int	TabCount(void **tab)
{
  int	i;

  if (tab == NULL)
    return (0);
  for (i = 0; tab[i]; i++)
    ;
  return (i);  
}

void	**TabClean(void **tab, int len)
{
  void	**new;
  int	i;
  int	j;

  if (len <= 0 || tab == NULL)
    return (NULL);
  new = malloc(sizeof (void *) * (len + 1));
  for (i = 0, j = 0; len; i++, j++, len--) {
    if (tab[j] == NULL) {
      i--;
      len++;
    }
    else
      new[i] = tab[j];
  }
  new[i] = NULL;
  return (new);
}
