package ve.usb.libGrafo.linkedList

import ve.usb.libGrafo.Vertice
import javax.management.openmbean.KeyAlreadyExistsException

class ListaEnlazada {
    var head: Vertice = Vertice(null)

    /**
     *  metodo  que dado una lista enlazada y un entero que pertenezca a la lista,
     *  elimina el HashEntry en el que se encuentra ese entero
     */
    fun List_Delete(l: ListaEnlazada, k: Int){
        /** Entrada: una lista enlazada y un entero a eliminar que se encuentre en la lista
         */
        val x = List_Search(l, k)
        if (x == null) {  throw KeyNotFoundException("no se encontro el objeto") }
        if (x.prev != null){
            x.prev!!.next = x.next
        } else{
            l.head = x.next!!
        }

        if (x.next != null){
            x.next!!.prev = x.prev
        }
    }

    /**
     *  metodo que dada una lista enlazada y un entero se retorna el HashEntry
     *  si se encuentra en la lista o se retorna null en caso contrario
     */
    fun List_Search(l: ListaEnlazada, k: Int): Vertice? {
        /** Entrada: una lista enlazada y un entero
         *  Salida: el HashEntry correspondiente si se encuentra en la lista o null en caso contrario
         */
        var x = l.head
        while (x.valor != k){
            if (x.next == null) {return null}
            x = x.next!!
        }
        return x
    }

    /**
     *  metodo que dado una lista enlazada, un entero y un valor, genera un HashEntry y lo inserta en la lista
     */
    fun List_Insert(l: ListaEnlazada, k: Int, PuedeRepetir: Boolean): Boolean {
        /** Entrada: una lista enlazada, un entero y un string
         */
        val y = List_Search(l, k)
        if (y != null) {
            if (!PuedeRepetir){
                throw KeyAlreadyExistsException("el objeto esta repetido")
            } else {
                return false
            }
        }
        val x = Vertice(k)
        x.next = l.head
        if (l.head.valor != null) l.head.prev = x
        l.head = x
        return true
    }

    /**
     *  metodo que dado una lista enlazada se retorna un string con todos los pares de claves y string
     */
    fun toString(l: ListaEnlazada): String {
        /** Salida: un string con todos los pares de claves y valores
         */
        var e= ""
        var inicio = l.head

        while (inicio.valor != null){
            e += "(${inicio.valor}), "
            inicio = inicio.next!!
        }
        return e
    }
}
