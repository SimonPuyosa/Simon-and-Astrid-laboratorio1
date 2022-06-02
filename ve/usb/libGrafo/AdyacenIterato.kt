package ve.usb.libGrafo

/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class AdyacenIterato(G: GrafoDirigido, private val v: Int) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private val adyacentes = G.listaDeAdyacencia[v]!!
    var i = 0

    override fun hasNext(): Boolean {
        if (adyacentes.size <= i) return false
        return true
    }

    override fun next(): Arco {
        val result = Arco(v, adyacentes[i])
        i += 1
        return result
    }
}