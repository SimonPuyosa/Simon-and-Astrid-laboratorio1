package ve.usb.libGrafo
import java.util.*

/**
 *  Clase que dado un grafo dorigido, calcula las componentes
 *  fuertemente conexas de dicho grafo y se colocan en la propiedad
 *  cfc la cual es una LinkedList de LinkedLists de enteros, estos
 *  enteros representan los valores de los vertices y cada subLinkedList
 *  representa un cfcs
 */
public class CFC(val g: GrafoDirigido) {
    val cfc = LinkedList<LinkedList<Int>>()        // se crea la propiedad previamente explicada

    init {
        // Se empieza a calcular los cfcs
        val orden = DFS(g).obtenerOrdTop()          // Se busca la LinkedList del ordenamiento topologico
        val grafInv = digrafoInverso(g)             // Se obtiene el grafo inverso del digrafo g
        val dfsInv = DFS(grafInv, orden.toTypedArray()) // Se aplica dfs al digrafo inverso en el orden topologico previamente obtenido
        cfc.addFirst(LinkedList<Int>())            // Se crea el primer subLinkedList

        // Se tiene que los tree edges de dfsInv son vertices CFC del grafo, tambien se tiene que
        // para que 2 vertices sean la misma CFCS debe haber un lado que los una, por lo que:
        var i = 0
        if  (dfsInv.hayLadosDeBosque()){
            val it = dfsInv.ladosDeBosque()             // guardamos el iterador de los tree edges de dfsInv
            var actual: Lado                            // se crean variables en la que asignaremos los lados de los tree edges
            var anterior: Lado? = null
            if (!it.hasNext() && (g.listaDeVertices.isEmpty() || g.listaDeAdyacencia.isEmpty())) throw RuntimeException("El grafo esta vacio")

            while (it.hasNext()) {                      // se itera sobre los lados del bosque de dfsInv
                actual = it.next()                      // se guarda el lado en actual
                if (anterior == null || anterior.b == actual.a) {   // si es el primer lado a estudiar o este lado viene de una cfcs previamente creada
                    cfc[i].addFirst(actual.a)                       // Se guarda solo el primer vertice
                } else if (anterior.b != actual.a) {                // en caso contrario
                    cfc[i].addFirst(anterior.b)                     // Se guarda el segundo vertice del lado anterior
                    cfc.addLast(LinkedList<Int>())                    // Se crea un nuevo SubLinkedList en la primera posicion
                    i++
                    cfc[i].addFirst(actual.a)                       // Se guarda el primer vertice del lado actual
                }
                anterior = actual                                   // el lado actual se convierte en el anterior
            }
            if (anterior != null ) cfc[i].addFirst(anterior.b)      // se guarda el segundo vertide del lado anterior
        }

        // ahora buscaremos los cfcs de un solo vertice es decir los cfcs causados por un bucle
        for (k in g.listaDeVertices) {                          // iteramos sobre los vertices
            if (obtenerIdentificadorCFC(k.valor) == -1){      // se verifica que el vertice no se encuentra en ningun cfcs
                if (g.listaDeAdyacencia[k.valor] != null && g.listaDeAdyacencia[k.valor]!!.contains(k)) {   // Si el vertice tiene un bucle
                    cfc.addLast(LinkedList<Int>())            // Se crea otro SubLinkedList
                    i++
                    cfc[i].addFirst(k.valor)                // Se agrega el vertice bucle
                }
            }
        }
    }

    /** Metodo que dado dos enteros que representan dos vertices del grafo, retorna un booleano que dictamina
     *  si ambos vertices se encuentran en el mismo cfcs. Si uno de estos dos vertices de entrada no se encuentra,
     *  en el grafo se lanza un RuntimeException
     */
    fun estanEnLaMismaCFC(v: Int, u: Int): Boolean {
        /** Entrada: dos enteros que representan dos vertices del grafo
         *  Salida: un booleano que determina si los dos vertices se encuentran en el mismo cfcs
         *  Precondicion: v in g.listaDeVertices && u in g.listaDeVertices
         *  Postcondicion: obtenerIdentificadorCFC(u) == obtenerIdentificadorCFC(v)
         *  Tiempo: O(|V| + |E|)
         */
        if (v >= g.listaDeVertices.size || u >= g.listaDeVertices.size) {   // se verifica que los vertices existan
            throw RuntimeException("Los vertices no pertenecen al grafo")
        }

        val it = cfc.iterator()             // iterador sobre todos los cfcs
        var temp: LinkedList<Int>
        while (it.hasNext()) {              // Se itera sobre cada LinkedList perteneciente a cfc
            temp = it.next()                // Se guarda la linkedList<Int> en temp
            if (temp.contains(v)) {         // Si v se encuentra en el linked list
                return temp.contains(u)     // Se retorna el booleano que es determinado si u pertenece a v
            }
        }
        return false                        // Si no se encuentra significa que el vertice v no es CFC
    }

    /** Metodo que retorna el numero de cfcs que contiene el grafoDirigido
     */
    fun numeroDeCFC(): Int {
        /** Salida: un entero del numero de cfcs que contiene el grafo
         *  Precondicion: ! g.listaDeVertices.isEmpty() && g.listaDeADyacentes != arrayOf(nulls)
         *  Postcondicion: cfc.size
         *  Tiempo: O(1)
         */
        return cfc.size
    }

    /** Metodo que dado un entero que representa un vertice del grafo, retorna el numero donde
     *  se encuentra la cfcs en la cual el vertice esta ubicado, es decir un numero desde 0
     *  hasta cfc.size - 1. Si el vertice no se encuentra en el grafo se lanza un RuntimeException
     *  Si el vertice no se encuentra en ningun cfcs se retorna -1
     */
    fun obtenerIdentificadorCFC(v: Int): Int {
        /** Entrada: un entero que representan un vertice del grafo
         *  Salida: un entero que determina la ubicacion en la que se encuentra el cfcs donde esta ubicado el entero dado
         *  Precondicion: v in g.listaDeVertices
         *  Postcondicion: -1 <= result < cfc.size
         *  Tiempo: O(|V| + |E|)
         */
        if (v >= g.listaDeVertices.size) {   // se verifica que el vertice exista
            throw RuntimeException("El vertice no pertenece al grafo")
        }

        val it = cfc.iterator()             // iterador sobre todos los cfcs
        var temp: LinkedList<Int>
        var result = 0                      // variable que indica el indice del cfcs que contiene a v
        while (it.hasNext()) {              // Se itera sobre cada LinkedList perteneciente a cfc
            temp = it.next()                // Se guarda la linkedList<Int> en temp
            if (temp.contains(v)) {         // Si v se encuentra en el linked list
                return result               // Se retorna result
            }
            result++
        }
        return -1                           // Si no se encuentra significa que el vertice v no es CFC
    }

    /** Metodo intero que dado un CFC retorna un iterador de MutableSet<Int> en donde cada set representa
     *  un cfcs, este set es un conjunto de enteros representan vertices del grafo las cuales no pueden
     *  estar repetidas en el MutableSet ni en otros cfcs
     */
    inner class ObtenerCFiterato(C: CFC) : Iterator<MutableSet<Int>> {
        /** Entrada: un CFC en el cual se buscaran todos los cfcs que contenga
         *  Salida: una iterador de MutableSet que contenga enteros de los vertices perteneciente a un cfcs
         *  Precondicion: ! C.cfc.isEmpty()
         *  Postcondicion: result in C.cfc
         *  Tiempo: O(1)
         */
        private val it = C.cfc.iterator()

        override fun hasNext(): Boolean {
            return it.hasNext()
        }

        override fun next(): MutableSet<Int> {
            return it.next().toMutableSet()
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase ObtenerCFiterato
     */
    inner class ObtenerCFCmutable(private val C: CFC): Iterable<MutableSet<Int>> {
        /** Entrada: un CFC en el cual se buscaran todos los cfcs que contenga
         *  Salida: una iterador de MutableSet que contenga enteros de los vertices perteneciente a un cfcs
         *  Precondicion: ! C.cfc.isEmpty()
         *  Postcondicion: result in C.cfc
         *  Tiempo: O(1)
         */
        override fun iterator(): Iterator<MutableSet<Int>> = ObtenerCFiterato(C)
    }


    /** Metodo que retorna un iterable de todos los cfcs representados como un conjunto de enteros donde estos
     *  enteros representan vertices los cuales no se pueden repetir en el propio conjunto ni en otros cfcs.
     *  Estos cfcs son retornados en el orden en el que se encuentran en CFC
     */
    fun obtenerCFC() : Iterable<MutableSet<Int>> {
        /** Salida: un iterable de MutableSet que contenga enteros de los vertices perteneciente a un cfcs
         *  Precondicion: ! C.cfc.isEmpty()
         *  Postcondicion: result in C.cfc
         *  Tiempo: O(1)
         */
        return ObtenerCFCmutable(this)
    }

    /** Metodo que retorna el grafo componente de las cfcs que estara representado por un grafo dirigido
     *  en el cual los vertices de dicho grafo seran los cfcs en el orden de obtenerIdentificadorCFC()
     *  a su vez que la lista de adyacencia de dicho grafo contendra los arcos de un CFC a otro
     *  en caso de tenerlos
     */
    fun obtenerGrafoComponente() : GrafoDirigido {
        /** Salida: un grafo dirigido que representa al grafo componente del CFC
         *  Precondicion: ! C.cfc.isEmpty() && cfc.size > 0
         *  Postcondicion: result.listaDeVertices.size > 0
         *  Tiempo: O(|V| + |E|)
         */
        val grafoComponente = GrafoDirigido(cfc.size)               // Se crea el grafo con el tamaño del cfc

        val it = g.iterator()                                       // se crea el iterador del grafo de la clase CFC
        var temp: Arco
        var u: Int
        var v: Int
        
        while (it.hasNext()){                                       // se itera sobre cada arco del grafo g
            temp = it.next()
            if (!this.estanEnLaMismaCFC(temp.a, temp.b)){           // si el arco es de dos vertices que no se encuentran en el mismo cfcs
                u = this.obtenerIdentificadorCFC(temp.a)
                v = this.obtenerIdentificadorCFC(temp.b)
                if (u != -1 && v != -1) grafoComponente.agregarArco(Arco(u, v))     // Se agrega a la lista de adyacencia de grafoComponente
            }
        }
        
        return grafoComponente                                      // Se retorna grafoComponente
    }
}
