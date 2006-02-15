/* -*- mode: C; c-file-style: "gnu" -*- */
/* parser.h Xml Parsing
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

#include "parser.h"

int	AdllXmlNodeIs(xmlNodePtr xmlNode, const char *to_test)
{
  xmlChar	*prop;
  int		v;

  prop = xmlGetProp(xmlNode, (const xmlChar *) to_test);
  if (prop == NULL)
    return (0);
  v = (tolower(prop[0]) == 'y');
  xmlFree(prop);
  return (v);
}

void	AdllNodeNewTemplate(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  adllNode->type = NODE_ROOT;
  adllNode->template.author = (char *) xmlGetProp(xmlNode, "author");
  adllNode->template.os_name = (char *) xmlGetProp(xmlNode, "os_name");
  adllNode->template.os_version = (char *) xmlGetProp(xmlNode, "os_version");
  adllNode->template.adll_version = (char *) xmlGetProp(xmlNode, "adll_version");
}

void		AdllNodeNewSection(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  adllNode->type = NODE_SECTION;
  adllNode->visible = AdllXmlNodeIs(xmlNode, "visible");
  adllNode->needed = AdllXmlNodeIs(xmlNode, "needed");
  adllNode->section.label = (char *) xmlGetProp(xmlNode, "label");
  adllNode->section.name = (char *) xmlGetProp(xmlNode, "name");
  adllNode->section.separator = (char *) xmlGetProp(xmlNode, "separator");
  adllNode->section.terminator = (char *) xmlGetProp(xmlNode, "terminator");
}

void		AdllNodeNewOption(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  xmlChar	*prop;

  adllNode->type = NODE_OPTION;
  adllNode->visible = AdllXmlNodeIs(xmlNode, "visible");
  adllNode->needed = AdllXmlNodeIs(xmlNode, "needed");
  adllNode->option.multi = AdllXmlNodeIs(xmlNode, "multi");
  adllNode->option.label = (char *) xmlGetProp(xmlNode, "label");
  adllNode->option.name = (char *) xmlGetProp(xmlNode, "name");
  adllNode->option.nb_select = 0;
  adllNode->option.multiid = 0;
  adllNode->option.separator = (char *) xmlGetProp(xmlNode, "separator");
  adllNode->option.terminator = (char *) xmlGetProp(xmlNode, "terminator");
  adllNode->option.xmlNode = xmlNode;
  prop = xmlGetProp(xmlNode, "type");
  if (!xmlStrcmp(prop, "combo"))
    adllNode->option.type = OPTION_CHECK; /* hack !! combo non codé */
  if (!xmlStrcmp(prop, "list"))
    adllNode->option.type = OPTION_LIST;
  if (!xmlStrcmp(prop, "radio"))
    adllNode->option.type = OPTION_RADIO;
  if (!xmlStrcmp(prop, "check"))
    adllNode->option.type = OPTION_CHECK;
  if (!xmlStrcmp(prop, "text")) {
    adllNode->option.type = OPTION_TEXT;
  }
}

void	AdllNodeNewChoice(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  adllNode->type = NODE_CHOICE;
  adllNode->needed = AdllXmlNodeIs(xmlNode, "needed");
  adllNode->choice.label = (char *) xmlGetProp(xmlNode, "label");
  adllNode->choice.selected = AdllXmlNodeIs(xmlNode, "selected");
  adllNode->choice.str = xmlNodeListGetString(xmlNode->doc, xmlNode->xmlChildrenNode, 1);
  if (!IsNotBlankString(adllNode->choice.str)) {
    free(adllNode->choice.str);
    adllNode->choice.str = NULL;
  }
}

void	AdllNodeNewComment(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  adllNode->type = NODE_COMMENT;
  adllNode->comment.str = xmlNodeListGetString(xmlNode->doc, xmlNode->xmlChildrenNode, 1);
}

void	AdllNodeNewCommand(xmlNodePtr xmlNode, AdllNode *adllNode)
{
  adllNode->type = NODE_COMMAND;
  adllNode->visible = AdllXmlNodeIs(xmlNode, "visible");
  adllNode->needed = AdllXmlNodeIs(xmlNode, "needed");
  adllNode->command.str = xmlNodeListGetString(xmlNode->doc, xmlNode->xmlChildrenNode, 1);
}

AdllNode		*AdllNodeNew(xmlNodePtr xmlNode)
{
  AdllNode	*new;

  new = malloc(sizeof (AdllNode));
  memset(new, 0, sizeof (AdllNode));
  new->visible = 1;
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "template"))
    AdllNodeNewTemplate(xmlNode, new);
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "section"))
    AdllNodeNewSection(xmlNode, new);
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "option"))
    AdllNodeNewOption(xmlNode, new);
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "choice"))
    AdllNodeNewChoice(xmlNode, new);
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "comment"))
    AdllNodeNewComment(xmlNode, new);
  if (!xmlStrcmp(xmlNode->name, (const xmlChar *) "command"))
    AdllNodeNewCommand(xmlNode, new);
  return (new);
}

AdllDoc		**AdllParseAll(AdllXmlData **xmls)
{
  int		i;
  AdllDoc	**adllDocs;

  if (xmls == NULL)
    return (NULL);
  adllDocs = malloc(sizeof (AdllDoc *) * (TabCount(xmls) + 1));
  for (i = 0; xmls[i]; i++)
    adllDocs[i] = AdllParseXml(xmls[i]);
  adllDocs[i] = NULL;
  return (adllDocs);
}

AdllDoc *AdllParseXml(AdllXmlData *xml)
{

  AdllDoc	*doc;
  xmlNodePtr	root;

  doc = malloc(sizeof (AdllDoc));
  doc->parseOK = 1;
  doc->xml = xml;
  root = xmlDocGetRootElement(xml->doc);
  doc->root = ntree_node_new(NULL);
  AdllParseXmlRec(doc, root, doc->root);
  doc->root = doc->root->children; /* !! */
  doc->root->parent = NULL;        /* !! */
  return (doc);
}

void		AdllParseXmlRec(AdllDoc *doc, xmlNodePtr node, NTreeNode *treeparent)
{
  xmlNodePtr	cur_node = NULL;
  NTreeNode	*sib = NULL;

  for (cur_node = node; cur_node; cur_node = cur_node->next) {
    if (cur_node->type == XML_ELEMENT_NODE) {
      sib = ntree_ins_append(treeparent, AdllNodeNew(cur_node));
    }
    AdllParseXmlRec(doc, cur_node->children, sib);
  }
}
