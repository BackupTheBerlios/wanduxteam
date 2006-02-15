/* -*- mode: C; c-file-style: "gnu" -*- */
/* common.h  Convenience header
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

#ifndef __ADLL_H__
#define __ADLL_H__


#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#ifdef HAVE_STDINT_H
#include <stdint.h>
#endif

#ifdef HAVE_STRING_H
#include <string.h>
#else
#include <strings.h>
#endif
#ifdef ENABLE_NLS
#include <libintl.h>
#include <locale.h>
#define _(String) gettext(String)
#else 
#define _(String) String
#endif
#define N_(String) String

#ifdef SUPPORT_GTK
#include <gtk/gtk.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#ifdef  __cplusplus
#  define ADLL_BEGIN_DECLS  extern "C" {
#  define ADLL_END_DECLS    }
#else
#  define ADLL_BEGIN_DECLS
#  define ADLL_END_DECLS
#endif

#ifndef FALSE
#  define FALSE 0
#endif
#ifndef TRUE
#  define TRUE !FALSE
#endif

#ifndef NULL
#  ifdef __cplusplus
#    define NULL        (0L)
#  else /* !__cplusplus */
#    define NULL        ((void*) 0)
#  endif /* !__cplusplus */
#endif

/**
 * @defgroup AdllMacros Standard macros.
 * @brief #TRUE, #FALSE, #NULL.
 *
 * Standard macros.
 *
 * @{
 */

/**
 * @def ADLL_BEGIN_DECLS
 *
 *
 * Macro used prior to declaring functions in the ADLL header
 * files. Expands to "extern "C"" when using a C++ compiler,
 * and expands to nothing when using a C compiler.
 */
/**
 * @def ADLL_END_DECLS
 *
 * Macro used after declaring functions in the ADLL header
 * files. Expands to "}" when using a C++ compiler,
 * and expands to nothing when using a C compiler.
 */
/**
 * @def TRUE
 *
 * Expands to "1"
 */
/**
 * @def FALSE
 *
 * Expands to "0"
 */
/**
 * @def NULL
 *
 * A null pointer, defined appropriately for C or C++.
 */

/** }@ */

#endif /* __ADLL_H__ */
