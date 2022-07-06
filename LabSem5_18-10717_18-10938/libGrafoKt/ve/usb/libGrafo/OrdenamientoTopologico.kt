package ve.usb.libGrafo

import java.util.*

/** Clase que dertermina el orden topológico de un Grafo Acíclico Directo,
*/
public class OrdenamientoTopologico(val g: GrafoDirigido) {
    var ordenTopologico = LinkedList<Vertice>()
    private var esDag = true

    init {
        //Se ejecuta el algoritmo de DFS para obtener el ordenamiento topológico del mismo
        for (V in g.listaDeVertices) {
            V.pred = null
            V.color = Color.BLANCO
        }

        for (V in g.listaDeVertices) {
            if (V.color == Color.BLANCO) {
                dfsVisit(g, V.valor)
            }
        }
    }

    fun dfsVisit(g: GrafoDirigido, u: Int){
        val temp: Vertice = g.listaDeVertices[u]
        var v: Vertice
        temp.color = Color.GRIS                  //y el color del vértice

        if (g.listaDeAdyacencia[u] != null) {
            val it = g.listaDeAdyacencia[u]!!.iterator()
            while (it.hasNext()) {                                       //Iteramos sobre los vértices adyacentes del vértice actual
                v = it.next()

                if (g.listaDeVertices[v.valor].color == Color.BLANCO) {
                    g.listaDeVertices[v.valor].pred = temp              //Guardamos el vértice predecesor
                    dfsVisit(g, v.valor)                               //y volvemos a llamar a visitDFS()
                } else if (g.listaDeVertices[v.valor].color == Color.GRIS) {
                    esDag = false
                }
            }
        }
        temp.color = Color.NEGRO                                   //se termina de explorar
        ordenTopologico.addFirst(temp)
    }

    /** Método que retorna un booleano que indica si el digrafo es un Grafo Acíclico Directo
     */
    fun esDAG() : Boolean{
        /** Salida: Un booleano que indica si el digrafo es un Grafo Acíclico Directo
         *  Postcondición: !CicloDigrafo(g).existeUnCiclo()
         *  Tiempo: O(1)
         */
        return esDag                                  //Un grafo se considera DAG si no posee ningún ciclo
    }

    /** Clase interna que dado un grafo luego de haberle sido aplicado el algoritmo DFS, retorna un
     *  iterador de enteros en los que cada uno de ellos es el orden topológico del difrafo.
     */
    inner class OrdenTopoIterato(orden: LinkedList<Vertice>) : Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ordenamiento topológico
         *  Precondicion: g.esDAG()
         *  Postcondicion: (results <= g.listaDeVertices.size && G.obtenerOrdTop()[i-1].tiempoFinal > G.obtenerOrdTop()[i].tiempoFinal)
         *  Tiempo: O(|V|)
         */
        private val it = orden.iterator()

        override fun hasNext(): Boolean {
            return it.hasNext()
        }

        override fun next(): Int {
            return it.next().valor
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase OrdenTopoIterato
     */
    inner class OrdenTopoIterable(private val orden: LinkedList<Vertice>) : Iterable<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ordenamiento topológico
         *  Precondicion: g.esDAG()
         *  Postcondicion: (results <= g.listaDeVertices.size && G.obtenerOrdTop()[i-1].tiempoFinal > G.obtenerOrdTop()[i].tiempoFinal)
         *  Tiempo: O(|V|)
         */
        override fun iterator(): Iterator<Int> = OrdenTopoIterato(orden)
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
        return OrdenTopoIterable(ordenTopologico)
    }
}
