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

#ifndef __UI_H__
#define __UI_H__


#include "common.h"
#include "parser.h"

ADLL_BEGIN_DECLS;

#define WIN_WIDTH       800
#define WIN_HEIGHT      600

#define SIGNAL_CONNECT(obj,signal,func,data) \
  g_signal_connect(G_OBJECT(obj), signal, G_CALLBACK(func), (GtkWidget*) data)
#define G_HOOKUP_OBJECT(component,widget,name) \
  g_object_set_data_full (G_OBJECT(component), name, \
    g_object_ref(G_OBJECT(widget)), (GDestroyNotify) g_object_unref)

void    AdllLunchGtkMode(AdllDoc **docs);

void    OnMenuOuvrir(GtkWidget *pWidget, gpointer pData);
void    OnMenuFermer(GtkWidget *pWidget, gpointer pData);
void    OnMenuQuitter(GtkWidget *pWidget, gpointer pData);
void    OnMenuAbout(GtkWidget *pWidget, gpointer pData);
void    OnMainWinDestroy(GtkWidget *pWidget, gpointer pData);
void	OnCursor(GtkTreeView *treeview, AdllDoc *doc);
void	OnCheckToggled(GtkToggleButton *togglebutton, gpointer user_data);
void	OnRadioToggled(GtkToggleButton *togglebutton, gpointer user_data);
gboolean OnTextEntry(GtkWidget *widget, GdkEventKey *event, gpointer user_data);
void	OnListChanged(GtkComboBox *widget, gpointer user_data);
void	OnButtonGenerate(GtkButton *button, gpointer user_data);
void	OnMultiEdit(GtkButton *button, gpointer user_data);
void	OnMultiEnlever(GtkButton *button, gpointer user_data);
void	OnMultiAjouter(GtkButton *button, gpointer user_data);
int	UiNodeProcess(NTreeNode *curnode, void *data);

ADLL_END_DECLS;

#endif /* __UI_H__ */
