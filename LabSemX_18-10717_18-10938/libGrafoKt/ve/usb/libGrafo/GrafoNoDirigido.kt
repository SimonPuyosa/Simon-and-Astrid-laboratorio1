package libGrafoKt.ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/** Grafo no Dirigido el cual tiene como propiedades:
 *      listaDeAdyacencia: un arreglo, que puede ser nulo, de linkedlist de vertices
 *      listaDeVertices: arreglo de enteros
 *      numDeLados: entero que representa el numero de lados del grafo
 *      numDeVertices: entero que representa el numero de vertices del grafo
 */
public class GrafoNoDirigido: Grafo {
    // Propiedades del grafo no dirigido
    override var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    override var listaDeVertices: Array<Vertice>
    override var numDeLados: Int = 0
    override var numDeVertices: Int = 0

    /** Contruye un grafo no dirigido partiendo de un entero que representaa el numero de vertices
     */
    constructor(numeroDeVertices: Int) {
        /** Entrada: un entero que determina el numero de vertices que tendra el grafo no dirigido
         *  Precondicion: numeroDeVertices > 0
         *  Postcondicion: listaDeAdyacencia.size() == numeroDeVertices
         *  Tiempo: de operacion: O(|V|)
         */
        listaDeAdyacencia = arrayOfNulls(numeroDeVertices)
        listaDeVertices = Array(numeroDeVertices) { Vertice(it) }
        numDeVertices = numeroDeVertices
    }

    /** Constructor que dado un String de la ubicacion de un archivo, lee el archivo y segun sus datos genera un grafo
     *  Este grafo estara representado por una lista de adyacencia la cual sera un arreglo de |V| elementos en donde
     *  cada casilla es una linked list de tipo vertice o nulo
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
        val a = File(nombreArchivo).readLines()                   // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices = a[0].toInt()                             // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numDeVertices)          // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = Array(numDeVertices) { Vertice(it) }   // Se genera un arreglo de vertices
        val numerodelados = a[1].toInt()                              // Se obtiene de la segunda linea el numero de lados
        numDeLados = 0

        // Agregamos los lados a la lista de adyacencia
        var i = 2
        var u: Int
        var v: Int
        var temp: List<String>
        while (i < 2 + numerodelados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}      // se separa cada linea por espacios
            u = temp[0].toInt()                                     // valor del vértice v
            v = temp[1].toInt()                                     // valor del vértice u

            if (! agregarArista(Arista(u, v))) throw KeyAlreadyExistsException("el objeto esta repetido")
            i++
        }
        if (numDeLados != numerodelados) throw RuntimeException("hubo un error subiendo la data, el numero de lados es incorrecto")
    }

    /** Metodo que agrega al grafo no dirigido una Arista dado y retorna un Booleano determinando si la Arista ya estaba agregado o no,
     *  si la Arista posee un vertice que no existe en el grafo, se lanza un RuntimeException
     */
    fun agregarArista(a: Arista) : Boolean {
        /** Entrada: una Arista a agregar
         *  Salida: Un booleano que indica si el arco ya estaba agregado previamente o no
         *  Precondicion: (Arista.v in listaDeVertices && Arista.u  in listaDeVertices)
         *  Postcondicion: Arista in listaDeAdyacencia
         *  Tiempo: O(|E|)
         */
        if (a.v >= listaDeVertices.size || a.u >= listaDeVertices.size){  // se verifica que los vertices existan
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.v] == null || listaDeAdyacencia[a.v]!!.indexOf(Vertice(a.u)) == -1){          // se verifica que el lado no exista
            if (listaDeAdyacencia[a.u] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[a.u] = LinkedList<Vertice>()
            }
            if (listaDeAdyacencia[a.v] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[a.v] = LinkedList<Vertice>()
            }
            this.numDeLados += 1                                         // si el lado no existe se aumenta el numero de lados
            listaDeVertices[a.v].gradoExterior += 1                       // se aumenta el grado de los vertices
            listaDeVertices[a.u].gradoExterior += 1
            listaDeAdyacencia[a.v]!!.addFirst(Vertice(a.u))                 // se agrega los lados a la lista de adyacencia
            listaDeAdyacencia[a.u]!!.addFirst(Vertice(a.v))
            return true
        }
        return false
    }

    /** Metodo en el que retorna un entero que representa el numero de lados del grafo
     */
    override fun obtenerNumeroDeLados() : Int {
        /** Salida: un entero del numero de lados del grafo
         *  Postcondicion: numDeLados == |E|
         *  Tiempo: O(1)
         */
        return numDeLados
    }

    /** Metodo en el que retorna un entero que representa el numero de vertices del grafo
     */
    override fun obtenerNumeroDeVertices() : Int {
        /** Salida: un entero del numero de vertices del grafo
         *  Postcondicion: numDeVertices == |V|
         *  Tiempo: O(1)
         */
        return numDeVertices
    }

    /**  Clase interna que dado un grafo y un entero que representa el valor de un vertice del grafo,
     *   se retorna un iterador de lados en los que el entero dado es el valor del vertice adyacente
     */
    inner class AdyacenIterato(G: GrafoNoDirigido, private val v: Int) : Iterator<Arista> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes
         *      v: un entero que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterador que retorna Arista pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        private var it = G.listaDeAdyacencia[v]!!.iterator()

        override fun hasNext(): Boolean {
            return it.hasNext()
        }

        override fun next(): Arista {
            return Arista(v, it.next().valor)
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase AdyacenIterato
     */
    inner class AdyacenIterable(private val G: GrafoNoDirigido, private val v: Int) : Iterable<Arista> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes
         *      v: un entero que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterable de lados que retorna Arista pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        override fun iterator(): Iterator<Arista> = AdyacenIterato(G, v)
    }

    /** Metodo que dado un entero que representa un vertice, retorna un iterable de todos los lados en los que
     *  el vertice dado es adyacente. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<Arista> {
        /** Entrada: un entero la cual se buscaran los vertices en los que este es adyacente
         *  Salida: un iterable de los Arista en los que v es adyacente
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        if (v >= listaDeVertices.size) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /**  Clase interna que dado un grafo y un Arista que representa un lado del grafo,
     *   se retorna un iterador de Arista adyacentes al Arista dado
     */
    inner class LadAdyacenIterato(private val G: GrafoNoDirigido, private val n: Arista) : Iterator<Arista> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes al Arista
         *      v: un Arista del cual se buscaran los lados adyacentes al Arista
         *  Salida: una iterador que retorna Arista pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoNoDirigido)
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        private var adyacList: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var i = 0
        private lateinit var it: MutableIterator<Vertice>

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){                            // se itera sobre el numero de vertices
                if (adyacList[i] != null){                          // Si la casilla del arreglo listaDeAdyaciencia es nula es porque ese vertice no tiene un vertice adyacente
                    it = adyacList[i]!!.iterator()
                    while(it.hasNext()){     // Se itera a lo largo de la linkedlist hasta conseguir al vertice v.incio o hasta conseguir un vertice nulo
                        if (it.next().valor == n.v) return true
                    }
                }
                i++
            }
            return false
        }

        override fun next(): Arista {
            val result = Arista(i, n.v)
            i += 1
            return result
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a la clase LadAdyacenIterato
     */
    inner class LadAdyacenIterable(private val G: GrafoNoDirigido, private val l: Arista) : Iterable<Arista>{
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes al Arista
         *      v: un Arista del cual se buscaran los lados adyacentes al Arista
         *  Salida: una iterable de lados que retorna un iterador de Arista pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoNoDirigido)
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        override fun iterator(): Iterator<Arista> = LadAdyacenIterato(G, l)
    }

    /** Metodo que dado un Arista que representa un lado, retorna un iterable de todos los Arista que son adyacentes
     *  al Arista dado. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
        /** Entrada: un Arista del cual se buscaran los lados adyacentes al Arista
         *  Salida: una iterable de Arista pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoNoDirigido)
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        if (l.v >= listaDeVertices.size) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (l.u >= listaDeVertices.size) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.v]!! // se verifica si el arco existe
        if (temp.indexOf(Vertice(l.u)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**  Clase interna que dado un grafo retorna un iterador de todos los Arista pertenecientes al grafo
     */
    inner class LadosIterato(private val G: GrafoNoDirigido) : Iterator<Arista> {
        /** Entrada: un grafo en el cual se buscaran todos los Arista pertenecientes a este
         *  Salida: una iterador que retorna todos los Arista pertenecientes a G
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: Arista in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        private var i = 0
        private lateinit var it: GrafoNoDirigido.AdyacenIterato
        init {
            while (i < G.numDeVertices){
                if (G.listaDeAdyacencia[i] != null) {
                    it = AdyacenIterato(G, i)
                    break
                }
                i++
            }
            if (i >= G.numDeVertices) throw RuntimeException("El grafo no tiene lados")
        }

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){                            // se itera sobre el numero de vertices
                if (it.hasNext()) return true
                i++
                if (i >= G.numDeVertices || G.listaDeAdyacencia[i] == null) continue
                if (i < G.numDeVertices) it = AdyacenIterato(G, i)
            }
            return false
        }

        override fun next(): Arista {
            return it.next()
        }
    }


    /** Metodo que retorna un iterator de todos los Arista que pertenecen al grafo actual
     *  esto lo hace llamando a LadosIterato
     */
    override operator fun iterator() : Iterator<Arista> = LadosIterato(this)
    /** Salida: un iterador de Arista que iterara sobre todos los lados del grafo
     *  Precondicion: listaDeAdyacencia != arrayOfNulls()
     *  Postcondicion: Arista in GrafoNoDirigido
     *  Tiempo: O(|V| + |E|)
     */

    /** Metodo que dado un entero que representa el valor de un vertice del grafo, retorna el grado de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    override fun grado(v: Int) : Int {
        /** Entrada: un entero del valor del vertice del cual se pide el grado
         *  Salida: un entero del grado del vertice pedido
         *  Precondicion: v in ListaDeVertices
         *  Postcondicion: grados.isInt() == true
         *  Tiempo: O(1)
         */
        if (v >= listaDeVertices.size) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        if (listaDeAdyacencia[v] == null) return 0
        return listaDeVertices[v].gradoExterior                                    // se retorna el grado exterior
    }


    /** Metodo que retorna un String del contenido del grafo, esto lo hace a traves de iterator()
     */
    override fun toString() : String {
        /** Salida: un String del contenido del grafo
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: Arista in GrafoNoDirigido
         *  Tiempo: O(|V| + |E|)
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
