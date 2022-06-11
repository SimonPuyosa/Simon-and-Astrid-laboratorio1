package libGrafoKt.ve.usb.libGrafo

import java.util.*

/*
  Obtiene las componentes fuertementes conexas de un grafo 
  La componentes fuertementes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase, es decir, en el constructor.
*/
public class CFC(val g: GrafoDirigido) {
    val CFCS = LinkedList<LinkedList<Int>>()

    init {
        val orden = DFS(g).obtenerOrdTop()
        val grafInv = digrafoInverso(g)
        val dfsInv = DFS(grafInv, orden.toTypedArray())
        CFCS.addFirst(LinkedList<Int>())

        var i = 0
        val it = dfsInv.ladosDeBosque()
        var actual: Lado
        var anterior: Lado? = null
        if (!it.hasNext() && (g.listaDeVertices.isEmpty() || g.listaDeAdyacencia.isEmpty())) throw RuntimeException("El grafo esta vacio")

        while (it.hasNext()) {
            actual = it.next()
            if (anterior == null || anterior.b == actual.a) {
                CFCS[i].addFirst(actual.a)
            } else if (anterior.b != actual.a) {
                CFCS[i].addFirst(anterior.b)
                CFCS.addLast(LinkedList<Int>())
                i++
                CFCS[i].addFirst(actual.a)
            }
            anterior = actual
        }
        CFCS[i].addFirst(anterior!!.b)
        for (k in g.listaDeVertices) {
            if (obtenerIdentificadorCFC(k!!.valor) == -1){
                if (g.listaDeAdyacencia[k.valor] != null && g.listaDeAdyacencia[k.valor]!!.contains(k)) {
                    CFCS.addLast(LinkedList<Int>())
                    i++
                    CFCS[i].addFirst(k.valor)
                }
            }
        }
    }

    /*
     Retorna true si dos vértices están en la misma CFC y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanEnLaMismaCFC(v: Int, u: Int): Boolean {
        if (v >= g.listaDeVertices.size || u >= g.listaDeVertices.size ||
            g.listaDeVertices[u] == null || g.listaDeVertices[v] == null
        ) {   // se verifica que los vertices existan
            throw RuntimeException("Los vertices no pertenecen al grafo")
        }
        val it = CFCS.iterator()
        var temp: LinkedList<Int>
        while (it.hasNext()) {
            temp = it.next()
            if (temp.contains(v)) {
                return temp.contains(u)
            }
        }
        return false
    }


    // Indica el número de componentes fuertemente conexas del digrafo.
    fun numeroDeCFC(): Int {
        return CFCS.size
    }

    /*
     Retorna el identificador de la componente fuertemente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , numeroDeCFC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerIdentificadorCFC(v: Int): Int {
        if (v >= g.listaDeVertices.size || g.listaDeVertices[v] == null) {   // se verifica que el vertice exista
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        val it = CFCS.iterator()
        var temp: LinkedList<Int>
        var result = 0
        while (it.hasNext()) {
            temp = it.next()
            if (temp.contains(v)) {
                return result
            }
            result++
        }
        return -1
    }

    inner class ObtenerCFiterato(C: CFC) : Iterator<MutableSet<Int>> {
        val it = C.CFCS.iterator()

        override fun hasNext(): Boolean {
            return it.hasNext()
        }

        override fun next(): MutableSet<Int> {
            return it.next().toMutableSet()
        }
    }


    inner class ObtenerCFCmutable(private val C: CFC): Iterable<MutableSet<Int>> {

        override fun iterator(): Iterator<MutableSet<Int>> = ObtenerCFiterato(C)
    }


    /*
     Retorna un objeto iterable, el cual contiene CFCs. Cada CFC esta representada
     como un  conjunto de vértices. El orden de las CFCs en el objeto iterable,
     debe corresponder al que se indica en el método obtenerIdentificadorCFC
     */
    fun obternerCFC() : Iterable<MutableSet<Int>> {
	    return ObtenerCFCmutable(this)
    }

    /*
     Retorna el grafo componente asociado a las componentes fuertemente conexas. 
     El identificador de los vértices del grafo componente está asociado 
     con los orden de la CFC en el objeto iterable que se obtiene del método obtenerCFC. 
     Es decir, si por ejemplo si los vértices 5,6 y 8 de G pertenecen a la
     componentemente fuertemente conexa cero, entonces 
     obtenerIdentificadorCFC(5) = obtenerIdentificadorCFC(6) = obtenerIdentificadorCFC(8) = 0
     y se va a tener que el en grafo componente el vértice 0 
     representa a la CFC que contiene a los vértices 5,6 y 8 de G.
     */
    fun obtenerGrafoComponente() : GrafoNoDirigido {
        return GrafoNoDirigido(CFCS.size)
    }

}
