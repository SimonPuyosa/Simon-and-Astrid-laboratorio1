package libGrafoKt.ve.usb.libGrafo
import libGrafoKt.ve.usb.libGrafo.Vertice
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/** Digrafo el cual tiene como propiedades:
 *      listaDeAdyacencia: un arreglo, que puede ser nulo, de liskedList de vertices
 *      listaDeVertices: arreglo, que puede ser nulo, de vertices
 *      numDeLados: entero que representa el número de lados del grafo
 *      numDeVertices: entero que representa el número de vertices del grafo
 */
public class GrafoDirigidoCosto : Grafo {
    // Propiedades del grafo dirigido
    override var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    override var listaDeVertices: Array<Vertice?> = arrayOf(null)
    override var numDeLados: Int = 0
    override var numDeVertices: Int = 0

    /**Construye un grafo dirigido partiendo de una integral que representa el número de vértices
     */
    constructor(numDeVertices: Int) {
        /** Entrada: un entero que determina el número de vertices que tendra el grafo
         *  Precondicion: numDeVertices > 0
         *  Postcondicion: listaDeAdyacencia.size() == numDeVertices
         *  Tiempo: de operacion: O(|V|)
         */
        listaDeAdyacencia = arrayOfNulls(numDeVertices)
    }

    /** Constructor que dado un String de la ubicación de un archivo, lee el archivo y segun sus datos genera un grafo
     *  Este grafo estará representado por una lista de adyacencia la cual será un arreglo de |V| elementos de donde
     *  cada casilla es una linked list de tipo vértice o nulo
     */
    constructor(nombreArchivo: String) {
        /** Entrada: un string de la ubicación del archivo a buscar
         *  Precondicion: el string debe tener una ubicación real y valida del archivo
         *  Postcondicion:
         *      numDeVertice == Archivo[0]
         *      numDeLados == Archivo[1]
         *      listaDeAdyacencia.sizes == listaDeVertices.size == numDeVertices
         *  Tiempo de operacion: O(|V| + |E|)
         */
        val a = File(nombreArchivo).readLines()                 // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices = a[0].toInt()                            // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numDeVertices)         // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = arrayOfNulls(numDeVertices)           // Se genera la lista de vertices la cual sera una lista enlazada
        numDeLados = a[1].toInt()                               // Se obtiene de la segunda linea el numero de lados

        // Agregamos los lados a la lista de adyacencia
        var i = 2
        var v: Int
        var u: Int
        var temp: List<String>
        var vertice1: Vertice
        var vertice2: Vertice

        while (i < 2 + numDeLados && a[i] != "") {                 // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter { it != "" }   // se separa cada linea por espacios

            u = temp[0].toInt()                                    // valor del vértice de inicio
            v = temp[1].toInt()                                    // valor del vértice de llegada
            vertice1 = Vertice(u)                                  // vértice de inicio
            vertice2 = Vertice(v)                                  // vértice de llegada

            if (listaDeAdyacencia[u] == null) {     // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[u] = LinkedList<Vertice>()
            }

            if (listaDeAdyacencia[u]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[u]!!.addFirst(vertice2)                // Se agrega el vértice 2, al inicio del linked list.
            listaDeAdyacencia[u]!![0].Costo = temp[2].toDouble()     // Se almacena el valor del costo de los lados
            if (listaDeVertices[u] == null) listaDeVertices[u] = vertice1   // Se agregan los dos vértices a la lista de vértices, si estos ya se
            if (listaDeVertices[v] == null) listaDeVertices[v] = vertice2   // encuentran en la lista de vectores el programa continua y no los agrega

            listaDeVertices[u]!!.gradoExterior += 1       // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[v]!!.gradoInterior += 1
            i++
        }
    }

    /** Metodo que agrega al digrafo un Arco dado y retorna un Booleano determinando si el Arco ya estaba agregado o no,
     *  si el Arco posee un vertice que no existe en el grafo, se lanza un RuntimeException
     */
    fun agregarArcoCosto(a: ArcoCosto): Boolean {
        /** Entrada: un arco costo a agregar
         *  Salida: Un booleano que indica si el arco ya estaba agregado previamente o no
         *  Precondición: (ArcoCosto.x in listaDeVertices && ArcoCosto.y in listaDeVertices)
         *  Postcondición: ArcoCosto in listaDeAdyacencia
         *  Tiempo: O(|E|)
         */
        if (listaDeVertices[a.inicio] == null || listaDeVertices[a.fin] == null) {        // se verifica que los vértices existan
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.inicio]!!.indexOf(Vertice(a.fin)) == -1) {                // se verifica que el lado no exista
            this.numDeLados += 1                                                          // si el lado no existe se aumenta el número de lados
            listaDeVertices[a.inicio]!!.gradoExterior += 1                                // se aumenta el grado exterior del vértice de inicio
            listaDeVertices[a.fin]!!.gradoInterior += 1                                   // se aumenta el grado interior del vértice final
            listaDeAdyacencia[a.inicio]!!.addFirst(Vertice(a.fin))                        // se agrega el arco a la lista de adyacencia
            listaDeAdyacencia[a.inicio]!![0].Costo = a.costo                              // y se almacena el costo de los lados
            return true
        }
        return false
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
      */
    override fun grado(v: Int): Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         *  Precondicion: v in listaDeVertices
         *  Postcondicion: grados = gradosInterior + gradosExterior
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return gradoExterior(v) + gradoInterior(v)               // se retorna la suma de los grados interiores y exteriores
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado exterior de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    fun gradoExterior(v: Int): Int {
        /** Entrada: una integral del valor vertice del cual se pide el grado exterior
         *  Salida: una integral del grado exterior del vertice pedido
         *  Precondicion: v in listaDeVertices
         *  Postcondicion: gradoExterior < grado
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoExterior                                // se retorna el grado exterior del vertice
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado interior de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    fun gradoInterior(v: Int): Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado interior
         *  Salida: una integral del grado interior del vertice pedido
         *  Precondicion: v in listaDeVertices
         *  Postcondicion: gradoInterior < grado
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoInterior                                // se retorna el grado interior del vertice
    }

    /** Metodo en el que retorna una integral que representa el numero de lados del grafo
     */
    override fun obtenerNumeroDeLados(): Int {
        /** Salida: una integral del numero de lados del grafo
         *  Postcondicion: numDeLados == |E|
         *  Tiempo: O(1)
         */
        return numDeLados
    }

    /** Metodo en el que retorna una integral que representa el numero de vertices del grafo
     */
    override fun obtenerNumeroDeVertices(): Int {
        /** Salida: una integral del numero de vertices del grafo
         *  Postcondicion: numDeVertices = |V|
         *  Tiempo: O(1)
         */
        return numDeVertices
    }

    /**  clase interna y privada que dado un digrafo y un entero que representa el valor de un vertice del digrafo,
     *   se retorna un iterador de ArcoCosto en los que el integral dado es el valor del vertice adyacente
     */
    private inner class AdyacenIterato(G: GrafoDirigidoCosto, private val v: Int) : Iterator<ArcoCosto> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterador que retorna ArcoCosto pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(1)
         */

        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(v, adyacentes[i].valor, adyacentes[i].Costo)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoDirigidoCosto, private val v: Int) : Iterable<ArcoCosto> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterable de ArcoCosto que retorna un iterador de ArcoCosto pertenecientes a G en la que la primera posicion se encuentra el vertice con valor v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(1)
         */
        override fun iterator(): Iterator<ArcoCosto> = AdyacenIterato(G, v)
    }

    /** Metodo que dado una integral que representa un vertice, retorna un iterable de todos los ArcoCosto en los que
     *  el vertice dado es adyacente. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    override fun adyacentes(v: Int): Iterable<ArcoCosto> {
        /** Entrada: una integral la cual se buscaran los vertices en los que este es adyacente
         *  Salida: un iterable de los arcos en los que v es adyacente
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }


    /**  clase interna y privada que dado un digrafo y un ArcoCosto que representa un lado del digrafo,
     *   se retorna un iterador de ArcosCosto adyacentes al ArcoCosto dado
     */
    private inner class LadAdyacenIterato(private val G: GrafoDirigidoCosto, private val v: ArcoCosto) :
        Iterator<ArcoCosto> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes al arco
         *      v: un ArcoCosto del cual se buscaran los lados adyacentes al arco
         *  Salida: una iterador que retorna ArcosCosto pertenecientes a G en la que la segunda posicion se encuentra v.inicio
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigidoCosto)
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        private var adyacList: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices) {                             // se itera sobre el número de vértices
                if (adyacList[i] != null) {                           // Si la casilla del arreglo listaDeAdyacencia es nula es porque ese vértice no tiene un vértice adyacente
                    j = 0
                    actual = adyacList[i]
                    while (actual != null && j < actual!!.size) {     // Se itera a lo largo de la linkedlist hasta conseguir al vertice v.incio o hasta conseguir un vertice nulo
                        if (actual!![j].valor == v.x) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(i, v.x, actual!![j].Costo)        // se prepara la variable de salida
            i += 1                                                   // una vez encontrado un vertice adyacente se busca otro vertice adyacente
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoDirigidoCosto, private val l: ArcoCosto) :
        Iterable<ArcoCosto> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un ArcoCosto del cual se buscaran los lados adyacentes al ArcoCosto
         *  Salida: una iterable de ArcoCosto que retorna un iterador de ArcoCosto pertenecientes a G en la que la segunda posicion se encuentra v.inicio
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: ArcoCosto in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */
        override fun iterator(): Iterator<ArcoCosto> = LadAdyacenIterato(G, l)
    }

    /** Metodo que dado un arco que representa un lado, retorna un iterable de todos los arcos que son adyacentes
     *  al arco dado. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    fun ladosAdyacentes(l: ArcoCosto): Iterable<ArcoCosto> {
        /** Entrada: un arco el cual se buscara los lados que son adyacentes a este
         *  Salida: un iterable de los arcos adyacentes al arco dado
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */

        // Se revisa si los vértices pertenecen al grafo
        if (listaDeVertices[l.x] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.y] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.x]!!    // Se verifica si el arco existe
        if (temp.indexOf(Vertice(l.y)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**  clase interna y privada que dado un digrafo retorna un iterador de todos los ArcoCosto pertenecientes al digrafo
     */
    private inner class LadosIterato(private val G: GrafoDirigidoCosto) : Iterator<ArcoCosto> {
        /** Entrada: un digrafo en el cual se buscaran todos los ArcosCosto pertenecientes a este
         *  Salida: una iterador que retorna todos los ArcosCosto pertenecientes a G
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: ArcosCosto in GrafoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */

        private val temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices) {                            // se itera sobre el numero de vertices
                if (temp[i] != null) {                               // Si la casilla del arreglo listaDeAdyaciencia es nula es porque ese vertice no tiene un vertice adyacente
                    actual = temp[i]
                    if (actual != null && j < actual!!.size) {       // Se itera a lo largo de la linkedlist hasta conseguir un vertice nulo
                        return true
                    }
                }
                j = 0
                i++
            }
            return false
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(i, actual!![j].valor, actual!![j].Costo)                      // se prepara la variable de salida
            j += 1                                                                               // una vez encontrado un vertice adyacente se busca otro vertice adyacente en el otro linkedlist del arreglo
            return result
        }
    }

    /** Metodo que retorna un iterator de todos los ArcoCosto que pertenecen al digrafo actual
     *  esto lo hace llamando a LadosIterato
     */
    override operator fun iterator(): Iterator<ArcoCosto> = LadosIterato(this)
    /** Salida: un iterador de arcos que iterara sobre todos los lados del grafo
     *  Precondicion: listaDeAdyacencia != arrayOfNulls()
     *  Postcondicion: ArcoCosto in GrafoDirigido
     *  Tiempo: O(|V| + |E|)
     */

    /** Metodo que retorna un String del contenido del grafo, esto lo hace a traves de iterator()
     */
    override fun toString(): String {
        /** Salida: un String del contenido del grafo
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: ArcoCosto in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */
        val it = iterator()
        var result = "[ "
        while (it.hasNext()) {
            result += it.next().toString()
        }
        result += "]"
        return result
    }
}

