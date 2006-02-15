/* -*- mode: C; c-file-style: "gnu" -*- */
/* xml.h Xml functions
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

#include "xml.h"

void	my_xml_error(void *userData, xmlErrorPtr error)
{
  int	ret;

  /*printf("domain :%i, code : %i, level: \n",
	 error->domain, error->code, error->level);
  printf("msg :%s", error->message);
  printf("line : %i, file: %s\n", error->line, error->file);
  printf("str1: %s, str2: %s, str3: %s\n\n",
  error->str1, error->str2, error->str3);*/
  switch (error->code) {
  case XML_IO_LOAD_ERROR:
    adll_error_push(WARN_ERROR, _("Can't open file '%s'"), error->str1);
    break ;
  case XML_DTD_CONTENT_MODEL:
    adll_error_push(WARN_ERROR, _("%s:%i: Element %s content does not follow the DTD"),
		  error->file, error->line, error->str1);
    break ;
  case XML_DTD_UNKNOWN_ELEM:
  case XML_DTD_UNKNOWN_ATTRIBUTE:
    adll_error_push(WARN_ERROR, _("%s:%i: element %s: No declaration for element %s"),
		  error->file, error->line, error->str1, error->str1);
    break ;
  case XML_ERR_TAG_NAME_MISMATCH:
    adll_error_push(WARN_ERROR, _("%s:%i: Opening and ending tag mismatch: %s line %i and %s"),
		  error->file, error->line, error->str1, error->line, error->str2);
    break ;
  case XML_DTD_ATTRIBUTE_VALUE:
    adll_error_push(WARN_ERROR, _("%s:%i: element %s: value '%s' is unkwnon"),
		  error->file, error->line, error->str3, error->str1);
    break ;
  default:
    adll_error_push(FATAL_ERROR, _("Unknow Error"));
    break ;
  };
}

AdllXmlData    	**open_all_xml(char **files, int nb)
{
  int		i;
  AdllXmlData	**datas;
  AdllXmlData	**datas2;

  int		not_opened = 0;

  if (files == NULL)
    return (NULL);
  datas = (AdllXmlData **) malloc(sizeof (AdllXmlData *) * (nb + 1)); 
  for (i = 0; files[i] != NULL; i++) {
    not_opened += open_xml(files[i], &datas[i]);
  }
  datas2 = (AdllXmlData **) TabClean(datas, TabCount(files) - not_opened);
  free(datas);
  return (datas2);
}

int			open_xml(const char *file, AdllXmlData **xmldata)
{
  xmlDocPtr		doc;
  xmlParserCtxtPtr	ctxtp;

  *xmldata = NULL;
  ctxtp = xmlNewParserCtxt();
  xmlSetStructuredErrorFunc(ctxtp, my_xml_error);
  doc = xmlCtxtReadFile(ctxtp, file, NULL, XML_PARSE_NOBLANKS|
                                           XML_PARSE_NOENT|
                                           XML_PARSE_DTDATTR|
			                   XML_PARSE_DTDVALID );
  if (doc == NULL)
    return (1);
  *xmldata = (AdllXmlData *) malloc(sizeof (AdllXmlData));
  (*xmldata)->doc = doc;
  (*xmldata)->file = strdup(file);
  xmlFreeParserCtxt(ctxtp);
  return (0);
}
