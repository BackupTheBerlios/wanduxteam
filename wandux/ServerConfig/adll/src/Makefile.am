bin_PROGRAMS    = adll

adll_SOURCES    = \
        error.c \
        gen.c \
	main.c \
        nodes.c \
        option.c \
        parser.c \
        string.c \
        textmain.c \
	textmode.c \
        utils.c \
        @UI_SOURCE@ \
        xmls_open.c
EXTRA_adll_SOURCES = ui.c callbacks.c
adll_DEPENDENCIES = @UI_SOURCE@
adll_LDADD	= \
       @UI_SOURCE@ \
       @GTK_LIBS@ \
       @xml_LIBS@ \
       @LIBINTL@ \
       ../lib/dlist/libdlist.a \
       ../lib/ntree/libntree.a

CLEANFILES      = *~

DISTCLEANFILES  = .deps/*.P

AM_CPPFLAGS	= \
        -DLOCALEDIR=\"$(datadir)/locale\" \
        -I$(top_srcdir)/include \
        @GTK_CFLAGS@ \
        @xml_CFLAGS@
