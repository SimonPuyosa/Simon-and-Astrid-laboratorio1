package ve.usb.libGrafo
import java.util.LinkedList


/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class LadosIterato(private val G: GrafoDirigido) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private val temp: Array<LinkedList<Int>?> = G.listaDeAdyacencia
    private var actual: LinkedList<Int>? = null
    private var i = 0
    private var j: Int = 0

    override fun hasNext(): Boolean {
        while (i < G.numeDeVertices){
            if (temp[i] != null){
                actual = temp[i]
                if (actual != null && j < actual!!.size){
                    return true
                }
            }
            j = 0
            i++
        }
        return false
    }

    override fun next(): Arco {
        val result = Arco(i, actual!![j])
        j += 1
        return result
    }
}