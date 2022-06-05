package libGrafoKt.ve.usb.libGrafo

import kotlin.RuntimeException

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/

var tiempo = 0

public class DFS(val g: Grafo) {
    
    init {
	// Se ejecuta DFS
        var v: Vertice
        for(i in g.listaDeAdyacencia.indices) {
            for (j in g.listaDeAdyacencia[i]!!.indices) {
                v = g.listaDeAdyacencia[i]!![j]
                v.color = Color.BLANCO
                v.pred = null
            }
        }
        for (i in g.listaDeAdyacencia.indices){
            for (j in g.listaDeAdyacencia[i]!!.indices){
                v = g.listaDeAdyacencia[i]!![j]
                if (v.color == Color.BLANCO)
                    dfsVisit(g, v.valor)
            }
        }
    }

    private fun dfsVisit(g: Grafo, u: Int) {
        val temp: Vertice? = g.listaDeVertices[u]!!

        tiempo += 1
        temp!!.t_inicial = tiempo
        temp.color = Color.GRIS
        for(v in g.listaDeAdyacencia[u]!!){
            if(v.color == Color.BLANCO){
                v.pred = temp
                dfsVisit(g,v.valor)
            }
        }
        temp.color = Color.NEGRO
        tiempo += 1
        temp.t_final = tiempo
    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor 
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException 
     */    
    fun obtenerPredecesor(v: Int) : Int? {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vértice no se encuentra en el grafo")
        return g.listaDeVertices[v]!!.pred?.valor
    }

     /*
     Retorna un par con el tiempo inical y final de un vértice durante la ejecución de DFS. 
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException 
     */
    fun obtenerTiempos(v: Int) : Pair<Int, Int> {
        val u: Vertice? = g.listaDeVertices[v]!!
        if(v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw RuntimeException("El vértice no se encuentra en el grafo")
        return Pair(u!!.t_inicial, u.t_final)
    }

    /*
     Indica si hay camino desde el vértice inicial u hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException 
     */ 
    fun hayCamino(u: Int, v: Int) : Boolean {
        if( u >= g.listaDeVertices.size || g.listaDeVertices[u] == null|| v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw kotlin.RuntimeException("El vértice no se encuentra en el grafo")

        if(g.listaDeVertices[u]!!.t_inicial < g.listaDeVertices[v]!!.t_inicial && g.listaDeVertices[u]!!.t_final < g.listaDeVertices[v]!!.t_final){
            return true
        }
        return false
    }

    /*
     Retorna el camino desde el vértice  u hasta el un vértice v. 
     El camino es representado como un objeto iterable con los vértices del camino desde u hasta v.
     En caso de que no exista un camino desde u hasta v, se lanza una RuntimeException. 
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException.
     */ 
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>{

    }

    // Retorna true si hay lados del bosque o false en caso contrario.
    fun hayLadosDeBosque(): Boolean {
        if(g.listaDeAdyacencia)
        return false
    }
    
    // Retorna los lados del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeBosque() : Iterator<Lado>{

    }

    // Retorna true si hay forward edges o false en caso contrario.
    fun hayLadosDeIda(): Boolean {

    }
    
    // Retorna los forward edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeIda() : Iterator<Lado> {

    }

    // Retorna true si hay back edges o false en caso contrario.
    fun hayLadosDeVuelta(): Boolean {

    }
    
    // Retorna los back edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeVuelta() : Iterator<Lado> {

    }

    // Retorna true si hay cross edges o false en caso contrario.
    fun hayLadosCruzados(): Boolean {

    }
    
    // Retorna los cross edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosCruzados() : Iterator<Lado> {

    }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() { }
    
}
