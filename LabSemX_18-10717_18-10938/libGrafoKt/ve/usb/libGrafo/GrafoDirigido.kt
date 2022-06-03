package ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/** Digrafo el cual tiene como propiedades:
 *      listaDeAdyacencia: un arreglo, que puede ser nulo, de linkedlist de vertices
 *      listaDeVertices: arreglo, que puede ser nulo, de vertices
 *      numDeLados: entero que representa el numero de lados del grafo
 *      numDeVertices: entero que representa el numero de vertices del grafo
 */
public class GrafoDirigido : Grafo {
    // Propiedades del grafo dirigido
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    lateinit var listaDeVertices: Array<Vertice?>
    var numDeLados: Int = 0
    var numDeVertices: Int = 0

    /** Contruye un grafo dirigido partiendo de una integral que representaa el numero de vertices
     */
    constructor(numDeVertices: Int) {
        /** Entrada: un entero que determina el numero de vertices que tendra el grafo
         *  Precondicion: numDeVertices > 0
         *  Postcondicion: listaDeAdyacencia.size() == numDeVertices
         *  Tiempo: de operacion: O(|V|)
         */
        listaDeAdyacencia = arrayOfNulls(numDeVertices)
    }

    /** Constructor que dado un String de la ubicacion de un archivo, lee el archivo y segun sus datos genera un grafo
     *  Este grafo estara representado por una lista de adyacencia la cual sera un arreglo de |V| elementos en donde
     *  cada casilla es una linked list de tipo vertice o nulo
     */
    constructor(nombreArchivo: String) {
        /** Entrada: un string de la ubicacion del archivo a buscar
         *  Precondicion: el string debe tener una ubicacion real y valida del archivo
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
        while (i < 2 + numDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}   // se separa cada linea por espacios

            u = temp[0].toInt()                                 // valor del vertice de inicio
            v = temp[1].toInt()                                 // valor del vertice de llegada
            vertice1 = Vertice(u)                               // vertice de inicio
            vertice2 = Vertice(v)                               // vertice de llegada

            if (listaDeAdyacencia[u] == null){     // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[u] = LinkedList<Vertice>()
            }

            if (listaDeAdyacencia[u]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[u]!!.addFirst(vertice2)                           // Se agrega el vertice 2, al inicio del linked list.
            if (listaDeVertices[u] == null) listaDeVertices[u] = vertice1       // Se agregan los dos vertices a la lista de vertices, si estos ya se
            if (listaDeVertices[v] == null) listaDeVertices[v] = vertice2     // encuentran en la lista de vectores el programa continua y no los agrega

            listaDeVertices[u]!!.gradoExterior += 1       // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[v]!!.gradoInterior += 1
            i++

        }
    }

    /** Metodo que agrega al digrafo un Arco dado y retorna un Booleano determinando si el Arco ya estaba agregado o no,
     *  si el Arco posee un vertice que no existe en el grafo, se lanza un RuntimeException
     */
    fun agregarArco(a: Arco) : Boolean {
        /** Entrada: un arco a agregar
         *  Salida: Un booleano que indica si el arco ya estaba agregado previamente o no
         *  Precondicion: (Arco.inicio in listaDeVertices && Arco.fin  in listaDeVertices)
         *  Postcondicion: Arco in listaDeAdyacencia
         *  Tiempo: O(|E|)
         */
        if (listaDeVertices[a.inicio] == null || listaDeVertices[a.fin] == null){   // se verifica que los vertices existan
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.inicio]!!.indexOf(Vertice(a.fin)) == -1){           // se verifica que el lado no exista
            this.numDeLados += 1                                                    // si el lado no existe se aumenta el numero de lados
            listaDeVertices[a.inicio]!!.gradoExterior += 1                          // se aumenta el grado exterior del vertice de inicio
            listaDeVertices[a.fin]!!.gradoInterior += 1                             // se aumenta el grado interior del vertice de llegada
            listaDeAdyacencia[a.inicio]!!.addFirst(Vertice(a.fin))                  // se agrega el arco a la lista de adyacencia
            return true
        }
        return false
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    override fun grado(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         *  Precondicion: v in ListaDeVertices
         *  Postcondicion: grados == gradosInterior + gradosExterior
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return gradoExterior(v) + gradoInterior(v)               // se retorna la suma de los grados interiores y exteriores
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado exterior de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    fun gradoExterior(v: Int) : Int {
        /** Entrada: una integral del valor vertice del cual se pide el grado exterior
         *  Salida: una integral del grado exterior del vertice pedido
         *  Precondicion: v in ListaDeVertices
         *  Postcondicion: gradoExterior < grado
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoExterior                                    // se retorna el grado exterior del vertice
    }

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado interior de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    fun gradoInterior(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado interior
         *  Salida: una integral del grado interior del vertice pedido
         *  Precondicion: v in ListaDeVertices
         *  Postcondicion: gradoInterior < grado
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoInterior                                    // se retorna el grado interior del vertice
    }

    /** Metodo en el que retorna una integral que representa el numero de lados del grafo
     */
    override fun obtenerNumeroDeLados() : Int {
        /** Salida: una integral del numero de lados del grafo
         *  Postcondicion: numDeLados == |E|
         *  Tiempo: O(1)
         */
        return numDeLados
    }

    /** Metodo en el que retorna una integral que representa el numero de vertices del grafo
     */
    override fun obtenerNumeroDeVertices() : Int {
        /** Salida: una integral del numero de vertices del grafo
         *  Postcondicion: numDeVertices = |V|
         *  Tiempo: O(1)
         */
        return numDeVertices
    }

    /**  clase interna y privada que dado un digrafo y un entero que representa el valor de un vertice del digrafo,
     *   se retorna un iterador de arcos en los que el integral dado es el valor del vertice adyacente
     */
    private inner class AdyacenIterato(G: GrafoDirigido, private val v: Int) : Iterator<Arco> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterador que retorna Arcos pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(1)
         */
        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): Arco {
            val result = Arco(v, adyacentes[i].valor)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoDirigido, private val v: Int) : Iterable<Arco>{
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterable de arcos que retorna un iterador de Arcos pertenecientes a G en la que la primera posicion se encuentra el vertice con valor v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(1)
         */
        override fun iterator(): Iterator<Arco> = AdyacenIterato(G, v)
    }

    /** Metodo que dado una integral que representa un vertice, retorna un iterable de todos los arcos en los que
     *  el vertice dado es adyacente. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<Arco>{
        /** Entrada: una integral la cual se buscaran los vertices en los que este es adyacente
         *  Salida: un iterable de los arcos en los que v es adyacente
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /**  clase interna y privada que dado un digrafo y un Arco que representa un lado del digrafo,
     *   se retorna un iterador de Arcos adyacentes al Arco dado
     */
    private inner class LadAdyacenIterato(private val G: GrafoDirigido, private val v: Arco) : Iterator<Arco> {
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes al arco
         *      v: un arco del cual se buscaran los lados adyacentes al arco
         *  Salida: una iterador que retorna Arcos pertenecientes a G en la que la segunda posicion se encuentra v.inicio
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */
        private var adyacList: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){                            // se itera sobre el numero de vertices
                if (adyacList[i] != null){                          // Si la casilla del arreglo listaDeAdyaciencia es nula es porque ese vertice no tiene un vertice adyacente
                    j = 0
                    actual = adyacList[i]
                    while(actual != null && j < actual!!.size){     // Se itera a lo largo de la linkedlist hasta conseguir al vertice v.incio o hasta conseguir un vertice nulo
                        if (actual!![j].valor == v.inicio) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): Arco {
            val result = Arco(i, v.inicio)                          // se prepara la variable de salida
            i += 1                                                  // una vez encontrado un vertice adyacente se busca otro vertice adyacente
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoDirigido, private val l: Arco) : Iterable<Arco>{
        /** Entrada:
         *      G: un digrafo en el cual se buscaran los vertices adyacentes
         *      v: un arco del cual se buscaran los lados adyacentes al arco
         *  Salida: una iterable de arcos que retorna un iterador de Arcos pertenecientes a G en la que la segunda posicion se encuentra v.inicio
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */
        override fun iterator(): Iterator<Arco> = LadAdyacenIterato(G, l)
    }

    /** Metodo que dado un arco que representa un lado, retorna un iterable de todos los arcos que son adyacentes
     *  al arco dado. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
        /** Entrada: un arco el cual se buscara los lados que son adyacentes a este
         *  Salida: un iterable de los arcos adyacentes al arco dado
         *  Precondicion: (v.inicio in listaDeVectores && v.fin in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */

        // Se revisa si los vertices pertenecen al grafo
        if (listaDeVertices[l.inicio] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.fin] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.inicio]!! // se verifica si el arco existe
        if (temp.indexOf(Vertice(l.fin)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**  clase interna y privada que dado un digrafo retorna un iterador de todos los Arcos pertenecientes al digrafo
     */
    private inner class LadosIterato(private val G: GrafoDirigido) : Iterator<Arco> {
        /** Entrada: un digrafo en el cual se buscaran todos los Arcos pertenecientes a este
         *  Salida: una iterador que retorna todos los Arcos pertenecientes a G
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: Arcos in GrafoDirigido
         *  Tiempo: O(|V| + |E|)
         */
        private val temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){                            // se itera sobre el numero de vertices
                if (temp[i] != null){                               // Si la casilla del arreglo listaDeAdyaciencia es nula es porque ese vertice no tiene un vertice adyacente
                    actual = temp[i]
                    if (actual != null && j < actual!!.size){       // Se itera a lo largo de la linkedlist hasta conseguir un vertice nulo
                        return true
                    }
                }
                j = 0
                i++
            }
            return false
        }

        override fun next(): Arco {
            val result = Arco(i, actual!![j].valor)                       // se prepara la variable de salida
            j += 1                                                        // una vez encontrado un vertice adyacente se busca otro vertice adyacente en el otro linkedlist del arreglo
            return result
        }
    }

    /** Metodo que retorna un iterator de todos los arcos que pertenecen al digrafo actual
     *  esto lo hace llamando a LadosIterato
     */
    override operator fun iterator() : Iterator<Arco> = LadosIterato(this)
    /** Salida: un iterador de arcos que iterara sobre todos los lados del grafo
     *  Precondicion: listaDeAdyacencia != arrayOfNulls()
     *  Postcondicion: Arcos in GrafoDirigido
     *  Tiempo: O(|V| + |E|)
     */

    /** Metodo que retorna un String del contenido del grafo, esto lo hace a traves de iterator()
     */
    override fun toString() : String {
        /** Salida: un String del contenido del grafo
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: Arcos in GrafoDirigido
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
