package libGrafoKt.ve.usb.libGrafo

import kotlin.RuntimeException
import java.util.concurrent.ConcurrentLinkedQueue


/** Clase que realiza el algoritmo DFS desde todos los vértices cuando se llama,
 *  esta clase recibe un grafo (de cualquier tipo)
 */

public class DFS(val g: Grafo) {
    private var tiempo = 0                                       // Variable Global
    private var DFStree = ConcurrentLinkedQueue<Vertice>()       // Cola en la que se almacenará en DFStree
    private var treeEdges = ArrayList<Arco>()                 // Arreglo donde se almacenará los lados del bosque generado por DFS
    private var forwardEdges = ArrayList<Arco>()              // Arreglo donde se almacenará los lados de ida del bosque generado por DFS
    private var backEdges = ArrayList<Arco>()                 // Arreglo donde se almacenará los lados de vuelta del bosque generado por DFS
    private var crossEdges = ArrayList<Arco>()                // Arreglo donde se almacenará los lados cruzados del bosque generado por DFS

    /** Constructor de la clase DFS el cual ejecuta dicho algoritmo tomando los valores del grafo
     *  previamente descrito, este constructor añade los valores correspondientes del color, tiempo inicial, tiempo final
     *  y predecesor en cada uno de los vertices de la lista de vertices solo si estos son recorridos, en caso contrario quedaran
     *  con los valores iniciales
     */

    init {
        /** Entrada:
         *      q: un grafo no vacio de cualquier tipo
         *  Precondicion: (q.listaDeVertices.size > 0)
         *  Postcondicion: ! DFStree.isEmpty()
         *  Tiempo de operacion: O(|V| + |E|)
         */
        for(i in g.listaDeAdyacencia.indices) {
            g.listaDeVertices[i]!!.pred = null
            g.listaDeVertices[i]!!.color = Color.BLANCO
        }
        for (i in g.listaDeAdyacencia.indices){
            if (g.listaDeVertices[i]!!.color == Color.BLANCO){
                dfsVisit(g, g.listaDeVertices[i]!!.valor)
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

        val temp: Vertice = g.listaDeVertices[u]!!

        tiempo += 1                              //Se empieza a explorar
        temp.tiempoInicial = tiempo              //Actualizamos el tiempo inicial
        temp.color = Color.GRIS                  //y el color del vértice
        for(v in g.listaDeAdyacencia[u]!!) {     //Iteramos sobre los vértices adyacentes del vértice actual
            if (v.color == Color.BLANCO) {
                treeEdges.add(Arco(temp.valor,v.valor))            //Si el vértice es blanco, el algoritmo se ejecuta y por lo tanto genera un lado del bosque
                v.pred = temp                                      //Guardamos el vértice predecesor
                dfsVisit(g, v.valor)                               //y volvemos a llamar a visitDFS()
            } else if (v.color == Color.GRIS) {
                backEdges.add(Arco(temp.valor, v.valor))           //Si el vértice adyacente al actual es gris, quiere decir que el vértice actual tiene un lado hasta su ancestro
            } else if (v.color == Color.NEGRO && temp.tiempoInicial < v.tiempoInicial) {            //Si el vértice adyacente al actual es negro, quiere decir que el vértice actual tiene un lado hasta su descendiente
                forwardEdges.add(Arco(temp.valor, v.valor))                                         //Además, si el tiempo inicial del vértice actual es menor a su adyacente, indica que dicho lado no pertenece a DFSTree
            } else {
                crossEdges.add(Arco(temp.valor, v.valor))          //En cambio, si el vértice adyacente al actual es negro, pero el tiempo inicial del vértice actual es mayor al adyacente, indica que hay un lado cualquiera
            }                                                      //que no pertenece al bosque generado
        }

        temp.color = Color.NEGRO                                   //se termina de explorar
        tiempo += 1                                                //almacenamos el tiempo final
        temp.tiempoFinal = tiempo
        DFStree.add(temp)                                          //Guardamos el vértice en la cola
    }

    /** Método que retorna el valor del vértice predecesor del valor de un vértice dado, si el valor del vértice dado
     *  no tiene predecesor retorna null. Si no se encuentra en la listaDeVertices entonces se lanza un RuntimeException
     */
    fun obtenerPredecesor(v: Int) : Int? {
        /** Entrada: un entero del valor del vértice a buscar su predecesor
         *  Salida: un entero que representa el valor del vértice predecesor o un valor nulo si este no tiene predecesor
         *  Precondicion: (v < q.listaDeVertices.size && q.listaDeVertices[v] != null)
         *  Postcondicion: result == g.listaDeVertices[v].pred.valor
         *  Tiempo: O(1)
         */
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vértice no se encuentra en el grafo")
        return g.listaDeVertices[v]!!.pred?.valor
    }


    /** Método que retorna un par que contiene el tiempo inicial y final de un vértice durante la ejecución de DFS
     * Si el vértice no se encuentra en el grafo se lanza una RuntimeException
     */

    fun obtenerTiempos(v: Int) : Pair<Int, Int> {
        /**Entrada: un entero del valor del vértice a buscar su tiempo inicial y final
         * Salida: un par de componentes enteros que representa el tiempo inicial y final de vértice
         * Precondición: (v < g.listaDeVertices.size && g.listaDeVertices[v] != null)
         * Postcondición: Par == (g.listaDeVertices[v].tiempoInicial, g.listaDeVertices[v].tiempoFinal)
         */
        val u: Vertice = g.listaDeVertices[v]!!
        if(v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vértice no se encuentra en el grafo")
        return Pair(u.tiempoInicial, u.tiempoFinal)
    }

    /** Método que dado dos enteros u y v, indica retornando un booleand si existe un camino desde el vértice inicial u hasta el vértice
     * final v. Si alguno de los dos vértices no pertenece al grafo se lanza una RuntimeException
     */

    fun hayCamino(u: Int, v: Int) : Boolean {
        /**Entrada: dos enteros de valores del vértice inicial u y el vértice final v
         * Salida: un booleano que representa si existe un camino desde el vértice u hasta el vértice v
         * Precondición: (u < g.listaDeVertices.size && g.listaDeVertices[u] != null && v < g.listaDeVertices.size && g.listaDeVertices[v] != null)
         * Postcondición: (g.listaDeVertices[u].tiempoInicial < g.listaDeVertices[v].tiempoInicial < g.listaDeVertices[v].tiempoFinal < g.listaDeVertices[u].tiempoFinal) || (crossEdges.contains(Arco(u,v)) || forwardEdges.contains(Arco(u,v)))
         * Tiempo: O(1)
         */

        if( u >= g.listaDeVertices.size || g.listaDeVertices[u] == null|| v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vértice no se encuentra en el grafo")

        if(g.listaDeVertices[u]!!.tiempoInicial < g.listaDeVertices[v]!!.tiempoInicial && g.listaDeVertices[u]!!.tiempoFinal < g.listaDeVertices[v]!!.tiempoFinal){
            return true
        }else if(crossEdges.contains(Arco(u,v)) || forwardEdges.contains(Arco(u,v))){
            return true
        }
        return false
    }

    /**  Clase interna que dado un grafo y dos enteros que representan el valor de dos vertices u y v del grafo,
     *   se retorna un iterador de enteros en los que cada uno de ellos es el camino desde el vertice inicial hasta el
     *   vertice final
     */

    inner class CamDesdeHastaIterato(private val G: Grafo, private val u: Int, private val v: Int): Iterator<Int> {
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vértice incial hasta el vértice final
         *      u: un entero que representa el valor del vértice desde donde se empieza a buscar el camino
         *      v: un entero que representa el valor del vértice hasta donde se termina el camino
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice final
         *  Precondicion: (u < g.listaDeVertices.size && v < g.listaDeVertices.size && g.listaDeVertices[u] != null && g.listaDeVertices[v] != null && hayCamino(u,v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        private var i: Int = G.listaDeVertices[v]!!.tiempoInicial - G.listaDeVertices[u]!!.tiempoInicial
        private var j: Int = G.listaDeVertices[u]!!.tiempoFinal - G.listaDeVertices[v]!!.tiempoFinal
        private var n: Int = i
        private var m: Int = j
        private lateinit var result: Vertice

        override fun hasNext(): Boolean {
            return i>=0 && j >=0
        }

        override fun next(): Int {
            n = i
            m = j
            result = G.listaDeVertices[v]!!
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

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase CamHastaIterato
     */
    inner class CamDesdeHastaIterable(private val G: Grafo, private val u: Int, private val v: Int): Iterable<Int>{
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vertice incial hasta el vertice final
         *      u: un entero que representa el valor del vertice inicial desde donde se empieza a buscar el camino
         *      v: un entero que representa el valor del vertice final hasta donde termina el camino
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice final
         *  Precondicion: (u < g.listaDeVertices.size && v < g.listaDeVertices.size && g.listaDeVertices[u] != null && g.listaDeVertices[v] != null && hayCamino(u,v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        override fun iterator(): Iterator<Int> = CamDesdeHastaIterato(G, u, v)
    }

    /** Método que dado dos enteros que representan dos vértices, retorna iterable con los vértices del camino desde el vértice
     *  inicial u hasta el vértice final v. Si no existe tal camino, o que no pertenezca algunos de los dos vértices al grafo
     *  se lanza una RuntimeException
     */
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>{
        /** Entrada:
         *      G: un grafo en el cual se buscara el camino desde el vertice incial hasta el vertice final
         *      u: un entero que representa el valor del vertice inicial desde donde se empieza a buscar el camino
         *      v: un entero que representa el valor del vertice final hasta donde termina el camino
         *  Salida: una iterador que retorna enteros que representan los valores de los vertices desde el vertice inicial hasta el vertice final
         *  Precondicion: (u < g.listaDeVertices.size && v < g.listaDeVertices.size && g.listaDeVertices[u] != null && g.listaDeVertices[v] != null && hayCamino(u,v))
         *  Postcondicion: (results <= g.listaDeVertices.size && g.listaDeVertices[results] != null)
         *  Tiempo: O(|E|)
         */
        if(!hayCamino(u, v)) throw RuntimeException("No existe camino desde el vértice $u hasta el vértice $v")
        if(g.listaDeVertices[v] == null || g.listaDeVertices[u] == null) throw RuntimeException("Alguno de los vértices no se encuentran en el grafo")
        return CamDesdeHastaIterable(g, u, v)
    }

    /** Método que indica si existe lados del bosque en el grafo
     */
    fun hayLadosDeBosque(): Boolean {
        /** Salida: un booleano que determina si existen tree edges
         *  Postcondición: result == treeEdges.isNotEmpty()
         *  Tiempo: O(1)
         */
        return treeEdges.isNotEmpty()
    }

    /** Método que retorna un iterador de lados en los que cada ellos son lados del bosque generado.
     *  Si no hay este tipo de lados en el bosque, se lanza una RuntimeException
     */
    fun ladosDeBosque() : Iterator<Lado>{
        /** Salida: un iterador que retorna lados que representan tree edges
         *  Precondición: hayLadosBosque()
         *  Postcondición: treeEdges.iterator()
         *  Tiempo: O(|E|)
         */
        if(!hayLadosDeBosque()) throw RuntimeException("No existe Tree Edges en este grafo")
        return treeEdges.iterator()
    }

    /** Método que indica si existe lados de ida del bosque en el grafo
     */
    fun hayLadosDeIda(): Boolean {
        /** Salida: un booleano que determina si existen forwards edges
         *  Postcondición: result == forwardEdges.isNotEmpty()
         *  Tiempo: O(1)
         */
        return forwardEdges.isNotEmpty()
    }

    /** Método que retorna un iterador de lados en los que cada ellos son lados de ida del bosque generado.
     *  Si no hay este tipo de lados en el bosque, se lanza una RuntimeException
     */
    fun ladosDeIda() : Iterator<Lado> {
        /** Salida: un iterador que retorna lados que representan forward edges
         *  Precondición: hayLadosDeIda()
         *  Postcondición: forwardEdges.iterator()
         *  Tiempo: O(|E|)
         */
        if(!hayLadosDeIda()) throw RuntimeException("No existe Forward Edges en este grafo")
        return forwardEdges.iterator()
    }

    /** Método que indica si existe lados de vuelta del bosque en el grafo
     */
    fun hayLadosDeVuelta(): Boolean {
        /** Salida: un booleano que determina si existen back edges
         *  Postcondición: result == forwardEdges.isNotEmpty()
         *  Tiempo: O(1)
         */
        return backEdges.isNotEmpty()
    }

    /** Método que retorna un iterador de lados en los que cada ellos son lados de vuelta del bosque generado.
     *  Si no hay este tipo de lados en el bosque, se lanza una RuntimeException
     */
    fun ladosDeVuelta() : Iterator<Lado> {
        /** Salida: un iterador que retorna lados que representan back edges
         *  Precondición: hayLadosDeVuelta()
         *  Postcondición: forwardEdges.iterator()
         *  Tiempo: O(|E|)
         */
        if(!hayLadosDeVuelta()) throw RuntimeException("No existe Back Edges en este grafo")
        return backEdges.iterator()
    }

    /** Método que indica si existe lados cruzados del bosque en el grafo
     */
    fun hayLadosCruzados(): Boolean{
        /** Salida: un booleano que determina si existen cross edges
         *  Postcondición: result == crossEdges.isNotEmpty()
         *  Tiempo: O(1)
         */
        return crossEdges.isNotEmpty()
    }

    /** Método que retorna un iterador de lados en los que cada ellos son lados cruzados del bosque generado.
     *  Si no hay este tipo de lados en el bosque, se lanza una RuntimeException
     */
    fun ladosCruzados() : Iterator<Lado> {
        /** Salida: un iterador que retorna lados que representan cross edges
         *  Precondición: hayLadosDeCruzados()
         *  Postcondición: crossEdges.iterator()
         *  Tiempo: O(|E|)
         */
        if(!hayLadosCruzados()) throw RuntimeException("No existe Cross Edges en este grafo")
        return crossEdges.iterator()
    }

    /** Metodo que imprime todos los valores de los vertices empezando por el vertice inicial hasta los vertices con mayor tiempo final,
     *  en otras palabras se imprime el depth-first tree
     */
    fun mostrarBosqueDFS(){
        /** Precondicion: g.listaDeAdyacencia != arrayOf(null)
         *  Postcondicion: println(DFStree.remove().valor)
         *  Tiempo: O(|E|)
         */
        if(DFStree.isEmpty()) throw RuntimeException("El grafo está vacío")
        while(!DFStree.isEmpty()){
            println(DFStree.remove().valor)
        }
    }
    
}
