package ve.usb.libGrafo
import java.util.*

/** Clase que determina si el digrafo ingresado posee o no ciclos mediante la ejecución del algoritmo de DFS
 */
public class CicloDigrafo(val g: GrafoDirigido) {
    private var backEdges = LinkedList<Pair<Int, Int>>()                 // Arreglo donde se almacenará los lados de vuelta del bosque generado por DFS
    private var tiempo = 0                                       // Variable Global

    init {
        /** Entrada:
         *      q: un grafo no vacio de cualquier tipo
         *  Precondicion: (q.listaDeVertices.size > 0)
         *  Postcondicion: ! DFStree.isEmpty()
         *  Tiempo de operacion: O(|V| + |E|)
         */
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

    private fun dfsVisit(g: Grafo, u: Int) {
        /** Entrada:
         *      g: un grafo no vacio de cualquier tipo
         *      u: un entero del valor del vértice a estudiar
         *  Precondicion: (q.listaDeVertices[u].color == Color.BLANCO)
         *  Postcondicion: ! DFStree.isEmpty()
         *  Tiempo de operacion: O(|E|)
         */

        val temp: Vertice = g.listaDeVertices[u]
        var v: Vertice
        tiempo += 1                              //Se empieza a explorar
        temp.tiempoInicial = tiempo              //Actualizamos el tiempo inicial
        temp.color = Color.GRIS                  //y el color del vértice

        if (g.listaDeAdyacencia[u] != null) {
            val it = g.listaDeAdyacencia[u]!!.iterator()
            while (it.hasNext()) {                                       //Iteramos sobre los vértices adyacentes del vértice actual
                v = it.next()

                if (g.listaDeVertices[v.valor].color == Color.BLANCO) {
                    g.listaDeVertices[v.valor].pred = temp              //Guardamos el vértice predecesor
                    dfsVisit(g, v.valor)                               //y volvemos a llamar a visitDFS()
                } else if (g.listaDeVertices[v.valor].color == Color.GRIS) {
                    backEdges.addFirst(Pair(temp.valor, v.valor))           //Si el vértice adyacente al actual es gris, quiere decir que el vértice actual tiene un lado hasta su ancestro
                }
            }
        }

        temp.color = Color.NEGRO                                   //se termina de explorar
        tiempo += 1                                                //almacenamos el tiempo final
        temp.tiempoFinal = tiempo
    }

    /** Método que retorna un booleano indicando si el digrafo posee o no un ciclo
     */
    fun existeUnCiclo(): Boolean {                     //Si al momento de ejecutar el algoritmo de DFS se encuentran lados de vuelta,
        return backEdges.isNotEmpty()                  //automáticamente este grafo posee un ciclo
    }

    /** Clase interna que dado un grafo luego de haber realizado el algoritmo DFS, retorna un
     *  iterador de enteros en los que cada uno de ellos es el ciclo encontrado.
     */
    inner class CicloEnconIterato(G: GrafoDirigido, backEdges: LinkedList<Pair<Int, Int>>) : Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ciclo
         *  Precondición: g es un digrafo
         *  Postcondicion: (results <= g.listaDeVertices.size && verticeInicial == verticeFinal)
         *  Tiempo: O(|V|)
         */
        private val list = backEdges
        private var i = G.listaDeVertices[list[0].first].tiempoInicial - G.listaDeVertices[list[0].second].tiempoInicial
        private var j = G.listaDeVertices[list[0].second].tiempoFinal - G.listaDeVertices[list[0].first].tiempoFinal
        private var n: Int = i
        private var m: Int = j
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
                result = g.listaDeVertices[list[0].first]
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
    inner class CicloEnconIterable(private val G: GrafoDirigido, private val backEdges: LinkedList<Pair<Int, Int>>) : Iterable<Int> {
        /** Entrada:
         *      G: un grafo en el cual se le aplicó el algoritmo de DFS
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices del ciclo
         *  Precondición: g es un digrafo
         *  Postcondicion: (results <= g.listaDeVertices.size && verticeInicial == verticeFinal)
         *  Tiempo: O(|V|)
         */
        override fun iterator(): Iterator<Int> = CicloEnconIterato(G, backEdges)
    }
    /** Método que retorna un itetable con los vértices del ciclo encontrado en el digrafo g.
     *  Si el grafo no posee ciclos, se lanza una RuntimeException()
     */
    fun cicloEncontrado(): Iterable<Int> {
        if (!existeUnCiclo()) throw RuntimeException("El grafo ingresado no tiene ciclos")
        return CicloEnconIterable(g, backEdges)
    }
}