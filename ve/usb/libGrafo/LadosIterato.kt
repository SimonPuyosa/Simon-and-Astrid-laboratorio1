package ve.usb.libGrafo

import java.util.LinkedList


/**
 *  clase que dada una tabla de hash retorna un iterador
 */
class LadosIterato(private val G: GrafoDirigido) : Iterator<Arco> {
    /** Entrada: un hashtable con el contenido a iterar
     *  Salida: una iterador que retorna el conjunto de pares de claves y valores
     */
    private var temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
    private var actual: Vertice? = temp[0]!!.first
    private var i = 0

    override fun hasNext(): Boolean {
        print("q paso")
        if (actual != null && actual!!.valor != null) return true
        if (actual == null) i++


        while (i < G.numeDeVertices){
            if (temp[i] == null || temp[i]!!.first == null) {
                i++
                print(i)
                //actual = temp[i]!!.first
            }else if (temp[i] != null && temp[i]!!.first != null) {
                actual = temp[i]!!.first
                return true
            }
        }
        return false

        /*
        while (actual == null && i < G.numeDeVertices){
            if (temp!![i] != null) actual = temp!![i]!!.first
            i++
        }

        if (actual != null && actual!!.valor != null) return true

        return false

         */
    }

    override fun next(): Arco {
        print("Nose")
        val result = Arco(i, actual!!.valor!!)
        actual = actual!!.next
        if (actual != null) println(actual!!.valor)
        return result
    }
}