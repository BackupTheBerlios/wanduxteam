/* -*- mode: C; c-file-style: "gnu" -*- */
/* error.h Error reporting
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

#ifndef __ERROR_H__
#define __ERROR_H__

#include <stdarg.h>

#include "common.h"
#include "dlist.h"

ADLL_BEGIN_DECLS;

#define WARN_ERROR	0
#define FATAL_ERROR	1

typedef struct	AdllError_ AdllError;

/**
 * Object representing an error.
 */
struct	AdllError_ 
{
  int		level;	/**< error level */
  char		*msg;	/**< error message */
};

void	adll_error_init();
void	adll_error_push(int level, const char *format, ...);
void    adll_error_print();
void	adll_errors_print();
void	adll_error_list_destroy();
void	adll_error_destroyCB(void *data);
int	adll_error_ge_nb();
char	*adll_error_get(int *level);
char	**adll_errors_get(int **level);

ADLL_END_DECLS;
#endif /* __ERROR_H__ */
