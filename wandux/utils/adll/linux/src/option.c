/* -*- mode: C; c-file-style: "gnu" -*- */
/* option.c Command line option ressources
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

#include "option.h"

extern int optind;

void	usage(const char *name, int style)
{
  char	text[2048];

  if (style == TRUE) {
    sprintf(text,
              (const char *) _("%s %s\n"
	      "Usage: %s [OPTION]... [FILES]...\n\n"
	      " OPTIONS :\n"
	      "  -q, --quiet                  Quiet mode\n"
	      "  -t, --text                   Text mode\n"
	      "  -o, --output                 Output file (valid with just 1 xml input)\n"
	      "  -v, --version                Display the current version and exit\n"
	      "  -h, --help                   Display this help and exit\n"
              "\n"
              "FILES :\n"
              "  filea.xml filen.xml...       XML files to parse\n"),
              PACKAGE, VERSION, name);
  }
  else
    sprintf(text,(const char *) _("%s %s. Try `%s -h' for more information.\n"),
             PACKAGE, VERSION, name);
  if (text) {
      puts(text);
  }
}

int    	adll_option_init(AdllOption *options, int ac, char **av)
{  
  int	opt;
  int	i;
  struct option long_options[] = {
    {"quiet", no_argument, NULL, 'q'},
    {"help", no_argument, NULL, 'h'},
    {"text", no_argument, NULL, 't'},
    {"output", required_argument, NULL, 'o'},
    {"version", no_argument, NULL, 'v'},
    {0, 0, 0, 0}
  };

  options->quietmode = FALSE;
  options->textmode = DEF_MODE;
  options->input = NULL;
  options->nbinput = 0;
  options->output = NULL;
#ifdef SUPPORT_GTK
  gtk_init(&ac, &av);
#endif
  while ((opt = getopt_long(ac, av, "qhto:v", long_options, NULL)) != -1) {
    switch (opt) {
    case 'q':
      options->quietmode = TRUE;
      break ;
    case 'h':
      usage(av[0], TRUE);
      exit(0);
      break ;
    case 'o':
      if (optarg)
	options->output = strdup(optarg);
    case 't':
      options->textmode = TRUE;
      break ;
    case 'v':
      printf("%s %s\n", PACKAGE, VERSION);
      exit(0);
      break ;
    case '?':
    default:
      usage(av[0], FALSE);
      exit(0);
      break ;
    };
  }
  if (options->quietmode == TRUE) {
    options->textmode = TRUE;
    puts(_("** Quiet mode seleted -> text mode forced"));
  }
  if (optind < ac) {
    if (options->textmode == TRUE && (ac - optind) > 1) {
      options->input = (char **) malloc(sizeof (char *) * 2);
      puts(_("** Only one XML file is parse in 'text mode'"));
      options->input[0] = strdup(av[optind]);
      options->input[1] = NULL;
      options->nbinput = 1;
    }
    else {
      options->input = (char **) malloc(sizeof (char *) * (ac - optind  + 1));
      i = 0;
      while (optind < ac) {
	options->input[i++] = strdup(av[optind++]);
      }
      options->input[i] = NULL;
      options->nbinput = i;
    }
  }
  if (options->nbinput > 1 && options->output != NULL) {
    puts(_("!* 'Output file' option invalid when using multiple xml input *!"));
      exit(0);
  }
  return (TRUE);
}
