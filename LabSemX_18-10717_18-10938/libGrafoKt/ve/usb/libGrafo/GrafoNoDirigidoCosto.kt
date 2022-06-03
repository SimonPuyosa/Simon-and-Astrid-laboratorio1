package libGrafoKt.ve.usb.libGrafo
import libGrafoKt.ve.usb.libGrafo.Vertice
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/** grafo no dirigido el cual tiene como propiedades:
 *      listaDeAdyacencia: un arreglo, que puede ser nulo, de linkedlist de vertices
 *      listaDeVertices: arreglo, que puede ser nulo, de vertices
 *      numDeLados: entero que representa el numero de lados del grafo
 *      numDeVertices: entero que representa el numero de vertices del grafo
 */
public class GrafoNoDirigidoCosto: Grafo {
    // Propiedades del grafo no dirigido
    override var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    override var listaDeVertices: Array<Vertice?> = arrayOf(null)
    override var numDeLados: Int = 0
    override var numDeVertices: Int = 0

    /** Contruye un grafo no dirigido partiendo de una integral que representa el numero de vertices
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
         *      numDeVertice = Archivo[0]
         *      numDeLados = Archivo[1]
         *      listaDeAdyacencia.sizes == listaDeVertices.size == numDeVertices
         *  Tiempo de operacion: O(|V| + |E|)
         */
        val a = File(nombreArchivo).readLines()                   // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices = a[0].toInt()                             // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numDeVertices)          // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = arrayOfNulls(numDeVertices)            // Se genera la lista de vertices la cual sera una lista enlazada
        numDeLados = a[1].toInt()                              // Se obtiene de la segunda linea el numero de lados

        // Agregamos los lados a la lista de adyacencia
        var i = 2
        var u: Int
        var v: Int
        var temp: List<String>
        var vertice1: Vertice
        var vertice2: Vertice
        while (i < 2 + numDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}      // se separa cada linea por espacios

            u = temp[0].toInt()                                 // valor del vertice 1
            v = temp[1].toInt()                                 // valor del vertice 2
            vertice1 = Vertice(u)                               // vertice 1
            vertice2 = Vertice(v)                               // vertice 2

            if (listaDeAdyacencia[u] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[u] = LinkedList<Vertice>()
            }
            if (listaDeAdyacencia[v] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[v] = LinkedList<Vertice>()
            }

            if (listaDeAdyacencia[u]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[u]!!.addFirst(vertice2)                   // se agrega el vertice 2, al inicio del arreglo
            listaDeAdyacencia[u]!![0].Costo = temp[2].toDouble()        // se le agrega el costo del lado
            if (listaDeAdyacencia[v]!!.indexOf(vertice1) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[v]!!.addFirst(vertice1)                   // lo mismo para el vertice 1
            listaDeAdyacencia[v]!![0].Costo = temp[2].toDouble()

            if (listaDeVertices[u] == null) listaDeVertices[u] = vertice1   // se agregan los dos vertices a la lista de vertices
            if (listaDeVertices[v] == null) listaDeVertices[v] = vertice2   // si estos ya se encuentran en la lista de vectores el programa continua y no los agrega

            listaDeVertices[u]!!.gradoExterior += 1       // se buscan los vertices y se aumentan sus respectivos grados
            listaDeVertices[v]!!.gradoExterior += 1
            i++
        }
    }

    /** Metodo que agrega al grafo un lado dado y retorna un Booleano determinando si el lado ya estaba agregado o no,
     *  si el lado posee un vertice que no existe en el grafo, se lanza un RuntimeException
     */
    fun agregarAristaCosto(a: AristaCosto) : Boolean {
        /** Entrada: un AristaCosto a agregar
         *  Salida: Un booleano que indica si el AristaCosto ya estaba agregado previamente o no
         *  Precondicion: (AristaCosto.x in listaDeVertices && AristaCosto.y  in listaDeVertices)
         *  Postcondicion: AristaCosto in listaDeAdyacencia
         *  Tiempo: O(|E|)
         */
        if (listaDeVertices[a.x] == null || listaDeVertices[a.y] == null){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.x]!!.indexOf(Vertice(a.y)) == -1){      // se verifica que el lado no exista
            this.numDeLados += 1                                     // si el lado no existe se aumenta el numero de lados
            listaDeVertices[a.x]!!.gradoExterior += 1                   // se aumenta el grado del vertice x
            listaDeVertices[a.u]!!.gradoExterior += 1                   // se aumenta el grado del vertice x

            listaDeAdyacencia[a.x]!!.addFirst(Vertice(a.y))             // se agrega el lado a la lista de adyacencia
            listaDeAdyacencia[a.x]!![0].Costo = a.costo                 // se le añade el costo
            listaDeAdyacencia[a.y]!!.addFirst(Vertice(a.x))             // lo mismo para a.x
            listaDeAdyacencia[a.y]!![0].Costo = a.costo
            return true
        }
        return false
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
         *  Postcondicion: numDeVertices == |V|
         *  Tiempo: O(1)
         */
        return numDeVertices
    }

    /**  clase interna y privada que dado un grafo y un entero que representa el valor de un vertice del grafo,
     *   se retorna un iterador de lados en los que el integral dado es el valor del vertice adyacente
     */
    private inner class AdyacenIterato(G: GrafoNoDirigidoCosto, private val v: Int) : Iterator<AristaCosto> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterador que retorna AristaCosto pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): AristaCosto {
            val result = AristaCosto(v, adyacentes[i].valor, adyacentes[i].Costo)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoNoDirigidoCosto, private val v: Int) : Iterable<AristaCosto> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes
         *      v: un integral que representa el valor del vertice del cual se buscaran los lados en los que este es adyacente
         *  Salida: una iterable de lados que retorna AristaCosto pertenecientes a G en la que la primera posicion se encuentra v
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        override fun iterator(): Iterator<AristaCosto> = AdyacenIterato(G, v)
    }

    /** Metodo que dado una integral que representa un vertice, retorna un iterable de todos los lados en los que
     *  el vertice dado es adyacente. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<AristaCosto> {
        /** Entrada: una integral la cual se buscaran los vertices en los que este es adyacente
         *  Salida: un iterable de los AristaCosto en los que v es adyacente
         *  Precondicion: v in listaDeVectores
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /**  clase interna y privada que dado un grafo y un AristaCosto que representa un lado del grafo,
     *   se retorna un iterador de AristaCosto adyacentes al AristaCosto dado
     */
    private inner class LadAdyacenIterato(private val G: GrafoNoDirigidoCosto, private val n: AristaCosto) : Iterator<AristaCosto> {
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes al AristaCosto
         *      v: un AristaCosto del cual se buscaran los lados adyacentes al AristaCosto
         *  Salida: una iterador que retorna AristaCosto pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        private var temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices){
                if (temp[i] != null){
                    j = 0
                    actual = temp[i]
                    while(actual != null && j < actual!!.size){
                        if (actual!![j].valor == n.x) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): AristaCosto {
            val result = AristaCosto(i, n.x, actual!![j].Costo)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoNoDirigidoCosto, private val l: AristaCosto) : Iterable<AristaCosto>{
        /** Entrada:
         *      G: un grafo en el cual se buscaran los vertices adyacentes al AristaCosto
         *      v: un AristaCosto del cual se buscaran los lados adyacentes al AristaCosto
         *  Salida: una iterable de lados que retorna un iterador de AristaCosto pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        override fun iterator(): Iterator<AristaCosto> = LadAdyacenIterato(G, l)
    }

    /** Metodo que dado un AristaCosto que representa un lado, retorna un iterable de todos los AristaCosto que son adyacentes
     *  al AristaCosto dado. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
        /** Entrada: un AristaCosto del cual se buscaran los lados adyacentes al AristaCosto
         *  Salida: una iterable de AristaCosto pertenecientes a G en la que la segunda posicion se encuentra v.x
         *  Precondicion: (v.x in listaDeVectores && v.y in listaDeVectores && v in GrafoDirigido)
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        if (listaDeVertices[l.x] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.y] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.x]!!
        if (temp.indexOf(Vertice(l.y)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**  clase interna y privada que dado un grafo retorna un iterador de todos los AristaCosto pertenecientes al grafo
     */
    class LadosIterato(private val G: GrafoNoDirigidoCosto) : Iterator<AristaCosto> {
        /** Entrada: un grafo en el cual se buscaran todos los AristaCosto pertenecientes a este
         *  Salida: una iterador que retorna todos los AristaCosto pertenecientes a G
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: AristaCosto in GrafoNoDirigidoCosto
         *  Tiempo: O(|V| + |E|)
         */
        private val temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
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

        override fun next(): AristaCosto {
            val result = AristaCosto(i, actual!![j].valor, actual!![j].Costo)
            j += 1
            return result
        }
    }

    /** Metodo que retorna un iterator de todos los AristaCosto que pertenecen al grafo actual
     *  esto lo hace llamando a LadosIterato
     */
     override operator fun iterator() : Iterator<AristaCosto> = LadosIterato(this)
    /** Salida: un iterador de lados que iterara sobre todos los lados del grafo
     *  Precondicion: listaDeAdyacencia != arrayOfNulls()
     *  Postcondicion: Arcos in GrafoDirigido
     *  Tiempo: O(|V| + |E|)
     */

    /** Metodo que dado un integral que representa el valor de un vertice del grafo, retorna el grado de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    override fun grado(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         *  Precondicion: v in ListaDeVertices
         *  Postcondicion: grados.isInt() == true
         *  Tiempo: O(1)
         */
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        if (listaDeAdyacencia[v] == null) return 0
        return listaDeVertices[v]!!.gradoExterior
    }

    /** Metodo que retorna un String del contenido del grafo, esto lo hace a traves de iterator()
     */
    override fun toString() : String {
        /** Salida: un String del contenido del grafo
         *  Precondicion: listaDeAdyacencia != arrayOfNulls()
         *  Postcondicion: AristaCosto in GrafoDirigido
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
