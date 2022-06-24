package ve.usb.libGrafo
import java.lang.Double.POSITIVE_INFINITY
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/*
 Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Prim.
 Si el grafo de entrada no es conexo, entonces se lanza un RuntineException.
*/
public class PrimAMC(val g: GrafoNoDirigidoCosto) {
    val salida = LinkedList<AristaCosto>()

    init{
        for (V in g.listaDeVertices){
            V.Costo = POSITIVE_INFINITY
            V.pred = null
        }
        g.listaDeVertices[0].Costo = 0.0
        val Q = PriorityQueue<Vertice>()
        Q.addAll(g.listaDeVertices)
        var u: Vertice
        var it: MutableIterator<Vertice>
        var V: Vertice

        while (Q.isNotEmpty()){
            u = Q.remove()
            if (g.listaDeAdyacencia[u.valor] != null){
                it = g.listaDeAdyacencia[u.valor]!!.iterator()
                while (it.hasNext()){
                    V = it.next()
                    if (Q.contains(V) && V.Costo < g.listaDeVertices[V.valor].Costo && u.valor != V.valor){
                        g.listaDeVertices[V.valor].pred = u
                        g.listaDeVertices[V.valor].Costo = V.Costo
                    }
                }
            }
            Q.add(Vertice(-1, -5.0))
            Q.remove()
        }
    }

    private inner class obtenerLadosIterato(val g: GrafoNoDirigidoCosto): Iterator<Arista>{
        val cola = ConcurrentLinkedQueue<Vertice>()
        lateinit var temp: Vertice

        init {
            cola.addAll(g.listaDeVertices)
        }

        override fun hasNext(): Boolean {
            while (cola.isNotEmpty() && cola.first().pred == null){
                cola.remove()
            }
            return cola.isNotEmpty()
        }

        override fun next(): Arista {
            temp = cola.remove()
            return Arista(temp.pred!!.valor, temp.valor)
        }
    }

    private inner class obtenerLadosIterable(val g: GrafoNoDirigidoCosto): Iterable<Arista> {

        override fun iterator(): Iterator<Arista> = obtenerLadosIterato(g)
    }

    // Retorna un objeto iterable que contiene los lados del árbol mínimo cobertor.
    fun obtenerLados() : Iterable<Arista> {
        return obtenerLadosIterable(g)
    }

    // Retorna el costo del árbol mínimo cobertor. 
    fun obtenerCosto() : Double {
        var sum = 0.0
        for (V in g.listaDeVertices){
            sum += V.Costo
        }
        return sum
    }
}
