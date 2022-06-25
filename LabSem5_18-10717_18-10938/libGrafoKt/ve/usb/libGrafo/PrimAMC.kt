package ve.usb.libGrafo
import java.lang.Double.POSITIVE_INFINITY
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/** Metodo que dado un grafo no dirigido no nulo calcula el arbol minimo cobertor
 *  de dicho grafo, si el grafo no es conexo se lanza un RuntimeException
 */
public class PrimAMC(val g: GrafoNoDirigidoCosto) {
    /** Entrada: un grafo no dirigido costo no vacio
     *  Precondicion: (g.listaDeVertices.size > 0) && nCC() == 1
     *  Postcondicion: obtenerCosto() != POSITIVE_INFINITY
     *  Tiempo de operacion: O(|E| * log|V|)
     */

    init{
        // Se verifica si g es conexo usando ComponentesConexasCD
        val compConexas = ConjuntosDisjuntos(g.numDeVertices)       //Se inicializa cada vértice como un conjunto disjunto
        val e = g.iterator()
        while(e.hasNext()){                                     //Iteramos sobre las aristas del grafo
            val arista = e.next()
            if(compConexas.encontrarConjunto(arista.u) != compConexas.encontrarConjunto(arista.v)){     //y si los vértices que conforman dicha arista se encuentran en diferentes conjuntos
                compConexas.union(arista.u, arista.v)                                                   //se realiza la unión de estos, obteniendo así las componentes conexas del grafo
            }
        }
        if (compConexas.numConjuntosDisjuntos() != 1) throw RuntimeException("el grafo no es conexo")

        // Algoritmo de prim
        for (V in g.listaDeVertices){                           // Se verifican los datos
            V.Costo = POSITIVE_INFINITY
            V.pred = null
        }
        g.listaDeVertices[0].Costo = 0.0                        // Se toma el vertice 0 como raiz
        val Q = PriorityQueue<Vertice>()
        Q.addAll(g.listaDeVertices)                             // Se agregan los vertices a la cola de prioridad
        var u: Vertice
        var it: MutableIterator<Vertice>
        var V: Vertice

        while (Q.isNotEmpty()){
            u = Q.remove()
            if (g.listaDeAdyacencia[u.valor] != null){
                it = g.listaDeAdyacencia[u.valor]!!.iterator()
                while (it.hasNext()){                           // Se itera sobre la lista de adyacencia
                    V = it.next()
                    if (Q.contains(V) && V.Costo < g.listaDeVertices[V.valor].Costo){
                        g.listaDeVertices[V.valor].pred = u
                        g.listaDeVertices[V.valor].Costo = V.Costo
                    }
                }
            }
            Q.add(Vertice(-1, -5.0))                // Se agrega y elimina un elemento para ordenar la cola de prioridad
            Q.remove()
        }
    }

    /** Clase interna que dado un grafo costo no dirigido retorna un iterator de los lados del arbol minimo cobertor
     */
    private inner class obtenerLadosIterato(g: GrafoNoDirigidoCosto): Iterator<Arista>{
        /** Entrada: un grafo no dirigido costo no vacio
         *  Salida: un iterador de aristas del arbol minimo cobertor
         *  Precondicion: g.listaDeVertices.size > 0
         *  Tiempo de operacion: O(|V|)
         */
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

    /** Clase interna que dado un grafo costo no dirigido sobreescribe la funcion iterator
     *  y la iguala a obtenerLadosIterato
     */
    private inner class obtenerLadosIterable(val g: GrafoNoDirigidoCosto): Iterable<Arista> {
        /** Entrada: un grafo no dirigido costo no vacio
         *  Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: g.listaDeVertices.size > 0
         *  Tiempo de operacion: O(|V|)
         */
        override fun iterator(): Iterator<Arista> = obtenerLadosIterato(g)
    }

    /** Metodo que retorna un iterable en el que contiene todas las aristas del arbol minimo cobertor
     */
    fun obtenerLados() : Iterable<Arista> {
        /** Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: g.listaDeVertices.size > 0
         *  Tiempo de operacion: O(|V|)
         */
        return obtenerLadosIterable(g)
    }

    /** Metodo que retorna un double que representa el costo total del arbol minimo cobertor
     */
    fun obtenerCosto() : Double {
        /** Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: g.listaDeVertices.size > 0
         *  Postcondicion: obtenerCosto() != POSITIVE_INFINITY
         *  Tiempo de operacion: O(|V|)
         */
        var sum = 0.0
        for (V in g.listaDeVertices){
            sum += V.Costo
        }
        return sum
    }
}
