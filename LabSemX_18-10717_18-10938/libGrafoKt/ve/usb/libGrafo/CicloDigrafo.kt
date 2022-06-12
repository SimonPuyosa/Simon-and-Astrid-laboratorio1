package libGrafoKt.ve.usb.libGrafo
import java.util.concurrent.ConcurrentLinkedQueue

/** Clase que determina si el digrafo ingresado posee o no ciclos mediante la ejecución del algoritmo de DFS
*/
public class CicloDigrafo(val g: GrafoDirigido) {
    private val gDFS = DFS(g)

    /** Método que retorna un booleano indicando si el digrafo posee o no un ciclo
     */
    fun existeUnCiclo(): Boolean {                     //Si al momento de ejecutar el algoritmo de DFS se encuentran lados de vuelta,
        return gDFS.hayLadosDeVuelta()                 //automáticamente este grafo posee un ciclo
    }

    /** Clase interna que dado un grafo luego de haber realizado el algoritmo DFS, retorna un
     *  iterador de enteros en los que cada uno de ellos es el ciclo encontrado.
     */
    inner class CicloEnconIterato(private val G: DFS) : Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ciclo
         *  Precondición: g es un digrafo
         *  Postcondicion: (results <= g.listaDeVertices.size && verticeInicial == verticeFinal)
         *  Tiempo: O(|V|)
         */
        private val list = G.backEdges
        private var ciclos = ConcurrentLinkedQueue<Vertice>()
        private var i = g.listaDeVertices[list[0].first]!!.tiempoInicial - g.listaDeVertices[list[0].second]!!.tiempoInicial
        private var j = g.listaDeVertices[list[0].second]!!.tiempoFinal - g.listaDeVertices[list[0].first]!!.tiempoFinal
        private var n: Int = i
        private var m: Int = j
        private var k: Int = 0
        private lateinit var result: Vertice

        override fun hasNext(): Boolean {
            return (i>=-1 && j>=-1)
        }

        override fun next(): Int {
            if(i==-1 || j==-1){
                i--
                j--
                return list[0].second
            }else{
                n = i
                m = j
                result = g.listaDeVertices[list[0].first]!!
                while(n>0 && m>0){
                    result = result.pred!!
                    n--
                    m--
                }
                i--
                j--
                return result.valor
            }
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la CicloEnconIterato
     */
    inner class CicloEnconIterable(private val G: DFS) : Iterable<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ciclo
         *  Precondición: g es un digrafo
         *  Postcondicion: (results <= g.listaDeVertices.size && verticeInicial == verticeFinal)
         *  Tiempo: O(|V|)
         */
        override fun iterator(): Iterator<Int> = CicloEnconIterato(G)
    }
    /** Método que retorna un itetable con los vértices del ciclo encontrado en el digrafo g.
     *  Si el grafo no posee ciclos, se lanza una RuntimeException()
     */
        fun cicloEncontrado(): Iterable<Int> {
            if (!existeUnCiclo()) throw RuntimeException("El grafo ingresado no tiene ciclos")
            return CicloEnconIterable(gDFS)
        }
}