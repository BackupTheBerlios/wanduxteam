#no, it doesn't ./configure (or smake)
#no, there is no 'make install'
#yes, it sucks

GCC=gcc -g -Wall -Werror
WABIDS=pstwabids.h pstwabids.c

all: wabread

$(WABIDS): known_ids
	./makeids.pl

libwab.o: libwab.h pstwabids.h libwab.c
		$(GCC) -c libwab.c -o libwab.o 

pstwabids.o: pstwabids.h pstwabids.c
		$(GCC) -c pstwabids.c -o pstwabids.o

wabread: wabread.c pstwabids.o libwab.o
		$(GCC) -o wabread pstwabids.o libwab.o wabread.c

clean:
	rm -f wabread libwab.o core
