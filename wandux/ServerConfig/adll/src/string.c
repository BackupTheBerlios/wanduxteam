/* -*- mode: C; c-file-style: "gnu" -*- */
/* string.h String utilities
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

#include "textmode.h"

void	getstring(char *s)
{
  char	*cp;

  if (fgets(s, BUFFER_IN, stdin) == NULL)
    exit(1);
  cp = strchr(s, '\n');
  if (cp)
    *cp = '\0';
}

int	TextAnswerIsYes(char *s)
{
  return (tolower(s[0]) == 'y');
}

int	IsNotBlankString(char *s)
{
  if (s == NULL)
    return (1);
  while (*s != '\0') {
    if (*s != '\n' && *s != '\t' && *s != ' ' && *s != '\r')   
      return (1);
    s++;
  }
  return (0);
}

char	*TextGetList(char *list)
{

  char	*positions;
  int	setautostart, start, stop;
  int	autostart, autostop, maxval;
  char	*pos;
  char	*p;

  adll_error_list_destroy();
  adll_error_init();
  positions = malloc(sizeof (char) * (_POSIX2_LINE_MAX + 1));
  memset(positions, 0, sizeof (char) * (_POSIX2_LINE_MAX + 1));
  for (; (p = strtok(list, ", \t")) != NULL; list = NULL) {
    setautostart = start = stop = 0;
    if (*p == '-') {
      ++p;
      setautostart = 1;
    }
    if (isdigit((unsigned char)*p)) {
      start = stop = strtol(p, &p, 10);
      if (setautostart && start > autostart)
	autostart = start;
    }
    if (*p == '-') {
      if (isdigit((unsigned char)p[1]))
	stop = strtol(p + 1, &p, 10);
      if (*p == '-') {
	++p;
	if (!autostop || autostop > stop)
	  autostop = stop;
      }
    }
    if (*p)
      adll_error_push(-1, _("** illegal list value"));
    if (!stop || !start)
      adll_error_push(-1, _("values may not include zero"));
    if (stop > _POSIX2_LINE_MAX)
      adll_error_push(-1, _("%d too large (max %d)"), stop, _POSIX2_LINE_MAX);
    if (maxval < stop)
      maxval = stop;
    for (pos = positions + start; start++ <= stop; *pos++ = 1);
  }
  /* overlapping ranges */
  if (autostop && maxval > autostop)
    maxval = autostop;
  /* set autostart */
  if (autostart)
    memset(positions + 1, '1', autostart);
}
