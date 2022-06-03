package libGrafoKt.ve.usb.libGrafo

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/* 
   Implementación del algoritmo BFS. 
   Con la creación de la instancia, se ejecuta el algoritmo BFS
   desde el vértice s
*/
public class BFS(val g: Grafo, val s: Int) {
    
    init {
	// Se ejecuta BFS
        for (i in g.listaDeAdyacencia)
        //val c = ConcurrentLinkedQueue<Int>()
        //c.add(2)
        //val b = c.remove()

    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor 
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException.
     */
    fun obtenerPredecesor(v: Int) : Int? {  }

    /*
     Retorna la distancia, del camino obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. En caso de que el vétice v no sea alcanzable desde s,
     entonces se retorna -1.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */
    fun obtenerDistancia(v: Int) : Int {  }

    /*
     Indica si hay camino desde el vértice inicial s hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */ 
    fun hayCaminoHasta(v: Int) : Boolean {  }

    /*
     Retorna el camino con menos lados, obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. El camino es representado como un objeto iterable con
     los vértices del camino desde s hasta v.
     En caso de que el vétice v no sea alcanzable desde s, entonces se lanza una RuntimeException.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException.
     */ 
    fun caminoHasta(v: Int) : Iterable<Int>  {  }

    // Imprime por la salida estándar el breadth-first tree
    fun mostrarArbolBFS() {}
}
