KC=		kotlinc
KFLAG=		-cp
LIBGRAPH=	libGrafoKt/
LIBJAR=		libGrafoKt/libGrafoKt.jar

all:	jarlib EvaluacionCCKt.class

jarlib:
	(cd $(LIBGRAPH); make)  

EvaluacionCCKt.class: EvaluacionCC.kt 
	$(KC) $(KFLAG) $(LIBJAR) EvaluacionCC.kt
clean:
	(cd $(LIBGRAPH); make clean)
	rm -rf *.class META-INF
