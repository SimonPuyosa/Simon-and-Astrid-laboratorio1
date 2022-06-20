package ve.usb.libGrafo

/**
 *  Clase que determina las componentes conexas de un grafo no dirigido
 *  usando el algoritmo de busqueda en profundidad (DFS)
 */
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {
    private var contCC = 0                                               // Variable Global

    init {
        /** Entrada: un grafo no dirigido no vacio
         *  Precondicion: (q.listaDeVertices.size > 0)
         *  Postcondicion: contCC != 0
         *  Tiempo de operacion: O(|V| + |E|)
         */
        for (i in g.listaDeVertices.indices) {
            g.listaDeVertices[i].pred = null
            g.listaDeVertices[i].color = Color.BLANCO
        }

        for (i in g.listaDeVertices) {
            if (g.listaDeVertices[i.valor].color == Color.BLANCO) {
                if (g.listaDeAdyacencia[i.valor] != null) dfsVisit(g, i.valor)
                contCC++
            }
        }
    }

    private fun dfsVisit(g: GrafoNoDirigido, u: Int) {
        /** Entrada:
         *      g: un grafo no dirigido no vacio
         *      u: un entero del valor del vértice a estudiar
         *  Precondicion: (q.listaDeVertices[u].color == Color.BLANCO)
         *  Postcondicion: contCC != 0
         *  Tiempo de operacion: O(|E|)
         */
        val temp: Vertice = g.listaDeVertices[u]
        var v: Vertice
        temp.color = Color.GRIS                  //y el color del vértice
        temp.cc = contCC

        val it = g.listaDeAdyacencia[u]!!.iterator()
        while (it.hasNext()) {                                       //Iteramos sobre los vértices adyacentes del vértice actual
            v = it.next()

            if (g.listaDeVertices[v.valor].color == Color.BLANCO) {
                v.pred = temp                                      //Guardamos el vértice predecesor
                g.listaDeVertices[v.valor].pred = temp
                if (g.listaDeAdyacencia[v.valor] != null) dfsVisit(g, v.valor)                               //y volvemos a llamar a visitDFS()
            }
        }
        temp.color = Color.NEGRO                                   //se termina de explorar
    }


    /** Método que dado dos enteros u y v, indica retornando un booleano si los dos vertices estan en la
     *  misma componente conexa, esto lo hace comprobando el numero de la componente conexa del vertice
     *  Si alguno de los dos vértices no pertenece al grafo se lanza una RuntimeException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
        /**Entrada: dos enteros de valores del vértice inicial u y el vértice final v
         * Salida: un booleano que representa si ambos vertices se encuentran en la misma componente
         * Precondición: (u < g.listaDeVertices.size && v < g.listaDeVertices.size)
         * Postcondición: (g.listaDeVertices[u].cc == g.listaDeVertices[v].cc)
         * Tiempo: O(1)
         */
        if (0 > u || u >= g.listaDeVertices.size || 0 > v || v >= g.listaDeVertices.size) throw RuntimeException(
            "Los vertices no se encuentran en el grafo"
        )
        return g.listaDeVertices[u].cc == g.listaDeVertices[v].cc
    }

    /** Metodo que indica el numero de componentes conexas que tiene el grafo no dirigid
     */
    fun nCC() : Int {
        /**Salida: un entero que muestra el numero de componentes conexas
         * Precondición: (0 < g.listaDeVertices.size && g.listaDeAdyacencia != null)
         * Tiempo: O(1)
         */
	    return contCC
    }

    /** Metodo que dado un entero que representa un vertice, retorna el identificador de
     *  la componente conexa donde este se encuentra, es decir un entero desde 0 hasta nCC()-1
     *  Si el vertice no se encuentra en el grafo se lanza un RuntimeException
     */
    fun obtenerComponente(v: Int) : Int {
        /** Entrada: un entero del valor del vertice del cual se pide el componente
         *  Salida: un entero del componente en el que se encuentra el vertice dado
         *  Precondicion: v in g.ListaDeVertices
         *  Postcondicion: 0 <= result < nCC()
         *  Tiempo: O(1)
         */
        if (0 > v || v >= g.listaDeVertices.size) {
            throw RuntimeException("Los vértices no se encuentran en el grafo")
        }
        return g.listaDeVertices[v].cc
    }

    /** Metodo que dado un entero que representa un identificador de la componente conexa, es decir,
     *  un numero entre el 0 y nCC()-1, se retorna un entero del numero de vertices que se encuentran
     *  en esa componente.
     *  Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        /** Entrada: un entero del identificador de la componente conexa a buscar
         *  Salida: un entero del numero de vertices que se encuentran en el identificador
         *  Precondicion: 0 <= compID < nCC()
         *  Postcondicion: 0 < result < g.numDeVertices
         *  Tiempo: O(|V|)
         */
        if (0 > compID || compID >= contCC) throw RuntimeException("el identificador no pertenece a ninguna componente conexa")
        var result = 0
        for (v in g.listaDeVertices){
            if (v.cc == compID) result++
        }
        if (result == 0) throw RuntimeException("el identificador no pertenece a ninguna componente conexa")
        return result
    }
}
