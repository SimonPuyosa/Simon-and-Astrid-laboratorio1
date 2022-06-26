package ve.usb.libGrafo

/** Clase que dertermina el orden topológico de un Grafo Acíclico Directo,
*/
public class OrdenamientoTopologico(val g: GrafoDirigido) {
    private val gDFS = DFS(g)                             //Se ejecuta el algoritmo de DFS para obtener el ordenamiento topológico del mismo

    /** Método que retorna un booleano que indica si el digrafo es un Grafo Acíclico Directo
     */
    fun esDAG() : Boolean{
        /** Salida: Un booleano que indica si el digrafo es un Grafo Acíclico Directo
         *  Postcondición: !CicloDigrafo(g).existeUnCiclo()
         *  Tiempo: O(1)
         */
        return !CicloDigrafo(g).existeUnCiclo()                                  //Un grafo se considera DAG si no posee ningún ciclo
    }

    /** Clase interna que dado un grafo luego de haberle sido aplicado el algoritmo DFS, retorna un
     *  iterador de enteros en los que cada uno de ellos es el orden topológico del difrafo.
     */

    inner class OrdenTopoIterato(G: DFS) : Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ordenamiento topológico
         *  Precondicion: g.esDAG()
         *  Postcondicion: (results <= g.listaDeVertices.size && G.obtenerOrdTop()[i-1].tiempoFinal > G.obtenerOrdTop()[i].tiempoFinal)
         *  Tiempo: O(|V|)
         */
        private val list = G.obtenerOrdTop()
        private var i: Int = 0
        private lateinit var result: Vertice

        override fun hasNext(): Boolean {
            return i < list.size
        }

        override fun next(): Int {
            result = list[i]
            i++
            return result.valor
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase OrdenTopoIterato
     */
    inner class OrdenTopoIterable(private val G: DFS) : Iterable<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ordenamiento topológico
         *  Precondicion: g.esDAG()
         *  Postcondicion: (results <= g.listaDeVertices.size && G.obtenerOrdTop()[i-1].tiempoFinal > G.obtenerOrdTop()[i].tiempoFinal)
         *  Tiempo: O(|V|)
         */
        override fun iterator(): Iterator<Int> = OrdenTopoIterato(G)
    }

    /** Método que retorna un itetable con los vértices del Ordenamiento Topológico realizado al digrafo g.
     *  Si el grafo no es un Grafo Acíclico Directo, se lanza una RuntimeException()
     */
    fun obtenerOrdenTopologico() : Iterable<Int>{
        /** Salida: una iterador que retorna enteros que representan los valores de los vertices del ordenamiento topológico
         *  Precondicion: g.esDAG()
         *  Postcondicion: (results <= g.listaDeVertices.size && G.obtenerOrdTop()[i-1].tiempoFinal > G.obtenerOrdTop()[i].tiempoFinal)
         *  Tiempo: O(|V|)
         */
        if(!esDAG()) throw RuntimeException("El grafo ingresado no es un Grafo Acíclico Directo")
        return OrdenTopoIterable(gDFS)
    }
}
