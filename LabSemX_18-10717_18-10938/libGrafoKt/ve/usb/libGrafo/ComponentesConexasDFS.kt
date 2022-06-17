package ve.usb.libGrafo

/**
 *  Clase que determina las componentes conexas de un grafo no dirigido
 *  usando el algoritmo de busqueda en profundidad (DFS)
 */
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {
    private val dfs = DFS(g)

    /** Método que dado dos enteros u y v, indica retornando un booleano si los dos vertices estan en la
     *  misma componente conexa, esto lo hace llamando al metodo hayCamino(u, v) que se encuentra en la
     *  clase DFS.
     *  Si alguno de los dos vértices no pertenece al grafo se lanza una RuntimeException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
        /**Entrada: dos enteros de valores del vértice inicial u y el vértice final v
         * Salida: un booleano que representa si ambos vertices se encuentran en la misma componente
         * Precondición: (u < g.listaDeVertices.size && v < g.listaDeVertices.size)
         * Postcondición: (g.listaDeVertices[u].cc == g.listaDeVertices[v].cc)
         * Tiempo: O(1)
         */
        return dfs.hayCamino(v,u)
    }

    /** Metodo que indica el numero de componentes conexas que tiene el grafo no dirigid
     */
    fun nCC() : Int {
        /**Salida: un entero que muestra el numero de componentes conexas
         * Precondición: (0 < g.listaDeVertices.size && g.listaDeAdyacencia != null)
         * Tiempo: O(1)
         */
	    return dfs.contCC
    }

    /** Metodo
     *
     */
    /*
     Retorna el identificador de la componente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , nCC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerComponente(v: Int) : Int {
        if (0 > v || v >= g.listaDeVertices.size) {
            throw RuntimeException("Los vértices no se encuentran en el grafo")
        }
        return g.listaDeVertices[v].cc
    }

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Se recibe como entrada el identificado de la componente conexa.
     El identificador es un número en el intervalo [0 , nCC()-1].
     Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        if (0 > compID || compID >= dfs.contCC) throw RuntimeException("el identificador no pertenece a ninguna componente conexa")
        var result = 0
        for (v in g.listaDeVertices){
            if (v.cc == compID) result++
        }
        if (result == 0) throw RuntimeException("el identificador no pertenece a ninguna componente conexa")
        return result
    }

}
