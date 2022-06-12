package libGrafoKt.ve.usb.libGrafo
import java.util.concurrent.ConcurrentLinkedQueue

/** Clase que realiza el algoritmo BFS cuando se llama, esta recive un grafo (de cualquier tipo)
 *  y un entero que representa el valor del vertice que se tomara como inicio en este algoritmo
*/
public class BFS(val g: Grafo, val s: Int) {
    // Se crea una cola para almacenar el BFStree
    private var BFStree = ConcurrentLinkedQueue<Vertice>()

    /** Constructor de la clase BFS el cual ejecuta dicho algoritmo tomando los valores del grafo y el entero
     *  previamente descritos, este constructor añade los valores correspondientes del color, distancia y predecesor
     *  en cada uno de los vertices de la lista de vertices solo si estos son recorridos, en caso contrario quedaran
     *  con los valores iniciales
     */
    init {
        /** Entrada:
         *      q: un grafo no vacio de cualquier tipo
         *      s: un entero del valor del vertice a estudiar
         *  Precondicion: (q.listaDeVertices.size > 0 && s < q.listaDeVertices.size && q.listaDeVertices[s] != null)
         *  Postcondicion: ! BFStree.isEmpty()
         *  Tiempo de operacion: O(|V| + |E|)
         */
        var it: MutableIterator<Vertice>                // variable temporal que nos permitira recorrer las listas enlazadas
        var temp: Vertice
        val cola = ConcurrentLinkedQueue<Vertice>()     // cola en la cual se almacenan los vertices por colorear

        // se verifica que las propiedades de los nodos sean las correctas antes de iniciar bfs
        for (i in g.listaDeAdyacencia.indices){         // se itera sobre cada elemento del arreglo
            g.listaDeVertices[i].distancia = 0
            g.listaDeVertices[i].pred = null
            g.listaDeVertices[i].color = Color.BLANCO

            if(g.listaDeAdyacencia[i] != null){         // si el vertice tiene adyacencias entonces se verifica la correctitud de las adyacencias
                it = g.listaDeAdyacencia[i]!!.iterator()
                while(it.hasNext()){                    // se itera sobre todas las incidencias del vertice de valor i
                    temp = it.next()
                    temp.distancia = 0                  // se verifica que los valores sean correctos
                    temp.color = Color.BLANCO
                    temp.pred = null
                }
            }
        }
        var u = Vertice(s)                              // se toma el vertice de inicio
        u.distancia = 0
        u.color = Color.GRIS
        u.pred = null
        cola.add(u)                                     // se añade el vertice de inicio
        while (!cola.isEmpty()){
            u = cola.remove()                           // se desencola el primer elemento de la cola
            BFStree.add(u)                              // se agrega este elemento al BFStree
            if (g.listaDeAdyacencia[u.valor] != null){  // se verifica si el vertice tiene adyacencias
                it = g.listaDeAdyacencia[u.valor]!!.iterator()  // se itera sobre todos los elementos de las linkedlist
                while (it.hasNext()){
                    temp = it.next()
                    if (g.listaDeVertices[temp.valor].color == Color.BLANCO){ // si el vertice es de color blanco entonces se trabaja sobre el
                        temp.color = Color.GRIS
                        temp.distancia = u.distancia + 1
                        temp.pred = u
                        g.listaDeVertices[temp.valor].color = Color.GRIS
                        g.listaDeVertices[temp.valor].distancia = u.distancia + 1
                        g.listaDeVertices[temp.valor].pred = u
                        cola.add(temp)                  // se añade el vertice gris a la cola
                    }
                }
            }
            u.color = Color.NEGRO
            g.listaDeVertices[u.valor].color = Color.NEGRO
        }
    }

    /** Metodo que retorna el valor del vertice predecesor del valor de un vertice dado, si el valor del vertice dado
     *  no tiene predecesor retorna null. Si no se encuentra en la listaDeVertices entonces se lanza un RuntimeException
     */
    fun obtenerPredecesor(v: Int) : Int? {
        /** Entrada: un entero del valor del vertice a buscar su predecesor
         *  Salida: un entero que representa el valor del vertice predecesor o un valor nulo si este no tiene predecesor
         *  Precondicion: (v < q.listaDeVertices.size && q.listaDeVertices[v] != null)
         *  Postcondicion: result == g.listaDeVertices[v].pred.valor
         *  Tiempo: O(1)
         */
        if (v >= g.listaDeVertices.size) throw RuntimeException("El vertice no se encuentra")
        return g.listaDeVertices[v].pred?.valor
    }

    /** Metodo que retorna la distancia del camino obtenido por BFS, desde el vertice inicial hasta un vertice dado.
     *  Si el vertice no es alcanzable se retorna -1 si este no se encuentra en el
     *  grafo se lanza un RuntimeException
     */
    fun obtenerDistancia(v: Int) : Int {
        /** Entrada: un entero del valor del vertice a buscar la distancia al vertice inicial
         *  Salida: un entero que representa la distancia del vertice dado al vertice inicial o -1 si este no es alcanzable
         *  Precondicion: (v < q.listaDeVertices.size && q.listaDeVertices[v] != null)
         *  Postcondicion: -1 || result == g.listaDeVertices[v].distancia
         *  Tiempo: O(1)
         */
        if (v >= g.listaDeVertices.size) throw RuntimeException("El vertice no se encuentra")
        if (v == s) return 0
        else if (g.listaDeVertices[v].distancia == 0) return -1
        return g.listaDeVertices[v].distancia
    }

    /** Metodo que dado un entero que representa el valor de un vertice, retorna un booleano determinando si existe un camino
     *  desde el vertice dado hasta el vertice inicia. Si este no se encuentra en el grafo se lanza un RuntimeException
     */
    fun hayCaminoHasta(v: Int) : Boolean {
        /** Entrada: un entero del valor del vertice a buscar si es alcanzable por el vertice inicial
         *  Salida: uun booleano que representa si existe un camino desde el vertice dado hasta el vertice inicial
         *  Precondicion: (v < q.listaDeVertices.size && q.listaDeVertices[v] != null)
         *  Postcondicion: (v == s || g.listaDeVertices[v].distancia != 0)
         *  Tiempo: O(1)
         */

        if (v >= g.listaDeVertices.size) throw RuntimeException("El vertice no se encuentra")
        if (v == s) return true                                             // el vertice es el vertice inicial
        else if (g.listaDeVertices[v].distancia == 0) return false        // el vertice no se le cambio la distancia por lo que no hay camino hasta el
        return true
    }

    /**  Clase interna que dado un grafo y un entero que representa el valor de un vertice del grafo,
     *   se retorna un iterador de enteros en los que cada uno de ellos es el camino desde el vertice inicial hasta el
     *   vertice dado
     */
    inner class CamHastaIterato(private val G: Grafo, private val v: Int) : Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vertice incial hasta
         *      v: un entero que representa el valor del vertice del cual se buscara el camino desde el vertice inicial
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice dado
         *  Precondicion: (v < g.listaDeVertices.size && g.listaDeVertices[v] == null && hayCaminoHasta(v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        private var i: Int = G.listaDeVertices[v].distancia   // se toma la distancia del vertice v
        private var j: Int = i
        private lateinit var result: Vertice

        override fun hasNext(): Boolean {
            return i >= 0                                       // se itera hasta que la distancia no sea negativa
        }

        override fun next(): Int {
            j = i
            result = G.listaDeVertices[v]
            while (j > 0){
                result = result.pred!!                          // como necesitamos el camino desde s hasta v
                j--                                             // buscamos el predecesor hasta que j > 0
            }
            i--
            return result.valor                                 // retornamos el predecesor
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase CamHastaIterato
     */
    inner class CamHastaIterable(private val G: Grafo, private val v: Int) : Iterable<Int>{
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vertice incial hasta
         *      v: un entero que representa el valor del vertice del cual se buscara el camino desde el vertice inicial
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice dado
         *  Precondicion: (v < g.listaDeVertices.size && g.listaDeVertices[v] == null && hayCaminoHasta(v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        override fun iterator(): Iterator<Int> = CamHastaIterato(G, v)
    }


    /** Metodo que dado un entero que representa un vertice, retorna un iterable del camino con menos lados obtenido por el
     *  algoritmo BFS desde el vertice inicial hasta el vertice v. Si el vertice v no es alcanzable o el vertice no existe
     *  en el grafo entonces se lanza un RuntimeException
     */
    fun caminoHasta(v: Int) : Iterable<Int>  {
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vertice incial hasta
         *      v: un entero que representa el valor del vertice del cual se buscara el camino desde el vertice inicial
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice dado
         *  Precondicion: (v < g.listaDeVertices.size && g.listaDeVertices[v] == null && hayCaminoHasta(v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        if (v >= g.listaDeVertices.size) throw RuntimeException("El vertice no se encuentra")
        if (! hayCaminoHasta(v)) throw RuntimeException("El vertice no es alcanzable")

        return CamHastaIterable(g, v)
    }

    /** Metodo que imprime todos los valores de los vertices empezando por el vertice inicial hasta los vertices con mayor distancia,
     *  en otras palabras se imprime el breadth-first tree
     */
    fun mostrarArbolBFS() {
        /** Precondicion: g.listaDeAdyacencia != arrayOf(null)
         *  Postcondicion: println(BFStree.remove().valor)
         *  Tiempo: O(|E|)
         */
        if(BFStree.isEmpty()) throw RuntimeException("el grafo esta vacio")
        while(! BFStree.isEmpty()){
            println(BFStree.remove().valor)
        }
    }
}
