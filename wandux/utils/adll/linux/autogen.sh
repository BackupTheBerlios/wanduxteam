#!/bin/sh
# Run this to generate all the initial makefiles, etc.

aclocal \
&& autoheader \
&& automake --add-missing \
&& autoconf \
|| exit 1