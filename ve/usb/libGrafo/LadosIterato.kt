package ve.usb.libGrafo

import ve.usb.libGrafo.linkedList.LinkedList
import ve.usb.libGrafo.linkedList.Vertice


/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class LadosIterato(private val G: GrafoDirigido) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private var temp: Array<LinkedList?>? = G.ListaDeAdyacencia!!
    private var actual: Vertice? = null
    private var i = 0

    override fun hasNext(): Boolean {
        while (actual == null && i < G.numeDeVertices){
            if (temp!![i] != null) actual = temp!![i]!!.head
            i++
        }

        if (actual != null && actual!!.valor != null) return true

        return false
    }

    override fun next(): Arco {
        val result = Arco(i, actual!!.valor!!)
        actual = actual!!.next
        return result
    }
}