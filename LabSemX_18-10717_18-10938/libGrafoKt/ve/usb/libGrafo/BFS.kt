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
        var temp: Vertice
        val cola = ConcurrentLinkedQueue<Vertice>()
        for (i in g.listaDeAdyacencia.indices){
            for (j in g.listaDeAdyacencia[i]!!.indices){
                temp = g.listaDeAdyacencia[i]!![j]
                temp.distancia = 0
                temp.color = Color.BLANCO
                temp.pred = null
            }
        }
        cola.add(Vertice(s))
        var u: Vertice
        while (!cola.isEmpty()){
            u = cola.remove()
            for (v in g.listaDeAdyacencia[u.valor]!!){
                if (v.color == Color.BLANCO){
                    v.color = Color.GRIS
                    v.distancia = u.distancia
                    v.pred = u
                    g.listaDeVertices[v.valor]!!.distancia = u.distancia
                    g.listaDeVertices[v.valor]!!.pred = u
                    cola.add(v)
                }
            }
            u.color = Color.NEGRO
        }
    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException.
     */
    fun obtenerPredecesor(v: Int) : Int? {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vertice no se encuentra")
        return g.listaDeVertices[v]!!.pred?.valor
    }

    /*
     Retorna la distancia, del camino obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. En caso de que el vétice v no sea alcanzable desde s,
     entonces se retorna -1.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */
    fun obtenerDistancia(v: Int) : Int {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vertice no se encuentra")
        if (v == s) return 0
        else if (g.listaDeVertices[v]!!.distancia == 0) return -1
        return g.listaDeVertices[v]!!.distancia
    }

    /*
     Indica si hay camino desde el vértice inicial s hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     */ 
    fun hayCaminoHasta(v: Int) : Boolean {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vertice no se encuentra")
        if (v == s) return true
        else if (g.listaDeVertices[v]!!.distancia == 0) return false
        return true
    }

    /*
     Retorna el camino con menos lados, obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. El camino es representado como un objeto iterable con
     los vértices del camino desde s hasta v.
     En caso de que el vétice v no sea alcanzable desde s, entonces se lanza una RuntimeException.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException.
     */ 
    fun caminoHasta(v: Int) : Iterable<Int>  {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vertice no se encuentra")
    }

    // Imprime por la salida estándar el breadth-first tree
    fun mostrarArbolBFS() {}
}
