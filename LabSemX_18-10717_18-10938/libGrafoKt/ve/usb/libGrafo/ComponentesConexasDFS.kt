package ve.usb.libGrafo

/*
  Determina las componentes conexas de un grafo no dirigido usando DFS. 
  La componentes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {
    private val dfs = DFS(g)

    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
        if (0 > u || u >= g.listaDeVertices.size || 0 > v || v >= g.listaDeVertices.size) {
            throw RuntimeException("Los vértices no se encuentran en el grafo")
        }
        return g.listaDeVertices[v].cc == g.listaDeVertices[u].cc
    }

    // Indica el número de componentes conexas
    fun nCC() : Int {
	    return dfs.contCC
    }

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
