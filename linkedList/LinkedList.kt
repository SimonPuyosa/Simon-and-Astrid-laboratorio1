package linkedList

class LinkedList {
    var head: HashEntry = HashEntry(null,null)

    /**
     *  metodo  que dado una lista enlazada y un entero que pertenezca a la lista,
     *  elimina el HashEntry en el que se encuentra ese entero
     */
    fun List_Delete(L: LinkedList, k: Int){
        /** Entrada: una lista enlazada y un entero a eliminar que se encuentre en la lista
         */
        val x = List_Search(L, k)
        if (x == null) {  throw KeyNotFoundException("no se encontro el objeto") }
        if (x.prev != null){
            x.prev!!.next = x.next
        } else{
            L.head = x.next!!
        }

        if (x.next != null){
            x.next!!.prev = x.prev
        }
    }

    /**
     *  metodo que dada una lista enlazada y un entero se retorna el HashEntry
     *  si se encuentra en la lista o se retorna null en caso contrario
     */
    fun List_Search(L: LinkedList, k: Int): HashEntry? {
        /** Entrada: una lista enlazada y un entero
         *  Salida: el HashEntry correspondiente si se encuentra en la lista o null en caso contrario
         */
        var x = L.head
        while (x.clave != k){
            if (x.next == null) {return null}
            x = x.next!!
        }
        return x
    }

    /**
     *  metodo que dado una lista enlazada, un entero y un valor, genera un HashEntry y lo inserta en la lista
     */
    fun List_Insert(L: LinkedList, k: Int, v: String){
        /** Entrada: una lista enlazada, un entero y un string
         */
        val x = HashEntry(k, v)
        x.next = L.head
        if (L.head.clave != null) L.head.prev = x
        L.head = x
    }

    /**
     *  metodo que dado una lista enlazada se retorna un string con todos los pares de claves y string
     */
    fun toString(L: LinkedList): String {
        /** Salida: un string con todos los pares de claves y valores
         */
        var e= ""
        var inicio = L.head

        while (inicio.clave != null){
            e += "(${inicio.clave}, ${inicio.valor}), "
            inicio = inicio.next!!
        }
        return e
    }
}
