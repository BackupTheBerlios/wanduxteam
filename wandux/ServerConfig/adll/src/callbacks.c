/* -*- mode: C; c-file-style: "gnu" -*- */
/* callbacks.h Callbacks for User Interface
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

#include "option.h"
#include "ui.h"
#include "parser.h"
#include "xml.h"

#ifdef SUPPORT_GTK
extern GtkWidget *gl_wnd;
#endif

/* pas besoin de data */
void OnMenuAbout(GtkWidget *widget, gpointer data)
{
  GtkWidget *win;

  win = gtk_message_dialog_new(GTK_WINDOW(data), GTK_DIALOG_MODAL,
                               GTK_MESSAGE_INFO, GTK_BUTTONS_OK,
                               "http://www.adll.net");
  gtk_dialog_run(GTK_DIALOG(win));
  gtk_widget_destroy(win);
}

void		OnMenuQuitter(GtkWidget *widget, gpointer data)
{

  GtkWidget	*win;

  win = gtk_message_dialog_new(GTK_WINDOW(data), GTK_DIALOG_MODAL,
                               GTK_MESSAGE_QUESTION, GTK_BUTTONS_YES_NO,
                                _("Really quit ?"));
  switch(gtk_dialog_run(GTK_DIALOG(win)))
    {
    case GTK_RESPONSE_YES:
      gtk_main_quit();
      break;
    case GTK_RESPONSE_NONE:
    case GTK_RESPONSE_NO:
      gtk_widget_destroy(win);
      break;
    }
}

void    OnMenuOuvrir(GtkWidget *widget, gpointer data)
{
  GtkWidget     *dialog;
  GtkWidget	*ntb_base;
  const gchar   *filename;
  AdllXmlData	*xmlDatas;
  AdllDoc	*doc;
  GtkFileFilter *filter;

  ntb_base = g_object_get_data(G_OBJECT(data), "Notebook1");
  filter = gtk_file_filter_new();
  gtk_file_filter_add_pattern(filter, "*.xml");
  gtk_file_filter_set_name(filter, _("Xml Files"));
  dialog = gtk_file_chooser_dialog_new (_("Open File"),
					GTK_WINDOW(data),
					GTK_FILE_CHOOSER_ACTION_OPEN,
					GTK_STOCK_CANCEL, GTK_RESPONSE_CANCEL,
					GTK_STOCK_OPEN, GTK_RESPONSE_ACCEPT,
					NULL);
  gtk_file_chooser_add_filter(GTK_FILE_CHOOSER(dialog), filter);
  if (gtk_dialog_run (GTK_DIALOG (dialog)) == GTK_RESPONSE_ACCEPT)
    {
      char *filename;
      
      filename = gtk_file_chooser_get_filename (GTK_FILE_CHOOSER (dialog));
      doc = malloc(sizeof (AdllDoc));
      if (open_xml(filename, &xmlDatas)) {
	gtk_widget_destroy(dialog);
	return ;
    }
      doc = AdllParseXml(xmlDatas);
      doc->ntb_base = ntb_base;
      ntree_traverse_preorder(doc->root, UiNodeProcess, (void *) doc);
      gtk_widget_show_all(doc->vbx_options);
    }
  gtk_widget_destroy(dialog);
}

void	OnCursor(GtkTreeView *treeview, AdllDoc *doc)
{
  GtkTreeSelection	*selection;
  GtkTreeModel          *model;
  GtkTreeIter           iter;

  selection = gtk_tree_view_get_selection(GTK_TREE_VIEW(treeview));
  if (gtk_tree_selection_get_selected(selection, &model, &iter)) {
    gint page;
		
    gtk_tree_model_get(model, &iter, 1, &page, -1);
    gtk_notebook_set_current_page(GTK_NOTEBOOK(doc->ntb_options), page);
  } 
} 


void	OnCheckToggled(GtkToggleButton *togglebutton,
		       gpointer user_data)
{
  NTreeNode	*n;
  AdllNode	*ParentOption;
  AdllNode	*anode;
  GtkWidget	*frm;
  int		s;

  n = (NTreeNode *) user_data;
  anode = (AdllNode *) n->data;
  ParentOption = (AdllNode *) n->parent->data;
  if (anode->choice.selected == 0) {
    anode->choice.selected = 1;
    ParentOption->option.nb_select += 1;
  }
  else {
    anode->choice.selected = 0;
    ParentOption->option.nb_select -= 1;
  }
  s = anode->choice.selected;
  if (n->children != NULL) {
    for (n = n->children; n != NULL; n = n->next) {
      anode = (AdllNode *) n->data;
      if (anode->type == NODE_OPTION) {
	if (s == 1) {
	  gtk_widget_show(anode->option.frm_options);
	}
	else
	  gtk_widget_hide(anode->option.frm_options);
      }
    }
  }
}
  
void	OnRadioToggled(GtkToggleButton *togglebutton,
		       gpointer user_data)
{
  NTreeNode	*n;
  AdllNode	*anode;
  AdllNode	*ParentOption;
  GtkWidget	*frm;
  int		s;

  n = (NTreeNode *) user_data;
  anode = (AdllNode *) n->data;
  ParentOption = (AdllNode *) n->parent->data;
  ParentOption->option.nb_select = 1;
  if (gtk_toggle_button_get_active(GTK_TOGGLE_BUTTON(togglebutton)) == 1)
    anode->choice.selected =  1;
  else
    anode->choice.selected = 0;
  s = anode->choice.selected;
  if (n->children != NULL) {
    for (n = n->children; n != NULL; n = n->next) {
      anode = (AdllNode *) n->data;
      if (anode->type == NODE_OPTION) {
	if (s == 1) {
	  gtk_widget_show(anode->option.frm_options);
	}
	else
	  gtk_widget_hide(anode->option.frm_options);
      }
    }
  }
}

void	OnListChanged(GtkComboBox *widget, gpointer user_data)
{
  NTreeNode	*n;
  NTreeNode	*j;
  AdllNode	*anode;
  AdllNode	*ParentOption;
  GtkWidget	*frm;
  int		s;

  n = (NTreeNode *) user_data;
  anode = (AdllNode *) n->data;
  ParentOption = (AdllNode *) n->parent->data;
  ParentOption->option.nb_select = 1;
  if (gtk_combo_box_get_active(GTK_COMBO_BOX(widget)) ==
			       anode->choice.listid)
    anode->choice.selected =  1;
  else
    anode->choice.selected = 0;
  s = anode->choice.selected;
  if (n->children != NULL) {
    for (n = n->children; n != NULL; n = n->next) {
      anode = (AdllNode *) n->data;
      if (anode->type == NODE_OPTION) {
	if (s)
	  gtk_widget_show(GTK_WIDGET(anode->option.frm_options));
	else
	  gtk_widget_hide(GTK_WIDGET(anode->option.frm_options));
      }
    }
  }
}

gboolean OnTextEntry(GtkWidget *widget, GdkEventKey *event, gpointer user_data)
{
  NTreeNode	*node;
  AdllNode	*anode;

  anode = (AdllNode *) ((NTreeNode *)user_data)->data;
  if (anode->option.name != NULL)
    free(anode->option.name);
   anode->option.name = strdup(gtk_entry_get_text(GTK_ENTRY(widget)));
   return (FALSE);
}

void OnButtonGenerate(GtkButton *button, gpointer user_data)
{
  GtkWidget *pFileSelection;
  GtkWidget *pDialog;
  const gchar *sChemin;
    
  pFileSelection = gtk_file_selection_new(_("Generate to...."));
  gtk_window_set_modal(GTK_WINDOW(pFileSelection), TRUE);

  switch(gtk_dialog_run(GTK_DIALOG(pFileSelection)))
    {
    case GTK_RESPONSE_OK:
            sChemin = gtk_file_selection_get_filename(GTK_FILE_SELECTION(pFileSelection));
	    AdllGen((AdllDoc *) user_data, sChemin);
            pDialog = gtk_message_dialog_new(GTK_WINDOW(pFileSelection),
                GTK_DIALOG_MODAL,
                GTK_MESSAGE_INFO,
                GTK_BUTTONS_OK,
                _("%s generate OK"), sChemin);
            gtk_dialog_run(GTK_DIALOG(pDialog));
            gtk_widget_destroy(pDialog);
            break;
        default:
            break;
    }
    gtk_widget_destroy(pFileSelection);
}

void OnMultiEdit(GtkButton *button, gpointer user_data)
{
  AdllNode	*anode;
  NTreeNode	*node;
  int		id;

  node = (NTreeNode *) user_data;
  anode = (AdllNode *) node->data;
  id = gtk_combo_box_get_active(GTK_COMBO_BOX(anode->option.multicombolist));
  if (id == -1)
    return ;
  anode =  (AdllNode *) anode->option.multinodelist[id]->data;
  gtk_widget_show(anode->option.wnd_multi);
}

void OnMultiEnlever(GtkButton *button, gpointer user_data)
{
  AdllNode	*anode;
  NTreeNode	*node;
  NTreeNode	*n;
  int		id;

  node = (NTreeNode *) user_data;
  anode = (AdllNode *) node->data;
  id = gtk_combo_box_get_active(GTK_COMBO_BOX(anode->option.multicombolist));
  if (id == -1)
    return ;
  ntree_node_unlink(anode->option.multinodelist[id]);
  gtk_combo_box_remove_text(GTK_COMBO_BOX(anode->option.multicombolist), id);
}

void OnMultiAjouter(GtkButton *button, gpointer user_data)
{
  AdllNode	*anode;
  NTreeNode	*node;
  AdllDoc      	*doc;
  NTreeNode	*n;
  int		id;

  node = (NTreeNode *) user_data;
  anode = (AdllNode *) node->data;
  doc = g_object_get_data(G_OBJECT(button), "Doc");
  AdllParseXmlRec(NULL, anode->option.xmlNode, node->parent);
  ntree_traverse_preorder(node->parent->children, UiNodeProcess, (void *) doc);
}


void OnMainWinDestroy(GtkWidget *pWidget, gpointer pData)
{
  gtk_main_quit();
}

/*oh !! pas bien ! */
void    OnMenuFermer(GtkWidget *widget, gpointer data)
{
  gtk_notebook_remove_page(GTK_NOTEBOOK(data),
			   gtk_notebook_get_current_page(GTK_NOTEBOOK(data)));
}
