/* -*- mode: C; c-file-style: "gnu" -*- */
/* textmode.h Texte mode functions
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

#ifndef __ADLLTEST_H__
#define __ADLLTEST_H__

#include "common.h"
#include "error.h"
#include "gen.h"
#include "ntree.h"
#include "nodes.h"
#include "option.h"
#include "parser.h"

#define EMPTY_LINES	50
#define BUFFER_IN	80
#ifndef _POSIX2_LINE_MAX
#define _POSIX2_LINE_MAX	2048
#endif

void	TextKeyPress(void);
void	TextPrintEmptylines(int n);
int	TextAnswerIsYes(char *s);

void	TextPrintIntro(NTreeNode *root, AdllDoc *doc);
void	TextPrintSectionHeader(NTreeNode *cur);
void	TextPrintOptionName(NTreeNode *cur);

int	IsNotBlankString(char *s);

void	AdllLunchTextMode(AdllDoc *doc, AdllOption *options);

#endif /* __ADLLTEST_H__ */
