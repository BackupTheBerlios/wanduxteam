/* -*- mode: C; c-file-style: "gnu" -*- */
/* option.h Command line option ressources
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

#ifndef __OPTION_H__
#define __OPTION_H__

#include <getopt.h>

#include "common.h"

ADLL_BEGIN_DECLS;

/* Default mode: 1=text_mode(forced)  0=gtk_mode (can switch to textmode) */
#ifndef SUPPORT_GTK
#define DEF_MODE	1
#else
#define DEF_MODE	0
#endif

typedef struct  AdllOption_ AdllOption;

struct  AdllOption_
{
  int		textmode;
  int		quietmode;
  char		*output;
  char		**input;
  int		nbinput;
};

int    	adll_option_init(AdllOption *option, int ac, char *av[]);

ADLL_END_DECLS;

#endif /* __OPTION_H__ */
