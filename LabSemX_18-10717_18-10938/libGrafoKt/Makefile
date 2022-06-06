KC=	kotlinc
KFLAG=	-cp
GLIB=	ve/usb/libGrafo

all:	libGrafoKt.jar

libGrafoKt.jar: $(GLIB)/RuntimeException.class\
		$(GLIB)/Color.class\
		$(GLIB)/Lado.class\
		$(GLIB)/Arco.class\
		$(GLIB)/ArcoCosto.class\
		$(GLIB)/Arista.class\
		$(GLIB)/AristaCosto.class\
		$(GLIB)/Vertice.class\
		$(GLIB)/Grafo.class\
		$(GLIB)/BFS.class\
		$(GLIB)/DFS.class\
		$(GLIB)/GrafoDirigido.class\
		$(GLIB)/GrafoDirigidoCosto.class\
		$(GLIB)/GrafoNoDirigido.class\
		$(GLIB)/GrafoNoDirigidoCosto.class
	jar -cvf $@ $(GLIB)/*.class

$(GLIB)/RuntimeException.class: $(GLIB)/RuntimeException.kt
	$(KC) $(GLIB)/RuntimeException.kt

$(GLIB)/Vertice.class: $(GLIB)/Vertice.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Vertice.kt

$(GLIB)/Color.class: $(GLIB)/Color.kt
	$(KC) $(GLIB)/Color.kt

$(GLIB)/Lado.class: $(GLIB)/Lado.kt
	$(KC) $(GLIB)/Lado.kt

$(GLIB)/Arco.class: $(GLIB)/Arco.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arco.kt

$(GLIB)/ArcoCosto.class: $(GLIB)/ArcoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ArcoCosto.kt

$(GLIB)/Arista.class: $(GLIB)/Arista.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arista.kt

$(GLIB)/AristaCosto.class: $(GLIB)/AristaCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/AristaCosto.kt

$(GLIB)/Grafo.class: $(GLIB)/Grafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Grafo.kt

$(GLIB)/BFS.class: $(GLIB)/BFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/BFS.kt

$(GLIB)/DFS.class: $(GLIB)/DFS.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/DFS.kt

$(GLIB)/GrafoDirigido.class: $(GLIB)/GrafoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoDirigido.kt

$(GLIB)/GrafoDirigidoCosto.class: $(GLIB)/GrafoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoDirigidoCosto.kt

$(GLIB)/GrafoNoDirigido.class: $(GLIB)/GrafoNoDirigido.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoNoDirigido.kt

$(GLIB)/GrafoNoDirigidoCosto.class: $(GLIB)/GrafoNoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoNoDirigidoCosto.kt

clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class
