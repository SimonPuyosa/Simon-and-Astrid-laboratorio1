package ve.usb.libGrafo

import java.util.*


/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class LadAdyacenIterato(private val G: GrafoDirigido, private val v: Arco) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private var temp: Array<LinkedList<Int>?> = G.listaDeAdyacencia
    private var actual: LinkedList<Int>? = null
    private var i = 0
    private var j: Int = 0

    override fun hasNext(): Boolean {
        while (i < G.numeDeVertices){
            if (temp[i] != null){
                j = 0
                actual = temp[i]
                while(actual != null && j < actual!!.size){
                    if (actual!![j] == v.inicio) return true
                    j += 1
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
