package ve.usb.libGrafo
import ve.usb.libGrafo.linkedList.Vertice

/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class AdyacenIterato(G: GrafoDirigido, private val v: Int) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private var actual: Vertice? = G.ListaDeAdyacencia!![v]!!.head

    override fun hasNext(): Boolean {
        if (actual == null || actual!!.valor == null) {
            return false
        }
        return true
    }

    override fun next(): Arco {
        val result = Arco(v, actual!!.valor!!)
        actual = actual!!.next!!
        return result
    }
}