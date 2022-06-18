package ve.usb.libGrafo

/** Clase que determina las componentes conexas para un grafo no dirigido empleando la estructura "Conjuntos Disjuntos",
 *  siendo estos representados como árboles.
*/
public class ComponentesConexasCD(val g: GrafoNoDirigido) {
    /** Entrada:
     *      g: Grafo no dirigido
     *  Salida: componentes conexas
     *  Precondición: g es un Grafo no dirigido
     *  Postcondición: compConexas.isNotEmpty
     *  Tiempo: O(|E|α(|V|))
     */
    val compConexas = ConjuntosDisjuntos(g.numDeVertices)       //Se inicializa cada vértice como un conjunto disjunto
    init{
        val e = g.iterator()
        while(e.hasNext()){                                     //Iteramos sobre las aristas del grafo
            val arista = e.next()
            println()
            println("${arista.u} && ${arista.v}")
            if(compConexas.encontrarConjunto(arista.u) != compConexas.encontrarConjunto(arista.v)){     //y si los vértices que conforman dicha arista se encuentran en diferentes conjuntos
                compConexas.union(arista.u, arista.v)                                                   //se realiza la unión de estos, obteniendo así las componentes conexas del grafo
            }
        }
    }

    /** Método que dado dos enteros que representan vértices del grafo, retorna un booleano que indica si ambos vértices
     *  están en la misma componente conexa. En caso de que alguno de los dos vértices de entrada no pertenece al grafo
     *  entonces se lanza una RuntimeException.
     */
    fun mismaComponente(v: Int, u: Int) : Boolean {
        /** Entrada:
         *      v: entero que representa el vértice v
         *      u: entero que representa el vértice u
         *  Salida: Booleano que indica si ambos vértices pertenecen a una misma componente conexa
         *  Precondición: 0 <= v && v<g.listaDeVertices.size && 0 <= u && u<g.listaDeVertices.size
         *  Postcondición: compConexas.encontrarConjunto(u) == compConexas.encontrarConjunto(v)
         *  Tiempo: O(rank)
         */
        if(0 > v || v>=g.listaDeVertices.size || 0 > u || u>=g.listaDeVertices.size) throw RuntimeException("El vértice no se encuentra en el grafo")
        return compConexas.encontrarConjunto(u) == compConexas.encontrarConjunto(v)
	
    }

    /** Método que retorna un entero indicando el número de componentes conexas que posee el grafo
     */
    fun nCC() : Int {
        /** Salida: entero que indica el número de componentes conexas del grafo no dirigido
         *  Postcondición: compConexas.numConjuntosDisjuntos()
         *  Tiempo: O(1)
         */
        return compConexas.numConjuntosDisjuntos()
    }

    /** Método que dado un entero que representa un vértice del grafo no dirigido, retorna un entero en el
     *  intervalo [0, nCC-1] que representa el identificador de la componente conexa donde está contenido el vértice.
     *  Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    fun obtenerComponente(v: Int) : Int {
        /** Entrada:
         *      v: entero que representa el vértice v
         *  Salida: entero que representa el identificador de la componente conexa a la cual v pertenece
         *  Precondición: 0 <= v && v<g.listaDeVertices.size
         *  Postcondición: result in [0, nCC-1]
         *  Tiempo: O(rank)
         */
        if(0 > v || v>=g.listaDeVertices.size) throw RuntimeException("El vértice no se encuentra en el grafo")
        val repr = compConexas.encontrarConjunto(v)
        return compConexas.ConjuntosDisjuntos.indexOf(repr)
    }

    /** Método que dado un entero que representa el identificador de la componente conexa, retorna un entero que representa
     *  el número de vértices que conforman dicha componente. Si el identificador no pertenece a ninguna componente conexa
     *  entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        /** Entrada:
         *      compID: entero que representa el identificador de la componente conexa
         *  Salida: entero que representa el número de vértices que posee la componente conexa
         *  Precondición: 0 <= compID && compID < compConexas.numConjuntosDisjuntos()
         *  Postcondición: result < g.numDeVertices
         *  Tiempo: O(1)
         */
        if(0 > compID || compID >= compConexas.numConjuntosDisjuntos()) throw RuntimeException("El identificador no está asociado a ninguna componente conexa")
        val repr = compConexas.ConjuntosDisjuntos[compID]
        return compConexas.verticesCD[repr]
    }


}
