package ve.usb.libGrafo

import java.util.*


/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class LadAdyacenIterato(private val G: GrafoDirigido, private val v: Arco) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private var actual: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia!!
    private var temp: Vertice? = null
    private var i = 0

    override fun hasNext(): Boolean {
        while (i < G.numeDeVertices){
            if (actual[i] != null){
                temp = actual[i]!!.first
                while(temp != null && temp!!.valor != null){
                    if (temp!!.valor == v.inicio) return true
                    temp = temp!!.next
                }
            }
            i++
        }
        return false
    }

    override fun next(): Arco {
        val result = Arco(i, v.inicio)
        i += 1
        return result
    }
}
