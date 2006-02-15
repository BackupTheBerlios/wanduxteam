/* -*- mode: C; c-file-style: "gnu" -*- */
/* ui.h User Interface
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

#include "ui.h"
#include "gen.h"

#define     GTK_STOCK_EDIT "gtk-edit"

GtkWidget *gl_wnd;

int	UiCheckParentOptionUnsensitive(NTreeNode *cur)
{
  AdllNode	*anode;
  GtkWidget	*frm;
  int		ret = 0;

  for (; cur != NULL; cur = cur->parent) {
    anode = (AdllNode *) cur->data;
    if (anode->type == NODE_SECTION ||
	anode->type == NODE_ROOT)
      break ;
    if (anode->type != NODE_OPTION)
      continue ;
    if (!GTK_WIDGET_VISIBLE(GTK_WIDGET(anode->option.frm_options)))
      return (1);
  }
  return (ret);
}

GtkTreeIter *GetSectionIterParent(NTreeNode *cur)
{
  NTreeNode	*parent;

  parent = AdllNodeGetSectionParent(cur);
  if (parent == NULL)
    return (NULL);
  return (&(((AdllNode *) parent->data)->section.Iter));
}

GtkWidget *GetOptionBoxParent(GtkWidget *vbox_options,  NTreeNode *cur)
{
  NTreeNode	*parent;
  GtkWidget *b;

  parent = AdllNodeGetOptionParent(cur);
  if (parent == NULL) {
    return (vbox_options);
  }
  return (((AdllNode *) parent->data)->option.vbox);
}

GtkWidget *NewMainMenu(GtkWidget *wnd_main,GtkWidget *ntb_base)
{
  GtkWidget     *mnb_menu;
  GtkWidget     *mnu_menu;
  GtkWidget     *itm_menu;

  mnb_menu = gtk_menu_bar_new();
  mnu_menu = gtk_menu_new();
  itm_menu = gtk_image_menu_item_new_from_stock(GTK_STOCK_OPEN, NULL);
  SIGNAL_CONNECT(itm_menu, "activate", OnMenuOuvrir, (gpointer) wnd_main);
  gtk_menu_shell_append(GTK_MENU_SHELL(mnu_menu), itm_menu);
  itm_menu = gtk_image_menu_item_new_from_stock(GTK_STOCK_CLOSE, NULL);
  SIGNAL_CONNECT(itm_menu, "activate", OnMenuFermer, (gpointer) ntb_base);
  gtk_menu_shell_append(GTK_MENU_SHELL(mnu_menu), itm_menu);
  itm_menu = gtk_separator_menu_item_new();
  gtk_menu_shell_append(GTK_MENU_SHELL(mnu_menu), itm_menu);
  itm_menu = gtk_image_menu_item_new_from_stock(GTK_STOCK_QUIT, NULL);
  SIGNAL_CONNECT(itm_menu, "activate", OnMenuQuitter, NULL);
  gtk_menu_shell_append(GTK_MENU_SHELL(mnu_menu), itm_menu);
  itm_menu = gtk_menu_item_new_with_label(_("File"));
  gtk_menu_item_set_submenu(GTK_MENU_ITEM(itm_menu), mnu_menu);
  gtk_menu_shell_append(GTK_MENU_SHELL(mnb_menu), itm_menu);
  gtk_widget_show_all(mnb_menu);
  return (mnb_menu);
}


static GtkWidget       *NewMainWinWithVBox(GtkWidget **vbx_main)
{
  GtkWidget     *wnd_main;

  wnd_main = gtk_window_new(GTK_WINDOW_TOPLEVEL);
  gtk_window_set_position(GTK_WINDOW(wnd_main), GTK_WIN_POS_CENTER);
  gtk_window_set_default_size(GTK_WINDOW(wnd_main), WIN_WIDTH , WIN_HEIGHT);
  gtk_window_set_title(GTK_WINDOW(wnd_main), PACKAGE);
  g_signal_connect(G_OBJECT(wnd_main), "destroy", G_CALLBACK(OnMainWinDestroy),
                   NULL);
  *vbx_main = gtk_vbox_new(FALSE, 0);
  gtk_container_add(GTK_CONTAINER (wnd_main), *vbx_main);
  gtk_widget_show_all(*vbx_main);
  return (wnd_main);
}

static GtkWidget *GenerateButton(AdllDoc *doc)
{
  GtkWidget	*but;

  but = gtk_button_new_from_stock(GTK_STOCK_CONVERT);
  gtk_button_set_label(GTK_BUTTON(but), _("Generate"));
  SIGNAL_CONNECT(but, "clicked",
		 OnButtonGenerate, (gpointer) doc);
  return (but);
}

static GtkWidget *NewTree(GtkWidget *DstRef, AdllDoc *doc)
{
  GtkTreeStore		*tst_section;
  GtkWidget             *trv_section;
  GtkCellRenderer       *crd_section;
  GtkTreeViewColumn     *trv_c_section;
  GtkWidget             *scw_section;
  
  tst_section = gtk_tree_store_new(2, G_TYPE_STRING, G_TYPE_INT);
  trv_section = gtk_tree_view_new_with_model(GTK_TREE_MODEL(tst_section));
  crd_section = gtk_cell_renderer_text_new();
  trv_c_section = gtk_tree_view_column_new_with_attributes("", crd_section, 
							   "text", 0, NULL);
  gtk_tree_view_set_headers_visible(GTK_TREE_VIEW(trv_section), FALSE);
  gtk_tree_view_append_column(GTK_TREE_VIEW(trv_section), trv_c_section);
  scw_section = gtk_scrolled_window_new(NULL, NULL);
  gtk_scrolled_window_set_policy(GTK_SCROLLED_WINDOW(scw_section),
				 GTK_POLICY_AUTOMATIC,
				 GTK_POLICY_AUTOMATIC);
  gtk_container_add(GTK_CONTAINER(scw_section), trv_section);
  g_signal_connect(G_OBJECT(trv_section), "cursor-changed", 
		   G_CALLBACK(OnCursor), doc);
  doc->tst_section = tst_section;
  gtk_widget_show_all(scw_section);
  return (scw_section);
}

static GtkWidget       *NewHPanedWith2Frame(GtkWidget **frm_1, GtkWidget **frm_2, gint pos)
{
  GtkWidget *pnd_main;
  
  pnd_main = gtk_hpaned_new();
  *frm_1 = gtk_frame_new(NULL);
  *frm_2 = gtk_frame_new(NULL);
  gtk_frame_set_shadow_type(GTK_FRAME (*frm_1), GTK_SHADOW_IN);
  gtk_frame_set_shadow_type(GTK_FRAME (*frm_2), GTK_SHADOW_IN);
  gtk_paned_pack1(GTK_PANED (pnd_main), *frm_1, TRUE, FALSE);
  gtk_paned_pack2(GTK_PANED (pnd_main), *frm_2, TRUE, FALSE);
  gtk_paned_set_position(GTK_PANED (pnd_main), pos);
  gtk_widget_show_all(pnd_main);
  return (pnd_main);
}

static GtkWidget *NewNotebookToFrameWith(GtkWidget *frm_container)
{
  GtkWidget       *ntb_options;
  
  ntb_options = gtk_notebook_new();
  gtk_notebook_set_show_tabs(GTK_NOTEBOOK(ntb_options), FALSE);
  gtk_container_add(GTK_CONTAINER(frm_container), ntb_options);
  gtk_widget_show(ntb_options);
  return (ntb_options);
}

static int	UiNodeRoot(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode	*anode;
  GtkWidget	*vbx_ntb;
  GtkWidget	*vbx_frm;
  GtkWidget	*chemin;
  GtkWidget	*hpn_ntb;
  GtkWidget	*frame1;
  GtkWidget	*frame2;
  GtkWidget	*scw_section;
  GtkWidget	*but_gen;

  anode = (AdllNode *) curnode->data;
  vbx_ntb = gtk_vbox_new(FALSE, 0);
  gtk_notebook_append_page(GTK_NOTEBOOK(doc->ntb_base), vbx_ntb, 
			   gtk_label_new(anode->template.os_name));
  chemin = gtk_label_new(doc->xml->file);
  gtk_box_pack_start(GTK_BOX(vbx_ntb), chemin, FALSE, FALSE, 5);
  
  hpn_ntb = NewHPanedWith2Frame(&frame1, &frame2, 280);
  gtk_box_pack_start(GTK_BOX(vbx_ntb), hpn_ntb, TRUE, TRUE, 5);
  
  vbx_frm = gtk_vbox_new(FALSE, 0);
  gtk_container_add(GTK_CONTAINER(frame1), vbx_frm);
  
  scw_section = NewTree(vbx_ntb, doc);
  gtk_box_pack_start(GTK_BOX(vbx_frm), scw_section, TRUE, TRUE, 5);

  doc->ntb_options = NewNotebookToFrameWith(frame2);
  gtk_widget_show_all(vbx_ntb);
  but_gen = GenerateButton(doc);
  gtk_box_pack_end(GTK_BOX(vbx_frm), but_gen, FALSE, FALSE, 5);
  gtk_widget_show(but_gen);
  return (0);
}

static int	UiNodeSection(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode	*anode;
  GtkWidget     *label;
  GtkWidget     *scw_2;
  GtkTreeIter   child;
  gint		page;

  anode = (AdllNode *) curnode->data;
  doc->vbx_options = gtk_vbox_new(FALSE, 7);
  gtk_widget_show(doc->vbx_options);
  label = gtk_label_new("page");
  scw_2 = gtk_scrolled_window_new(NULL, NULL);
  gtk_scrolled_window_set_policy(GTK_SCROLLED_WINDOW(scw_2),
				 GTK_POLICY_AUTOMATIC,
				 GTK_POLICY_AUTOMATIC);
  gtk_scrolled_window_add_with_viewport(GTK_SCROLLED_WINDOW(scw_2),
					doc->vbx_options);
  gtk_widget_show_all(scw_2);
  page = gtk_notebook_append_page(GTK_NOTEBOOK(doc->ntb_options), scw_2, label);
  gtk_tree_store_append(doc->tst_section, &child,
			GetSectionIterParent(curnode));
  anode->section.Iter = child;
  gtk_tree_store_set(doc->tst_section, &child, 0, anode->section.label,
		     1, page, -1);
  return (0);
}

static int	UiNodeChoiceRadio(AdllDoc *doc,NTreeNode *curnode)
{
  AdllNode	*anode;  /* le choice */
  AdllNode	*canode; /* choice précédant */
  AdllNode	*prevanode; /* option précédante */

  anode = (AdllNode *) curnode->data;
  if (curnode->prev != NULL)
    canode = (AdllNode *) curnode->prev->data;
  prevanode = (AdllNode *) curnode->parent->data;
  if (curnode->prev == NULL) {
    anode->choice.listid = 0;
    anode->choice.radio = gtk_radio_button_new_with_label(NULL,
							  anode->choice.label);
    gtk_widget_show(anode->choice.radio);
  }
  else {
    anode->choice.radio = 
      gtk_radio_button_new_with_label_from_widget(GTK_RADIO_BUTTON(canode->choice.radio),
						  anode->choice.label);
    anode->choice.listid = canode->choice.listid + 1;
    gtk_widget_show(anode->choice.radio);
  }
  gtk_box_pack_start(GTK_BOX(prevanode->option.vbox), anode->choice.radio,
		     FALSE, FALSE, 10);
  if (anode->choice.selected == 1)
    gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(anode->choice.radio), TRUE);
 SIGNAL_CONNECT(anode->choice.radio, "toggled",
  		 OnRadioToggled, (gpointer) curnode);
  return (0);
}

static int	UiNodeChoiceText(AdllDoc *doc,NTreeNode *curnode)
{
  GtkWidget	*label;
  AdllNode	*anode;  /* le choice */
  AdllNode	*prevanode; /* option précédante */

  anode = (AdllNode *) curnode->data;
  prevanode = (AdllNode *) curnode->parent->data;
  label = gtk_label_new(anode->choice.label);
  gtk_box_pack_start(GTK_BOX(prevanode->option.vbox), label, FALSE, FALSE, 2);
  gtk_widget_show(label);
  anode->choice.entry = gtk_entry_new();
  SIGNAL_CONNECT(anode->choice.entry, "key-release-event",
  		 OnTextEntry, (gpointer) curnode);
  if (anode->choice.str != NULL)
    gtk_entry_set_text(GTK_ENTRY(anode->choice.entry), anode->choice.str);
  gtk_box_pack_start(GTK_BOX(prevanode->option.vbox), anode->choice.entry, FALSE, FALSE, 2);
  anode->choice.selected = 1; /* hack pour la generation */
  gtk_widget_show(anode->choice.entry);
  gtk_widget_show(prevanode->option.vbox);
  return (0);
}


static int	UiNodeChoiceCheck(AdllDoc *doc, NTreeNode *curnode)
{
  AdllNode	*anode;  /* le choice */
  AdllNode	*prevanode; /* option précédante */

  anode = (AdllNode *) curnode->data;;
  prevanode = (AdllNode *) curnode->parent->data;
  anode->choice.check = gtk_check_button_new_with_label(anode->choice.label);
  SIGNAL_CONNECT(anode->choice.check, "toggled",
		 OnCheckToggled, (gpointer) curnode);
  gtk_box_pack_start(GTK_BOX(prevanode->option.vbox), anode->choice.check, FALSE, FALSE, 2);
  gtk_widget_show(anode->choice.check);
  if (anode->choice.selected == 1) {
    anode->choice.selected = 0; /* hack pour ligne suivante */
    gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(anode->choice.check), TRUE);
  }
  return (0);
}


static int	UiNodeChoiceList(AdllDoc *doc,NTreeNode *curnode)
{
  AdllNode	*anode;  /* le choice */
  AdllNode	*canode; /* choice précédant */
  AdllNode	*prevanode; /* option précédante */

  anode = (AdllNode *) curnode->data;
  if (curnode->prev != NULL)
    canode = (AdllNode *) curnode->prev->data;
  prevanode = (AdllNode *) curnode->parent->data;
  if (curnode->prev == NULL) {
    anode->choice.listid = 0;
    anode->choice.combolist = gtk_combo_box_new_text();
    gtk_box_pack_start(GTK_BOX(prevanode->option.vbox),
		       anode->choice.combolist, FALSE, FALSE, 2);
    gtk_widget_show(anode->choice.combolist);
  }
  else {
    anode->choice.combolist = canode->choice.combolist;
    anode->choice.listid = canode->choice.listid + 1;
  }
  gtk_combo_box_append_text(GTK_COMBO_BOX(anode->choice.combolist), anode->choice.label);
  if (anode->choice.selected == 1)
    gtk_combo_box_set_active(GTK_COMBO_BOX(anode->choice.combolist), 
			     anode->choice.listid);
  SIGNAL_CONNECT(anode->choice.combolist, "changed",
		 OnListChanged, (gpointer) curnode);
  return (0);
}

static int	UiNodeChoice(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode	*anode;
  AdllNode	*prevanode; /* une option */

  anode = (AdllNode *) curnode->data;
  prevanode = (AdllNode *) curnode->parent->data;
  if (prevanode->option.type == OPTION_RADIO)
    return (UiNodeChoiceRadio(doc, curnode));
  if (prevanode->option.type == OPTION_TEXT)
    return (UiNodeChoiceText(doc, curnode));
  if (prevanode->option.type == OPTION_CHECK)
    return (UiNodeChoiceCheck(doc, curnode));
  if (prevanode->option.type == OPTION_LIST)
    return (UiNodeChoiceList(doc, curnode));
  return (0);
}

static int	UiNodeOptionMulti(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode	*anode;
  AdllNode	*canode; /* option précédant */
  GtkWidget	*prevvbox;
  GtkWidget     *wnd_multi;
  GtkWidget	*hbox;
  gchar		*name;
  GtkWidget	*but;

  anode = (AdllNode *) curnode->data;
  if (curnode->prev != NULL)
    canode = (AdllNode *) curnode->prev->data;
  prevvbox = GetOptionBoxParent(doc->vbx_options, curnode);
  anode->option.wnd_multi = wnd_multi = gtk_window_new(GTK_WINDOW_TOPLEVEL);
  gtk_window_set_position(GTK_WINDOW(wnd_multi), GTK_WIN_POS_CENTER);
  gtk_window_set_default_size(GTK_WINDOW(wnd_multi), 800 , 600);
  gtk_window_set_title(GTK_WINDOW(wnd_multi), "Multi");
  g_signal_connect(G_OBJECT(wnd_multi), "delete-event", G_CALLBACK(gtk_widget_hide),
                   NULL);
  anode->option.frm_options = gtk_frame_new(anode->option.label);
  gtk_container_add(GTK_CONTAINER (wnd_multi), anode->option.frm_options);
  gtk_widget_show(anode->option.frm_options);;
  anode->option.vbox = gtk_vbox_new(FALSE, 2);
  gtk_widget_show(anode->option.vbox);
  gtk_container_add(GTK_CONTAINER(anode->option.frm_options), anode->option.vbox);
  if (curnode->prev == NULL) {
    anode->option.multinodelist = malloc(sizeof (NTreeNode *) * 256);
    anode->option.multiid = 0;
    anode->option.multicombolist = gtk_combo_box_new_text();
    gtk_box_pack_start(GTK_BOX(prevvbox),
		       anode->option.multicombolist, FALSE, FALSE, 2);
    gtk_widget_show(anode->option.multicombolist);
  hbox = gtk_hbox_new(FALSE, 2);
  gtk_box_pack_start(GTK_BOX(prevvbox), hbox, FALSE, FALSE, 2);
  but = gtk_button_new_from_stock(GTK_STOCK_ADD);
  g_object_set_data(G_OBJECT(but), "Doc", doc);
  SIGNAL_CONNECT(but, "clicked", OnMultiAjouter, (gpointer) curnode);
  gtk_box_pack_start(GTK_BOX(hbox), but, FALSE, FALSE, 2);
  but = gtk_button_new_from_stock(GTK_STOCK_EDIT);
  SIGNAL_CONNECT(but, "clicked", OnMultiEdit , (gpointer) curnode);
  gtk_box_pack_start(GTK_BOX(hbox), but, FALSE, FALSE, 2);
  but = gtk_button_new_from_stock(GTK_STOCK_REMOVE);
  SIGNAL_CONNECT(but, "clicked", OnMultiEnlever, (gpointer) curnode);
  gtk_box_pack_start(GTK_BOX(hbox), but, FALSE, FALSE, 2);
  gtk_widget_show_all(hbox);
  }
  else {
    anode->option.multicombolist = canode->option.multicombolist;
    anode->option.multiid = canode->option.multiid + 1;
    anode->option.multinodelist =  canode->option.multinodelist;
  }
  anode->option.multinodelist[anode->option.multiid] = curnode;
  name = g_strdup_printf("%i %s", anode->option.multiid+1, anode->option.label);
  gtk_combo_box_append_text(GTK_COMBO_BOX(anode->option.multicombolist),
			    name);
  return (0);
}


static int	UiNodeOption(NTreeNode *curnode, AdllDoc *doc)
{
  AdllNode	*anode;
  GtkWidget	*prevvbox;

  anode = (AdllNode *) curnode->data;
  prevvbox = GetOptionBoxParent(doc->vbx_options, curnode);
  if (!AdllNodeOptionIsMulti(curnode))
    return (UiNodeOptionMulti(curnode, doc));
  anode->option.frm_options = gtk_frame_new(anode->option.label);
  gtk_widget_show(anode->option.frm_options);
  gtk_box_pack_start(GTK_BOX(prevvbox), anode->option.frm_options,
		     FALSE, FALSE, 4);
  anode->option.vbox = gtk_vbox_new(FALSE, 2);
  gtk_widget_show(anode->option.vbox);
  gtk_container_add(GTK_CONTAINER(anode->option.frm_options), anode->option.vbox);
  if (!AdllNodeParentChoiceIsSelected(curnode)) {
    if (!UiCheckParentOptionUnsensitive(curnode)) {
      gtk_widget_hide(anode->option.frm_options);
    }
  }
  return (0);
}

int	UiNodeProcess(NTreeNode *curnode, void *data)
{
  AdllNode	*adllnode = (AdllNode *) curnode->data;

  if (adllnode->type == NODE_CHOICE &&
	 ((AdllNode *) curnode->parent->data)->option.type == OPTION_TEXT) {
     adllnode->choice.selected = 1;
  }
  if (!AdllNodeParentIsVisible(curnode))
    return (0);
  if (adllnode->type == NODE_ROOT)
    return (UiNodeRoot(curnode, (AdllDoc *) data));
  if (adllnode->type == NODE_SECTION)
    return (UiNodeSection(curnode, (AdllDoc *) data));
  if (adllnode->type == NODE_OPTION)
    return (UiNodeOption(curnode, (AdllDoc *) data));
  if (adllnode->type == NODE_CHOICE)
    return (UiNodeChoice(curnode, (AdllDoc *) data));
  return (0);
}

void    AdllLunchGtkMode(AdllDoc **docs)
{
  GtkWidget     *wnd_main;
  GtkWidget     *vbx_vbox;
  GtkWidget     *mnb_menu;
  GtkWidget	*ntb_base;
  int		i;

  gl_wnd = wnd_main = NewMainWinWithVBox(&vbx_vbox);
  ntb_base = gtk_notebook_new();
  gtk_widget_show(ntb_base);
  G_HOOKUP_OBJECT(wnd_main, ntb_base, "Notebook1");
  mnb_menu = NewMainMenu(wnd_main, ntb_base);
  gtk_notebook_set_scrollable(GTK_NOTEBOOK(ntb_base), TRUE); 
  gtk_box_pack_start(GTK_BOX(vbx_vbox), mnb_menu, FALSE, FALSE, 0);
  gtk_box_pack_start(GTK_BOX(vbx_vbox), ntb_base, TRUE, TRUE, 0);
  for (i = 0; docs != NULL && docs[i] != NULL; i++) {
    docs[i]->ntb_base = ntb_base;
    ntree_traverse_preorder(docs[i]->root, UiNodeProcess, (void *) docs[i]);
  }
  gtk_widget_show(wnd_main);
  gtk_main();
}
