KC=	kotlinc
KFLAG=	-cp

all:	libGrafoKt.jar

libGrafoKt.jar: \
		clean\
		RuntimeException.class\
		Vertice.class\
		Color.class\
		Lado.class\
		Arco.class\
		ArcoCosto.class\
		Arista.class\
		AristaCosto.class\
		Grafo.class\
		GrafoDirigido.class\
		GrafoDirigidoCosto.class\
		GrafoNoDirigido.class\
		GrafoNoDirigidoCosto.class
	jar -cvf $@ *.class 

RuntimeException.class: RuntimeException.kt
	$(KC) RuntimeException.kt

Vertice.class: Vertice.kt
	$(KC) Vertice.kt

Color.class: Color.kt
	$(KC) Color.kt

Lado.class: Lado.kt
	$(KC) Lado.kt

Arco.class: Arco.kt
	$(KC) $(KFLAG) Arco.kt

ArcoCosto.class: ArcoCosto.kt
	$(KC) $(KFLAG) ArcoCosto.kt

Arista.class: Arista.kt
	$(KC) $(KFLAG) Arista.kt

AristaCosto.class: AristaCosto.kt
	$(KC) $(KFLAG) AristaCosto.kt

Grafo.class: Grafo.kt
	$(KC) $(KFLAG) Grafo.kt

GrafoDirigido.class: GrafoDirigido.kt
	$(KC) $(KFLAG) GrafoDirigido.kt

GrafoDirigidoCosto.class: GrafoDirigidoCosto.kt
	$(KC) $(KFLAG) GrafoDirigidoCosto.kt

GrafoNoDirigido.class: GrafoNoDirigido.kt
	$(KC) $(KFLAG) GrafoNoDirigido.kt

GrafoNoDirigidoCosto.class: GrafoNoDirigidoCosto.kt
	$(KC) $(KFLAG) GrafoNoDirigidoCosto.kt

clean:
	rm -rf libGrafoKt.jar META-INF *.class
