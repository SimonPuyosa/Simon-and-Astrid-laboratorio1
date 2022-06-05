package libGrafoKt.ve.usb.libGrafo

import kotlin.RuntimeException
import java.util.concurrent.ConcurrentLinkedQueue

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/

public class DFS(val g: Grafo) {
    private var tiempo = 0
    private var DFStree = ArrayList<Vertice>()
    
    init {
	// Se ejecuta DFS
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
        val temp: Vertice? = g.listaDeVertices[u]!!

        tiempo += 1
        temp!!.tiempoInicial = tiempo
        temp.color = Color.GRIS
        for(v in g.listaDeAdyacencia[u]!!){
            if(v.color == Color.BLANCO){
                v.pred = temp
                dfsVisit(g,v.valor)
            }
        }
        temp.color = Color.NEGRO
        tiempo += 1
        temp.tiempoFinal = tiempo
        DFStree.add(temp.pred?.valor!!,temp)
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
        return Pair(u!!.tiempoInicial, u.tiempoFinal)
    }

    /*
     Indica si hay camino desde el vértice inicial u hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException 
     */ 
    fun hayCamino(u: Int, v: Int) : Boolean {
        for(i in g.listaDeAdyacencia.indices) {
            g.listaDeVertices[i]!!.pred = null
            g.listaDeVertices[i]!!.color = Color.BLANCO
        }
        for (i in g.listaDeAdyacencia.indices){
            if (g.listaDeVertices[i]!!.color == Color.BLANCO){
                dfsVisit(g, g.listaDeVertices[u]!!.valor)
            }
        }
        if( u >= g.listaDeVertices.size || g.listaDeVertices[u] == null|| v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) throw kotlin.RuntimeException("El vértice no se encuentra en el grafo")

        return (g.listaDeVertices[u]!!.tiempoInicial < g.listaDeVertices[v]!!.tiempoInicial && g.listaDeVertices[u]!!.tiempoFinal < g.listaDeVertices[v]!!.tiempoFinal)
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
        return g.numDeLados > 0
    }
    
    // Retorna los lados del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeBosque() : Iterator<Lado>{
    }

    // Retorna true si hay forward edges o false en caso contrario.
    fun hayLadosDeIda(): Boolean {
        var forwardEdges = 0
        for(i in g.listaDeAdyacencia.indices){
            for(k in g.listaDeAdyacencia[i]!!){
                if(g.listaDeVertices[i]!!.tiempoInicial < k.tiempoInicial && g.listaDeVertices[i]!!.tiempoFinal > k.tiempoFinal){
                    forwardEdges += 1
                }
            }
        }
        return forwardEdges > 0
    }
    
    // Retorna los forward edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeIda() : Iterator<Lado> {

    }

    // Retorna true si hay back edges o false en caso contrario.
    fun hayLadosDeVuelta(): Boolean {
        var backEdges = 0
        for(i in g.listaDeAdyacencia.indices){
            for(k in g.listaDeAdyacencia[i]!!){
                if(g.listaDeVertices[i]!!.tiempoInicial > k.tiempoInicial && g.listaDeVertices[i]!!.tiempoFinal < k.tiempoFinal){
                    backEdges += 1
                }
            }
        }
        return backEdges > 0
    }

    }
    
    // Retorna los back edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosDeVuelta() : Iterator<Lado> {

    }

    // Retorna true si hay cross edges o false en caso contrario.
    fun hayLadosCruzados(): Boolean{

    }
    
    // Retorna los cross edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    fun ladosCruzados() : Iterator<Lado> {

    }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() { }
    
}
