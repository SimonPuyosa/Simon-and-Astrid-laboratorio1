package ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException


public class GrafoDirigido : Grafo {
    // Propiedades del grafo dirigido
    var listaDeAdyacencia: Array<LinkedList<Int>?> = arrayOf(LinkedList())       // Arreglo, que puede ser nulo, de elementos que puden ser listas enlazadas o nulos
    var numDeLados: Int = 0
    lateinit var listaDeVertices: Array<Int?>                 // arreglo que contiene todos los vertices sin repeticiones
    var numDeVertices = 0

    /** Contruye un grafo dirigido partiendo de una integral que representaa el numero de vertices
     */
    constructor(numDeVertices: Int) {
        /** Entrada: un entero que determina el numero de vertices que tendra el grafo
         *  Precondicion: numDeVertices > 0
         *  Postcondicion: listaDeAdyacencia.size() == numDeVertices
         *  Tiempo de operacion: O(|V|)
         */

        listaDeAdyacencia = arrayOfNulls(numDeVertices)
    }

    /** Constructor que dado un String de la ubicacion de un archivo, lee el archivo y segun sus datos genera un grafo
     *  Este grafo estara representado por una lista de adyacencia la cual sera un arreglo de |V| elementos en donde
     *  cada casilla es una linked list de tipo entero o nulo, por lo que los vertices seran representados como enteros
     */
    constructor(nombreArchivo: String) {
        /** Entrada: un string de la ubicacion del archivo a buscar
         *  Precondicion: el string debe tener una ubicacion real y valida del archivo
         *  Postcondicion:
         *      numDeVertice = Archivo[0]
         *      numDeLados = Archivo[1]
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
        var vertice1: Int
        var vertice2: Int
        var temp: List<String>
        while (i < 2 + numDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}   // se separa cada linea por espacios

            if (listaDeAdyacencia[temp[0].toInt()] == null){     // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[temp[0].toInt()] = LinkedList<Int>()
            }

            vertice2 = temp[1].toInt()
            vertice1 = temp[0].toInt()
            if (listaDeAdyacencia[vertice1]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[temp[vertice1].toInt()]!!.addFirst(vertice2)       // se agrega el vertice 2, al inicio del arreglo
            if (listaDeVertices[vertice1] == null) {
                listaDeVertices[vertice1] = vertice1             // se agregan los dos vertices a la lista de vertices
            }
            if (listaDeVertices[vertice2] == null) {             // si estos ya se encuentran en la lista de vectores el programa continua y no los agrega
                listaDeVertices[vertice2] = vertice2
            }
            i++
        }
    }

    /** Agrega un lado al digrafo. Si el lado a agregar contiene
     * un vértice que no pertenece al grafo, entonces se lanza una
     * RuntimeException. Si el lado no se encuentra
     * en el grafo se agrega y retorna true, en caso contrario
     * no se agraga al grafo y se retorna false.
     */
    fun agregarArco(a: Arco) : Boolean {
        /** Entrada: un arco a agregar
         *  Salida: Un booleano que indica si el arco ya estaba agregado previamente o no
         */
        val indexVertice1 = listaDeVertices[a.inicio]
        val indexVertice2 = listaDeVertices[a.fin]
        if (indexVertice1 == null || indexVertice2 == null){          // se verifica que los vertices existan
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.inicio]!!.indexOf(a.fin) == -1){   // se verifica que el lado no exista
            this.numDeLados += 1
            listaDeAdyacencia[a.inicio]!!.add(a.fin)
            return true
        }
        return false
    }


    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return this.gradoExterior(v) + this.gradoInterior(v)               // se retorna la suma de los grados interiores y exteriores
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        if (listaDeVertices.indexOf(v) == -1) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        if (listaDeAdyacencia[v] == null) return 0
        return listaDeAdyacencia[v]!!.size                                    // se retorna el grado exterior
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        if (listaDeVertices.indexOf(v) == -1) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")

        var i = 0
        var j: Int
        var grados = 0
        var actual: LinkedList<Int>?

        while (i < numDeVertices){
            if (listaDeAdyacencia[i] != null){
                j = 0
                actual = listaDeAdyacencia[i]
                while(actual != null && j < actual.size){
                    if (actual[j] == v) { grados++; break }
                    j += 1
                }
            }
            i++
        }
        return grados                                    // se retorna el grado exterior
    }


    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        /**  Salida: una integral del numero de lados del grafo
         */
        return numDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        /**  Salida: una integral del numero de vertices del grafo
         */
        return numDeVertices
    }

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

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoDirigido, private val v: Int) : Iterable<Arco>{
        /** Entrada:
         *      El grafo dirigido sobre el que se va a trabajar
         *      una integral la cual se buscaran los vertices adyacentes a esta
         *  Salida: un iterable de arco
         */
        override fun iterator(): Iterator<Arco> = AdyacenIterato(G, v)
    }

    /**
     * Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v.
     * Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<Arco>{
        /** Entrada: una integral la cual se buscaran los vertices adyacentes a esta
         *  Salida: un iterable de los arcos adyacentes a v
         */
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }


    /**
     *  clase que dada una tabla de hash retorna un iterador
     */
    private inner class LadAdyacenIterato(private val G: GrafoDirigido, private val v: Arco) : Iterator<Arco> {
        /** Entrada: un hashtable con el contenido a iterar
         *  Salida: una iterador que retorna el conjunto de pares de claves y valores
         */
        private var temp: Array<LinkedList<Int>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Int>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){
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


    /** Clase interna que sobreescribe el metodo iterator y lo iguala a LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoDirigido, private val l: Arco) : Iterable<Arco>{
        /** Entrada:
         *      El grafo dirigido sobre el que se va a trabajar
         *      un arco el cual se buscara los lados adyacentes a este
         *  Salida: un iterable de arco
         */
        override fun iterator(): Iterator<Arco> = LadAdyacenIterato(G, l)
    }


    /** Retorna los lados adyacentes de un lado l.
     * Se tiene que dos lados son iguales si tiene los mismos extremos.
     * Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
        /** Entrada: un arco el cual se buscara los lados adyacentes a este
         *  Salida: un iterable de los arcos adyacentes al arco l
         */
        // Se revisa si los vertices pertenecen al grafo
        if (listaDeVertices[l.inicio] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.fin] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.inicio]!! // se verifica si el arco existe
        if (temp.indexOf(l.fin) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

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
            while (i < G.numDeVertices){
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

    // Retorna todos los lados del digrafo
    override operator fun iterator() : Iterator<Arco> = LadosIterato(this)
    /** Salida: un iterador de arcos que iterara sobre todos los lados del grafo
     */
     
    // Retorna un String que muestra el contenido del grafo
    override fun toString() : String {
        /** Salida: un string que muestra el contenido del grafo
         */
        val it = iterator()
        var result = "[ "
        while (it.hasNext()){
            result += it.next().toString()
        }
        result += "]"
        return result
     }
}
